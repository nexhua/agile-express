function hashCodeStr(string) {
  var hash = 0;
  for (var i = 0; i < string.length; i++) {
    var code = string.charCodeAt(i);
    hash = (hash << 5) - hash + code;
    hash = hash & hash; // Convert to 32bit integer
  }
  return hash;
}

function hashCodeArr(array) {
  var hash = 0;
  for (var i = 0; i < array.length; i++) {
    var code = hashCodeStr(array[i]);
    hash += code;
  }
  return Math.abs(hash);
}

function hashProject(project) {
  if (!project) {
    return Math.random();
  }

  let activeSprint = project.sprints.find((sprint) => sprint.active === true);
  let activeSprintID = "Not Found";
  if (activeSprint) {
    activeSprintID = activeSprint.id;
  }

  let taskHashArr = [];
  for (var i = 0; i < project.tasks.length; i++) {
    const task = project.tasks[i];
    taskHashArr.push(hashTask(task));
  }

  return (
    project.id +
    project.tasks.length +
    project.sprints.length +
    hashCodeStr(project.projectName) +
    hashCodeArr(
      project.statusFields + hashCodeStr(project.startDate + project.endDate)
    ) +
    hashCodeArr(project.teamMembers.map((u) => u.projectRole)) +
    hashCodeStr(activeSprintID)
  );
}

function hashTask(task) {
  return Math.abs(
    hashCodeStr(task.id) +
      hashCodeStr(task.name) +
      hashCodeStr(task.sprint === null ? task.id : task.sprint) +
      hashCodeStr(String(task.storyPoint)) +
      hashCodeStr(task.description) +
      hashCodeStr(String(task.currentStatus)) +
      hashCodeStr(task.assignees.length + task.comments.length) +
      hashCodeArr(new Date().toISOString()) +
      hashCodeStr(String(Math.random()))
  );
}

export { hashCodeStr, hashCodeArr, hashProject, hashTask };
