package AgileExpress.Server.Seeding;

import AgileExpress.Server.Entities.Employee;
import AgileExpress.Server.Repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {
    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);
;

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            repository.save(new Employee("Bilbo Baggins", "burglar"));
            repository.save(new Employee("Frodo Baggins", "thief"));
        };
    }
}
