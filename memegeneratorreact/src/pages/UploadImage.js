import React from 'react';
import { useState, useEffect } from "react";
import ReactDOM from 'react-dom';

var init = false;

function ShowMessage(message) {
    alert(message);
}

async function invokePostForFile(method, file, successMsg, failureMsg) {
    const formData = new FormData();

    formData.append("name", file.name);
    formData.append("file", file);

    const requestOptions = {
        method: "POST",
        body: formData
    };
    const res = await fetch("/MemeGenerator/rest/"+method,requestOptions);
    if (res.ok) ShowMessage(successMsg);
    else ShowMessage(failureMsg);
}

async function invokeGetForFile(method, failureMsg) {
    const res = await fetch("/MemeGenerator/rest/"+method);
    if (res.ok) return await res.json();
    ShowMessage(failureMsg);
    return null;
}


export function UploadImage() {

    const [image, setImage] = useState();
    const [file, setFile] = useState();
    var fileName = "";

    const [list, setList] = useState([]);

    function handleChange(event) {
        setImage(URL.createObjectURL(event.target.files[0]));
        var files = event.target.files;
        setFile(files[0]);
    }

    function ListPicture() {

        if (init) {
            init = false;
            invokeGetForFile("listimage", "pb with listimage").then(data => setList(data));
            for (var k of list) {
                ShowMessage(k.path);
            }
        }

        var pathList = list.map(function(l){
            return (
                <>
                <li> {l.path} </li>
                </>
            )
        })

        return (
            <>
            <h2> Yo </h2>
            {pathList}
            <h3> test </h3>
            </>
        )
    }

    /* function loadAllImage() {
    invokeGetForFile("listimage", "pb with load image").then(data => setImage(URL.createObjectURL(data.get("file"))));
}*/

    const test = () => {
        init = true;
        ReactDOM.render(<ListPicture />, document.getElementById("Test"));
    }

    return (
        <>
        <div className="UploadImage">
        <input type="file" name="image-upload" id="input" onChange={handleChange}/>
        <br/>
        <img src={image} width="500"/>
        <br/>
        <input type="text" />
        <button onClick={() => invokePostForFile("addimage", file, "image added", "pb with image")}> Save </button>
        <button onClick={() => test()} > Load </button>
        </div>
        <div id="Test">
        </div>
        </>
    );
}

export default UploadImage;
