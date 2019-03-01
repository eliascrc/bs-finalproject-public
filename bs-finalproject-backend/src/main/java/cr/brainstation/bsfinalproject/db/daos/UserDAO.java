package cr.brainstation.bsfinalproject.db.daos;

import cr.brainstation.bsfinalproject.db.dtos.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface in charge of the interaction with the database for the system's users.
 */
public interface UserDAO extends JpaRepository<UserDTO, String> {

    UserDTO findByEmail(String email);

    /**
     * Finds a user by id and eagerly fetches the user's addresses and credit cards.
     * @param id the user's id
     * @return the user dto with the specified collections loaded.
     */
    @Query("from UserDTO user left join fetch user.addresses left join fetch user.creditCards where user.id = :id")
    UserDTO findByIdEager(String id);

}
