import '../WebContent/css/General.css';
import '../WebContent/css/Signup.css';
import React, { useState, useEffect, Component } from "react";
import {Link, useNavigate} from "react-router-dom";
import ReactDOM from 'react-dom';

function ShowMessage(message) {
  //alert(message);}
}

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

export function Signup (){

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [emails, setEmail] = useState("");
  let navigate = useNavigate();


  const handleSubmit = async (event) => {

    event.preventDefault();
    let user={
     pseudo : username,
     password : password,
     email : emails
    };
    invokePost("adduser", user, "ajout de l'user fait", "ajout du test echoué");
    navigate("/compte");
  }
  return (
  <><h1 id="login" class="texte">Sign Up</h1>
  <p class="connection">
  <form onSubmit={handleSubmit} >
  <label>
        <p id="username">Username</p>
        <input id="setusername" type="text" onChange={(e) => setUsername(e.target.value)}  />
      </label>
      <label>
        <p id="password">Password</p>
        <input id="setpassword" type="password" onChange={(e) => setPassword(e.target.value)} />
      </label>
      <label>
        <p id="email">email</p>
        <input id="setemail" type="text" onChange={(e) => setEmail(e.target.value)}  />
      </label>
      <div>
        <button id="signup" class="button" type="submit">Sign up</button>
      </div>
    </form>
    </p>
    </>
  );
}
export default Signup;

