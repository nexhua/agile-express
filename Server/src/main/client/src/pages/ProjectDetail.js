import React from "react";
import AppNavbar from "../components/AppNavbar";
import ProjectDetailComponent from "../components/ProjectDetailComponent";

class ProjectDetail extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      projectID: this.props.match.params.projectID,
    };
  }

  async componentDidMount() {
    const response = await fetch(`/api/projects/${this.state.projectID}`);
    const data = await response.json();
    this.setState({
      project: data,
    });
  }

  render() {
    const hasProject = this.state.project;
    let projectDetails = <p>not found</p>;

    if (hasProject) {
      projectDetails = <p>project name - {this.state.project.projectName}</p>;
    }

    let projectDetailComponent = <p> loading.. </p>;

    if (this.state.project) {
      projectDetailComponent = (
        <ProjectDetailComponent project={this.state.project} />
      );
    }

    return (
      <div>
        <AppNavbar />
        <p>project detail with {this.state.projectID}</p>
        {projectDetails}
        {projectDetailComponent}
      </div>
    );
  }
}

export default ProjectDetail;
