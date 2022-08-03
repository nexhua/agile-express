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
        <Navbar className="app-bg-primary" dark expand="md">
          <NavbarBrand className="ms-3 pt-2" href="/">
            Home
          </NavbarBrand>

          <Nav className="ml-auto">
            <NavItem>
              <NavLink className="text-light mt-2" href="/hello">
                Hello
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink className="text-light mt-2" href="/user">
                User
              </NavLink>
            </NavItem>
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
