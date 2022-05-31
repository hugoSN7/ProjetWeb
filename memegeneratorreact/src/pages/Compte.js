import '../WebContent/css/General.css';
import '../WebContent/css/Compte.css';
import React from "react";
//import {Link, useNavigate, BrowserRouter, Route, Switch} from "react-router-dom";
//import ReactDOM from 'react-dom';
import Login from './Login';
import Generate from './Generate';
import useToken from './useToken';
import { useNavigate } from 'react-router-dom';
import Pictureuser from './Pictureuser';

function ShowMessage(message) {
    alert(message);}

function getToken(){
    const tokenString = localStorage.getItem('token');
    const userToken = JSON.parse(tokenString);
    if (userToken==null){
        return "false";
    }else{
        return userToken;
    }
    };
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
      


function Compte (){
    const {token, setToken } = useToken();
    let navigate = useNavigate();
    ShowMessage(token)

    const delaccount = () =>{
        let user = {
            name : getToken()
        };
        invokePost("removeuser",user,"user supprimé", "pb avec la suppression")
        setToken("false");
        navigate("/home");
    }
    const disconnect = () =>{
        //on m'affiche une error mais cela fonctionn quand meme
        setToken("false");
        navigate("/home");
    }

    if (getToken() == "false") {
        return <Login setToken={setToken} />
    }else{
    return (
    <div>
        <h1>Bienvenue sur votre compte</h1>
        <Generate/>
        <Pictureuser/>
        <button type="click" onClick={disconnect}> disconnect</button>
        <button type="click" onClick={delaccount}> delete my account</button>
    </div>
    );
    }

}
export default Compte;
