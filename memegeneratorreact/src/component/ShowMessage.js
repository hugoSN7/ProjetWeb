import React from "react";
import ReactDOM from 'react-dom';

function ShowMessage(message) {
    ReactDOM.render(<p>{message}</p>, document.getElementById("Message"));
}

export default ShowMessage;
