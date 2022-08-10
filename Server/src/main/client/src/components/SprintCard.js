import React from "react";
import AccessLevelService from "../helpers/AccessLevelService";

export default class SprintCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      sprint: this.props.sprint,
      isActive: false,
      count: this.props.count,
      getTasks: this.props.getTasks,
      tasks: [],
      delete: this.props.deleteSprint,
      activate: this.props.activateSprint,
      accessLevel: 0,
    };
  }

  async componentDidMount() {
    const accessLevel = await AccessLevelService.getAccessLevel();

    if (this.state.isActive) {
      const card = document.getElementById("sprint_" + this.state.count);
      card.classList.remove("border-0");
      card.classList.add("active-sprint", "border-1");
    }

    const tasks = this.state.getTasks(this.state.sprint.id);
    this.setState({
      tasks: [...tasks],
      accessLevel: accessLevel,
    });
  }

  render() {
    let status;
    if (this.state.sprint.active) {
      status = "Active";

      const card = document.getElementById("sprint_" + this.state.count);
      if (card) {
        card.classList.remove("border-0");
        card.classList.add("border-1", "active-sprint");
      }
    } else {
      if (this.state.sprint.isClosed) {
        status = "Finished";
      } else {
        status = "Not Started";
      }
    }

    return (
      <div>
        <div
          key={"sprint_" + this.state.count + this.state.accessLevel}
          id={"sprint_" + this.state.count}
          className="card border-0"
          style={{ width: "20rem" }}
        >
          <div className="card-body app-bg-secondary">
            <div className="card-title pt-2 text-secondary fst-italic fs-6 d-flex justify-content-between align-items-center">
              <h5 className="text-muted">#{this.state.count + 1} Sprint</h5>
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
                  <li
                    onClick={
                      this.state.accessLevel >= 1
                        ? () => this.state.activate(this.state.sprint.id)
                        : undefined
                    }
                  >
                    <a
                      className="dropdown-item text-warning align-items-center fst-normal"
                      href="#"
                    >
                      <svg
                        width={"16px"}
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 448 512"
                        className="me-2"
                      >
                        <path
                          d="M438.6 105.4C451.1 117.9 451.1 138.1 438.6 150.6L182.6 406.6C170.1 419.1 149.9 419.1 137.4 406.6L9.372 278.6C-3.124 266.1-3.124 245.9 9.372 233.4C21.87 220.9 42.13 220.9 54.63 233.4L159.1 338.7L393.4 105.4C405.9 92.88 426.1 92.88 438.6 105.4H438.6z"
                          fill="currentColor"
                        />
                      </svg>
                      Activate
                    </a>
                  </li>
                  <li
                    onClick={
                      this.state.accessLevel >= 1
                        ? () => this.state.delete(this.state.sprint.id)
                        : undefined
                    }
                  >
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

            <hr className="text-white" />
            <div className="text-white my-2">{this.state.sprint.goal}</div>
            <div className="text-white mt-3">{status}</div>
            <div className="text-white mt-3">
              Has {this.state.tasks.length} tasks
            </div>
            <div className="text-white mt-3">
              Start Date -{" "}
              {new Date(this.state.sprint.startDate).toLocaleDateString()}
            </div>
            <div className="text-white mt-3">
              End Date -{" "}
              {new Date(this.state.sprint.endDate).toLocaleDateString()}
            </div>
          </div>
        </div>
      </div>
    );
  }
}
