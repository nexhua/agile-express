import React from "react";

export default class UserRow extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      projectID: this.props.projectID,
      teamMembers: [],
    };
  }

  async componentDidMount() {
    if (this.state.projectID) {
      const response = await fetch(
        `/api/projects/${this.state.projectID}/users`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const data = await response.json();

      this.setState({
        teamMembers: data.projectTeamMembers,
      });
    }
  }

  render() {
    const names = this.state.teamMembers
      .map((user) => {
        return user.username;
      })
      .join(", ");

    if (this.state.teamMembers.length === 0) {
      return <p className="my-2">No User Assigned</p>;
    }

    return <div className="my-2 w-100">{names}</div>;
  }
}
