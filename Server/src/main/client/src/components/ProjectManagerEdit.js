import React from "react";
import UserTypes from "../helpers/UserTypes";
import userTypeStringToOrdinal from "../helpers/UserTypesConverter";

export default class ProjectManagerEdit extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      projectRoles: [...this.props.projectRoles],
    };
  }

  getOutput() {
    for (var i = 0; i < this.state.projectRoles.length; i++) {
      const teamMember = this.state.projectRoles[i];

      const teamMemberCheck = document.getElementById("team_member_" + i);

      const teamMemberSelect = document.getElementById(
        "team_member_select_" + i
      );

      if (teamMemberCheck) {
        teamMember.isChecked = teamMemberCheck.checked;

        if (teamMemberSelect) {
          teamMember.selectedRole = teamMemberSelect.value;
        } else {
          teamMember.selectedRole = UserTypes.TEAM_MEMBER;
        }
      } else {
        teamMember.isChecked = false;
        teamMember.selectedRole = UserTypes.TEAM_MEMBER;
      }
    }

    return this.state.projectRoles;
  }

  render() {
    let teamMembers;
    for (var i = 0; i < this.state.projectRoles.length; i++) {
      teamMembers = this.state.projectRoles.map((member, index) => {
        return (
          <div key={member.username + index}>
            <div className="form-check d-inline-block d-flex row align-items-center my-3">
              <div className="col-4">
                <input
                  className="form-check-input d-inline-block"
                  type="checkbox"
                  id={"team_member_" + index}
                  name={"team_member_" + index}
                />
                <p className="my-0">{member.username}</p>
              </div>
              <div className="col-8">
                <select
                  id={"team_member_select_" + index}
                  className="form-select d-inline-block"
                  aria-label="Default select example"
                  defaultValue={UserTypes.TEAM_MEMBER}
                >
                  <option value={UserTypes.TEAM_MEMBER}>Team Member</option>
                  <option value={UserTypes.TEAM_LEAD}>Team Lead</option>
                  <option value={UserTypes.PROJECT_MANAGER}>
                    Project Manager
                  </option>
                  <option value={UserTypes.ADMIN}>Admin</option>
                </select>
              </div>
            </div>
          </div>
        );
      });
    }

    return <div>{teamMembers}</div>;
  }
}
