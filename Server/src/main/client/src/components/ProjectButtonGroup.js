import React from "react";

export default class ProjectButtonGroup extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      accessLevel: this.props.accessLevel,
      projectRole: this.props.projectRole,
      deleteProjectFunc: this.props.deleteProjectFunc,
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
              aria-label="Basic example"
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
