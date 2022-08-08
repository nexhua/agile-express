import React from "react";

export default class UserEdit extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      allUsers: [],
      userList: this.props.userList,
      setList: [],
    };

    this.updateSetList = this.updateSetList.bind(this);
  }

  getOutput() {
    for (var i = 0; i < this.state.setList.length; i++) {
      const user = this.state.setList[i];

      const currentUserCheck = document.getElementById("team_member_user_" + i);

      if (currentUserCheck) {
        user.isChecked = currentUserCheck.checked;
      } else {
        user.isChecked = false;
      }
    }
    return this.state.setList;
  }

  updateSetList() {
    let setList = [];
    for (var i = 0; i < this.state.allUsers.length; i++) {
      const user = this.state.allUsers[i];

      const setUser = this.state.userList.find((u) => u.id === user.id);

      if (setUser) {
        const user = structuredClone(setUser);
        user.isTeamMember = true;
        setList.push(user);
      } else {
        const nonMember = structuredClone(user);
        nonMember.isTeamMember = false;
        setList.push(nonMember);
      }
    }

    this.setState({ setList: setList });
  }

  async componentDidMount() {
    const response = await fetch("/api/users");

    if (response.status === 200) {
      const data = await response.json();

      this.setState(
        {
          allUsers: data,
        },
        this.updateSetList
      );
    }
  }

  render() {
    let allUsers;
    for (var i = 0; i < this.state.setList.length; i++) {
      allUsers = this.state.setList.map((user, index) => {
        return (
          <div key={user.username + index}>
            <div className="form-check d-inline-block">
              <input
                className="form-check-input d-inline-block"
                type="checkbox"
                id={"team_member_user_" + index}
                name={"team_member_user_" + index}
                defaultChecked={user.isTeamMember}
              />
              <p>{user.username}</p>
            </div>
          </div>
        );
      });
    }

    return <div>{allUsers}</div>;
  }
}
