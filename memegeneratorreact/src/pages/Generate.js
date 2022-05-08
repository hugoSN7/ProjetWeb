import React, { useState, useEffect, Component } from "react";
import {Link} from "react-router-dom";
import UploadImage from "./UploadImage";

function Generate() {
    return (
        <div style={{ textAlign: "center" }}>
            <UploadImage />
        </div>
    )
}
export default Generate;
