import React from "react";
import AccessLevelService from "../helpers/AccessLevelService";
import { getStoryPoint } from "../helpers/CommentHelper";
import AssigneeEdit from "./AssigneeEdit";
import ClickableInfo from "./ClickableInfo";
import Comment from "./Comment";

export default class TaskEdit extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      task: structuredClone(this.props.task),
      toggleSidePane: this.props.onClose,
      statusFields: this.props.statusFields,
      text: "",
      newComments: [],
      newName: this.props.task.name,
      newDescription: this.props.task.description,
      newStoryPoint: this.props.task.storyPoint,
      sprints: this.props.sprints,
      teamMembers: this.props.teamMembers,
      assignedSprint: "",
      onSave: this.props.onSave,
      accessLevel: 0,
    };

    this.setText = this.setText.bind(this);
    this.handleKeyDown = this.handleKeyDown.bind(this);
    this.createCommentIfValid = this.createCommentIfValid.bind(this);
    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
    this.handleStoryPointChange = this.handleStoryPointChange.bind(this);
    this.onSaveHandler = this.onSaveHandler.bind(this);
    this.initialize = this.initialize.bind(this);

    this.assigneeRef = React.createRef();

    this.initialize();
  }

  async initialize() {
    const accessLevel = await AccessLevelService.getAccessLevel();

    this.setState({ accessLevel: accessLevel });
  }

  onSaveHandler() {
    const operations = {};

    let assigneesToDelete = [];
    let assigneesToAdd = [];
    let assigneeOperations = this.assigneeRef.current.getOutput();

    for (var i = 0; i < assigneeOperations.length; i++) {
      const user = assigneeOperations[i];
      if (user.isAssignee === true) {
        if (!user.isChecked) {
          assigneesToDelete.push(user.assigneeID);
        }
      } else {
        if (user.isChecked) {
          assigneesToAdd.push(user.id);
        }
      }
    }

    if (assigneesToAdd.length > 0) {
      operations.assigneesToAdd = assigneesToAdd;
    }

    if (assigneesToDelete.length > 0) {
      operations.assigneesToDelete = assigneesToDelete;
    }

    if (this.state.task.name !== this.state.newName) {
      operations.name = this.state.newName;
    }

    if (this.state.task.description !== this.state.newDescription) {
      operations.description = this.state.newDescription;
    }

    if (this.state.task.storyPoint !== this.state.newStoryPoint) {
      operations.storyPoint = this.state.newStoryPoint;
    }

    if (this.state.assignedSprint !== "") {
      operations.sprint = this.state.assignedSprint;
    }

    if (this.state.newComments.length > 0) {
      operations.commentsToAdd = this.state.newComments;
    }

    this.state.onSave(operations);
  }

  handleStoryPointChange(newStoryPoint) {
    this.setState({ newStoryPoint: newStoryPoint });
  }

  handleDescriptionChange(newDescription) {
    this.setState({ newDescription: newDescription });
  }

  handleNameChange(newName) {
    this.setState({ newName: newName });
  }

  setText(e) {
    this.setState({
      text: e.target.value,
    });
  }

  async handleKeyDown(e) {
    if (e.key === "Enter") {
      e.preventDefault();

      const username = await AccessLevelService.getUsername();
      const response = this.createCommentIfValid(username);

      if (response.isValid) {
        const newComments = [...this.state.newComments];
        newComments.push(response.comment);
        this.setState({ newComments: newComments, text: "" });
      }
    }
  }

  createCommentIfValid(username) {
    const response = {
      isValid: false,
      comment: {
        username: username,
        comment: "",
        action: "",
        date: new Date(),
      },
    };

    const point = getStoryPoint(this.state.text);

    if (point > 0) {
      response.isValid = true;
      response.comment.comment = this.state.text;
      response.comment.action = "POINT";
      return response;
    } else {
      if (point === -2) {
        response.isValid = false;
        return response;
      } else {
        response.isValid = true;
        response.comment.comment = this.state.text;
        response.comment.action = "COMMENT";
        return response;
      }
    }
  }

  render() {
    let comments;
    let newComments;
    if (this.state.task.comments.length === 0) {
      comments = <p className="text-muted">No comment added.</p>;
    } else {
      comments = this.state.task.comments.map((c, index) => {
        return <Comment key={"comment_" + index} comment={c} />;
      });
    }

    if (this.state.newComments.length > 0) {
      newComments = this.state.newComments.map((c, index) => {
        return <Comment key={"new_comment_" + index} comment={c} />;
      });
    }

    let sprints;
    if (this.state.sprints) {
      sprints = this.state.sprints.map((s, index) => {
        return (
          <div key={"task_edit_sprint_" + index}>
            <div className="form-check">
              <input
                className="form-check-input"
                type="radio"
                name={"task_edit_sprint_radio"}
                id={"task_edit_sprint_radio_" + index}
                value={s.id}
              />
              <label
                className="form-check-label"
                for={"task_edit_sprint_radio_" + index}
              >
                #{index + 1} &nbsp; {s.goal}
              </label>
            </div>
          </div>
        );
      });
    }

    return (
      <div
        key={"comments_" + this.state.task.comments.length}
        className="app-bg-secondary h-100 overflow-auto d-flex flex-column"
      >
        <div className="d-flex justify-content-between text-white pt-4 px-3">
          <div className="fs-3 d-inline-block">
            <ClickableInfo
              key={"clickable_name_change" + this.state.accessLevel}
              classNames={"d-inline-block"}
              text={this.state.task.name}
              onChange={this.handleNameChange}
              accessLevel={this.state.accessLevel}
              requiredAccessLevel={2}
            />
            <div className="d-inline-block text-muted fs-6 mb-3">
              Created by {this.state.task.createdBy} on{" "}
              {new Date(this.state.task.createdAt).toLocaleString()}
            </div>
          </div>
          <div
            onClick={() => this.state.toggleSidePane()}
            className="p-1 clickable muted-gray-hover"
          >
            &#10006;
          </div>
        </div>
        <hr className="text-white m-0 p-0" />
        <div className="main-content app-bg-primary flex-grow-1 text-white row pt-4 px-4">
          <div className="col-5 ">
            <div>
              <div className="fs-2">
                Description
                <div className="d-inline-block fs-6 text-muted">
                  &nbsp;&nbsp;&nbsp; provided by {this.state.task.createdBy}
                </div>
              </div>
              <ClickableInfo
                key={"handle_description_change" + this.state.accessLevel}
                classNames="mt-2 py-2"
                text={this.state.task.description}
                onChange={this.handleDescriptionChange}
                accessLevel={this.state.accessLevel}
                requiredAccessLevel={2}
              />
            </div>
            <div className="my-4">
              <div className="fs-2">Comments</div>
              <div className="mt-2">{comments}</div>
              <div> {newComments}</div>
            </div>
            <div>
              <div className="form-group">
                <label for="comment_textarea" className="text-white mb-2">
                  Add Comment
                </label>
                <textarea
                  placeholder="Add comment or use /point {number} to add story point to task"
                  value={this.state.text}
                  onKeyDown={this.handleKeyDown}
                  onChange={this.setText}
                  className="form-control"
                  id="comment_textarea"
                  rows="3"
                ></textarea>
              </div>
            </div>
          </div>
          <div className="col position-relative">
            <div className="row">
              <div className="col">Story Point :</div>
              <div className="col-2">
                <ClickableInfo
                  key={"handle_story_point_change" + this.state.accessLevel}
                  classNames="mx-auto"
                  text={this.state.task.storyPoint}
                  onChange={this.handleStoryPointChange}
                  accessLevel={this.state.accessLevel}
                  requiredAccessLevel={2}
                />
              </div>
            </div>
            <div className="row">
              <div className="col">
                <div className="mx-auto my-2 mt-3">Assignees</div>
                <AssigneeEdit
                  ref={this.assigneeRef}
                  teamMembers={this.state.teamMembers}
                  assignees={this.state.task.assignees}
                />
              </div>
              <div
                className="col"
                onChange={(e) =>
                  this.setState({ assignedSprint: e.target.value })
                }
              >
                <div className="mx-auto my-2 mt-3">Sprints</div>
                {sprints}
              </div>
            </div>
            <button
              className="btn btn-primary position-absolute bottom-0 end-0 mb-5 mx-3"
              onClick={() => {
                this.onSaveHandler();
              }}
            >
              Save Changes
            </button>
          </div>
        </div>
      </div>
    );
  }
}
