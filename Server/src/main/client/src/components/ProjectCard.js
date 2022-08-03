import React from "react";
import CardRow from "./CardRow";
import DateRow from "./DateRow";
import SprintsRow from "./SprintsRow";
import StatusFieldsRow from "./StatusFieldsRow";
import TasksRow from "./TasksRow";
import UserRow from "./UserRow";
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap";

export default class ProjectCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      project: this.props.project,
      count: this.props.count,
      isEmpty: this.props.isEmpty,
      modal: false,
    };

    this.toggle = this.toggle.bind(this);
  }

  toggle() {
    this.setState({
      modal: !this.state.modal,
    });
  }

  render() {
    if (this.state.isEmpty) {
      return (
        <div
          className="text-white d-flex flex-column"
          style={{ minWidth: "420px", maxWidth: "420px" }}
        >
          <div className="card border-secondary app-bg-primary h-100">
            <div className="card-body pt-0 d-flex flex-column justify-content-center align-items-center">
              <Button color="link" onClick={this.toggle}>
                <div
                  className="text-muted"
                  style={{ width: "170px", height: "170px" }}
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 448 512"
                    fill="currentColor"
                  >
                    <path d="M432 256c0 17.69-14.33 32.01-32 32.01H256v144c0 17.69-14.33 31.99-32 31.99s-32-14.3-32-31.99v-144H48c-17.67 0-32-14.32-32-32.01s14.33-31.99 32-31.99H192v-144c0-17.69 14.33-32.01 32-32.01s32 14.32 32 32.01v144h144C417.7 224 432 238.3 432 256z" />
                  </svg>
                  <p className="m-0 text-muted text-italic text-center text-decoration-none">
                    Create New Project
                  </p>
                </div>
              </Button>
              <Modal isOpen={this.state.modal} toggle={this.toggle}>
                <ModalHeader
                  className="app-bg-primary mb-0"
                  toggle={this.toggle}
                >
                  <p className="d-inline-block text-white text-italic my-auto">
                    New Project
                  </p>
                </ModalHeader>
                <ModalBody className="app-bg-secondary text-white my-0 py-4">
                  Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
                  do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                  Ut enim ad minim veniam, quis nostrud exercitation ullamco
                  laboris nisi ut aliquip ex ea commodo consequat. Duis aute
                  irure dolor in reprehenderit in voluptate velit esse cillum
                  dolore eu fugiat nulla pariatur. Excepteur sint occaecat
                  cupidatat non proident, sunt in culpa qui officia deserunt
                  mollit anim id est laborum.
                </ModalBody>
                <ModalFooter className="app-bg-primary mt-0">
                  <Button color="primary" onClick={this.toggle}>
                    Do Something
                  </Button>{" "}
                  <Button color="secondary" onClick={this.toggle}>
                    Cancel
                  </Button>
                </ModalFooter>
              </Modal>
            </div>
          </div>
        </div>
      );
    }

    const keys = Object.keys(this.state.project)[1];

    return (
      <div
        className="text-white d-flex flex-column"
        style={{ minWidth: "420px", maxWidth: "420px" }}
      >
        <div className="card border-secondary app-bg-primary h-100">
          <div className="card-header mt-2 fs-4">
            <p className="text-gray d-inline-block my-0 fst-italic text-muted">
              #{this.state.count}
              {"\u00a0\u00a0"}
            </p>
            {this.state.project.projectName}
            <hr />
          </div>
          <div className="card-body pt-0">
            <CardRow
              id={keys[1]}
              title={"Project Name"}
              component={
                <p className="my-2">{this.state.project.projectName}</p>
              }
            />
            <CardRow
              id={keys[2]}
              title={"Start Date"}
              component={<DateRow date={this.state.project.startDate} />}
            />
            <CardRow
              id={keys[3]}
              title={"End Date"}
              component={<DateRow date={this.state.project.endDate} />}
            />
            <CardRow
              id={keys[4]}
              title={"Status Fields"}
              component={
                <StatusFieldsRow
                  statusFields={this.state.project.statusFields}
                />
              }
            />
            <CardRow
              id={keys[5]}
              title={"Team Members"}
              component={<UserRow projectID={this.state.project.id} />}
            />
            <CardRow id={keys[6]} title={"Tasks"} component={<TasksRow />} />
            <CardRow
              id={keys[7]}
              title={"Sprints"}
              component={<SprintsRow />}
            />
          </div>
        </div>
      </div>
    );
  }
}
