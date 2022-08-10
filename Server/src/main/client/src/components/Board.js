import { render } from "@testing-library/react";
import React from "react";
import AccessLevelService from "../helpers/AccessLevelService";
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
      projectUserRoles: [],
    };

    this.onDropHandler = this.onDropHandler.bind(this);
    this.changeTaskStatus = this.changeTaskStatus.bind(this);
    this.checkStatusMismatch = this.checkStatusMismatch.bind(this);
    this.getTaskAssignees = this.getTaskAssignees.bind(this);
    this.initialize = this.initialize.bind(this);

    if (this.state.tasks) {
      this.state.tasks.map((task, index) => (task.selfIndex = index));
    }

    this.checkStatusMismatch();
    this.initialize();
  }

  async initialize() {
    const user = await AccessLevelService.getUser();
    if (this.state.projectID) {
      const teamMembers = await AccessLevelService.getTeamMembers(
        this.state.projectID
      );

      this.setState({
        projectUserRoles: [...teamMembers],
      });
    }

    if (this.state.tasks) {
      if (user.accessLevel >= 1) {
        for (var i = 0; i < this.state.tasks.length; i++) {
          const task = this.state.tasks[i];

          task.shouldBeDraggable = true;
        }
      } else {
        for (var i = 0; i < this.state.tasks.length; i++) {
          const task = this.state.tasks[i];

          const assignedTasksToCurrentUser = task.assignees.filter(
            (assignee) => assignee.userID === user.id
          );

          if (assignedTasksToCurrentUser.length > 0) {
            task.shouldBeDraggable = true;
          } else {
            task.shouldBeDraggable = false;
          }
        }
      }
    }
  }

  getTaskAssignees(task) {
    let members = [];
    for (var i = 0; i < task.assignees.length; i++) {
      const assignee = task.assignees[i];

      const member = this.state.projectUserRoles.find(
        (member) => member.id === assignee.userID
      );

      if (member) {
        members.push(member.username);
      }
    }
    return members;
  }

  checkStatusMismatch() {
    if (this.state.tasks && this.state.statusFields) {
      const maxStatusAllowed = this.state.statusFields.length - 1;

      const statusMismacthTasks = this.state.tasks.filter(
        (task) => task.currentStatus > maxStatusAllowed
      );

      if (statusMismacthTasks.length > 0) {
        for (var i = 0; i < statusMismacthTasks.length; i++) {
          const task = statusMismacthTasks[i];

          task.currentStatus = 0;
          this.changeTaskStatus(task.id, 0);
        }

        this.forceUpdate();
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
            key={"board_" + index + this.state.projectUserRoles.length}
            id={"board_" + index}
            index={index}
            className="w-25 app-bg-tertiary mx-4 mw-300px"
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
                    draggable={task.shouldBeDraggable ? "true" : "false"}
                  >
                    <TaskView
                      index={task.selfIndex}
                      task={task}
                      assignees={this.getTaskAssignees(task)}
                    />
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
        className="app-bg-primary mx-5 rounded-1 d-flex overflow-auto"
      >
        {statusFields}
      </div>
    );
  }
}
