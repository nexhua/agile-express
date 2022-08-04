import React from "react";
import CardRow from "./CardRow";
import DateRow from "./DateRow";
import SprintsRow from "./SprintsRow";
import StatusFieldsRow from "./StatusFieldsRow";
import TasksRow from "./TasksRow";
import UserRow from "./UserRow";
import NewProjectCard from "./NewProjectCard";
import AccessLevelService from "../helpers/AccessLevelService";
import userTypeStringToOrdinal from "../helpers/UserTypesConverter";
import ProjectButtonGroup from "./ProjectButtonGroup";

export default class ProjectCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      project: this.props.project,
      count: this.props.count,
      isEmpty: this.props.isEmpty,
      createCallback: this.props.createCallback,
      currentUserProjectRole: 0,
      deleteProjectFunc: this.props.deleteProjectFunc,
    };

    this.deleteProjectHandler = this.deleteProjectHandler.bind(this);
  }

  async componentDidMount() {
    const currentUser = await AccessLevelService.getUser();

    if (this.state.project) {
      const matches = this.state.project.teamMembers.filter(
        (user) => user.id === currentUser.id
      );

      if (matches[0]) {
        const projectRoleOrdinal = userTypeStringToOrdinal(
          matches[0].projectRole
        );

        if (projectRoleOrdinal !== -1) {
          this.setState({
            currentUserProjectRole: projectRoleOrdinal,
          });
        }
      }
    }
  }

  deleteProjectHandler() {
    this.state.deleteProjectFunc(this.state.project.id);
  }

  render() {
    if (this.state.isEmpty) {
      return <NewProjectCard createCallback={this.state.createCallback} />;
    }

    const keys = Object.keys(this.state.project)[1];

    return (
      <div
        className="text-white d-flex flex-column"
        style={{ minWidth: "420px", maxWidth: "420px" }}
      >
        <div className="card border-radius-bottom border-secondary app-bg-primary h-100">
          <div className="card-header mt-2 fs-4">
            <p className="text-gray d-inline-block my-0 fst-italic text-muted">
              #{this.state.count}
              {"\u00a0\u00a0"}
            </p>
            {this.state.project.projectName}
            <hr />
          </div>
          <div className="card-body pt-0 d-flex flex-column px-0 pb-0">
            <div className="px-3">
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
                component={<UserRow projectID={this.state.project.id} />}
              />
              <CardRow id={keys[6]} title={"Tasks"} component={<TasksRow />} />
              <CardRow
                id={keys[7]}
                title={"Sprints"}
                component={<SprintsRow />}
              />
            </div>
            <div className="mt-auto">
              <ProjectButtonGroup
                projectRole={this.state.currentUserProjectRole}
                deleteProjectFunc={this.deleteProjectHandler}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}
