package AgileExpress.Server.Helpers;

import AgileExpress.Server.Entities.Assignee;
import AgileExpress.Server.Entities.Project;
import AgileExpress.Server.Entities.Task;
import AgileExpress.Server.Inputs.Task.TaskAddAssigneeInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

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
}
