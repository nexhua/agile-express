import React from "react";
import { Container, Table } from "reactstrap";
import DetailList from "../components/DetailList";

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
      if (this.state.projects.length === 0) {
        console.log("No projects found to display");
      }
    }
  }

  handleClick(i) {
    console.log("row " + i + " is clicked");
  }

  render() {
    const detailList = this.state.projects.map((project, i) => {
      return (
        <DetailList
          key={project.id}
          project={project}
          row={i}
          clickFunction={() => this.handleClick(i)}
        />
      );
    });

    return (
      <div>
        <Container fluid>
          <h2>Projects</h2>
          <Table dark striped hover>
            <thead>
              <tr>
                <th>#</th>
                <th>Project Name</th>
                <th>Start Date</th>
                <th>End Date</th>
              </tr>
            </thead>
            <tbody>{detailList}</tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default Dashboard;
