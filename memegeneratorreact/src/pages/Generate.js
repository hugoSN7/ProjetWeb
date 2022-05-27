import React, { useState, useEffect, Component } from "react";
import {Link} from "react-router-dom";
import UploadPicture from "./UploadPicture";

function Generate() {
    return (
        <div style={{ textAlign: "center" }}>
            <UploadPicture />
        </div>
    )
}
export default Generate;
