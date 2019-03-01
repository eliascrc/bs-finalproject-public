package cr.brainstation.bsfinalproject.services;

import cr.brainstation.bsfinalproject.model.CreditCard;

public interface CreditCardService {

    /**
     * Verifies all the fields within the sent credit card and user id, and persists it in the database.
     * @param creditCard object with all the necessary fields to create a
     * {@link cr.brainstation.bsfinalproject.db.dtos.CreditCardDTO}
     * @param userId the id of the user to attach the credit card
     * @return The newly created payment.
     */
    CreditCard create(CreditCard creditCard, String userId);

    /**
     * Verifies the credit card and user id and deletes the stored credit card if exists.
     * @param creditCardId id necessary to retrieve the stored payment.
     * @param userId the id of an existing user in the database.
     */
    void delete(int creditCardId, String userId);

}
