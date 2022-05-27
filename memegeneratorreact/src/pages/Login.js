import React, { useState, useEffect, Component } from "react";
import {Link} from "react-router-dom";
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

async function loginUser(username,password) {
  return fetch("/memeGenerator/rest/authentification", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(username,password)
  })
    .then(data => data.json())
 }



function Login (){
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");



  const handleSubmit = async (event) => {

    event.preventDefault();
    let test={};
    test.pseudo="test";
    test.password="123";
    test.email="reveillerg@gmail.com";


    invokePost("adduser", test, "ajout du test fait", "ajout du test echoué")
    var token = loginUser(
      username,
      password
    );
    if(token){
      ShowMessage("OK");
    } else {
      ShowMessage("failureMsg");
    }
  }



  return (
  <>
    <h1 id="login" class="texte">Login</h1>
    <form onSubmit={handleSubmit} >
    <div class="connection">
    <label>
        <p class="texte">Username</p>
        <input type="text" onChange={(e) => setUsername(e.target.value)}  />
    </label>
    <label>
        <p class="texte">Password</p>
        <input type="password" onChange={(e) => setPassword(e.target.value)} />
    </label>

        <button type="submit" id="logbutton" class="button">Submit</button>
    </div>
  </form>
</>



  )
}

function ShowMessage(message) {
  ReactDOM.render(<p>{message}</p>, document.getElementById("Message"));
}

function CleanWorker() {
  ReactDOM.render("", document.getElementById("Worker"));
}

export default Login;
