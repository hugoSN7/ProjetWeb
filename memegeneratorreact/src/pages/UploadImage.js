import React from 'react';
import { useState, useEffect } from "react";
import ReactDOM from 'react-dom';

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
    if (res.ok) return await res.formData();
    ShowMessage(failureMsg);
    return null;
}

export function UploadImage() {

    const [image, setImage] = useState();
    const [loadImage, setLoadImage] = useState([]);
    const [file, setFile] = useState();
    var fileName = "";

    function handleChange(event) {
        setImage(URL.createObjectURL(event.target.files[0]));
        var files = event.target.files;
        setFile(files[0]);
    }

    function loadAllImage() {
        invokeGetForFile("listimage", "pb with load image").then(data => setImage(URL.createObjectURL(data.get("file"))));
    }

    return (
        <div className="UploadImage">
        <input type="file" name="image-upload" id="input" onChange={handleChange}/>
        <br/>
        <img src={image} width="500"/>
        <br/>
        <input type="text" />
        <button onClick={() => invokePostForFile("addimage", file, "image added", "pb with image")}> Save </button>
        <button onClick={() => loadAllImage()}> Load </button>
        <img src={loadImage[0]} width="500" />
        </div>
    );
}

export default UploadImage;
