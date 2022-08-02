import React from "react";

export default class BasicTable extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      rows: this.props.rows,
    };
  }

  render() {
    const rows = this.state.rows;

    let table = [];
    for (var i = 0; i < rows.length; i++) {
      table = rows.map((row) => {
        return (
          <div key={row.title} className="row text-light row-hover">
            <div className="title text-secondary fs-8 col-5"> {row.title} </div>
            <div className="value col"> {row.value}</div>
          </div>
        );
      });
    }

    return <div className="bg-dark">{table}</div>;
  }
}
