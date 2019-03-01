package cr.brainstation.bsfinalproject.db.daos;

import cr.brainstation.bsfinalproject.db.dtos.CreditCardDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface in charge of the interaction with the database for the credit cards of a user.
 */
public interface CreditCardDAO extends JpaRepository<CreditCardDTO, Integer> {
}
