import React from "react";
import StatusFieldsRow from "./StatusFieldsRow";

export default class StatusDropdown extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      statusFileds: this.props.statusFields,
    };
  }

  render() {
    let statusFieldRows = this.state.statusFileds.map((status, index) => {
      return <li>{status}</li>;
    });

    return (
      <div>
        <div className="dropdown">
          <button
            className="btn app-bg-secondary muted-gray-hover svg-icon text-muted"
            type="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            <StatusFieldsRow
              statusFields={this.state.statusFileds}
              hasCreate={true}
            />
          </button>
          <ul className="dropdown-menu dropdown-menu-dark">
            {statusFieldRows}
          </ul>
        </div>
      </div>
    );
  }
}
