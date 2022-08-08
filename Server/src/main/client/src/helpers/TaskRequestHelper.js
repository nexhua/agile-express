async function fetchTask(projectID, taskID) {
  const response = await fetch(`/api/projects/task/${taskID}?pid=${projectID}`);

  if (response.status === 200) {
    const data = await response.json();
    return data;
  } else {
    return null;
  }
}

async function changeTask(taskID, body) {
  if (hasLessOrEqual(body, 1)) {
    return -1;
  }

  const response = await fetch(`/api/projects/task/${taskID}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });

  return response.status;
}

async function assignToSprint(body) {
  if (hasLessOrEqual(body, 2)) {
    return -1;
  }

  const response = await fetch("/api/projects/task/assign", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });
  return response.status;
}

async function addCommentToTask(body) {
  const response = await fetch("/api/projects/task/comments", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });
  return response.status;
}

async function addAssigneeToTask(body) {
  const response = await fetch("/api/projects/task/assignees", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });

  return response.status;
}

async function deleteAssigneeFromTask(body) {
  const response = await fetch("/api/projects/task/assignees", {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });
  return response.status;
}

function hasLessOrEqual(object, ref) {
  return Object.keys(object).length <= ref;
}

export {
  fetchTask,
  changeTask,
  assignToSprint,
  addCommentToTask,
  addAssigneeToTask,
  deleteAssigneeFromTask,
};
