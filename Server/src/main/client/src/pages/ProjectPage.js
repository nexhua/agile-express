import React from "react";
import AppNavbar from "../components/AppNavbar";
import { Container } from "reactstrap";
import ProjectDetail from "../components/ProjectDetail";
import { ToastContainer, toast } from "react-toastify";
import { hashCodeArr, hashCodeStr, hashProject } from "../helpers/GetHashCode";

export default class ProjectPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      projectID: "",
      project: null,
    };

    this.fetchProject = this.fetchProject.bind(this);
    this.updateProject = this.updateProject.bind(this);
  }

  async componentDidMount() {
    const idString = window.location.search.replace("?pid=", "");

    this.setState(
      {
        projectID: idString,
      },
      this.fetchProject
    );
  }

  async fetchProject() {
    const response = await fetch(`/api/projects/${this.state.projectID}`);

    if (response.status === 200) {
      const data = await response.json();
      this.setState({
        project: data,
      });
    }
  }

  updateProject() {
    this.fetchProject();
  }

  render() {
    let projectComponent;
    if (this.state.project !== null) {
      projectComponent = (
        <ProjectDetail
          key={hashProject(this.state.project)}
          project={this.state.project}
          updateProject={this.updateProject}
        />
      );
    }
    return (
      <div>
        <AppNavbar />
        <Container fluid className="app-body app-bg-secondary pb-5">
          <h2 className="text-center py-4 text-white">
            {this.state.project !== null
              ? this.state.project.projectName
              : "Your Project"}
          </h2>
          {projectComponent}
        </Container>
        <ToastContainer />
      </div>
    );
  }
}
