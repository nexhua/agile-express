import React from "react";
import AppNavbar from "../components/AppNavbar";

class User extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userName: "",
    };
  }

  async componentDidMount() {
    const response = await fetch("/api/auth/username", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    }).catch((e) => console.log(e));
    const data = await response.json();
    this.setState({
      userName: data.username,
    });
  }

  render() {
    return (
      <div>
        <AppNavbar />
        <p>Hello {this.state.userName}</p>
      </div>
    );
  }
}

export default User;
