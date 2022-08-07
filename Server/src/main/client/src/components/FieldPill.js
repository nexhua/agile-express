import React from "react";

export default class FieldPill extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      fieldName: this.props.fieldName,
      isUpdating: false,
      handle: this.props.handle,
    };

    this.handleWrapper = this.handleWrapper.bind(this);
  }

  handleWrapper(e) {
    this.state.handle(e, this.state.fieldName);
  }

  render() {
    return this.state.isUpdating === true ? (
      <input
        id={this.state.fieldName + "_change_input"}
        type="text"
        className="form-controli input-sm text-white app-bg-primary border-white"
        placeholder="New Status"
        defaultValue={this.state.fieldName}
        aria-label="Status Field"
        onKeyDown={(e) => this.handleWrapper(e)}
      ></input>
    ) : (
      <span
        key={this.state.fieldName}
        className="badge app-bg-primary border border-2 border-secondary fw-lighter clickable"
        onClick={() => this.setState({ isUpdating: true })}
      >
        {this.state.fieldName}
      </span>
    );
  }
}
