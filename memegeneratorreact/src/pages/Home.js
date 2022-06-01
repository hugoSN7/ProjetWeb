import '../WebContent/css/General.css';
import '../WebContent/css/Home.css';
import Mosaic from './Mosaique';
import React, { Component }  from 'react';


const Home = () => {
  return (
    <div>
    <h1 id="home" class="texte">Home</h1>
    <Mosaic/>
    </div>
  )
};

export default Home;

