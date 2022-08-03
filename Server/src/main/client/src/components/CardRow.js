import React from "react";
import { Spinner } from "reactstrap";

export default class CardRow extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.id,
      title: this.props.title,
      component: this.props.component,
      isLoaded: false,
    };
  }

  render() {
    if (this.state.component) {
      this.state.isLoaded = true;
    }

    return (
      <div key={this.state.id}>
        <div className="row">
          <div className="col-5 text-muted d-flex align-items-center">
            {this.state.title}
          </div>
          <div className="col muted-gray-hover my-0">
            {this.state.component}
          </div>
        </div>
      </div>
    );
  }
}
