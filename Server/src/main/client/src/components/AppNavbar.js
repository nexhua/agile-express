import React from "react";
import { Navbar, NavItem, NavLink, NavbarBrand, Nav } from "reactstrap";

class AppNavbar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      clicked: false,
    };
  }

  render() {
    return (
      <div>
        <Navbar
          className="app-bg-primary border-secondary border-bottom"
          dark
          expand="md"
        >
          <NavbarBrand className="ms-3 pt-2" href="/">
            Home
          </NavbarBrand>

          <Nav className="ml-auto">
            <NavItem>
              <NavLink className="text-light mt-2" href="/dashboard">
                Dashboard
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink className="text-light mt-2" href="/logout">
                Logout
              </NavLink>
            </NavItem>
          </Nav>
        </Navbar>
      </div>
    );
  }
}

export default AppNavbar;
