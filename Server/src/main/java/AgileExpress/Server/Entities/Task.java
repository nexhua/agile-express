package AgileExpress.Server.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("tasks")
public class Task extends TaskBase {

    public Task() { }
}
