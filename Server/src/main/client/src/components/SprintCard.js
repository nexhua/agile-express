import React from "react";

export default class SprintCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      sprint: this.props.sprint,
      isActive: false,
      count: this.props.count,
      getTasks: this.props.getTasks,
      tasks: [],
    };
  }

  componentDidMount() {
    if (this.state.isActive) {
      const card = document.getElementById("sprint_" + this.state.count);
      card.classList.remove("border-0");
      card.classList.add("active-sprint", "border-1");
    }

    const tasks = this.state.getTasks(this.state.sprint.id);
    this.setState({
      tasks: [...tasks],
    });
  }

  render() {
    let status;
    if (this.state.sprint.isClosed === true) {
      status = "Closed";
    } else {
      status = "Not Started";
    }

    if (this.state.isActive) {
      status = "Active";
    }

    return (
      <div>
        <div
          key={"sprint_" + this.state.count}
          id={"sprint_" + this.state.count}
          className="card border-0"
          style={{ width: "20rem" }}
        >
          <div className="card-body app-bg-secondary">
            <h5 className="card-title text-muted fst-italic">
              #{this.state.count + 1} Sprint
            </h5>
            <hr className="text-white" />
            <div className="text-white my-2">{this.state.sprint.goal}</div>
            <div className="text-white mt-3">{status}</div>
            <div className="text-white mt-3">
              Has {this.state.tasks.length} tasks
            </div>
          </div>
        </div>
      </div>
    );
  }
}
