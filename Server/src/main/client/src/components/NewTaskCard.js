import React from "react";
import { toast } from "react-toastify";

export default class NewTaskCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isActive: false,
      createTaskFunc: this.props.createTaskFunc,
    };

    this.createTask = this.createTask.bind(this);
  }

  createTask() {
    const tasknameInput = document.getElementById("taskname_input");
    const descriptionInput = document.getElementById("description_input");

    if (this.validateTaskInput(tasknameInput, descriptionInput)) {
      const task = {};

      task.name = tasknameInput.value;
      task.description = descriptionInput.value;
      task.storyPoint = 50;

      this.state.createTaskFunc(task);
    }
  }

  validateTaskInput(tasknameInput, descriptionInput) {
    let taskname = "";
    let description = "";

    if (tasknameInput) {
      taskname = tasknameInput.value;

      if (taskname === "") {
        toast.warn("Task name can't be empty", {
          position: "top-right",
          autoClose: 2000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: false,
          draggable: true,
          progress: undefined,
        });
        return false;
      }
    } else {
      return false;
    }

    if (descriptionInput) {
      description = descriptionInput.value;
    }

    return true;
  }

  render() {
    let currentContent;

    if (this.state.isActive) {
      currentContent = (
        <div className="card-body text-muted py-0 px-2 row align-items-center create-section flex-column gap-0">
          <div className="input-group mb-1">
            <input
              id="taskname_input"
              type="text"
              className="form-control bg-dark text-white input-sm"
              placeholder="Taskname"
              aria-label="Taskname"
            />
          </div>
          <div className="input-group mb-1">
            <input
              id="description_input"
              type="text"
              className="form-control bg-dark text-white input-sm"
              placeholder="Description"
              aria-label="Description"
            />
          </div>

          <div
            className="btn-group w-100 p-0 m-0 border-radius-bottom mt-auto"
            role="group"
          >
            <button
              type="button"
              className="btn app-bg-primary muted-gray-hover text-danger"
              onClick={() => this.setState({ isActive: false })}
            >
              Cancel
            </button>
            <button
              type="button"
              className="btn app-bg-primary muted-gray-hover text-success"
              onClick={() => this.createTask()}
            >
              Create
            </button>
          </div>
        </div>
      );
    } else {
      currentContent = (
        <div
          className="card-body text-muted py-0 px-2 row align-items-center create-section"
          onClick={() => this.setState({ isActive: true })}
        >
          <div className="col-5 ps-5">
            <svg
              height={"80px"}
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 448 512"
            >
              <path
                d="M432 256c0 17.69-14.33 32.01-32 32.01H256v144c0 17.69-14.33 31.99-32 31.99s-32-14.3-32-31.99v-144H48c-17.67 0-32-14.32-32-32.01s14.33-31.99 32-31.99H192v-144c0-17.69 14.33-32.01 32-32.01s32 14.32 32 32.01v144h144C417.7 224 432 238.3 432 256z"
                fill="currentColor"
              />
            </svg>
          </div>
          <div className="col-7">
            <p className="text-muted ps-2 pt-3">Create Task</p>
          </div>
        </div>
      );
    }

    return (
      <div>
        <div
          className="card app-bg-secondary mb-2 h-100"
          style={{ width: "17rem" }}
        >
          {currentContent}
        </div>
      </div>
    );
  }
}
