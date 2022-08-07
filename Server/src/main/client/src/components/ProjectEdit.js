import React from "react";
import { Form, FormGroup, Label, Col, Input, InputGroup } from "reactstrap";
import AppDatePicker from "./AppDatePicker";
import StatusFieldsRow from "./StatusFieldsRow";

export default class ProjectEdit extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.id,
      statusFields: this.props.statusFields,
    };

    this.getOutput = this.getOutput.bind(this);
    this.setOutput = this.setOutput.bind(this);

    this.newFields = this.props.statusFields;
  }

  setOutput(newFields) {
    this.newFields = newFields;
  }

  getOutput() {
    return this.newFields;
  }

  render() {
    return (
      <div id={this.state.id}>
        <Form>
          <FormGroup row className="mb-4">
            <Label
              for="projectName"
              sm={4}
              className="d-flex align-items-center"
            >
              <div className="form-check d-inline-block">
                <input
                  className="form-check-input d-inline-block"
                  type="checkbox"
                  id="projectName_check"
                  name="projectName_check"
                />
              </div>
              Project Name
            </Label>
            <Col sm={8}>
              <Input
                type="text"
                name="projectName"
                id="projectName"
                placeholder="Project Name"
                className=""
                required
              />
            </Col>
          </FormGroup>
          <FormGroup row className="mb-4">
            <Label for="startDate" sm={4} className="d-flex align-items-center">
              <div className="form-check d-inline-block">
                <input
                  className="form-check-input d-inline-block"
                  type="checkbox"
                  id="startDate_check"
                  name="startDate_check"
                />
              </div>
              Start Date
            </Label>
            <Col sm={8}>
              <AppDatePicker
                id="startDatePicker"
                allowSameDay={true}
                hasHours={true}
              />
            </Col>
          </FormGroup>
          <FormGroup row className="mb-4">
            <Label for="startDate" sm={4} className="d-flex align-items-center">
              <div className="form-check d-inline-block">
                <input
                  className="form-check-input d-inline-block"
                  type="checkbox"
                  id="endDate_check"
                  name="endDate_check"
                />
              </div>
              End Date
            </Label>
            <Col sm={8}>
              <AppDatePicker
                id="endDatePicker"
                allowSameDay={false}
                hasHours={true}
              />
            </Col>
          </FormGroup>
          <FormGroup row className="mb-4">
            <Label
              for="status_field_create"
              sm={4}
              className="d-flex align-items-center"
            >
              <div className="form-check d-inline-block">
                <input
                  className="form-check-input d-inline-block"
                  type="checkbox"
                  id="statusField_check"
                  name="statusField_check"
                />
              </div>
              Status Fields
            </Label>
            <Col sm={8}>
              <StatusFieldsRow
                statusFields={this.state.statusFields}
                hasCreate={true}
                clickableFields={true}
                setOutput={this.setOutput}
              />
            </Col>
          </FormGroup>
        </Form>
      </div>
    );
  }
}
