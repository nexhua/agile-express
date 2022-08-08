import React from "react";

export default class AssigneeEdit extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      teamMembers: [...this.props.teamMembers],
      assignees: [...this.props.assignees],
    };

    for (var i = 0; i < this.state.teamMembers.length; i++) {
      const member = this.state.teamMembers[i];

      const assignee = this.state.assignees.find((a) => a.userID === member.id);
      if (assignee) {
        member.isAssignee = true;
        member.assigneeID = assignee.id;
      } else {
        member.isAssignee = false;
      }
    }
  }

  getOutput() {
    for (var i = 0; i < this.state.teamMembers.length; i++) {
      const member = this.state.teamMembers[i];

      const memberCheck = document.getElementById("team_member_user_" + i);

      if (memberCheck) {
        member.isChecked = memberCheck.checked;
      } else {
        member.isChecked = false;
      }
    }
    return this.state.teamMembers;
  }

  render() {
    let teamMembers;
    for (var i = 0; i < this.state.teamMembers.length; i++) {
      teamMembers = this.state.teamMembers.map((user, index) => {
        return (
          <div key={user.username + index}>
            <div className="form-check d-inline-block">
              <input
                className="form-check-input d-inline-block"
                type="checkbox"
                id={"team_member_user_" + index}
                name={"team_member_user_" + index}
                defaultChecked={user.isAssignee}
              />
              <p>{user.username}</p>
            </div>
          </div>
        );
      });
    }

    return <div>{teamMembers}</div>;
  }
}
