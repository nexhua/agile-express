import React from "react";

export default class TaskProgressBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      storyPoint: this.props.storyPoint,
      progress: this.props.progress,
    };
  }

  render() {
    return (
      <div class="progress">
        <div
          class="progress-bar"
          role="progressbar"
          aria-valuenow={this.state.progress}
          aria-valuemin="0"
          aria-valuemax={this.state.storyPoint}
        ></div>
      </div>
    );
  }
}
