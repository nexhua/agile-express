import React from "react";
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Form,
  FormGroup,
  Label,
  Col,
  Input,
} from "reactstrap";
import AppDatePicker from "./AppDatePicker";

export default class NewProjectCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      createCallback: this.props.createCallback,
    };

    this.toggle = this.toggle.bind(this);
    this.onModalOpen = this.onModalOpen.bind(this);
    this.createProjectRequest = this.createProjectRequest.bind(this);
  }

  toggle(e, isSaved) {
    this.setState({
      modal: !this.state.modal,
    });

    if (isSaved) {
      const projetcName = document.querySelector(
        "#new_project_modal #projectName"
      );
      const startDate = document.querySelector(
        "#new_project_modal #startDatePicker"
      );
      const endDate = document.querySelector(
        "#new_project_modal #endDatePicker"
      );

      if (!projetcName.value) {
        return;
      }

      const name = projetcName.value;
      const start = new Date(startDate.value);
      const end = new Date(endDate.value);

      if (start > end) {
        return;
      }

      this.createProjectRequest(name, start, end);
    }
  }

  async createProjectRequest(projectName, startDate, endDate) {
    const data = {
      projectName: projectName,
      startDate: startDate,
      endDate: endDate,
    };

    const response = await fetch("/api/projects", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (response.status === 201) {
      this.state.createCallback();
    }
  }

  onModalOpen() {
    const modalHeader = document.querySelector(
      `#new_project_modal button.close`
    );
    if (modalHeader) {
      modalHeader.classList.add(
        "app-bg-primary",
        "border-0",
        "text-white",
        "muted-gray-hover",
        "rounded-1"
      );
    }
  }

  render() {
    return (
      <div
        className="text-white d-flex flex-column"
        style={{ minWidth: "420px", maxWidth: "420px", minHeight: "300px" }}
      >
        <div className="card border-secondary app-bg-primary h-100">
          <div className="card-body pt-0 d-flex flex-column justify-content-center align-items-center">
            <Button
              className="text-decoration-none"
              color="link"
              onClick={this.toggle}
            >
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
                <p className="m-0 text-muted text-italic text-center">
                  Create New Project
                </p>
              </div>
            </Button>
            <Modal
              id="new_project_modal"
              isOpen={this.state.modal}
              toggle={this.toggle}
              onOpened={this.onModalOpen}
            >
              <ModalHeader
                className="app-bg-primary mb-0 border-0"
                toggle={this.toggle}
              >
                <p className="d-inline-block text-white text-italic my-auto">
                  New Project
                </p>
              </ModalHeader>
              <ModalBody className="app-bg-secondary text-white text-lighter my-0 py-4">
                <Form>
                  <FormGroup row className="mb-4">
                    <Label for="projectName" sm={4}>
                      Project Name
                    </Label>
                    <Col sm={8}>
                      <Input
                        type="text"
                        name="projectName"
                        id="projectName"
                        placeholder="Project Name"
                        required
                      />
                    </Col>
                  </FormGroup>
                  <FormGroup row className="mb-4">
                    <Label for="startDate" sm={4}>
                      Start Date
                    </Label>
                    <Col sm={8}>
                      <AppDatePicker id="startDatePicker" allowSameDay={true} />
                    </Col>
                  </FormGroup>
                  <FormGroup row className="mb-4">
                    <Label for="startDate" sm={4}>
                      End Date
                    </Label>
                    <Col sm={8}>
                      <AppDatePicker id="endDatePicker" allowSameDay={false} />
                    </Col>
                  </FormGroup>
                </Form>
              </ModalBody>
              <ModalFooter className="app-bg-primary mt-0 border-0 d-flex justify-content-around">
                <Button
                  color="secondary"
                  onClick={(e) => this.toggle(e, false)}
                >
                  Cancel
                </Button>
                <Button color="primary" onClick={(e) => this.toggle(e, true)}>
                  Do Something
                </Button>{" "}
              </ModalFooter>
            </Modal>
          </div>
        </div>
      </div>
    );
  }
}
