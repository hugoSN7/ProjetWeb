import React, { useState, useEffect, Component } from "react";
import {Link} from "react-router-dom";
import '../WebContent/css/General.css';
import '../WebContent/css/Login.css';
import ReactDOM from 'react-dom';

var init=false;

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
  const [token, setToken] = Boolean

  const handleSubmit = async (event) => {
    event.preventDefault();
    const token = loginUser(
      username,
      password
    );
    setToken(token);
    if(token){
      CleanWorker();
    } else {
      ShowMessage("failureMsg")
    }
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
        <button type="submit">Submit</button>
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

