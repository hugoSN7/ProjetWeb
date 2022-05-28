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


function Compte (){
    const {token, setToken } = useToken();
    let navigate = useNavigate();

    if (token == "false") {
        return <Login setToken={setToken} />
    }

    const disconnect = () =>{
        //on m'affiche une error mais cela fonctionn quand meme
        setToken("false");
        navigate("/home");
    }

    return (
    <div>
        <h1>Compte on a réussi</h1>
        <Generate/>
        <button type="click" onClick={disconnect}> disconnect</button>
    </div>
    
    );

}
export default Compte;
