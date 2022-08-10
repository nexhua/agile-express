import React from "react";
import userTypeStringToOrdinal from "../helpers/UserTypesConverter";

export default class ProjectManagerRow extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      teamMembers: this.props.teamMembers,
      onClick: this.props.onClick,
      className: this.props.className,
    };

    if (this.props.accessLevel) {
      this.state.accessLevel = this.props.accessLevel;
    } else {
      this.state.accessLevel = 0;
    }
  }

  componentDidMount() {
    if (this.state.accessLevel < 2) {
      const row = document.getElementById("project_manager_row");
      row.classList.remove("clickable");
    }
  }

  getNameString() {
    const managers = this.state.teamMembers.filter(
      (member) => userTypeStringToOrdinal(member.currentProjectRole) === 2
    );

    if (managers.length > 0) {
      return managers.map((m) => m.username).join(", ");
    } else {
      return "Manager Not Assigned";
    }
  }

  render() {
    const nameString = this.getNameString();
    return (
      <p
        id="project_manager_row"
        className={this.state.className}
        onClick={this.state.accessLevel >= 2 ? this.state.onClick : undefined}
      >
        {nameString}
      </p>
    );
  }
}
