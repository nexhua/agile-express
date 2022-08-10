import { useEffect, useState } from "react";
import AccessLevelService from "../helpers/AccessLevelService";
import FieldPill from "./FieldPill";

export default function StatusFieldsRow(props) {
  const [createStarted, setCreate] = useState(false);
  const [statusFieldsArr, updateFields] = useState([...props.statusFields]);
  const [statusHistory, updateHistory] = useState([props.statusFields]);
  const [accessLevel, setAccessLevel] = useState(0);

  useEffect(() => {
    updateFields(props.statusFields);
  }, [props.statusFields]);

  useEffect(() => {
    const fetchAccessLevel = async () => {
      const level = await AccessLevelService.getAccessLevel();

      setAccessLevel(level);
    };

    fetchAccessLevel();
  }, []);

  let statusFields;
  if (props.clickableFields === true) {
    statusFields = statusFieldsArr.map((field, index) => {
      return (
        <FieldPill
          key={field + index + statusHistory.length}
          fieldName={field}
          handle={handleFieldPillKeyDown}
        />
      );
    });
  } else {
    statusFields = statusFieldsArr.map((field, index) => {
      return (
        <span
          key={field + index}
          className="badge app-bg-primary border border-2 border-secondary fw-lighter"
        >
          {field}
        </span>
      );
    });
  }

  let classNames = ["my-2", "w-100", "d-flex", "gap-2", "flex-wrap"];

  if (props.className) {
    classNames = classNames.concat(props.className);
    if (accessLevel < 2) {
      const index = classNames.indexOf("clickable");
      if (index > 0) {
        classNames.splice(index, 1);
      }
    }
  }

  let createButton;
  if (props.hasCreate && createStarted !== true) {
    createButton = (
      <div>
        <span
          className="svg-icon text-muted border-1 border-muted muted-gray-hover p-2 clickable"
          onClick={() => setCreate(true)}
        >
          <svg
            width={"16px"}
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 512 512"
          >
            <path
              d="M0 256C0 114.6 114.6 0 256 0C397.4 0 512 114.6 512 256C512 397.4 397.4 512 256 512C114.6 512 0 397.4 0 256zM256 368C269.3 368 280 357.3 280 344V280H344C357.3 280 368 269.3 368 256C368 242.7 357.3 232 344 232H280V168C280 154.7 269.3 144 256 144C242.7 144 232 154.7 232 168V232H168C154.7 232 144 242.7 144 256C144 269.3 154.7 280 168 280H232V344C232 357.3 242.7 368 256 368z"
              fill="currentColor"
            />
          </svg>
        </span>
      </div>
    );
  }

  let createInput;
  if (createStarted) {
    createInput = (
      <input
        id="status_field_create"
        type="text"
        className="form-controli input-sm text-white app-bg-primary border-white"
        placeholder="New Status"
        aria-label="Status Field"
        onKeyDown={(e) => handleKeyDown(e)}
      ></input>
    );
  }

  return (
    <div
      className={classNames.join(" ")}
      onClick={accessLevel >= 2 ? props.onClick : undefined}
    >
      {statusFields}
      {createButton}
      {createInput}
    </div>
  );

  function handleFieldPillKeyDown(e, fieldName) {
    if (e.key === "Enter") {
      const input = document.getElementById(fieldName + "_change_input");
      const index = statusFieldsArr.indexOf(fieldName);
      if (input && index !== -1) {
        if (input.value) {
          statusFieldsArr[index] = input.value;
          updateFields(statusFieldsArr);
          props.setOutput(statusFieldsArr);
          updateHistory(statusHistory.concat(statusFieldsArr));
        } else if (input.value === "") {
          let newStatusFields = [...statusFieldsArr];
          newStatusFields.splice(index, 1);
          statusFieldsArr.splice(index, 1);
          updateFields(newStatusFields);
          props.setOutput(statusFieldsArr);
          updateHistory(statusHistory.concat(newStatusFields));
        }
      }
      e.preventDefault();
    } else if (e.key === "Escape") {
      e.preventDefault();
      updateHistory(statusHistory.concat([...statusFieldsArr]));
    }
  }

  function handleKeyDown(e) {
    if (e.key === "Enter") {
      const input = document.getElementById("status_field_create");
      if (input.value) {
        statusFieldsArr.push(input.value);
        updateFields(statusFieldsArr);
        props.setOutput(statusFieldsArr);
        setCreate(false);
      }
    } else if (e.key === "Escape") {
      e.preventDefault();
      setCreate(false);
    }
  }
}
