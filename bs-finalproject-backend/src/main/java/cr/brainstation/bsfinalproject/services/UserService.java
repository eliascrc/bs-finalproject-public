package cr.brainstation.bsfinalproject.services;

import cr.brainstation.bsfinalproject.model.User;
import cr.brainstation.bsfinalproject.model.UserDetails;

public interface UserService {

    User getUserById(String id);
    User getUserByEmail(String username);
    UserDetails getUserDetails(String id);
    User create(User user);

}
