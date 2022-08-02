import React from "react";

export default class BasicTable extends React.Component {
  constructor(props) {
    super(props);
    let rows = [];
    if (this.props.rows) {
      rows = this.props.rows;
    }
    this.state = {
      rows: rows,
    };
  }

  render() {
    const rows = this.state.rows;

    let table = [];
    for (var i = 0; i < rows.length; i++) {
      table = rows.map((row) => {
        return (
          <div className="row">
            <div className="title text-gray col-5"> {row.title} </div>
            <div className="value col"> {row.value}</div>
          </div>
        );
      });
    }

    return <div>{table}</div>;
  }
}
