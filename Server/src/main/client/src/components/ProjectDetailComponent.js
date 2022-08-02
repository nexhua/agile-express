import React from "react";
import BasicTable from "./BasicTable";

export default class ProjectDetailComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      project: this.props.project,
    };
  }

  render() {
    const rows = [
      {
        title: "Project Name",
        value: this.state.project.projectName,
      },
      {
        title: "Start Date",
        value: this.state.project.startDate,
      },
      {
        title: "End Date",
        value: this.state.project.endDate,
      },
    ];

    return (
      <div>
        <BasicTable
          key={this.state.project.id}
          style={{ width: "100px" }}
          rows={rows}
        />
      </div>
    );
  }
}
