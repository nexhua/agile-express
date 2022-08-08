import React from "react";

export default class ClickableInfo extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      classNames: this.props.classNames,
      text: this.props.text,
      newText: this.props.text,
      type: typeof this.props.text,
      isUpdating: false,
      onChange: this.props.onChange,
    };

    this.handleKeyDown = this.handleKeyDown.bind(this);
    this.ensureType = this.ensureType.bind(this);
    this.fallback = this.fallback.bind(this);
  }

  handleKeyDown(e) {
    if (e.key === "Enter") {
      const input = document.getElementById(
        "clickable_info_change_" + this.state.text + "_input"
      );

      let newValue;
      if (input) {
        newValue = input.value;
      }

      if (this.ensureType(newValue)) {
        this.setState({ isUpdating: false, newText: newValue });
        this.state.onChange(newValue);
      } else {
        this.fallback();
      }
    } else if (e.key === "Escape") {
      this.setState({ isUpdating: false });
      e.preventDefault();
    }
  }

  fallback() {
    this.setState({ isUpdating: false });
  }

  ensureType(value) {
    if (this.state.type === "string") {
      return typeof value === "string";
    } else if (this.state.type === "number") {
      const number = parseInt(value.trim());
      return !isNaN(number);
    } else {
      return true;
    }
  }

  render() {
    let content;

    if (this.state.isUpdating) {
      content = (
        <div className="input-group mb-1">
          <input
            id={"clickable_info_change_" + this.state.text + "_input"}
            type="text"
            className="form-control bg-dark text-white"
            placeholder={this.state.newText}
            onKeyDown={(e) => this.handleKeyDown(e)}
          />
        </div>
      );
    } else {
      content = (
        <div
          className="clickable muted-gray-hover d-flex justify-content-between"
          key={"clickable_info_" + this.state.text}
          onClick={() => this.setState({ isUpdating: true })}
        >
          <div className={this.state.classNames}>{this.state.newText}</div>
          <div className="my-auto">
            <span className="svg-icon mx-2">
              <svg
                width={"16px"}
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 512 512"
              >
                <path
                  d="M362.7 19.32C387.7-5.678 428.3-5.678 453.3 19.32L492.7 58.75C517.7 83.74 517.7 124.3 492.7 149.3L444.3 197.7L314.3 67.72L362.7 19.32zM421.7 220.3L188.5 453.4C178.1 463.8 165.2 471.5 151.1 475.6L30.77 511C22.35 513.5 13.24 511.2 7.03 504.1C.8198 498.8-1.502 489.7 .976 481.2L36.37 360.9C40.53 346.8 48.16 333.9 58.57 323.5L291.7 90.34L421.7 220.3z"
                  fill="currentColor"
                />
              </svg>
            </span>
          </div>
        </div>
      );
    }

    return <div>{content}</div>;
  }
}
