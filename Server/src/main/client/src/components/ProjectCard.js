import React from "react";
import CardRow from "./CardRow";
import DateRow from "./DateRow";
import StatusFieldsRow from "./StatusFieldsRow";
import UserRow from "./UserRow";

export default class ProjectCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      project: this.props.project,
      count: this.props.count,
    };
    console.log(this.props);
  }

  render() {
    const keys = Object.keys(this.state.project)[1];

    return (
      <div className="text-white" style={{ maxWidth: "25rem" }}>
        <div className="card border-secondary app-bg-primary">
          <div className="card-header mt-2 fs-4">
            <p className="text-gray d-inline-block my-0 fst-italic text-muted">
              #{1}
              {"\u00a0\u00a0"}
            </p>
            {this.state.project.projectName}
            <hr />
          </div>
          <div className="card-body pt-0">
            <CardRow
              id={keys[1]}
              title={"Project Name"}
              component={
                <p className="my-2">{this.state.project.projectName}</p>
              }
            />
            <CardRow
              id={keys[2]}
              title={"Start Date"}
              component={<DateRow date={this.state.project.startDate} />}
            />
            <CardRow
              id={keys[3]}
              title={"End Date"}
              component={<DateRow date={this.state.project.endDate} />}
            />
            <CardRow
              id={keys[4]}
              title={"Status Fields"}
              component={
                <StatusFieldsRow
                  statusFields={this.state.project.statusFields}
                />
              }
            />
            <CardRow
              id={keys[5]}
              title={"Team Members"}
              component={
                <UserRow teamMembers={this.state.project.teamMembers} />
              }
            />
          </div>
        </div>
      </div>
    );
  }
}
