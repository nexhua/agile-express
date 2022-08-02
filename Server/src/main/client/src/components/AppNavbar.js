import React from "react";
import {
  Navbar,
  NavItem,
  NavLink,
  NavbarBrand,
  UncontrolledDropdown,
  DropdownMenu,
  NavbarToggler,
  Collapse,
  Nav,
  DropdownToggle,
  DropdownItem,
} from "reactstrap";

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
        <Navbar color="dark" dark expand="md">
          <NavbarBrand className="ms-3" href="/">
            Home
          </NavbarBrand>

          <Nav className="ml-auto">
            <NavItem>
              <NavLink href="/hello">Hello</NavLink>
            </NavItem>
            <NavItem>
              <NavLink href="/user">User</NavLink>
            </NavItem>
            <NavItem>
              <NavLink href="/dashboard">Dashboard</NavLink>
            </NavItem>
          </Nav>
        </Navbar>
      </div>
    );
  }
}

export default AppNavbar;
