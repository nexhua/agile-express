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

    const teamMemberIcons = this.state.teamMembers.map((user, index) => {
      return (
        <div key={user.id} className="svg-icon d-flex align-items-center">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 448 512"
            fill="currentColor"
          >
            <path d="M224 256c70.7 0 128-57.31 128-128s-57.3-128-128-128C153.3 0 96 57.31 96 128S153.3 256 224 256zM274.7 304H173.3C77.61 304 0 381.6 0 477.3c0 19.14 15.52 34.67 34.66 34.67h378.7C432.5 512 448 496.5 448 477.3C448 381.6 370.4 304 274.7 304z" />
          </svg>
          <p className="m-0">{user.username}</p>
        </div>
      );
    });

    return <div className="my-2 w-100">{names}</div>;
  }
}
