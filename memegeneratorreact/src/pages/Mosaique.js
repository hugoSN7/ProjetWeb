import React from "react";
import { render } from "react-dom";
import InfiniteScroll from "react-infinite-scroll-component";
import { useState, useEffect } from "react";
import UploadPicture from "./UploadPicture";

var init=true;

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

function ShowMessage(message) {
    alert(message);
}

export function Mosaic() {

    const [list, setList] = useState(Array.from({ length: 50 }));

    const [oldN, setOldN] = useState(0);
    const [newN, setNewN] = useState(50);

    const [hasMore, setHasMore] = useState(true);

    //const [items, setItems] = useState(Array.from({ length: 20 }));






        // invokeGet("listmemes", "pb with listmemes")
        //     .then(data => setList(data));


        function fetchMoreData() {
            // a fake async api call like which sends
            // 20 more records in 1.5 secs
            setOldN(list.lenght);
            setList(
                list.concat(Array.from({ length: 50 }))
              );

            if (list.lenght >= 150) {
                setHasMore(false)
            }
              //setNewN(newN + 50);
          };

    if (init) {
      init = false;
      invokeGet("listmeme", "pb with listmeme").then(data => setList(data));
    }

    /* var memeList = list.map(function(l){
      return <img src={require('../db/memes/' + l.namePicture.toString())} id={l.namePicture} width="250"/>
    }) */


    return (
      <div>
        <hr />
        <InfiniteScroll
          dataLength={list.length}
          next={fetchMoreData}
          hasMore={ hasMore}
          loader={<h4>Loading...</h4>}
        >
          <div>
            {/* {memeList} */}
            {/* {list.map((i, index) => (<img src={require('../db/memes/meme1.jpg')} width="250"/>))} */}
            <h1>Coucou</h1>
          </div>
        </InfiniteScroll>
      </div>
    );

}

export default Mosaic;
