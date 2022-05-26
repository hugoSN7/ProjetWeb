import React from 'react';
import { useState, useEffect, useLocation } from "react";
import ReactDOM from 'react-dom';
import Popup from '../component/Popup';
import GenerateYourMeme from '../component/GenerateYourMeme';

var init = false;

let image;
let imageUrl;

var isYourTemplate = false;

function ShowAlert(message) {
    alert(message);
}

function ShowMessage(message) {
    ReactDOM.render(<p>{message}</p>, document.getElementById("Message"));
}

async function invokePostForFile(method, file, tag, successMsg, failureMsg) {
    const formData = new FormData();

    formData.append("name", file.name);
    formData.append("file", file);
    formData.append("tag", tag);

    const requestOptions = {
        method: "POST",
        body: formData
    };
    const res = await fetch("/MemeGenerator/rest/"+method,requestOptions);
    if (res.ok) ShowMessage(successMsg);
    else ShowMessage(failureMsg);
}

async function invokeGet(method, failureMsg) {
    const res = await fetch("/MemeGenerator/rest/"+method);
    if (res.ok) return await res.json();
    ShowMessage(failureMsg);
    return null;
}

function List() {
    const [list, setList] = useState([]);
    var canvas = document.getElementById("canvas-mm-preview");
    canvas.height = 0;

    if (init) {
        init = false;
        invokeGet("listimage", "pb with listimage").then(data => setList(data));
    }

    var templateList = list.map(function(l){
        return <img src={require('../db/' + l.namePicture.toString())} id={l.namePicture} width="250" onClick={() => YourTemplate(l.namePicture)}/>
    })

    return (
        <>
        <h1> List of Template </h1>
        {templateList}
        </>
    )
}

function YourTemplate(l) {
    CleanWorker();
    var x = document.getElementById(l);
    updateMMPreview(x.src, '');
    document.getElementById("idMemeContent").value = '';
}

function CleanWorker() {
    ReactDOM.render("", document.getElementById("Upload"));
}

function CleanMain() {
    ReactDOM.render("", document.getElementById("mm-setting"));
}

function updateMMPreview(url, text) {
    var canvas = document.getElementById("canvas-mm-preview");
    var ctx = canvas.getContext("2d");
    canvas.width = 600;
    imageUrl = url;

    //Update Background
    const img = new Image();
    img.src = url;
    var ratio = img.naturalWidth / img.naturalHeight;
    var width = canvas.width;
    var height = width / ratio;
    canvas.height = height;
    ctx.drawImage(img, 0, 0, width, height);

    //Prepare Text
    const fontSize = Math.floor(width / 10);
    const yOffset = height / 25;
    ctx.strokeStyle = "black";
    ctx.lineWidth = Math.floor(fontSize / 4);
    ctx.fillStyle = "white";
    ctx.textAlign = "center";
    ctx.lineJoin = "round";
    ctx.font = `${fontSize}px sans serif`;

    //Add text
    ctx.textBaseline = "bottom";
    ctx.strokeText(text, width / 2, height - yOffset);
    ctx.fillText(text, width / 2, height - yOffset);
}

function clearCanvas() {
    var canvas = document.getElementById("canvas-mm-preview");
    var ctx = canvas.getContext("2d");
    canvas.height = 0;
    canvas.width = 0;
}

function UploadPicture() {

    const hiddenFileInput = React.useRef(null);

    const handleClick = event => {
        hiddenFileInput.current.click();
    }

    const [file, setFile] = useState();
    var fileName = "";

    const handleChange = (event) => {
        event.preventDefault();
        const imageDataUrl = URL.createObjectURL(event.target.files[0]);
        imageUrl = imageDataUrl;
        image = new Image();
        image.src = imageDataUrl;
        isYourTemplate = !isYourTemplate;
        image.onload = function() {
            updateMMPreview(image.src, '');
        }
        var files = event.target.files;
        setFile(files[0]);
        CleanWorker();
    }

    const handleChangeTextArea = (event) => {
        updateMMPreview(imageUrl, event.target.value);
    }

    const list = () => {
        init = true;
        ReactDOM.render(<List />, document.getElementById("Upload"));
        //{isOpen && <Popup handleClose={togglePopup} content={<List init={true}/>}/>}
    }

    const [isOpen, setIsOpen] = useState(false);

    const togglePopup = () => {
        setIsOpen(!isOpen);
    }

    const [memeName, setMemeName] = useState();
    const [decision, setDecision] = useState(false);
    const [tag, setTag] = useState([]);

    const handleGenerate = (event) => {
        var canvas = document.getElementById("canvas-mm-preview");
        var url = canvas.toDataURL("image/png");
        let newFile;
        canvas.toBlob((blob) => {
            if (memeName == null) {
                setMemeName("default");
            }
            newFile = new File([blob], memeName + ".jpg", { type: "image/jpeg"})
            ShowMessage(newFile.name)
            invokePostForFile("addimage", newFile, null, "image added", "pb with image");
            document.getElementById("idMemeName").value = '';
            document.getElementById("idTag").value = '';
            document.getElementById("idMemeContent").value = '';
        }, 'image.jpeg');
        setMemeName();
        clearCanvas();
        CleanWorker();
        if (decision) {
            invokePostForFile("addimage", file, tag, "image added", "pb with image");
            setDecision(false);
            setTag();
        }
    }

    return (
        <>
        
        <div id="Message">
        </div>

        <div id="Upload">
        </div>

        <div id="mm-preview">
        <canvas class="mm-canv" width="0" height="0" id="canvas-mm-preview"></canvas>
        <div class="drag-box-text" id="mm-text">
        </div>
        </div>

        <div id="mm-setting">
        <button class="button" onClick={handleClick}> Add your Picture </button>
        <button class="button" onClick={() => list()}> Load Template </button>
        <input type="file" name="image-upload" id="input" onChange={handleChange} style={{display: 'none'}} ref={hiddenFileInput}/>
        <br/>
        <div class="box-edit">
        <textarea class="mm-text" id="idMemeContent" placeholder="Text #1" onChange={handleChangeTextArea} ></textarea>
        </div>
        </div>


        <div id="generate" class="texte">
        Give a name to your meme
        <br/>
        <input type="text" value={memeName} id="idMemeName" onChange={(e) => setMemeName(e.target.value)}/><br/>
        {isYourTemplate &&
            <>
            Can we keep your template ?
            <br/>
            <input type="checkbox" id="decision" value={decision} checked={decision} onChange={(e) => setDecision(e.target.value)}/>
            <label for="decision">Yes</label><br/>
            Give it some tags
            <br/>
            <input type="text" id="idTag" value={tag} onChange={(e) => setTag(e.target.value)}/><br/>
            <br/>
            </>
        }
        <button class="button" onClick={handleGenerate}> Generate </button>
        </div>
        </>
    );
}

export default UploadPicture;
