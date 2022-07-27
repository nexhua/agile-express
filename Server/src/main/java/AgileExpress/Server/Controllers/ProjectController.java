package AgileExpress.Server.Controllers;

import AgileExpress.Server.Constants.ApiRouteConstants;
import AgileExpress.Server.Constants.ErrorMessages;
import AgileExpress.Server.Entities.Project;
import AgileExpress.Server.Helpers.ReflectionHelper;
import AgileExpress.Server.Inputs.*;
import AgileExpress.Server.Repositories.ProjectRepository;
import AgileExpress.Server.Utility.PropertyInfo;
import com.mongodb.MongoException;
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

    public ProjectController(ProjectRepository repository) {
        this.repository = repository;
    }

    //GET PROJECT
    @GetMapping(ApiRouteConstants.GetProject)
    public ResponseEntity<?> getProject(@PathVariable String projectID) {
        ResponseEntity response;
        try {
            Optional<Project> project = repository.findById(projectID);
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
    @GetMapping(ApiRouteConstants.GetProjects)
    public ResponseEntity<?> getProjects() {
        ResponseEntity response;
        try {
            List<Project> result = this.repository.findAll();
            response = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //CREATE PROJECT
    @PostMapping(ApiRouteConstants.GetProjects)
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

    //GET TASK FROM PROJECT
    @GetMapping(ApiRouteConstants.ProjectsTask)
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
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
