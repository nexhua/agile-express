import React from "react";
import AppNavbar from "../components/AppNavbar";

class Hello extends React.Component {
  render() {
    return (
      <div>
        <AppNavbar />
        <div className="app-body app-bg-secondary">
          <p>Hello world</p>
        </div>
      </div>
    );
  }
}

export default Hello;
