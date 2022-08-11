import React from "react";
import { Container, Table } from "reactstrap";
import { ToastContainer, toast } from "react-toastify";
import AppNavbar from "../components/AppNavbar";
import ProjectCard from "../components/ProjectCard";
import AccessLevelService from "../helpers/AccessLevelService";
import SearchBar from "../components/SearchBar";
import { Spinner } from "reactstrap";
import AppFooter from "../components/AppFooter";

class Dashboard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      projects: [],
      accessLevel: 0,
      searchStarted: false,
      searchEnded: false,
      foundProjects: [],
    };

    this.fetchProjects = this.fetchProjects.bind(this);
    this.deleteProject = this.deleteProject.bind(this);
    this.onSearchStart = this.onSearchStart.bind(this);
    this.onSearchEnd = this.onSearchEnd.bind(this);
    this.onSearch = this.onSearch.bind(this);
    this.initialize = this.initialize.bind(this);

    this.initialize();
  }

  async initialize() {
    const accessLevel = await AccessLevelService.getAccessLevel();

    await this.fetchProjects(false);

    this.setState({
      accessLevel: accessLevel,
    });
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
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        draggable: true,
        progress: undefined,
      });
    }
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

  onSearchStart() {
    this.setState({ searchStarted: true, searchEnded: false });
  }

  async onSearch(query) {
    if (query) {
      const response = await fetch(`/api/search?q=${query}`);

      if (response.status === 200) {
        const data = await response.json();
        return data;
      }
      return [];
    } else {
      this.setState({
        searchStarted: false,
        searchEnded: false,
      });
      return [];
    }
  }

  onSearchEnd(result) {
    this.setState({
      searchEnded: true,
      foundProjects: result,
    });
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

    const foundCards = this.state.foundProjects.map((project, index) => {
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

    let content;

    if (this.state.searchStarted) {
      if (this.state.searchEnded) {
        if (this.state.foundProjects.length > 0) {
          content = (
            <div>
              <h2 className="text-center py-4 text-white">Found Projects</h2>
              <div className="d-flex flex-row flex-wrap gap-5">
                {foundCards}
              </div>
            </div>
          );
        } else {
          content = (
            <div className="flex-grow-1 d-flex flex-column justify-content-center align-items-center gap-5">
              <div>
                <span className="svg-icon">
                  <svg
                    width={"300px"}
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 512 512"
                  >
                    <path
                      d="M169.6 291.3C172.8 286.9 179.2 286.9 182.4 291.3C195.6 308.6 223.1 349 223.1 369C223.1 395 202.5 416 175.1 416C149.5 416 127.1 395 127.1 369C127.1 349 156.6 308.6 169.6 291.3H169.6zM368 346.8C377.9 355.6 378.7 370.8 369.9 380.7C361 390.6 345.9 391.4 335.1 382.6C314.7 363.5 286.7 352 256 352C242.7 352 232 341.3 232 328C232 314.7 242.7 304 256 304C299 304 338.3 320.2 368 346.8L368 346.8zM335.6 176C353.3 176 367.6 190.3 367.6 208C367.6 225.7 353.3 240 335.6 240C317.1 240 303.6 225.7 303.6 208C303.6 190.3 317.1 176 335.6 176zM175.6 240C157.1 240 143.6 225.7 143.6 208C143.6 190.3 157.1 176 175.6 176C193.3 176 207.6 190.3 207.6 208C207.6 225.7 193.3 240 175.6 240zM256 0C397.4 0 512 114.6 512 256C512 397.4 397.4 512 256 512C114.6 512 0 397.4 0 256C0 114.6 114.6 0 256 0zM175.9 448C200.5 458.3 227.6 464 256 464C370.9 464 464 370.9 464 256C464 141.1 370.9 48 256 48C141.1 48 48 141.1 48 256C48 308.7 67.59 356.8 99.88 393.4C110.4 425.4 140.9 447.9 175.9 448V448z"
                      fill="currentColor"
                    />
                  </svg>
                </span>
              </div>
              <div className="display-1 text-muted">NO RESULTS FOUND</div>
            </div>
          );
        }
      } else {
        content = (
          <div className="flex-grow-1 d-flex justify-content-center align-items-center ">
            <div className="spinner-border text-light" role="status">
              <span className="visually-hidden">Loading...</span>
            </div>
          </div>
        );
      }
    } else {
      content = (
        <div className="flex-grow-1">
          <h2 className="text-center py-4 text-white">Projects</h2>
          <div className="d-flex flex-row flex-wrap gap-5">
            {cards}
            {noProjectsFound}
            {this.state.accessLevel >= 2 && newProjectCard}
          </div>
        </div>
      );
    }

    return (
      <div>
        <AppNavbar />
        <ToastContainer />
        <Container
          fluid
          className="app-body app-bg-secondary px-0 mh-800px d-flex flex-column"
        >
          <SearchBar
            className="form-control bg-dark text-white input-sm border border-end-0 border-start-0 border-top-0 border-secondary text-center text-light"
            searchStart={this.onSearchStart}
            searchEnd={this.onSearchEnd}
            search={this.onSearch}
            placeholder="Search For Projects"
          />
          {content}
        </Container>
        <ToastContainer />
        <AppFooter />
      </div>
    );
  }
}

export default Dashboard;
