import React, { useState, useEffect, Component } from "react";
import {Link} from "react-router-dom";

var init=false;

async function invokeGet(method, failureMsg) {

  const res = await fetch("/memeGenerator/rest/"+method);
  if (res.ok) return await res.json();	
  ShowMessage(failureMsg);
  return null;
}  




function Login (){
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();
    const token = await loginUser(
      username,
      password
    );
    setToken(token);
    invokeGet("authentification", address, "address added", "pb with addaddress");
    CleanWorker();
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

