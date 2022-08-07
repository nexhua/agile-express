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
import NewTaskCard from "./NewTaskCard";
import { toast } from "react-toastify";
import SprintCard from "./SprintCard";
import NewSprintCard from "./NewSprintCard";
import AppModal from "./AppModal";
import ProjectEdit from "./ProjectEdit";
import { hashCodeArr } from "../helpers/GetHashCode";

export default class ProjectDetail extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      project: this.props.project,
      projectUserRoles: [],
      update: this.props.updateProject,
      projectModal: false,
    };

    this.editChild = React.createRef();

    this.deleteTask = this.deleteTask.bind(this);
    this.createTask = this.createTask.bind(this);
    this.getSprintTasks = this.getSprintTasks.bind(this);
    this.getTaskSprint = this.getTaskSprint.bind(this);
    this.createSprint = this.createSprint.bind(this);

    this.toggleProjectModal = this.toggleProjectModal.bind(this);
    this.editProject = this.editProject.bind(this);
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

  toggleProjectModal() {
    this.setState({
      projectModal: !this.state.projectModal,
    });
  }

  async editProject() {
    const newFields = this.editChild.current.getOutput();

    const body = getProjectUpdateInputs(newFields);
    body.projectID = this.state.project.id;

    if (Object.keys(body).length <= 1) {
      toast.warning("Select atleast 1 field to update", {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        draggable: true,
        progress: undefined,
      });
      return;
    }

    const response = await fetch("/api/projects", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    });

    if (response.status === 200) {
      toast.success("Project updated successfully", {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        draggable: true,
        progress: undefined,
      });

      this.state.update();
    } else {
      toast.error("Error during project update", {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        draggable: true,
        progress: undefined,
      });
    }

    this.toggleProjectModal();
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

  async createTask(task) {
    const currentUser = await AccessLevelService.getUsername();
    const body = {
      projectID: this.state.project.id,
      name: task.name,
      description: task.description,
      createdBy: currentUser,
      storyPoint: task.storyPoint,
    };

    const response = await fetch("/api/projects/task", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    });

    if (response.status === 200) {
      toast.success("Task created successfully", {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        draggable: true,
        progress: undefined,
      });

      this.state.update();
    }
  }

  getSprintTasks(sprintID) {
    if (this.state.project.tasks) {
      let tasks = [];

      for (let i = 0; i < this.state.project.tasks.length; i++) {
        let task = this.state.project.tasks[i];

        if (task.sprint === sprintID) {
          tasks.push(task);
        }
      }

      return tasks;
    }
  }

  getTaskSprint(sprintID) {
    if (this.state.project.sprints) {
      return this.state.project.sprints.findIndex((s) => s.id === sprintID);
    }
    return -1;
  }

  async createSprint(body) {
    if (this.state.project.id) {
      body.projectID = this.state.project.id;

      const response = await fetch("/api/projects/sprints", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(body),
      });

      if (response.status === 201) {
        toast.success("Sprint created successfully", {
          position: "top-right",
          autoClose: 2000,
          hideProgressBar: false,
          closeOnClick: true,
          draggable: true,
          progress: undefined,
        });

        this.state.update();
      }
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
    if (this.state.project.tasks) {
      tasks = this.state.project.tasks.map((task, index) => {
        return (
          <TaskCard
            key={task.id + "_" + this.state.projectUserRoles.length}
            index={index}
            task={task}
            projectRoles={this.state.projectUserRoles}
            deleteTask={this.deleteTask}
            getSprint={this.getTaskSprint}
          ></TaskCard>
        );
      });
    }

    let sprints;
    if (this.state.project.sprints) {
      sprints = this.state.project.sprints.map((sprint, index) => {
        return (
          <SprintCard
            key={sprint.id + "_" + this.state.project.sprints.length}
            sprint={sprint}
            count={index}
            getTasks={this.getSprintTasks}
          />
        );
      });
    }

    const createTaskCard = <NewTaskCard createTaskFunc={this.createTask} />;
    const createSprintCard = (
      <NewSprintCard createSprintFunc={this.createSprint} />
    );

    let projectModal;
    if (this.state.projectModal) {
      projectModal = (
        <AppModal
          isOpen={this.state.projectModal}
          toggle={this.toggleProjectModal}
          title="Edit Project"
          cancelString="Cancel"
          successString="Update Project"
          content={
            <ProjectEdit
              ref={this.editChild}
              id="project_edit_modal"
              statusFields={this.state.project.statusFields}
            />
          }
          onSuccess={this.editProject}
        />
      );
    }

    let statusFieldRowComponent;
    if (this.state.project) {
      statusFieldRowComponent = (
        <CardRow
          key={hashCodeArr(this.state.project.statusFields)}
          id={keys[4]}
          title={"Status Fields"}
          component={
            <StatusFieldsRow
              statusFields={this.state.project.statusFields}
              className={["clickable"]}
              onClick={this.toggleProjectModal}
              hasCreate={false}
            />
          }
        />
      );
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
                    <p
                      className="my-2 clickable"
                      onClick={this.toggleProjectModal}
                    >
                      {this.state.project.projectName}
                    </p>
                  }
                />
                <CardRow
                  id={keys[2]}
                  title={"Start Date"}
                  component={
                    <DateRow
                      className={["clickable"]}
                      date={this.state.project.startDate}
                      onClick={this.toggleProjectModal}
                    />
                  }
                />
                <CardRow
                  id={keys[3]}
                  title={"End Date"}
                  component={
                    <DateRow
                      className={["clickable"]}
                      date={this.state.project.endDate}
                      onClick={this.toggleProjectModal}
                    />
                  }
                />
                {statusFieldRowComponent}
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
              {createTaskCard}
            </div>
          </div>
        </div>
        <hr className="text-white mx-3" />
        <div className="mx-3 mt-3 mb-4 d-flex flex-row flex-wrap gap-3">
          {sprints}
          {createSprintCard}
        </div>
        {projectModal}
      </div>
    );
  }
}

function getProjectUpdateInputs(statusFields) {
  const output = {};

  const projectNameCheck = document.getElementById("projectName_check");
  if (projectNameCheck.checked) {
    const projectNameInput = document.getElementById("projectName");

    if (projectNameInput && projectNameInput.value) {
      output.projectName = projectNameInput.value;
    }
  }

  const startDateCheck = document.getElementById("startDate_check");
  if (startDateCheck.checked) {
    const startDateInput = document.getElementById("startDatePicker");

    if (startDateInput) {
      output.startDate = new Date(startDateInput.value);
    }
  }

  const endDateCheck = document.getElementById("endDate_check");
  if (endDateCheck.checked) {
    const endDateInput = document.getElementById("endDatePicker");

    if (endDateInput) {
      output.endDate = new Date(endDateInput.value);
    }
  }

  const statusFieldCheck = document.getElementById("statusField_check");
  if (statusFieldCheck.checked && statusFields.length > 0) {
    output.statusFields = statusFields;
  }
  return output;
}
