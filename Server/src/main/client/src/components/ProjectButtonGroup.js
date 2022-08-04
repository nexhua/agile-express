import React from "react";

export default class ProjectButtonGroup extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      projectRole: this.props.projectRole,
      deleteProjectFunc: this.props.deleteProjectFunc,
    };
  }

  render() {
    return (
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
            className="btn app-bg-primary text-light muted-gray-hover"
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
  }
}
