import React from "react";
import { useState, useEffect } from "react";
import ShowMessage from "./ShowMessage";

async function invokePostForFile(method, file, tag, successMsg, failureMsg) {
    const formData = new FormData();

    formData.append("name", file.name);
    formData.append("file", file);
    formData.append("tag", tag)

    const requestOptions = {
        method: "POST",
        body: formData
    };
    const res = await fetch("/MemeGenerator/rest/"+method,requestOptions);
    if (res.ok) ShowMessage(successMsg);
    else ShowMessage(failureMsg);
}

const GenerateYourMeme = props => {

    const [fileName, setFileName] = useState();
    const [decision, setDecision] = useState(false);
    const [tag, setTag] = useState([]);

    const handleSubmit = (event) => {
        alert("oui");
    }

    return (
        <>
        <form onSubmit={handleSubmit}>
        Give a name to your meme
        <br/>
        <input type="text" value={fileName} onChange={(e) => setFileName(e.target.value)}/><br/>
        Can we keep your template ?
        <br/>
        <input type="checkbox" id="decision" value={decision} checked={decision} onChange={(e) => setDecision(e.target.value)}/>
        <label for="decision">Yes</label><br/>
        Give it some tags
        <br/>
        <input type="text" value={tag} onChange={(e) => setTag(e.target.value)}/><br/>
        <br/>
        <input type="submit" onClick={props.handleClose} value="Generate"/>
        </form>
        </>
    );
};

export default GenerateYourMeme;
