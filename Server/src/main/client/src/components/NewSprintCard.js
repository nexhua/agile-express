import React from "react";
import { FormGroup, Label, Col } from "reactstrap";
import AppDatePicker from "./AppDatePicker";

export default class NewSprintCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isActive: false,
      createSprint: this.props.createSprintFunc,
    };

    this.validateAndGetInput = this.validateAndGetInput.bind(this);
    this.createSprintFunc = this.createSprintFunc.bind(this);
  }

  createSprintFunc() {
    const body = this.validateAndGetInput();

    if (body && body.isValid) {
      this.state.createSprint(body.sprint);
    }
  }

  validateAndGetInput() {
    const goalInput = document.getElementById("sprint_goal_input");
    const startDateInput = document.getElementById("startDatePicker_sprint");
    const endDateInput = document.getElementById("endDatePicker_sprint");

    const body = {
      isValid: true,
      sprint: {
        projectID: "",
        goal: "",
        startDate: "",
        endDate: "",
      },
    };

    if (goalInput && goalInput.value.length > 0) {
      body.sprint.goal = goalInput.value;
    } else {
      body.isValid = false;
      return body;
    }

    if (startDateInput && endDateInput) {
      const start = new Date(startDateInput.value);
      const end = new Date(endDateInput.value);

      if (start > end) {
        body.isValid = false;
        return body;
      }

      body.sprint.startDate = start;
      body.sprint.endDate = end;
      return body;
    } else {
      body.isValid = false;
      return body;
    }
  }

  render() {
    let currentContent;

    if (this.state.isActive) {
      currentContent = (
        <div className="card-body text-muted py-0 px-2 row align-items-center create-section flex-column gap-0">
          <div className="input-group mb-1">
            <input
              id="sprint_goal_input"
              type="text"
              className="form-control bg-dark text-white input-sm"
              placeholder="Sprint Goal"
              aria-label="Sprint Goal"
              required
            />
          </div>
          <FormGroup row className="my-2">
            <Label for="startDate" sm={5} className="px-0">
              Start Date
            </Label>
            <Col sm={7} className="px-0">
              <AppDatePicker
                id="startDatePicker_sprint"
                allowSameDay={true}
                hasHours={false}
              />
            </Col>
          </FormGroup>
          <FormGroup row>
            <Label for="startDate" sm={5} className="px-0">
              End Date
            </Label>
            <Col sm={7} className="px-0">
              <AppDatePicker
                id="endDatePicker_sprint"
                allowSameDay={false}
                hasHours={false}
              />
            </Col>
          </FormGroup>

          <div
            className="btn-group w-100 p-0 m-0 border-radius-bottom mt-auto"
            role="group"
          >
            <button
              type="button"
              className="btn app-bg-primary muted-gray-hover text-danger"
              onClick={() => this.setState({ isActive: false })}
            >
              Cancel
            </button>
            <button
              type="button"
              className="btn app-bg-primary muted-gray-hover text-success"
              onClick={() => this.createSprintFunc()}
            >
              Create
            </button>
          </div>
        </div>
      );
    } else {
      currentContent = (
        <div
          className="card-body text-muted py-0 px-2 row align-items-center create-section"
          onClick={() => this.setState({ isActive: true })}
        >
          <div className="col-5 ps-5">
            <svg
              height={"80px"}
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 448 512"
            >
              <path
                d="M432 256c0 17.69-14.33 32.01-32 32.01H256v144c0 17.69-14.33 31.99-32 31.99s-32-14.3-32-31.99v-144H48c-17.67 0-32-14.32-32-32.01s14.33-31.99 32-31.99H192v-144c0-17.69 14.33-32.01 32-32.01s32 14.32 32 32.01v144h144C417.7 224 432 238.3 432 256z"
                fill="currentColor"
              />
            </svg>
          </div>
          <div className="col-7">
            <p className="text-muted ps-2 pt-3">Create Sprint</p>
          </div>
        </div>
      );
    }

    return (
      <div>
        <div
          className="card app-bg-secondary mb-2 h-100"
          style={{ width: "20rem" }}
        >
          {currentContent}
        </div>
      </div>
    );
  }
}
