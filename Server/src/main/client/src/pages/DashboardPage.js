import React from "react";
import { Container, Table } from "reactstrap";
import { ToastContainer, toast } from "react-toastify";
import AppNavbar from "../components/AppNavbar";
import ProjectCard from "../components/ProjectCard";
import AccessLevelService from "../helpers/AccessLevelService";

class Dashboard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      projects: [],
      accessLevel: 0,
    };

    this.fetchProjects = this.fetchProjects.bind(this);
    this.deleteProject = this.deleteProject.bind(this);
  }

  async fetchProjects(hasNew) {
    const response = await fetch("/api/projects", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    }).catch((e) => console.log(e));

    if (response.status === 200) {
      const data = await response.json();
      this.setState({ projects: data });
    }

    if (hasNew) {
      toast.success("Project created successfully", {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        draggable: true,
        progress: undefined,
      });
    }
  }

  async componentDidMount() {
    const accessLevel = await AccessLevelService.getAccessLevel();

    await this.fetchProjects(false);

    this.setState({
      accessLevel: accessLevel,
    });
  }

  async deleteProject(projectID) {
    const projectToDeleteIndex = this.state.projects.findIndex(
      (project) => project.id === projectID
    );

    if (projectToDeleteIndex >= 0) {
      const jsonObject = {
        projectID: projectID,
      };

      const response = await fetch("/api/projects", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(jsonObject),
      });

      if (response.status === 200) {
        const newProjects = [...this.state.projects];
        newProjects.splice(projectToDeleteIndex, 1);
        this.setState({
          projects: newProjects,
        });

        toast.success("Project deleted successfully", {
          position: "top-right",
          autoClose: 2000,
          hideProgressBar: false,
          closeOnClick: true,
          draggable: true,
          progress: undefined,
        });
      }
    }
  }

  render() {
    const cards = this.state.projects.map((project, index) => {
      return (
        <ProjectCard
          key={project.id}
          project={project}
          accessLevel={this.state.accessLevel}
          count={index + 1}
          isEmpty={false}
          deleteProjectFunc={this.deleteProject}
        />
      );
    });

    let noProjectsFound;
    if (cards.length === 0) {
      noProjectsFound = (
        <h1
          className="my-5 mx-auto text-secondary"
          style={{ fontSize: "5rem" }}
        >
          NO PROJECTS FOUND
        </h1>
      );
    }

    const newProjectCard = (
      <ProjectCard isEmpty={true} createCallback={this.fetchProjects} />
    );

    return (
      <div>
        <AppNavbar />
        <ToastContainer />
        <Container fluid className="app-body app-bg-secondary pb-5">
          <h2 className="text-center py-4 text-white">Projects</h2>
          <div className="d-flex flex-row flex-wrap gap-5">
            {cards}
            {noProjectsFound}
            {this.state.accessLevel >= 2 && newProjectCard}
          </div>
        </Container>
        <ToastContainer />
      </div>
    );
  }
}

export default Dashboard;
