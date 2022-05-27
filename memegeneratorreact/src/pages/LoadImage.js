import React from 'react';
import { useState, useEffect } from "react";
import ReactDOM from 'react-dom';

function ShowMessage(message) {
    alert(message);
}

async function invokeGetForFile(method, failureMsg) {
    const res = await fetch("/MemeGenerator/rest/"+method);
    if (res.ok) return await res.formData();
    ShowMessage(failureMsg);
    return null;
}


export function LoadImage() {

    const [loadImage, setLoadImage] = useState();
    const [keys, setKeys] = useState([]);
    const [allImg, setAllImg] = useState([]);

    function ListPicture() {
        invokeGetForFile("listimage", "pb with load image").then(data => setLoadImage(data));
        setKeys(loadImage.keys());
        setAllImg(loadImage.values());

        for (var key of loadImage.keys()) {
            ShowMessage(key);
        }

        for (var k of allImg) {
            //setImage(URL.createObjectURL(k));
        }
    }

    /* function loadAllImage() {
        invokeGetForFile("listimage", "pb with load image").then(data => setImage(URL.createObjectURL(data.get("file"))));
    }*/

    return (
        <div className="LoadImage">
        <h1> List of Picture </h1>
        {allImg.map(pic => {
            return <img src={URL.createObjectURL(pic)} width="500"/>
        })}
        </div>
    );
}

export default LoadImage;

