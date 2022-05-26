import React from 'react';
import { useState, useEffect, useLocation } from "react";
import ReactDOM from 'react-dom';
import Popup from '../component/Popup';
import GenerateYourMeme from '../component/GenerateYourMeme';

//pour ne faire qu'une fois un GET request
var init = false;

let image;
let imageUrl;

//pour afficher ou non la demande de si on peut enregistrer un template utiliser par l'utilisateur
var isYourTemplate = false;

//pop une alerte
function ShowAlert(message) {
    alert(message);
}

//afficher un msg
function ShowMessage(message) {
    ReactDOM.render(<p>{message}</p>, document.getElementById("Message"));
}

//communiquer avec le serveur jboss
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

//communiquer avec le serveur jboss
async function invokeGet(method, failureMsg) {
    const res = await fetch("/MemeGenerator/rest/"+method);
    if (res.ok) return await res.json();
    ShowMessage(failureMsg);
    return null;
}

//appel a la bdd et lister tous les templates
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

//Permet de selectionner le template parmi la liste de tous les templates et update le canvas
function YourTemplate(l) {
    CleanWorker();
    var x = document.getElementById(l);
    updateMMPreview(x.src, '');
    document.getElementById("idMemeContent").value = '';
}

//clean la partie Upload
function CleanWorker() {
    ReactDOM.render("", document.getElementById("Upload"));
}

//clean la partie mm-setting
function CleanMain() {
    ReactDOM.render("", document.getElementById("mm-setting"));
}

//update le canvas
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

//effacer le canvas
function clearCanvas() {
    var canvas = document.getElementById("canvas-mm-preview");
    var ctx = canvas.getContext("2d");
    canvas.height = 0;
    canvas.width = 0;
}

function test() {
    var loc = document.location.pathname;
    var dir = loc.substring(0, loc.lastIndexOf('/'));
    ShowMessage(loc);
}

function UploadPicture() {

    //recuperer la ref de la balise qui permet d'ouvrir les files
    const hiddenFileInput = React.useRef(null);

    //handler pour lorsque le button Add your picture est click on ouvre la recherche de file
    const handleClick = event => {
        hiddenFileInput.current.click();
    }

    //enregistrer le fichier que l'utilisateur charge qlq part
    const [file, setFile] = useState();
    var fileName = "";

    //handler lorsqu'un fichier est upload sur la page
    const handleChange = (event) => {
        event.preventDefault();
        const imageDataUrl = URL.createObjectURL(event.target.files[0]);
        imageUrl = imageDataUrl;
        image = new Image();
        image.src = imageDataUrl;
        isYourTemplate = !isYourTemplate;
        //une fois l'image chargé, on l'envoie sur le canvas
        image.onload = function() {
            updateMMPreview(image.src, '');
        }
        var files = event.target.files;
        setFile(files[0]);
        CleanWorker();
    }

    //handler qui va se trigger des que du texte est entrain ds la zone de texte pour ecrire un texte sur l'image
    const handleChangeTextArea = (event) => {
        updateMMPreview(imageUrl, event.target.value);
    }

    //permet d'appeler la function qui va lister tous les templates
    const list = () => {
        init = true;
        ReactDOM.render(<List />, document.getElementById("Upload"));
        //{isOpen && <Popup handleClose={togglePopup} content={<List init={true}/>}/>}
    }

    //variable utile a la creation d'un meme
    //decision est un bool pour demander si l'on peut enregistrer ou noon le template qu'à upload le user
    const [memeName, setMemeName] = useState();
    const [decision, setDecision] = useState(false);
    const [tag, setTag] = useState([]);

    //Comportement du bouton Generate
    const handleGenerate = (event) => {
        //Recupere le canvas où est visualisé le futur meme
        var canvas = document.getElementById("canvas-mm-preview");
        var url = canvas.toDataURL("image/png");
        let newFile;
        //On en fait un fichier
        canvas.toBlob((blob) => {
            if (memeName == null) {
                //si aucun nom n'a été donné, on en donne un par defaut
                setMemeName("default");
            }
            newFile = new File([blob], memeName + ".jpg", { type: "image/jpeg"})
            ShowMessage(newFile.name)
            //on envoie à la bdd le meme crée
            invokePostForFile("addimage", newFile, null, "image added", "pb with image");
            //on nettoie les champs
            document.getElementById("idMemeName").value = '';
            document.getElementById("idTag").value = '';
            document.getElementById("idMemeContent").value = '';
        }, 'image.jpeg');
        //on nettoie les variables
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

        {/*Tout ce qui permet de visualiser le meme*/}
        <div id="mm-preview">
        <canvas class="mm-canv" width="0" height="0" id="canvas-mm-preview"></canvas>
        <div class="drag-box-text" id="mm-text">
        </div>
        </div>

        {/*Tout ce qui gere la partie pour le contenu du meme */}
        <div id="mm-setting">
        <button onClick={handleClick}> Add your Picture </button>
        <button onClick={() => list()}> Load Template </button>
        <input type="file" name="image-upload" id="input" onChange={handleChange} style={{display: 'none'}} ref={hiddenFileInput}/>
        <br/>
        <div class="box-edit">
        <textarea class="mm-text" id="idMemeContent" placeholder="Text #1" onChange={handleChangeTextArea} ></textarea>
        </div>
        </div>

        {/*Tout ce qui gere la partie pour generer le meme */}
        <div id="generate">
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
        <button onClick={handleGenerate}> Generate </button>
        </div>

        <div id="test">
        <button onClick={() => test("Test")}> Test </button>
        </div>
        </>
    );
}

export default UploadPicture;
