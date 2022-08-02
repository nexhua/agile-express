import React from "react";
import { Button } from "reactstrap";

class DetailList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      project: this.props.project,
      row: this.props.row,
    };
    this.handleClick.bind(this);
  }

  handleClick(id) {
    window.location.href = `/details/${id}`;
  }

  render() {
    return (
      <tr key={this.state.row} onClick={this.props.clickFunction}>
        <th scope="row">{this.state.row + 1}</th>
        <td>{this.state.project.projectName}</td>
        <td>{this.state.project.startDate}</td>
        <td>{this.state.project.endDate}</td>
        <td>
          <Button
            variant="outline-light"
            onClick={() => this.handleClick(this.props.project.id)}
          >
            Detail
          </Button>
        </td>
      </tr>
    );
  }
}

export default DetailList;
