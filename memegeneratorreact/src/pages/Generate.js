import React, { useState, useEffect, Component } from "react";
import {Link} from "react-router-dom";
import UploadImage from "./UploadImage";
import LoadImage from "./LoadImage";
import UploadPicture from "./UploadPicture";

function Generate() {
    return (
        <div style={{ textAlign: "center" }}>
            <UploadPicture />
        </div>
    )
}
export default Generate;
