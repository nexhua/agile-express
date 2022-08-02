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
    ];

    return (
      <div>
        <BasicTable rows={rows} />
      </div>
    );
  }
}
