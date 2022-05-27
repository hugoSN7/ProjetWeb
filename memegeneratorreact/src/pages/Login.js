import React, { useState, useEffect, Component } from "react";
import {Link, useNavigate} from "react-router-dom";
import '../WebContent/css/General.css';
import '../WebContent/css/Login.css';
import ReactDOM from 'react-dom';

function ShowMessage(message) {
  alert(message);}



async function loginUser(method,credentials) {
  const res = await fetch("/MemeGenerator/rest/"+method, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(credentials)
  });
  if (res.ok) {
    return await res.json();
  } else {
    ShowMessage("erreur")
    return null;
  }
 }





export function Login (){

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [token, setToken] = useState("token non modifié");
  let navigate = useNavigate();

  const handleSubmit = async (event) => {
    
    event.preventDefault();
    loginUser("authentification",{username,password}).then(data => setToken(data));
    ShowMessage(token);
  }

  const signup = () =>{
    navigate("/signup");
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
      <div>
        <button type="click" onClick={signup}>sign up</button>
      </div>
    </form>
    
    </>
  
  
  
  );
}

export default Login;