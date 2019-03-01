package cr.brainstation.bsfinalproject.db.daos;

import cr.brainstation.bsfinalproject.db.dtos.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface in charge of the interaction with the database for the orders of a user.
 */
public interface OrderDAO extends JpaRepository<OrderDTO, Integer> {
}
