import React from "react";
import { Link, NavLink } from "react-router-dom";

export default class ProjectButtonGroup extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      accessLevel: this.props.accessLevel,
      projectRole: this.props.projectRole,
      deleteProjectFunc: this.props.deleteProjectFunc,
      projectID: this.props.projectID,
    };
  }

  render() {
    const allButtons = (
      <div>
        <div
          className="btn-group w-100 p-0 m-0 h-100 mt-5 border-radius-bottom"
          role="group"
          aria-label="Basic example"
        >
          <button
            type="button"
            className="btn app-bg-primary muted-gray-hover text-danger"
            onClick={() => this.state.deleteProjectFunc()}
          >
            Delete
          </button>
          <button
            type="button"
            className="btn app-bg-primary text-secondary muted-gray-hover"
            onClick={() =>
              (window.location.href = `project?pid=${this.state.projectID}`)
            }
          >
            Edit
          </button>
          <button
            type="button"
            className="btn app-bg-primary muted-gray-hover text-primary"
          >
            Board
          </button>
        </div>
      </div>
    );

    if (this.state.accessLevel === 3) {
      return allButtons;
    } else {
      if (this.state.projectRole !== 0) {
        return allButtons;
      } else {
        return (
          <div>
            <div
              className="btn-group w-100 p-0 m-0 h-100 mt-5 border-radius-bottom"
              role="group"
            >
              <button
                type="button"
                className="btn app-bg-primary muted-gray-hover text-primary"
              >
                Board
              </button>
            </div>
          </div>
        );
      }
    }
  }
}
