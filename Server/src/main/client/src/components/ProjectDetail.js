import React from "react";
import CardRow from "./CardRow";
import DateRow from "./DateRow";
import StatusFieldsRow from "./StatusFieldsRow";
import UserRow from "./UserRow";
import TasksRow from "./TasksRow";
import SprintsRow from "./SprintsRow";
import AccessLevelService from "../helpers/AccessLevelService";
import ProjectManagerRow from "./ProjectManagerRow";
import TaskCard from "./TaskCard";

export default class ProjectDetail extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      project: this.props.project,
      projectUserRoles: [],
      update: this.props.updateProject,
    };

    this.deleteTask = this.deleteTask.bind(this);
  }

  async componentDidMount() {
    //GET TEAM MEMBERS IN THIS PROJECT AND THEIR USER INFORMATION
    const teamMembers = await AccessLevelService.getTeamMembers(
      this.state.project.id
    );

    this.setState({
      projectUserRoles: [...teamMembers],
    });

    const height = document.getElementById(
      "general_project_info_container"
    ).offsetHeight;
    document.getElementById("project_tasks_container").style.maxHeight =
      height + "px";
  }

  async deleteTask(taskID) {
    const body = {
      projectID: this.state.project.id,
      taskID: taskID,
    };

    const response = await fetch("/api/projects/task", {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    });

    if (response.status === 200) {
      this.state.update();
    }
  }

  render() {
    const keys = Object.keys(this.state.project);

    let teamMemberInfo;
    if (this.state.projectUserRoles.length > 0) {
      teamMemberInfo = (
        <CardRow
          key="projectManager"
          id={"projectManager"}
          title={"Project Manager"}
          component={
            <ProjectManagerRow
              key="projectManagerRow"
              teamMembers={this.state.projectUserRoles}
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

    let tasks;
    if (this.state.projectUserRoles.length > 0) {
      tasks = this.state.project.tasks.map((task, index) => {
        return (
          <TaskCard
            key={task.id}
            index={index}
            task={task}
            projectRoles={this.state.projectUserRoles}
            deleteTask={this.deleteTask}
          ></TaskCard>
        );
      });
    }

    return (
      <div className="card app-bg-primary border-secondary mx-5">
        <div className="row">
          <div className="col-4 pe-0">
            <div className="card-body pt-0 d-flex flex-column px-0 pb-0 text-white overflow-hidden">
              <div id="general_project_info_container" className="px-3">
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
                  component={
                    <SprintsRow sprints={this.state.project.sprints} />
                  }
                />
              </div>
            </div>
          </div>
          <div className="col ps-0 pt-3">
            <div
              id="project_tasks_container"
              className="d-flex flex-wrap gap-3 overflow-auto"
            >
              {tasks}
            </div>
          </div>
        </div>
        <hr className="text-white mx-3" />
        <div className="row">
          <p className="my-5 text-white">sprintler buraya gelecek</p>
        </div>
      </div>
    );
  }
}
