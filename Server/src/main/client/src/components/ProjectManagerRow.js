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
      <p className={this.state.className} onClick={this.state.onClick}>
        {nameString}
      </p>
    );
  }
}
