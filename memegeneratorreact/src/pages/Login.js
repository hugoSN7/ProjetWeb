import React, { useState, useEffect, Component } from "react";
import {Link} from "react-router-dom";
import '../WebContent/css/General.css';
import '../WebContent/css/Login.css';
import ReactDOM from 'react-dom';

function ShowMessage(message) {
  alert(message);}

async function invokePost(method, data, successMsg, failureMsg) {
  const requestOptions = {
       method: "POST",
       headers: { "Content-Type": "application/json; charset=utf-8" },
       body: JSON.stringify(data)
   };
   const res = await fetch("/MemeGenerator/rest/"+method,requestOptions);
   if (res.ok) ShowMessage(successMsg);
   else ShowMessage(failureMsg);
}

async function loginUser(method,credentials) {
  const res = await fetch("/MemeGenerator/rest/"+method, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(credentials)
  });
  if (res.ok) {
    ShowMessage("on est dans login user2");
    return await res.json();
  } else {
    ShowMessage("requete envoyé mais erreur")
    return null;
  }
 }



export function Login (){

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [token, setToken] = useState("token non modifié");

  const handleSubmit = async (event) => {
    
    event.preventDefault();
    let test={
     pseudo : "tests",
     password : "123",
     email : "reveillerg@gmail.com"};
    invokePost("adduser", test, "ajout du test fait", "ajout du test echoué");

    loginUser("authentification",{username,password}).then(data => setToken(data));
    ShowMessage(token);
  }



  return (
  <><h1>Login</h1>
  <form onSubmit={handleSubmit} >
  <label>
        <p>Username</p>
        <input type="text" onChange={(e) => setUsername(e.target.value)}  />
      </label>
      <label>
        <p>Password</p>
        <input type="password" onChange={(e) => setPassword(e.target.value)} />
      </label>
      <div>
        <button type="submit">Sign in</button>
      </div>
    </form>
    <form>
    <input type="button"  onclick="window.location.href = 'https://www.google.fr';" value="sign up" />
    </form>
    </>
  
  
  
  );
}

export default Login;