import React, { useState } from "react";
import ClickableInfo from "./ClickableInfo";

function Field(props) {
  const [fieldName, setField] = useState(props.fieldName);
  const [index, setIndex] = useState(props.index);

  const drop = (e) => {
    e.preventDefault();

    const taskID = e.dataTransfer.getData("taskID");
    const task = document.getElementById(taskID);

    task.style.display = "block";

    e.target.appendChild(task);

    props.onDropCallback(task, index);
  };

  const dragOver = (e) => {
    e.preventDefault();
  };

  return (
    <div
      id={props.id}
      onDrop={drop}
      onDragOver={dragOver}
      className={props.className}
    >
      <ClickableInfo
        classNames={"text-white m-2 my-3"}
        text={fieldName}
        onChange={setField}
        accessLevel={props.accessLevel ? props.accessLevel : 0}
        requiredAccessLevel={2}
      />
      {props.children}
    </div>
  );
}

export default Field;
