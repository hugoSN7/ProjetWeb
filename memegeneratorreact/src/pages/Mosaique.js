import React from "react";
import { render } from "react-dom";
import InfiniteScroll from "react-infinite-scroll-component";
import { useState, useEffect } from "react";
import UploadPicture from "./UploadPicture";
//import Popup from 'reactjs-popup';
//import 'reactjs-popup/dist/index.css';
import Popup from '../component/Popup';
import useToken from './useToken';
import '../WebContent/css/General.css';
import '../WebContent/css/Home.css';

//pour ne faire qu'une fois un GET request
var init = true;

function getToken(){
    const tokenString = localStorage.getItem('token');
    const userToken = JSON.parse(tokenString);
    if (userToken==null){
        return "false";
    }else{
        return userToken;
    }
    };

function ShowMessage(message) {
    //alert(message);
}

//pop une alerte
function ShowAlert(message) {
    //alert(message);
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

async function invokeGet(method, failureMsg) {
    const res = await fetch("/MemeGenerator/rest/"+method);
    if (res.ok) return await res.json();
    ShowMessage(failureMsg);
    return null;
}

//communiquer avec le serveur jboss avec de la data
async function invokeGetWithData(method, data, failureMsg) {
    //const encodedValue = encodeURIComponent(data.toString());
    //const res = await fetch(`/MemeGenerator/rest/${method}?tag=${encodedValue}&?hashpwd=${}`);
    const res = await fetch("/MemeGenerator/rest/"+method+"?tag="+data);
    if (res.ok) return await res.json();
    ShowMessage(failureMsg);
    return null;
}


export function Mosaic() {


    const [scroller, setMyScroll] = useState([]);
    const [s, setMyS] = useState([]);


    //if (true) {
    //init = false;
    invokeGet("listmeme", "pb with listmeme").then(data => setMyScroll(data));
    //}
    //ShowAlert(list.length);

    //setList(list.concat(list));
    //setScroll(list);

    //
    //
    //ShowAlert(scroll.length);

    //const [oldN, setOldN] = useState(0);
    //const [newN, setNewN] = useState(50);

    const [hasMore, setHasMore] = useState(false);
    const [isOpen, setIsOpen] = useState(false);
    const [name, setL] = useState();
    const [idMeme, setIdMeme] = useState();

    const [comments, setComments] = useState([]);

    //const [items, setItems] = useState(Array.from({ length: 20 }));






    // invokeGet("listmeme", "pb with listmeme")
    //     .then(data => setList(data));


    const fetchMoreData = () => {

        invokeGet("listmeme", "pb with listmeme").then(data => setMyScroll(scroller.concat(data)));

        //invokeGet("listmeme", "pb with listmeme").then(data => setScroll(data));

        //if (list.lenght >= 150) {
        //    setHasMore(false)
        //}
        //setNewN(newN + 50);
    };

    /*
        const PopupExample = (l) => {
          <Popup trigger={<button>Trigger</button>} position="center">
              {close => (
                <div>
                  {l.namePicture}
                  <a className="close" onClick={setIsOpen(false)}>
                    &times;
                  </a>
                </div>
              )}
            </Popup>
        }
        */

    /*
        const PopupExample = () => {
          <Popup trigger={<button>Trigger</button>} position="center">
              {close => (
                <div>
                  {l.namePicture}
                  <a className="close" onClick={setIsOpen(false)}>
                    &times;
                  </a>
                </div>
              )}
            </Popup>
        }
        */

    /*
        const PopupExample = (l) => (
          <Popup trigger={isOpen} position="right center">
            <div>
              {l.namePicture}
              <button class="button" onClick={() => {setIsOpen(false)}}> X </button>
            </div>
          </Popup>
        );
        */

    /* const PopupExample = () => {
          const [open, setOpen] = useState(false);
          const closeModal = () => setOpen(false);
          return (
            <div>
              <button type="button" className="button" onClick={() => setOpen(o => !o)}>
                Controlled Popup
              </button>
              <Popup open={open} closeOnDocumentClick onClose={closeModal}>
                <div className="modal">
                  <a className="close" onClick={closeModal}>
                    &times;
                  </a>
                  Lorem ipsum dolor sit amet, consectetur adipisicing elit. Beatae magni
                  omnis delectus nemo, maxime molestiae dolorem numquam mollitia, voluptate
                  ea, accusamus excepturi deleniti ratione sapiente! Laudantium, aperiam
                  doloribus. Odit, aut.
                </div>
              </Popup>
            </div>
          );
        }; */

    /////*

    const [isLogged, setIsLogged] = useState(false);

    /*
        //pour récupérer le token stocker afin de gérer l'user
        const getToken = () => {
          const tokenString = localStorage.getItem('token');
          const userToken = JSON.parse(tokenString);
          if (userToken==null){
            setIsLogged(false);
              return "false";
          }else{
            setIsLogged(true);
              return userToken;
          }
          };

    //*/
    const [token, setToken] = useState("false");
    //*/


    const onClick = (l1, l2) => {
        setIsOpen(true);

        setL(l1);
        setIdMeme(l2);

        invokeGetWithData("listcomment_picture",  idMeme, "pb with comm pice").then(data => setComments(data));

        const tokenString = localStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        if (userToken =="false"){
           setIsLogged(false);
        }else{
            setIsLogged(true);
        }
    };
    //*/
    const togglePopup = () => {
        setIsOpen(false);
    };


    const [content, setContent] = useState("");

    const postComment = () => {
        invokePost("associate_comment", {token, idMeme, content}, "association comment faite", "pb association comment non faite");
        document.getElementById("content").value = '';
    }

    //*/





    /* var memeList = list.map(function(l){
      return <img src={require('../db/meme/' + l.namePicture.toString())} id={l.namePicture} width="250"/>
    }) */

    /*return <img src={require(`../db/template/${l.namePicture}`)} id={l.namePicture} width="250" onClick={() => YourTemplate(l.namePicture)}/> */

    return (
        <div>
        <hr />
        <InfiniteScroll
        dataLength={scroller.length}
        next={fetchMoreData}
        hasMore={hasMore}
        loader={<h4 id="loading">Loading...</h4>}
        >
        <div align="center">

        {scroller.map((l,index) =>
            (
            <div align="center">
            <img src={require(`../db/meme/${l.namePicture}`)} id={l.namePicture} width="250" onClick={() => onClick(l.namePicture, l.idPicture)}/>
            <h4>{l.namePicture.substring(0,l.namePicture.length - 4)}</h4>

            </div>
        )
        )
        }
        </div>
        </InfiniteScroll>
        {isOpen && <Popup handleClose={togglePopup} content={
            <div align="center">
            <img src={require(`../db/meme/${name}`)} id={name} width="500" />
            <h4>{name}</h4>
            {/*comments.map( (l) =>
                  (<h6>{l.pseudo} : {l.content}</h6>))*/}
            {isLogged &&
                    <input type="text" value={content} id="content" onChange={(e) => setContent(e.target.value)}/>}
            {isLogged &&
                    <button class="button" onClick={postComment}>Post comment</button>}



            </div>}>
            </Popup>}
        <h4 id="memeavai">No more meme available.</h4>

        </div>
    );

}

export default Mosaic;
