import React from "react";
import { Progress } from "reactstrap";
import { calculateSpentPoints } from "../helpers/CommentHelper";

export default class TaskView extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      index: this.props.index,
      task: this.props.task,
      spendStoryPoint: 0,
      assignees: this.props.assignees,
    };
  }

  componentDidMount() {
    this.setState({
      spendStoryPoint: calculateSpentPoints(this.state.task.comments),
    });
  }

  render() {
    let usernamesOfAssignees = "Not Assigned";

    if (this.state.assignees.length > 0) {
      usernamesOfAssignees = this.state.assignees.join(", ");
    }

    return (
      <div
        id={this.state.task.id + "_view"}
        className="card app-bg-secondary mb-2"
      >
        <div className="card-body text-white py-0 px-2">
          <div className="card-title pt-2 text-secondary fst-italic fs-6 d-flex justify-content-between align-items-center">
            <div>
              #{this.state.index + 1} {this.state.task.name}
            </div>
          </div>
          <div
            style={{ fontSize: "14px" }}
            className="my-2 mb-1 d-flex justify-content-between"
          >
            <div className="d-inline-block">{this.state.task.description}</div>
          </div>
          <div className="my-2 text-light">{usernamesOfAssignees}</div>
          <div className="row d-flex">
            <div className="col">
              <Progress
                style={{ height: "3px" }}
                className="mt-2"
                color="info"
                value={this.state.spendStoryPoint}
                max={this.state.task.storyPoint}
              />
            </div>
            <p className="col-3 px-0 m-0 d-inline-block">
              {this.state.spendStoryPoint} / {this.state.task.storyPoint}
            </p>
          </div>
        </div>
      </div>
    );
  }
}
