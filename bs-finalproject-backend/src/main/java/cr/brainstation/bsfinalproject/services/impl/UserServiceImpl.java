package cr.brainstation.bsfinalproject.services.impl;

import cr.brainstation.bsfinalproject.constants.ResourceBundleConstants;
import cr.brainstation.bsfinalproject.db.daos.UserDAO;
import cr.brainstation.bsfinalproject.db.dtos.UserDTO;
import cr.brainstation.bsfinalproject.i18n.ResourceBundleLoader;
import cr.brainstation.bsfinalproject.model.User;
import cr.brainstation.bsfinalproject.model.UserDetails;
import cr.brainstation.bsfinalproject.services.UserService;
import cr.brainstation.bsfinalproject.utils.exceptions.EmptyFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getUserById(String id) {
        UserDTO response = this.userDAO.findById(id).orElse(null);
        return response == null ? null : new User(response);
    }

    @Override
    public User getUserByEmail(String email) {
        UserDTO response = this.userDAO.findByEmail(email);
        return response == null ? null : new User(response);
    }

    @Override
    public UserDetails getUserDetails(String id) {
        UserDTO dtoResponse = this.userDAO.findByIdEager(id);

        UserDetails userDetails = null;
        if (dtoResponse != null) {
            userDetails = new UserDetails(dtoResponse);
            userDetails.getCreditCards().forEach(creditCard -> creditCard.setToken(null));
        }
        return userDetails;
    }

    @Override
    public User create(User user) {

        if (StringUtils.isEmpty(user.getEmail())) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(ResourceBundleConstants.EMPTY_USER_EMAIL_MSG));
        }

        if (StringUtils.isEmpty(user.getName())) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(ResourceBundleConstants.EMPTY_USER_NAME_MSG));
        }

        if (this.userDAO.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException(ResourceBundleLoader.getDefaultBundle()
                    .getString(ResourceBundleConstants.USER_ALREADY_EXISTS_MSG) + user.getEmail());
        }

        UserDTO dto = new UserDTO(user);
        dto = this.userDAO.save(dto);

        return new User(dto);
    }
}
