package cr.brainstation.bsfinalproject.services;

import cr.brainstation.bsfinalproject.model.Address;

public interface AddressService {

    /**
     * Verifies all the fields within the sent address and persists it in the database.
     * @param address object with all the necessary field to create an {@link cr.brainstation.bsfinalproject.db.dtos.AddressDTO}
     * @param userId the id of an existing user in the database.
     * @return The newly created address.
     */
    Address create(Address address, String userId);

    /**
     * Verifies the address and user id and deletes the stored address if exists.
     * @param addressId id necessary to retrieve the stored address.
     * @param userId the id of an existing user in the database.
     */
    void delete(int addressId, String userId);

    /**
     * Verifies all the fields within the sent address and persists the changes in the database.
     * @param address object with all the necessary field to retrieve and update an address in the database.
     * @param userId the id of an existing user in the database.
     * @return The newly updated address.
     */
    Address edit(Address address, String userId);

}
