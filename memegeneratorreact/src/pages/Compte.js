import '../WebContent/css/General.css';
import '../WebContent/css/Compte.css';
import React from "react";
//import {Link, useNavigate, BrowserRouter, Route, Switch} from "react-router-dom";
//import ReactDOM from 'react-dom';
import Login from './Login';
import Generate from './Generate';
import useToken from './useToken';
import { useNavigate } from 'react-router-dom';

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


function Compte (){
    const {token, setToken } = useToken();
    let navigate = useNavigate();
    ShowMessage(token)

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
        <button type="click" onClick={disconnect}> disconnect</button>
    </div>
    );
    }

}
export default Compte;
