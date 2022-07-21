package AgileExpress.Server.Repositories;

import AgileExpress.Server.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
