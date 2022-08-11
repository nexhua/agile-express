import React from "react";
import { Container } from "reactstrap";
import { ToastContainer } from "react-toastify";
import AppNavbar from "../components/AppNavbar";
import Board from "../components/Board";
import AppFooter from "../components/AppFooter";

export default class BoardPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      project: {},
    };
  }

  async componentDidMount() {
    const idString = window.location.search.replace("?pid=", "");

    const response = await fetch(`/api/projects/${idString}`);

    if (response.status === 200) {
      const data = await response.json();
      this.setState({
        project: data,
      });
    }
  }

  render() {
    let board;
    if (this.state.project) {
      board = (
        <Board
          key={this.state.project.id}
          projectID={this.state.project.id}
          tasks={this.state.project.tasks}
          statusFields={this.state.project.statusFields}
        />
      );
    }
    return (
      <div>
        <AppNavbar />
        <ToastContainer />
        <Container fluid className="app-body app-bg-secondary pb-5">
          <h2 className="text-center py-4 text-white">
            {this.state.project.projectName}
          </h2>
          {board}
        </Container>
        <ToastContainer />
        <AppFooter />
      </div>
    );
  }
}
