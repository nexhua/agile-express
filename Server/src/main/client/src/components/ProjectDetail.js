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
import { hashCodeArr, hashCodeStr, hashTask } from "../helpers/GetHashCode";
import UserEdit from "./UserEdit";
import ProjectManagerEdit from "./ProjectManagerEdit";
import userTypeStringToOrdinal from "../helpers/UserTypesConverter";
import { SidePane } from "react-side-pane";
import TaskEdit from "./TaskEdit";
import commentTypeToOrdinal from "../helpers/CommentTypeConverter";
import {
  addAssigneeToTask,
  addCommentToTask,
  assignToSprint,
  changeTask,
  deleteAssigneeFromTask,
  fetchTask,
} from "../helpers/TaskRequestHelper";

export default class ProjectDetail extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      project: this.props.project,
      projectUserRoles: [],
      update: this.props.updateProject,
      projectModal: false,
      userModal: false,
      projectManagerModal: false,
      sidePaneOpen: false,
    };

    this.editChild = React.createRef();
    this.userChild = React.createRef();
    this.ProjectManagerChild = React.createRef();

    this.deleteTask = this.deleteTask.bind(this);
    this.createTask = this.createTask.bind(this);

    this.getSprintTasks = this.getSprintTasks.bind(this);

    this.getTaskSprint = this.getTaskSprint.bind(this);
    this.createSprint = this.createSprint.bind(this);
    this.deleteSprint = this.deleteSprint.bind(this);
    this.activateSprint = this.activateSprint.bind(this);

    this.toggleProjectModal = this.toggleProjectModal.bind(this);
    this.toggleUserModal = this.toggleUserModal.bind(this);
    this.toggleProjectManagerModal = this.toggleProjectManagerModal.bind(this);

    this.editProject = this.editProject.bind(this);
    this.editUsers = this.editUsers.bind(this);
    this.editProjectManager = this.editProjectManager.bind(this);

    this.toggleSidePane = this.toggleSidePane.bind(this);

    this.onSave = this.onSave.bind(this);
    this.updateTaskCard = this.updateTaskCard.bind(this);
  }

  updateTaskCard(newTask) {
    const index = this.state.project.tasks.findIndex(
      (task) => task.id === newTask.id
    );

    if (index !== -1) {
      const newProject = structuredClone(this.state.project);

      newProject.tasks[index] = newTask;
      this.setState({ project: newProject });
    }
  }

  async onSave(operations) {
    const currentUser = await AccessLevelService.getUsername();

    const passedTaskID = this.taskToPass.id;

    //region Put Task
    const putTaskBody = {
      projectID: this.state.project.id,
    };

    if (operations.name) {
      putTaskBody.name = operations.name;
    }

    if (operations.description) {
      putTaskBody.description = operations.description;
    }

    if (operations.storyPoint) {
      putTaskBody.storyPoint = operations.storyPoint;
    }
    //endregion

    //region assign sprint
    const assignSprintBody = {
      projectID: this.state.project.id,
      taskID: this.taskToPass.id,
    };

    if (operations.sprint) {
      assignSprintBody.sprintID = operations.sprint;
    }
    //endregion

    //region Add Comments
    let commentBodyArr = [];
    if (operations.commentsToAdd) {
      for (var i = 0; i < operations.commentsToAdd.length; i++) {
        const commentAddBody = {
          projectID: this.state.project.id,
          taskID: this.taskToPass.id,
        };

        const comment = operations.commentsToAdd[i];

        commentAddBody.username = comment.username;
        commentAddBody.comment = comment.comment;
        commentAddBody.action = commentTypeToOrdinal(comment.action);

        commentBodyArr.push(commentAddBody);
      }
    }
    //endregion

    //region add assignees
    let assigneesToAddArr = [];
    if (operations.assigneesToAdd) {
      for (var i = 0; i < operations.assigneesToAdd.length; i++) {
        const assigneeAddBody = {
          projectID: this.state.project.id,
          taskID: this.taskToPass.id,
          userID: operations.assigneesToAdd[i],
          assignedBy: currentUser,
        };

        assigneesToAddArr.push(assigneeAddBody);
      }
    }
    //endregion

    //region delete assignees
    let assigneesToRemove = [];
    if (operations.assigneesToDelete) {
      for (var i = 0; i < operations.assigneesToDelete.length; i++) {
        const assigneeDeleteBody = {
          projectID: this.state.project.id,
          taskID: this.taskToPass.id,
          assigneeID: operations.assigneesToDelete[i],
        };
        assigneesToRemove.push(assigneeDeleteBody);
      }
    }
    //endregion

    changeTask(this.taskToPass.id, putTaskBody);
    assignToSprint(assignSprintBody);
    commentBodyArr.forEach((comment) => addCommentToTask(comment));
    assigneesToAddArr.forEach((assignee) => addAssigneeToTask(assignee));
    assigneesToRemove.forEach((assignee) => deleteAssigneeFromTask(assignee));

    const updatedTask = await fetchTask(
      this.state.project.id,
      this.taskToPass.id
    );

    if (updatedTask) {
      this.updateTaskCard(updatedTask);
    }

    this.toggleSidePane();
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

  toggleSidePane(task) {
    this.taskToPass = task;
    this.setState({
      sidePaneOpen: !this.state.sidePaneOpen,
    });
  }

  toggleProjectModal() {
    this.setState({
      projectModal: !this.state.projectModal,
    });
  }

  toggleUserModal() {
    this.setState({
      userModal: !this.state.userModal,
    });
  }

  toggleProjectManagerModal() {
    this.setState({
      projectManagerModal: !this.state.projectManagerModal,
    });
  }

  async editProjectManager() {
    const managerChangeList = this.ProjectManagerChild.current.getOutput();

    let changeList = [];
    for (var i = 0; i < managerChangeList.length; i++) {
      const teamMember = managerChangeList[i];

      if (teamMember.isChecked) {
        const currentRole = teamMember.currentProjectRole;
        const selectedRole = parseInt(teamMember.selectedRole);

        if (userTypeStringToOrdinal(currentRole) !== selectedRole) {
          const body = {
            projectID: this.state.project.id,
            userID: teamMember.id,
            projectRole: selectedRole,
          };

          changeList.push(body);
        }
      }
    }

    let changedCount = 0;
    if (changeList.length > 0) {
      for (var i = 0; i < changeList.length; i++) {
        const response = await fetch("/api/projects/manager", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(changeList[i]),
        });

        if (response.status === 200) {
          changedCount += 1;
        }
      }

      if (changedCount > 0) {
        toast.success(
          `Changed the roles of ${changedCount} team members succesfully`,
          {
            position: "top-right",
            autoClose: 2000,
            hideProgressBar: false,
            closeOnClick: true,
            draggable: true,
            progress: undefined,
          }
        );

        this.state.update();
      }
    }

    this.toggleProjectManagerModal();
  }

  async editUsers() {
    const userChangeList = this.userChild.current.getOutput();

    const usersToAdd = {
      userIds: [],
      projectID: this.state.project.id,
    };

    const usersToDelete = [];

    for (var i = 0; i < userChangeList.length; i++) {
      const user = userChangeList[i];

      if (user.isTeamMember === false && user.isChecked === true) {
        usersToAdd.userIds.push(user.id);
        continue;
      }

      if (user.isTeamMember === true && user.isChecked === false) {
        const userToDelete = {
          projectID: this.state.project.id,
          userID: user.id,
        };

        usersToDelete.push(userToDelete);
      }
    }

    let addedUser = false;
    if (usersToAdd.userIds.length > 0) {
      const response = await fetch("/api/projects/user", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(usersToAdd),
      });

      if (response.status === 200) {
        addedUser = true;

        toast.success(`${usersToAdd.userIds.length} users added succesfully`, {
          position: "top-right",
          autoClose: 2000,
          hideProgressBar: false,
          closeOnClick: true,
          draggable: true,
          progress: undefined,
        });
      }
    }

    let deletedUser = false;
    let deletedCount = 0;
    if (usersToDelete.length > 0) {
      for (var i = 0; i < usersToDelete.length; i++) {
        const deleteBody = usersToDelete[i];

        const response = await fetch("/api/projects/user", {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(deleteBody),
        });

        if (response.status === 200) {
          deletedCount += 1;
          deletedUser = true;
        }
      }

      if (deletedCount > 0) {
        toast.success(`${deletedCount} users deleted succesfully`, {
          position: "top-right",
          autoClose: 2000,
          hideProgressBar: false,
          closeOnClick: true,
          draggable: true,
          progress: undefined,
        });
      }
    }

    if (addedUser || deletedUser) {
      this.state.update();
    }

    this.toggleUserModal();
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

  async deleteSprint(sprintID) {
    const body = {
      projectID: this.state.project.id,
      sprintID: sprintID,
    };

    const response = await fetch("/api/projects/sprints", {
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

  async activateSprint(sprintID) {
    const body = {
      projectID: this.state.project.id,
      sprintID: sprintID,
    };

    const response = await fetch("/api/projects/sprints/active", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    });

    if (response.status === 200) {
      this.state.update();
    }
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
          key={hashCodeArr(
            this.state.projectUserRoles.map((u) => u.currentProjectRole)
          )}
          id={"projectManager"}
          title={"Project Manager"}
          component={
            <ProjectManagerRow
              key="projectManagerRow"
              teamMembers={this.state.projectUserRoles}
              className="my-2 clickable"
              onClick={this.toggleProjectManagerModal}
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
            key={hashTask(task)}
            index={index}
            task={task}
            projectRoles={this.state.projectUserRoles}
            deleteTask={this.deleteTask}
            getSprint={this.getTaskSprint}
            toggleSidePane={this.toggleSidePane}
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
            deleteSprint={this.deleteSprint}
            activateSprint={this.activateSprint}
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

    let userModal;
    if (this.state.userModal) {
      userModal = (
        <AppModal
          isOpen={this.state.userModal}
          toggle={this.toggleUserModal}
          title="Edit Project Users"
          cancelString="Cancel"
          successString="Change"
          content={
            <UserEdit
              ref={this.userChild}
              id="user_edit_modal"
              userList={this.state.projectUserRoles}
            />
          }
          onSuccess={this.editUsers}
        />
      );
    }

    let projectManagerModal;
    if (this.state.projectManagerModal) {
      projectManagerModal = (
        <AppModal
          isOpen={this.state.projectManagerModal}
          toggle={this.toggleProjectManagerModal}
          title="Change Project Managers"
          cancelString="Cancel"
          successString="Change"
          content={
            <ProjectManagerEdit
              ref={this.ProjectManagerChild}
              projectRoles={this.state.projectUserRoles}
            />
          }
          onSuccess={this.editProjectManager}
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
                  component={
                    <UserRow
                      className="clickable"
                      projectID={this.state.project.id}
                      onClick={this.toggleUserModal}
                    />
                  }
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
        {userModal}
        {projectManagerModal}
        <SidePane
          open={this.state.sidePaneOpen}
          width={80}
          onClose={this.toggleSidePane}
          disableEscapeKeyDown={true}
        >
          <TaskEdit
            task={this.taskToPass}
            statusFields={this.state.project.statusFields}
            sprints={this.state.project.sprints}
            onClose={this.toggleSidePane}
            teamMembers={this.state.projectUserRoles}
            onSave={this.onSave}
          />
        </SidePane>
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
