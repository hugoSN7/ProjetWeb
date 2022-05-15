import React, { useState, useEffect, Component } from "react";
import {Link} from "react-router-dom";
import UploadImage from "./UploadImage";
import '../WebContent/css/General.css';
import '../WebContent/css/Generate.css';

function Generate() {
    return (
        <div style={{ textAlign: "center" }} class="generate">
            <UploadImage />
        </div>
    )
}
export default Generate;
