package AgileExpress.Server.Controllers;

import AgileExpress.Server.Constants.ApiRouteConstants;
import AgileExpress.Server.Entities.Project;
import AgileExpress.Server.Inputs.ProjectInput;
import AgileExpress.Server.Repositories.ProjectRepository;
import com.mongodb.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    private final ProjectRepository repository;

    public ProjectController(ProjectRepository repository) {
        this.repository = repository;
    }

    @PostMapping(ApiRouteConstants.AddProject)
    public ResponseEntity createProject(@RequestBody ProjectInput input) {
        Project project = new Project(input.getProjectName(), input.getStartDate(), input.getEndDate());
        HttpStatus status = HttpStatus.OK;

        try {
            repository.insert(project);
        } catch (MongoException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(status);
    }
}
