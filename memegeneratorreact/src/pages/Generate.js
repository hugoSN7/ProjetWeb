import React, { useState, useEffect, Component } from "react";
import {Link} from "react-router-dom";
import UploadImage from "./UploadImage";
import '/home/ternardin/Documents/2A/ProjetWeb/memegeneratorreact/src/Design.css';

function Generate() {
    return (
        <div style={{ textAlign: "center" }} class="generate">
            <UploadImage />
        </div>
    )
}
export default Generate;
