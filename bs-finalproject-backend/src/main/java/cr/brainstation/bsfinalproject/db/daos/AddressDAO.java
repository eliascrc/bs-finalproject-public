package cr.brainstation.bsfinalproject.db.daos;

import cr.brainstation.bsfinalproject.db.dtos.AddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface in charge of the interaction with the database for the addresses of a user.
 */
public interface AddressDAO extends JpaRepository<AddressDTO, Integer> {
}
