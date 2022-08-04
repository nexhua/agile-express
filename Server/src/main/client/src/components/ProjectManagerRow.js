import React from "react";

export default class ProjectManagerRow extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      teamMembers: this.props.teamMembers,
    };
  }

  render() {
    return (
      <p className="my-2">projeect manager {this.state.teamMembers.length}</p>
    );
  }
}
