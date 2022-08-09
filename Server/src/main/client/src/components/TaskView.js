import React from "react";
import { Progress } from "reactstrap";
import { calculateSpentPoints } from "../helpers/CommentHelper";

export default class TaskView extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      index: this.props.index,
      task: this.props.task,
      assigneeString: "No User Assigned",
      spendStoryPoint: 0,
    };
  }

  componentDidMount() {
    this.setState({
      spendStoryPoint: calculateSpentPoints(this.state.task.comments),
    });
  }

  render() {
    let userInfo;
    if (this.state.assigneeCount > 0) {
      userInfo = (
        <div className="d-inline-block me-2" title={this.state.assigneeString}>
          <svg
            width={"16px"}
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 640 512"
          >
            <path
              d="M351.8 367.3v-44.1C328.5 310.7 302.4 304 274.7 304H173.3c-95.73 0-173.3 77.65-173.3 173.4C.0005 496.5 15.52 512 34.66 512h378.7c11.86 0 21.82-6.337 28.07-15.43l-61.65-61.57C361.7 416.9 351.8 392.9 351.8 367.3zM224 256c70.7 0 128-57.31 128-128S294.7 0 224 0C153.3 0 96 57.31 96 128S153.3 256 224 256zM630.6 364.8L540.3 274.8C528.3 262.8 512 256 495 256h-79.23c-17.75 0-31.99 14.25-31.99 32l.0147 79.2c0 17 6.647 33.15 18.65 45.15l90.31 90.27c12.5 12.5 32.74 12.5 45.24 0l92.49-92.5C643.1 397.6 643.1 377.3 630.6 364.8zM447.8 343.9c-13.25 0-24-10.62-24-24c0-13.25 10.75-24 24-24c13.38 0 24 10.75 24 24S461.1 343.9 447.8 343.9z"
              fill="currentColor"
            />
          </svg>
        </div>
      );
    }

    return (
      <div
        id={this.state.task.id + "_view"}
        className="card app-bg-secondary mb-2"
      >
        <div className="card-body text-white py-0 px-2">
          <div className="card-title pt-2 text-secondary fst-italic fs-6 d-flex justify-content-between align-items-center">
            <div>
              #{this.state.index + 1} {this.state.task.name}
            </div>
            <div>
              <div className="dropdown">
                <button
                  className="btn app-bg-secondary muted-gray-hover svg-icon text-muted"
                  type="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                >
                  <svg
                    width={"16px"}
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 448 512"
                  >
                    <path
                      d="M416 288C433.7 288 448 302.3 448 320C448 337.7 433.7 352 416 352H32C14.33 352 0 337.7 0 320C0 302.3 14.33 288 32 288H416zM416 160C433.7 160 448 174.3 448 192C448 209.7 433.7 224 416 224H32C14.33 224 0 209.7 0 192C0 174.3 14.33 160 32 160H416z"
                      fill="currentColor"
                    />
                  </svg>
                </button>
                <ul className="dropdown-menu dropdown-menu-dark">
                  <li>
                    <a
                      className="dropdown-item text-secondary fst-normal"
                      href="#"
                    >
                      <svg
                        className="me-2"
                        width={"16px"}
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 512 512"
                      >
                        <path
                          d="M362.7 19.32C387.7-5.678 428.3-5.678 453.3 19.32L492.7 58.75C517.7 83.74 517.7 124.3 492.7 149.3L444.3 197.7L314.3 67.72L362.7 19.32zM421.7 220.3L188.5 453.4C178.1 463.8 165.2 471.5 151.1 475.6L30.77 511C22.35 513.5 13.24 511.2 7.03 504.1C.8198 498.8-1.502 489.7 .976 481.2L36.37 360.9C40.53 346.8 48.16 333.9 58.57 323.5L291.7 90.34L421.7 220.3z"
                          fill="currentColor"
                        />
                      </svg>
                      Edit
                    </a>
                  </li>
                  <li>
                    <a
                      className="dropdown-item text-danger align-items-center fst-normal"
                      href="#"
                    >
                      <svg
                        width={"16px"}
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 448 512"
                        className="me-2"
                      >
                        <path
                          d="M135.2 17.69C140.6 6.848 151.7 0 163.8 0H284.2C296.3 0 307.4 6.848 312.8 17.69L320 32H416C433.7 32 448 46.33 448 64C448 81.67 433.7 96 416 96H32C14.33 96 0 81.67 0 64C0 46.33 14.33 32 32 32H128L135.2 17.69zM31.1 128H416V448C416 483.3 387.3 512 352 512H95.1C60.65 512 31.1 483.3 31.1 448V128zM111.1 208V432C111.1 440.8 119.2 448 127.1 448C136.8 448 143.1 440.8 143.1 432V208C143.1 199.2 136.8 192 127.1 192C119.2 192 111.1 199.2 111.1 208zM207.1 208V432C207.1 440.8 215.2 448 223.1 448C232.8 448 240 440.8 240 432V208C240 199.2 232.8 192 223.1 192C215.2 192 207.1 199.2 207.1 208zM304 208V432C304 440.8 311.2 448 320 448C328.8 448 336 440.8 336 432V208C336 199.2 328.8 192 320 192C311.2 192 304 199.2 304 208z"
                          fill="currentColor"
                        />
                      </svg>
                      Delete
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div
            style={{ fontSize: "14px" }}
            className="my-2 mb-3 d-flex justify-content-between"
          >
            <div className="d-inline-block">{this.state.task.description}</div>
            {userInfo}
          </div>
          <div className="row d-flex">
            <div className="col">
              <Progress
                style={{ height: "3px" }}
                className="mt-2"
                color="info"
                value={this.state.spendStoryPoint}
                max={this.state.task.storyPoint}
              />
            </div>
            <p className="col-3 px-0 m-0 d-inline-block">
              {this.state.spendStoryPoint} / {this.state.task.storyPoint}
            </p>
          </div>
        </div>
      </div>
    );
  }
}
