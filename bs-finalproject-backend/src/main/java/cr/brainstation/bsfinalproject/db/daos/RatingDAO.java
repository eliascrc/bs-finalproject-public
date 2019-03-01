package cr.brainstation.bsfinalproject.db.daos;

import cr.brainstation.bsfinalproject.db.dtos.ProductDTO;
import cr.brainstation.bsfinalproject.db.dtos.RatingDTO;
import cr.brainstation.bsfinalproject.db.dtos.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface in charge of the interaction with the database for the products of a user.
 */
public interface RatingDAO extends JpaRepository<RatingDTO, Integer> {

    RatingDTO findByUserAndProduct(UserDTO user, ProductDTO product);

}
