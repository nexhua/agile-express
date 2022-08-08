import React from "react";

export default class Comment extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      comment: this.props.comment,
    };
  }

  render() {
    let comment;
    if (this.state.comment.action === "COMMENT") {
      comment = (
        <div>
          <div className="text-white fs-4">
            {this.state.comment.username}
            <div className="text-muted fs-5 d-inline-block px-3">
              on {new Date(this.state.comment.date).toLocaleDateString()}
            </div>
          </div>
          <div>{this.state.comment.comment}</div>
        </div>
      );
    } else if (this.state.comment.action === "POINT") {
      comment = (
        <div>
          <div className="text-white fs-4">
            {this.state.comment.username}
            <div className="text-muted fs-5 d-inline-block px-3">
              on {new Date(this.state.comment.date).toLocaleDateString()}
            </div>
          </div>
          <div>
            {this.getPointString(
              this.state.comment.comment,
              this.state.comment.username
            )}
          </div>
        </div>
      );
    }

    return (
      <div
        key={this.state.comment.id}
        className="app-bg-tertiary rounded-1 p-2 mb-3"
      >
        {comment}
      </div>
    );
  }

  getPointString(comment, username) {
    const startIndex = comment.indexOf("/point");

    const pointStr = comment.substring(startIndex + "/point".length).trim();

    return `${username} added ${pointStr} points to the task`;
  }
}
