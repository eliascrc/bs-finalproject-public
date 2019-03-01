package cr.brainstation.bsfinalproject.services.impl;

import cr.brainstation.bsfinalproject.db.daos.AddressDAO;
import cr.brainstation.bsfinalproject.db.daos.UserDAO;
import cr.brainstation.bsfinalproject.db.dtos.AddressDTO;
import cr.brainstation.bsfinalproject.db.dtos.UserDTO;
import cr.brainstation.bsfinalproject.i18n.ResourceBundleLoader;
import cr.brainstation.bsfinalproject.model.Address;
import cr.brainstation.bsfinalproject.services.AddressService;
import cr.brainstation.bsfinalproject.utils.exceptions.EmptyFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotAuthorizedException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.ADDRESS_FOR_USER_NOT_AUTHORIZED_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.ADDRESS_NOT_FOUND_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_ADDRESS_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.USER_NOT_FOUND_MSG;

/**
 * Implementation of {@link AddressService}
 */
@Service("addressService")
public class AddressServiceImpl implements AddressService {

    private AddressDAO addressDAO;
    private UserDAO userDAO;

    public AddressServiceImpl() {
    }

    @Autowired
    public AddressServiceImpl(AddressDAO addressDAO, UserDAO userDAO) {
        this.addressDAO = addressDAO;
        this.userDAO = userDAO;
    }

    /**
     * @see AddressService#create(Address, String)
     */
    @Transactional
    @Override
    public Address create(Address address, String userId) {

        if (StringUtils.isEmpty(address.getAddress())) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(EMPTY_ADDRESS_MSG));
        }

        UserDTO userDTO = this.userDAO.findById(userId).orElse(null);
        if (userDTO == null) {
            throw new NotFoundException(ResourceBundleLoader.getDefaultBundle()
                    .getString(USER_NOT_FOUND_MSG));
        }

        AddressDTO addressDTO = new AddressDTO(address);
        addressDTO.setUser(userDTO);
        addressDTO = this.addressDAO.save(addressDTO);

        return new Address(addressDTO);
    }

    /**
     * @see AddressService#delete(int, String)
     */
    @Transactional
    @Override
    public void delete(int addressId, String userId) {
        AddressDTO addressDTO = this.addressDAO.findById(addressId).orElse(null);

        if (addressDTO == null) {
            throw new NotFoundException(ResourceBundleLoader.getDefaultBundle()
                    .getString(ADDRESS_NOT_FOUND_MSG));
        }

        UserDTO userDTO = this.userDAO.findById(userId).orElse(null);
        if (userDTO == null) {
            throw new NotFoundException(ResourceBundleLoader.getDefaultBundle()
                    .getString(USER_NOT_FOUND_MSG));
        }

        Set<AddressDTO> matches = userDTO.getAddresses().stream()
                .filter(address -> address.getId() == addressId)
                .collect(Collectors.toSet());

        if (matches.size() < 1) {
            throw new NotAuthorizedException(ResourceBundleLoader.getDefaultBundle()
                    .getString(ADDRESS_FOR_USER_NOT_AUTHORIZED_MSG));
        }

        userDTO.getAddresses().remove(addressDTO);
        this.userDAO.save(userDTO);
    }

    /**
     * @see AddressService#edit(Address, String)
     */
    @Transactional
    @Override
    public Address edit(Address address, String userId) {
        AddressDTO addressDTO = this.addressDAO.findById(address.getId()).orElse(null);

        if (addressDTO == null) {
            throw new NotFoundException(ResourceBundleLoader.getDefaultBundle()
                    .getString(ADDRESS_NOT_FOUND_MSG));
        }

        UserDTO userDTO = this.userDAO.findById(userId).orElse(null);
        if (userDTO == null) {
            throw new NotFoundException(ResourceBundleLoader.getDefaultBundle()
                    .getString(USER_NOT_FOUND_MSG));
        }

        // Filters the addresses of a user in order to see if it belongs to it.
        Set<AddressDTO> matches = userDTO.getAddresses().stream()
                .filter(storedAddress -> storedAddress.getId() == address.getId())
                .collect(Collectors.toSet());

        if (matches.size() < 1) {
            throw new NotAuthorizedException(ResourceBundleLoader.getDefaultBundle()
                    .getString(ADDRESS_FOR_USER_NOT_AUTHORIZED_MSG));
        }

        addressDTO.setAddress(address.getAddress());
        addressDTO = this.addressDAO.save(addressDTO);
        return new Address(addressDTO);
    }
}
