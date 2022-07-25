package AgileExpress.Server.Controllers;

import AgileExpress.Server.Constants.ApiRouteConstants;
import AgileExpress.Server.Entities.Project;
import AgileExpress.Server.Inputs.CreateProjectInput;
import AgileExpress.Server.Repositories.ProjectRepository;
import com.mongodb.MongoException;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Optional;

@RestController
public class ProjectController {

    private final ProjectRepository repository;

    public ProjectController(ProjectRepository repository) {
        this.repository = repository;
    }

    @GetMapping(ApiRouteConstants.GetProject)
    public ResponseEntity<?> getProject(@RequestParam(name = "id") String id) {
        ResponseEntity response;
        try {
            Optional<Project> project = repository.findById(id);
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

    @PostMapping(ApiRouteConstants.AddProject)
    public ResponseEntity createProject(@RequestBody CreateProjectInput input) {
        DateTime startDateMilis = new DateTime(input.getStartDate());
        DateTime endDateMilis = new DateTime(input.getEndDate());
        Project project = new Project(input.getProjectName(), new Date(startDateMilis.getMillis()), new Date(endDateMilis.getMillis()));
        HttpStatus status = HttpStatus.OK;

        try {
            repository.insert(project);
        } catch (MongoException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(status);
    }
}
