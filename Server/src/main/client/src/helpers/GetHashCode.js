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
  let activeSprint = project.sprints.find((sprint) => sprint.active === true);
  let activeSprintID = "Not Found";
  if (activeSprint) {
    activeSprintID = activeSprint.id;
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

export { hashCodeStr, hashCodeArr, hashProject };
