import React from 'react';
import { useState, useEffect } from "react";
import ReactDOM from 'react-dom';

var init=false;

async function invokePost(method, data, successMsg, failureMsg) {
    const requestOptions = {
        method: "POST",
        headers: { "Content-Type": "application/json; charset=utf-8" },
        body: JSON.stringify(data)
    };
    const res = await fetch("/memeGenerator/rest/"+method,requestOptions);
    if (res.ok) ShowMessage(successMsg);
    else ShowMessage(failureMsg);
}

function ShowMessage(message) {
    alert(message);
}

export function UploadImage() {

    const [image, setImage] = useState();
    const [listImage, setListImage] = useState([]);

    function createUser(event) {
        let person={};
        person.firstName="Cedric";
        person.lastName="Cazanove";
        invokePost("addperson", person, "person added", "pb with addperson");
    }

    function handleChange(event) {
        setImage(URL.createObjectURL(event.target.files[0]));
    }

    return (
        <div className="UploadImage">
        <input type="file" accept="image/*" name="image-upload" id="input" onChange={handleChange}/>
        <br/>
        <img src={image} width="500"/>
        <br/>
        <input type="text" />
        <button onClick={() => createUser()}> Save </button>
        </div>
    );
}

export default UploadImage;
