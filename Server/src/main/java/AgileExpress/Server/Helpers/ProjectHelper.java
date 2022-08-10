package AgileExpress.Server.Helpers;

import AgileExpress.Server.Entities.Assignee;
import AgileExpress.Server.Entities.Project;
import AgileExpress.Server.Entities.Sprint;
import AgileExpress.Server.Entities.Task;
import AgileExpress.Server.Inputs.Sprint.SprintActiveInput;
import AgileExpress.Server.Inputs.Sprint.SprintCreateInput;
import AgileExpress.Server.Inputs.Task.TaskAddAssigneeInput;
import AgileExpress.Server.Repositories.ProjectRepository;
import com.mongodb.MongoException;
import com.mongodb.client.result.UpdateResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static AgileExpress.Server.Constants.MongoConstants.Sprint;

public class ProjectHelper {

    public static boolean isUserAssigned(Optional<Project> optionalProject, TaskAddAssigneeInput input) {
        boolean isAssigned = false;

        if (!optionalProject.isEmpty()) {
            Project project = optionalProject.get();
            Optional<Task> optionalTask = project.getTask(input.getTaskID());
            if (!optionalTask.isEmpty()) {
                Task task = optionalTask.get();
                Optional<Assignee> optionalAssignee = task.getAssignee(input.getUserID());
                if (!optionalAssignee.isEmpty()) {
                    isAssigned = true;
                }
            }
        }

        return isAssigned;
    }


    public static void addFirstSprintAndActivate(String projectID, ProjectRepository repository) {
        Date startDate = new Date();
        Date end = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(end);
        c.add(Calendar.DATE, 14);
        end = c.getTime();
        String goal = "Not Provided";

        SprintCreateInput input = new SprintCreateInput(projectID, startDate, end, goal);

        try {
            UpdateResult result = repository.addSprint(input);

            if (result.getModifiedCount() > 0) {
                Optional<Project> optionalProject = repository.findById(projectID);

                if (!optionalProject.isEmpty()) {
                    Project project = optionalProject.get();

                    Sprint firstSprint = project.getSprints().get(0);

                    if (firstSprint != null) {
                        repository.setActiveSprint(new SprintActiveInput(projectID, firstSprint.getId()));
                    }
                }
            }
        } catch (MongoException e) {

        }
    }
}
