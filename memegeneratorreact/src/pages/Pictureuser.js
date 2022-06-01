import React, { Component }  from 'react';
import '../WebContent/css/General.css';
import '../WebContent/css/Pictureuser.css';
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

//pour récupérer le token stocker afin de gérer l'user
function getToken(){
    const tokenString = localStorage.getItem('token');
    const userToken = JSON.parse(tokenString);
    if (userToken==null){
        return "false";
    }else{
        return userToken;
    }
    };

//communiquer avec le serveur jboss avec de la data
async function invokeGetWithData(method, data, failureMsg) {
    //const encodedValue = encodeURIComponent(data.toString());
    //const res = await fetch(`/MemeGenerator/rest/${method}?tag=${encodedValue}&?hashpwd=${}`);
    const res = await fetch("/MemeGenerator/rest/"+method+"?token="+data);
    if (res.ok) return await res.json();
    ShowMessage(failureMsg);
    return null;
}

//clean la partie Upload
function CleanWorker() {
    ReactDOM.render("", document.getElementById("Picture"));
}

function Listmeme(){
    const [list, setList] = useState([]);
    if (init) {
        init = false;
        invokeGetWithData("listuser_meme", getToken(), "pb with listimage").then(data => setList(data));

    }
    var memeList;
    if (list != null) {
        memeList = list.map(function(l){
            return <img src={require(`../db/meme/${l.namePicture}`)} id={l.namePicture} width="250"/>
        })
    }else{memeList="vous n'avez pas de meme"}
    return (
        <>
        <h1> Liste de vos Memes </h1>
        {memeList}
        </>
    )

}

function Pictureuser() {
    
    const listm = () => {
        CleanWorker();
        init = true;
        ReactDOM.render(<Listmeme />, document.getElementById("Picture"));
        //{isOpen && <Popup handleClose={togglePopup} content={<List init={true}/>}/>}
    }


//<img src={require(`../db/template/${l.namePicture}`)} id={l.namePicture} width="250" onClick={() => YourTemplate(l.namePicture)}/>
    //function update(mot, bool) {
    //    setMot(mot);
    //    init = bool;
    //}

    return(
        <> 
        <button class="button" onClick={() => listm()}> Load your meme </button>
        <div id="Picture">
        </div>
        </>
    )

    
}


export default Pictureuser;