import React from "react";
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap";

export default class AppModal extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isOpen: this.props.isOpen,
      toggle: this.props.toggle,
      title: this.props.title,
      content: this.props.content,
      onSuccess: this.props.onSuccess,
      cancelString: this.props.cancelString,
      successString: this.props.successString,
    };
  }

  onModalOpen() {
    const modalHeader = document.querySelector(
      `#project_edit_modal button.close`
    );
    if (modalHeader) {
      modalHeader.classList.add(
        "app-bg-primary",
        "border-0",
        "text-white",
        "muted-gray-hover",
        "rounded-1"
      );
    }
  }

  render() {
    return (
      <div>
        <Button color="danger" onClick={this.state.toggle}>
          {this.props.buttonLabel}
        </Button>
        <Modal
          id="project_edit_modal"
          isOpen={this.state.isOpen}
          toggle={this.state.toggle}
          className={this.props.className}
          onOpened={this.onModalOpen}
        >
          <ModalHeader
            className="app-bg-primary mb-0 border-0"
            toggle={this.state.toggle}
          >
            <p className="d-inline-block text-white text-italic my-auto">
              {this.state.title}
            </p>
          </ModalHeader>
          <ModalBody className="app-bg-secondary text-white text-lighter my-0 py-4">
            {this.state.content}
          </ModalBody>
          <ModalFooter className="app-bg-primary mt-0 border-0 d-flex justify-content-around">
            <Button color="secondary" onClick={this.state.toggle}>
              {this.state.cancelString}
            </Button>
            <Button color="primary" onClick={() => this.state.onSuccess()}>
              {this.state.successString}
            </Button>{" "}
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}
