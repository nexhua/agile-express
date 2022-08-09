import { render } from "@testing-library/react";
import React from "react";
import Field from "./Field";
import TaskBoardCard from "./TaskBoardCard";
import TaskView from "./TaskView";

export default class Board extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      projectID: this.props.projectID,
      tasks: structuredClone(this.props.tasks),
      statusFields: this.props.statusFields,
    };

    this.onDropHandler = this.onDropHandler.bind(this);
    this.changeTaskStatus = this.changeTaskStatus.bind(this);

    if (this.state.tasks) {
      this.state.tasks.map((task, index) => (task.selfIndex = index));
    }
  }

  componentDidMount() {
    if (this.state.tasks) {
      for (var i = 0; i < this.state.tasks.length; i++) {
        const taskDOM = document.getElementById(this.state.tasks[i].id);

        if (!taskDOM) {
          this.changeTaskStatus(this.state.tasks[i].id, 0);
        }
      }
    }
  }

  onDropHandler(task, currentStatus) {
    this.changeTaskStatus(task.id, currentStatus);
  }

  async changeTaskStatus(taskID, newStatus) {
    const body = {
      projectID: this.state.projectID,
      taskID: taskID,
      currentStatus: newStatus,
    };

    const response = await fetch("/api/projects/task/status", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    });
  }

  render() {
    let statusFields;
    if (this.state.statusFields) {
      statusFields = this.state.statusFields.map((field, index) => {
        return (
          <Field
            key={"board_" + index}
            id={"board_" + index}
            index={index}
            className="w-25 app-bg-tertiary mx-4"
            fieldName={field}
            onDropCallback={this.onDropHandler}
          >
            {this.state.tasks
              .filter((t) => t.currentStatus === index)
              .map((task, index) => {
                return (
                  <TaskBoardCard
                    key={task.id}
                    id={task.id}
                    className="w-100"
                    draggable="true"
                  >
                    <TaskView index={task.selfIndex} task={task} />
                  </TaskBoardCard>
                );
              })}
          </Field>
        );
      });
    }

    return (
      <div
        id={this.state.id}
        onDrop={this.drop}
        onDragOver={this.dragOver}
        style={{ minHeight: "800px" }}
        className="app-bg-primary mx-5 rounded-1 d-flex"
      >
        {statusFields}
      </div>
    );
  }
}
