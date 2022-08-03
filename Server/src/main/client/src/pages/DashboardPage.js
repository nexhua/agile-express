import React from "react";
import { Container, Table } from "reactstrap";
import { ToastContainer, toast } from "react-toastify";
import AppNavbar from "../components/AppNavbar";
import ProjectCard from "../components/ProjectCard";

class Dashboard extends React.Component {
  state = {
    projects: [],
  };

  async componentDidMount() {
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
  }

  render() {
    let projectCard;

    const cards = this.state.projects.map((project, index) => {
      return (
        <ProjectCard
          key={project.id}
          project={project}
          count={index + 1}
          isEmpty={false}
        />
      );
    });

    const newProjectCard = <ProjectCard isEmpty={true} />;

    return (
      <div>
        <AppNavbar />
        <ToastContainer />
        <Container fluid className="app-body app-bg-secondary pb-5">
          <h2 className="text-center py-4 text-white">Projects</h2>
          <div className="d-flex flex-row flex-wrap gap-5">
            {cards}
            {newProjectCard}
          </div>
        </Container>
        <ToastContainer />
      </div>
    );
  }
}

export default Dashboard;
