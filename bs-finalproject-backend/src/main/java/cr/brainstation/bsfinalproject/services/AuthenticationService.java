package cr.brainstation.bsfinalproject.services;

import cr.brainstation.bsfinalproject.model.User;

public interface AuthenticationService {

    /**
     * Verifies all the fields within the sent user and persists it in the database.
     * @param user object with all the necessary fields to create a {@link cr.brainstation.bsfinalproject.db.dtos.UserDTO}
     */
    void signUp(User user);

}
