package AgileExpress.Server.Controllers;

import AgileExpress.Server.Constants.ApiRouteConstants;
import AgileExpress.Server.Constants.ErrorMessages;
import AgileExpress.Server.Constants.MongoConstants;
import AgileExpress.Server.Constants.UserTypes;
import AgileExpress.Server.Entities.*;
import AgileExpress.Server.Helpers.AccessLevelHelper;
import AgileExpress.Server.Helpers.AuthHelper;
import AgileExpress.Server.Helpers.ProjectHelper;
import AgileExpress.Server.Helpers.ReflectionHelper;
import AgileExpress.Server.Inputs.Project.*;
import AgileExpress.Server.Inputs.Sprint.SprintChangeInput;
import AgileExpress.Server.Inputs.Sprint.SprintCreateInput;
import AgileExpress.Server.Inputs.Sprint.SprintDeleteInput;
import AgileExpress.Server.Inputs.Task.*;
import AgileExpress.Server.Repositories.ProjectRepository;
import AgileExpress.Server.Services.AccessLevelService;
import AgileExpress.Server.Utility.PropertyInfo;
import com.mongodb.MongoException;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {

    private final ProjectRepository repository;

    private final AccessLevelService service;

    public ProjectController(ProjectRepository repository, AccessLevelService service) {
        this.repository = repository;
        this.service = service;
    }

    //GET PROJECT
    @GetMapping(ApiRouteConstants.Project)
    public ResponseEntity<?> getProject(@PathVariable String projectID) {
        ResponseEntity response;
        try {
            Optional<Project> project = this.repository.findById(projectID);
            if (project.isEmpty()) {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Project projectObject = project.get();
                response = new ResponseEntity<Project>(projectObject, HttpStatus.OK);

            }
        } catch (MongoException e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //GET PROJECTS
    @GetMapping(ApiRouteConstants.Projects)
    public ResponseEntity<?> getProjects() {
        UserContext context = this.service.getUserType(AuthHelper.getUsername());

        if(context == null) {
            return new ResponseEntity<>(ErrorMessages.with(ErrorMessages.UserNotFoundError()), HttpStatus.NOT_FOUND);
        }

        UserTypes userTypes = context.getType();
        ResponseEntity response;

        if (AccessLevelHelper.hasHigherOrEqualAccessLevel(userTypes, UserTypes.ADMIN)) {
            try {
                List<Project> result = this.repository.findAll();
                response = new ResponseEntity(result, HttpStatus.OK);
            } catch (Exception e) {
                response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            if (!context.getId().isEmpty()) {
                try {
                    List<Project> projects = this.repository.findProjectsOfUser(context.getId());
                    response = new ResponseEntity(projects, HttpStatus.OK);
                } catch (Exception e) {
                    response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                response = new ResponseEntity(
                        ErrorMessages.with(ErrorMessages.MissingPropertyError(MongoConstants.UserID)),
                        HttpStatus.BAD_REQUEST);
            }

        }
        return response;
    }

    //CREATE PROJECT
    @PostMapping(ApiRouteConstants.Projects)
    public ResponseEntity createProject(@RequestBody CreateProjectInput input) {
        DateTime startDateMilis = new DateTime(input.getStartDate());
        DateTime endDateMilis = new DateTime(input.getEndDate());
        Project project = new Project(input.getProjectName(), new Date(startDateMilis.getMillis()), new Date(endDateMilis.getMillis()));
        HttpStatus status = HttpStatus.CREATED;
        try {
            repository.insert(project);
        } catch (MongoException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(status);
    }

    //UPDATE PROJECT
    @PutMapping(ApiRouteConstants.Projects)
    public ResponseEntity<?> updateProject(@RequestBody(required = false) ChangeProjectInput input) {
        ArrayList<PropertyInfo<?>> propertyInfoList = ReflectionHelper.getFieldsWithValues(input);


        ResponseEntity response;
        Optional<PropertyInfo<?>> optionalPropertyInfo = propertyInfoList.stream().filter(propertyInfo -> propertyInfo.getPropertyName().equals("projectID")).findFirst();
        if (optionalPropertyInfo.isEmpty()) {
            response = new ResponseEntity(
                    new Document(ErrorMessages.Title, ErrorMessages.MissingPropertyError("projectID")),
                    HttpStatus.BAD_REQUEST);
        } else {
            PropertyInfo<?> propertyInfo = optionalPropertyInfo.get();
            String projectID = propertyInfo.getPropertyValue().toString();
            Document result = this.repository.updateProject(projectID, propertyInfoList);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }

        return response;
    }

    @DeleteMapping(ApiRouteConstants.Projects)
    public ResponseEntity<?> deleteProject(@RequestBody BaseProjectInput input) {
        ResponseEntity<?> response;
        try {
            DeleteResult result = this.repository.deleteProject(input);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //ADD USER TO PROJECT
    @PostMapping(ApiRouteConstants.ProjectUser)
    public ResponseEntity addUsers(@RequestBody ProjectAddUserInput input) {
        UpdateResult result = this.repository.addTeamMember(input);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    //DELETE USER FROM PROJECT
    @DeleteMapping(ApiRouteConstants.ProjectUser)
    public ResponseEntity removeUser(@RequestBody ProjectRemoveUserInput input) {
        UpdateResult result = this.repository.removeTeamMember(input);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    //GET TASKS FROM PROJECT
    @GetMapping(ApiRouteConstants.ProjectsTasks)
    public ResponseEntity<?> getTasks(@RequestBody ProjectGetTasksInput input) {
        ResponseEntity response;
        try {
            Optional<Project> project = this.repository.findById(input.getProjectID());
            if (project.isEmpty()) {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Project projectObject = project.get();
                response = new ResponseEntity<>(projectObject.getTasks(), HttpStatus.OK);
            }
        } catch (Exception e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //GET A TASK FROM A PROJECT
    @GetMapping(ApiRouteConstants.ProjectsTask)
    public ResponseEntity<?> getTask(@RequestBody BaseProjectAndTaskInput input) {
        ResponseEntity response;
        try {
            Optional<Project> optionalProject = this.repository.findById(input.getProjectID());
            if (optionalProject.isEmpty()) {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Optional<Task> optionalTask = optionalProject.get().getTask(input.getTaskID());
                if (optionalTask.isEmpty()) {
                    response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    Task task = optionalTask.get();
                    response = new ResponseEntity<>(task, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //CREATE TASK IN A PROJECT
    @PostMapping(ApiRouteConstants.ProjectsTask)
    public ResponseEntity<?> addTask(@RequestBody ProjectCreateTaskInput input) {
        UpdateResult result = this.repository.addTask(input);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //UPDATE SOME PROPERTIES OF AN TASK IN A PROJECT
    @PutMapping(ApiRouteConstants.ProjectsTaskPath)
    public ResponseEntity<?> updateProject(@PathVariable String taskID, @RequestBody(required = false) ProjectUpdateTaskInput input) {
        ArrayList<PropertyInfo<?>> propertyInfoList = ReflectionHelper.getFieldsWithValues(input);


        ResponseEntity response;
        Optional<PropertyInfo<?>> optionalPropertyInfo = propertyInfoList.stream().filter(propertyInfo -> propertyInfo.getPropertyName().equals("projectID")).findFirst();
        if (optionalPropertyInfo.isEmpty()) {
            response = new ResponseEntity(
                    new Document(ErrorMessages.Title, ErrorMessages.MissingPropertyError("projectID")),
                    HttpStatus.BAD_REQUEST);
        } else {
            PropertyInfo<?> propertyInfo = optionalPropertyInfo.get();
            String projectID = propertyInfo.getPropertyValue().toString();
            Document result = this.repository.updateTask(projectID, taskID, propertyInfoList);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }

        return response;
    }

    //DELETE TASK FROM A PROJECT
    @DeleteMapping(ApiRouteConstants.ProjectsTask)
    public ResponseEntity<?> deleteTask(@RequestBody ProjectDeleteTaskInput input) {
        ResponseEntity<?> response;
        try {
            UpdateResult result = this.repository.deleteTask(input);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //GET COMMENTS FROM A TASK IN A PROJECT
    @GetMapping(ApiRouteConstants.ProjectsTaskComment)
    public ResponseEntity<?> getComments(@RequestBody BaseProjectAndTaskInput input) {
        ResponseEntity response;
        try {
            Optional<Project> optionalProject = this.repository.findById(input.getProjectID());
            if (optionalProject.isEmpty()) {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Project project = optionalProject.get();
                Optional<Task> optionalTask = project.getTask(input.getTaskID());
                if (optionalTask.isEmpty()) {
                    response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    Task task = optionalTask.get();
                    response = new ResponseEntity<>(task.getComments(), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //ADD COMMENT TO A TASK IN A PROJECT
    @PostMapping(ApiRouteConstants.ProjectsTaskComment)
    public ResponseEntity<?> addCommentToTask(@RequestBody TaskAddCommentInput input) {
        ResponseEntity response;
        try {
            UpdateResult result = this.repository.addCommentToTask(input);
            response = new ResponseEntity(result, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //DELETE A COMMENT FROM A TASK IN A PROJECT
    @DeleteMapping(ApiRouteConstants.ProjectsTaskComment)
    public ResponseEntity<?> deleteCommentFromTask(@RequestBody TaskDeleteCommentInput input) {
        ResponseEntity response;
        try {
            UpdateResult result = this.repository.removeCommentFromTask(input);
            response = new ResponseEntity(result, HttpStatus.OK);
        } catch (MongoException e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    //TODO: Change to search userID for all projects
    //GET ASSIGNEE OF A TASK IN A PROJECT
    @GetMapping(ApiRouteConstants.ProjectTaskAssigneePath)
    public ResponseEntity<?> getAssignee(@PathVariable String userID, @RequestBody TaskGetAssigneeInput input) {
        ResponseEntity response;
        try {
            Optional<Project> optionalProject = this.repository.findById(input.getProjectID());
            if (optionalProject.isEmpty()) {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Project project = optionalProject.get();
                Optional<Task> optionalTask = project.getTask(input.getTaskID());
                if (optionalTask.isEmpty()) {
                    response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    Task task = optionalTask.get();
                    Optional<Assignee> optionalAssignee = task.getAssignee(userID);
                    if (optionalAssignee.isEmpty()) {
                        response = new ResponseEntity(HttpStatus.NOT_FOUND);
                    } else {
                        response = new ResponseEntity<>(optionalAssignee.get(), HttpStatus.OK);
                    }
                }
            }
        } catch (Exception e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //GET ASSIGNEES OF A TASK IN A PROJECT
    @GetMapping(ApiRouteConstants.ProjectTaskAssignee)
    public ResponseEntity<?> getAssignees(@RequestBody BaseProjectAndTaskInput input) {
        ResponseEntity response;
        try {
            Optional<Project> optionalProject = this.repository.findById(input.getProjectID());
            if (optionalProject.isEmpty()) {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Project project = optionalProject.get();
                Optional<Task> optionalTask = project.getTask(input.getTaskID());
                if (optionalTask.isEmpty()) {
                    response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    Task task = optionalTask.get();
                    response = new ResponseEntity<>(task.getAssignees(), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //ADD ASSIGNEE TO A TASK IN A PROJECT
    @PostMapping(ApiRouteConstants.ProjectTaskAssignee)
    public ResponseEntity<?> addAssigneeToTask(@RequestBody TaskAddAssigneeInput input) {
        ResponseEntity response;
        try {
            boolean userAssigned = ProjectHelper.isUserAssigned(this.repository.findById(input.getProjectID()), input);

            if (!userAssigned) {
                UpdateResult result = this.repository.addAssigneeToTask(input);
                response = new ResponseEntity(result, HttpStatus.CREATED);
            } else {
                response = new ResponseEntity(new Document(ErrorMessages.Title,
                        ErrorMessages.PropertyAlreadyExistsWithValueError(MongoConstants.UserID)), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //DELETE ASSIGNEE FROM A TASK IN A PROJECT
    @DeleteMapping(ApiRouteConstants.ProjectTaskAssignee)
    public ResponseEntity<?> removeAssigneeFromTask(@RequestBody TaskDeleteAssigneeInput input) {
        ResponseEntity response;
        try {
            UpdateResult result = this.repository.removeAssigneeFromTask(input);
            response = new ResponseEntity(result, HttpStatus.OK);
        } catch (MongoException e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //GET LABELS FROM A TASK IN A PROJECT
    @GetMapping(ApiRouteConstants.ProjectTaskLabel)
    public ResponseEntity<?> getLabels(@RequestBody BaseProjectAndTaskInput input) {
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        try {
            Optional<Project> optionalProject = this.repository.findById(input.getProjectID());
            if (!optionalProject.isEmpty()) {
                Project project = optionalProject.get();
                Optional<Task> optionalTask = project.getTask(input.getTaskID());
                if (!optionalTask.isEmpty()) {
                    Task task = optionalTask.get();
                    response = new ResponseEntity<>(task.getLabels(), HttpStatus.OK);
                }
            }
        } catch (MongoException e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //ADD LABEL TO A TASK IN A PROJECT
    @PostMapping(ApiRouteConstants.ProjectTaskLabel)
    public ResponseEntity<?> addLabel(@RequestBody TaskAddLabelInput input) {
        ResponseEntity response;
        try {
            UpdateResult result = this.repository.addLabelToTask(input);
            response = new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (MongoException e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //DELETE LABEL FROM A TASK IN A PROJECT
    @DeleteMapping(ApiRouteConstants.ProjectTaskLabel)
    public ResponseEntity<?> deleteLabel(@RequestBody TaskDeleteLabelInput input) {
        ResponseEntity response;
        try {
            UpdateResult result = this.repository.removeLabelFromTask(input);
            response = new ResponseEntity(result, HttpStatus.OK);
        } catch (MongoException e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //GET SPRINTS OF A PROJECT
    @GetMapping(ApiRouteConstants.ProjectsSprint)
    public ResponseEntity<?> getSprints(@RequestBody BaseProjectInput input) {
        ResponseEntity response;
        try {
            Optional<Project> project = this.repository.findById(input.getProjectID());
            if (project.isEmpty()) {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Project projectObject = project.get();
                response = new ResponseEntity<List<Sprint>>(projectObject.getSprints(), HttpStatus.OK);

            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //ADD SPRINT TO A PROJECT
    @PostMapping(ApiRouteConstants.ProjectsSprint)
    public ResponseEntity<?> addSprint(@RequestBody SprintCreateInput input) {
        ResponseEntity response;
        try {
            UpdateResult result = this.repository.addSprint(input);
            response = new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (MongoException e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //CHANGE A SPRINT IN A PROJECT
    @PutMapping(ApiRouteConstants.ProjectsSprintPath)
    public ResponseEntity<?> changeSprint(@PathVariable String sprintID, @RequestBody(required = false) SprintChangeInput i̇nput) {
        ArrayList<PropertyInfo<?>> propertyInfoList = ReflectionHelper.getFieldsWithValues(i̇nput);


        ResponseEntity response;
        Optional<PropertyInfo<?>> optionalPropertyInfo = propertyInfoList.stream().filter(propertyInfo -> propertyInfo.getPropertyName().equals("projectID")).findFirst();
        if (optionalPropertyInfo.isEmpty()) {
            response = new ResponseEntity(
                    new Document(ErrorMessages.Title, ErrorMessages.MissingPropertyError("projectID")),
                    HttpStatus.BAD_REQUEST);
        } else {
            Document result = this.repository.updateSprint(i̇nput.getProjectID(), sprintID, propertyInfoList);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }

        return response;
    }


    //DELETE A SPRINT FROM A PROJECT
    @DeleteMapping(ApiRouteConstants.ProjectsSprint)
    public ResponseEntity<?> deleteSprint(@RequestBody SprintDeleteInput input) {
        ResponseEntity response;
        try {
            UpdateResult result = this.repository.deleteSprint(input);
            response = new ResponseEntity(result, HttpStatus.OK);
        } catch (MongoException e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
