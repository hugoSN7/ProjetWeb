
import '../WebContent/css/imageuser.css';
import React  from 'react';
import { useState, useEffect, useLocation } from "react";
import '../WebContent/css/General.css';
import useToken from './useToken';

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

//communiquer avec le serveur jboss
async function invokeGet(method, failureMsg) {
  const res = await fetch("/MemeGenerator/rest/"+method);
  if (res.ok) return await res.json();
  ShowMessage(failureMsg);
  return null;
}

//communiquer avec le serveur jboss avec de la data
async function invokeGetWithData(method, data, failureMsg) {
  //const encodedValue = encodeURIComponent(data.toString());
  //const res = await fetch(`/MemeGenerator/rest/${method}?tag=${encodedValue}&?hashpwd=${}`);
  const res = await fetch("/MemeGenerator/rest/"+method+"?tag="+data);
  if (res.ok) return await res.json();
  ShowMessage(failureMsg);
  return null;
}

//communiquer avec le serveur jboss avec de la data
async function invokeGetWithDatat(method, data, failureMsg) {
  //const encodedValue = encodeURIComponent(data.toString());
  //const res = await fetch(`/MemeGenerator/rest/${method}?tag=${encodedValue}&?hashpwd=${}`);
  const res = await fetch("/MemeGenerator/rest/"+method+"?token="+data);
  if (res.ok) return await res.json();
  ShowMessage(failureMsg);
  return null;
}

function Imageuser (){
  const [list, setList] = useState([]);
  var canvas = document.getElementById("canvas-mm-preview");
  canvas.height = 0;
  var token = getToken();

  
  invokeGetWithDatat("listuser_meme",token, "pb with listusermeme").then(data => setList(data));
  

  function askTemplateWithTag(mot) {
      invokeGetWithData("listtemplatewithtag", mot.toLowerCase(), "error with template with tag");
  }

  var memelist = list.map(function(l){
      return <img src={require(`../db/template/${l.namePicture}`)} id={l.namePicture} width="250" onClick={() => YourTemplate(l.namePicture)}/>
  })

  return (
      <>
      <h1> List of your meme </h1>
      <input type="search" id="idSearchTag" placeholder="Use a tag for more precision" name="" onChange={(e) => askTemplateWithTag(e.target.value)}></input><br/><br/>
      {memelist}
      </>
  )
}  
export default Imageuser;
  