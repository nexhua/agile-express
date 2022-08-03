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

  handleClick(i) {
    console.log("row " + i + " is clicked");
  }

  render() {
    let projectCard;

    if (this.state.projects[0]) {
      projectCard = (
        <ProjectCard
          key={this.state.projects[0].id}
          project={this.state.projects[0]}
          count={1}
        />
      );
    }

    return (
      <div>
        <AppNavbar />
        <ToastContainer />
        <Container fluid className="app-body app-bg-secondary">
          <h2 className="text-center py-4 text-white">Projects</h2>
          {projectCard}
        </Container>
        <ToastContainer />
      </div>
    );
  }
}

export default Dashboard;
