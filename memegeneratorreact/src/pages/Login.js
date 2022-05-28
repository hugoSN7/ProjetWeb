import React, { useState, useEffect, Component } from "react";
import {Link, useNavigate} from "react-router-dom";
import PropTypes from "prop-types";
import '../WebContent/css/General.css';
import '../WebContent/css/Login.css';
import ReactDOM from 'react-dom';
import Compte from "./Compte";

function ShowMessage(message) {
  alert(message);}
  




async function loginUser(method,username,password) {
  const res = await fetch("/MemeGenerator/rest/"+method+"?username="+username+"&password="+password);//, {
  //  method: 'POST',
  //  headers: {
  //    'Content-Type': 'application/json; charset=utf-8'
  //  },
  //  body: JSON.stringify(credentials)
  //})
  //  .then(data => data.json())
  if (res.ok) {
    return await res.json();
  } else {
     ShowMessage("erreur")
    return null;
  }
 }





export default function Login({setToken}){

  const [username, setUsername] = useState();
  const [password, setPassword] = useState();

  let navigate = useNavigate();

  const handleSubmit = async e => {
    
    e.preventDefault();
    const re = await loginUser("authentification",username,password);
    if (re == true){
      setToken(username);
      navigate("/compte")
    
    }
    else {
      ShowMessage("votre mot de passe ou votre identifiant est erroné");
      setToken("false");
  }
  }

  const signup = () =>{
    navigate("/signup");
  }



  return (
  <><h1>Login</h1>
  <div>
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
    </div>
    
    </>
  
  
  
  );
}

Login.propTypes = {setToken: PropTypes.func.isRequired};