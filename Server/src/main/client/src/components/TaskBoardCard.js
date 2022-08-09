import React from "react";

function TaskBoardCard(props) {
  const dragStart = (e) => {
    const target = e.target;

    e.dataTransfer.setData("taskID", target.id);

    //setTimeout(() => {}, 0);
  };

  const dragOver = (e) => {
    e.stopPropagation();
  };

  return (
    <div
      id={props.id}
      className={props.className}
      draggable={props.draggable}
      onDragStart={dragStart}
      onDragOver={dragOver}
    >
      {props.children}
    </div>
  );
}

export default TaskBoardCard;
