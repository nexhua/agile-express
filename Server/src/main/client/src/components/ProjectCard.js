import React from "react";
import CardRow from "./CardRow";
import DateRow from "./DateRow";
import SprintsRow from "./SprintsRow";
import StatusFieldsRow from "./StatusFieldsRow";
import TasksRow from "./TasksRow";
import UserRow from "./UserRow";
import NewProjectCard from "./NewProjectCard";
import AccessLevelService from "../helpers/AccessLevelService";
import ProjectButtonGroup from "./ProjectButtonGroup";
import ProjectManagerRow from "./ProjectManagerRow";

export default class ProjectCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      project: this.props.project,
      count: this.props.count,
      isEmpty: this.props.isEmpty,
      createCallback: this.props.createCallback,
      currentUserProjectRole: 0, //Assigned role of a user in a project, can be different from real role
      deleteProjectFunc: this.props.deleteProjectFunc,
      accessLevel: this.props.accessLevel,
      teamMembersWithUserInfo: [],
    };

    this.deleteProjectHandler = this.deleteProjectHandler.bind(this);
  }

  async componentDidMount() {
    //IF THIS CARD HAS A PROJECT
    if (!this.state.isEmpty) {
      //GET CURRENT USER
      const currentUser = await AccessLevelService.getUser();

      //GET TEAM MEMBERS IN THIS PROJECT AND THEIR USER INFORMATION
      const teamMembers = await AccessLevelService.getTeamMembers(
        this.state.project.id
      );

      //IF ANY TEAM MEMBERS IS FOUND CHECK IF CURRENT LOGGED IN USER IS PART OF PROJECT
      if (teamMembers.length > 0) {
        this.setState({
          teamMembersWithUserInfo: [...teamMembers],
        });

        const result = AccessLevelService.getProjectRoleIfCurrentUserIsMember(
          currentUser,
          teamMembers
        );

        //IF CURRENT USER IS PART OF PROJECT CHECK HIS/HER ROLE AND SET STATE
        if (result.isMember) {
          this.setState({
            currentUserProjectRole: result.ordinal,
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

    const keys = Object.keys(this.state.project);

    let teamMemberInfo;
    if (this.state.teamMembersWithUserInfo.length > 0) {
      teamMemberInfo = (
        <CardRow
          key="projectManager"
          id={"projectManager"}
          title={"Project Manager"}
          component={
            <ProjectManagerRow
              key="projectManagerRow"
              teamMembers={this.state.teamMembersWithUserInfo}
            />
          }
        />
      );
    } else {
      teamMemberInfo = (
        <CardRow
          id={"projectManagerEmpty"}
          title={"Project Manager"}
          component={<p className="my-2">No User Assigned</p>}
        />
      );
    }

    return (
      <div
        className="text-white d-flex flex-column"
        style={{ minWidth: "420px", maxWidth: "420px" }}
      >
        <div className="card border-secondary app-bg-primary h-100">
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
              {teamMemberInfo}
              <CardRow
                id={keys[6]}
                title={"Tasks"}
                component={<TasksRow tasks={this.state.project.tasks} />}
              />
              <CardRow
                id={keys[7]}
                title={"Sprints"}
                component={<SprintsRow sprints={this.state.project.sprints} />}
              />
            </div>
            <div className="mt-auto">
              <ProjectButtonGroup
                accessLevel={this.state.accessLevel}
                projectRole={this.state.currentUserProjectRole}
                deleteProjectFunc={this.deleteProjectHandler}
                projectID={this.state.project.id}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}
