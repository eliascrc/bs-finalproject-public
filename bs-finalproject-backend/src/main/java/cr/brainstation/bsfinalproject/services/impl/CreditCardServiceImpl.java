package cr.brainstation.bsfinalproject.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import cr.brainstation.bsfinalproject.constants.StripeConstants;
import cr.brainstation.bsfinalproject.db.daos.CreditCardDAO;
import cr.brainstation.bsfinalproject.db.daos.UserDAO;
import cr.brainstation.bsfinalproject.db.dtos.CreditCardDTO;
import cr.brainstation.bsfinalproject.db.dtos.UserDTO;
import cr.brainstation.bsfinalproject.i18n.ResourceBundleLoader;
import cr.brainstation.bsfinalproject.model.CreditCard;
import cr.brainstation.bsfinalproject.services.CreditCardService;
import cr.brainstation.bsfinalproject.utils.exceptions.EmptyFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.InvalidFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.InvalidPaymentInformationException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotAuthorizedException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.CREDIT_CARD_FOR_USER_NOT_AUTHORIZED_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.CREDIT_CARD_NOT_FOUND_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_CREDIT_CARD_BRAND_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_CREDIT_CARD_EXPIRATION_DATE_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_CREDIT_CARD_TOKEN_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_CREDIT_CARD_USER_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.INVALID_CREDIT_CARD_FOUR_DIGITS_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.USER_NOT_FOUND_MSG;
import static cr.brainstation.bsfinalproject.constants.StripeConstants.STRIPE_SOURCE_KEY;

@Service("creditCardService")
@Transactional
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardDAO creditCardDAO;
    private UserDAO userDAO;

    public CreditCardServiceImpl() {
    }

    @Autowired
    public CreditCardServiceImpl(CreditCardDAO creditCardDAO, UserDAO userDAO) {
        this.creditCardDAO = creditCardDAO;
        this.userDAO = userDAO;
    }

    /**
     * @see CreditCardService#create(CreditCard, String)
     */
    @Override
    public CreditCard create(CreditCard creditCard, String userId) {

        if (StringUtils.isEmpty(creditCard.getToken())) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(EMPTY_CREDIT_CARD_TOKEN_MSG));
        }

        if (StringUtils.isEmpty(creditCard.getBrand())) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(EMPTY_CREDIT_CARD_BRAND_MSG));
        }

        if (StringUtils.isEmpty(creditCard.getExpiration())) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(EMPTY_CREDIT_CARD_EXPIRATION_DATE_MSG));
        }

        if (StringUtils.isEmpty(creditCard.getLastFourDigits())
                || creditCard.getLastFourDigits().length() != 4) {
            throw new InvalidFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(INVALID_CREDIT_CARD_FOUR_DIGITS_MSG));
        }

        if (StringUtils.isEmpty(userId)) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(EMPTY_CREDIT_CARD_USER_MSG));
        }

        UserDTO userDTO = this.userDAO.findById(userId).orElse(null);
        if (userDTO == null) {
            throw new NotFoundException(ResourceBundleLoader.getDefaultBundle()
                    .getString(USER_NOT_FOUND_MSG));
        }

        Stripe.apiKey = StripeConstants.STRIPE_API_KEY;
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put(STRIPE_SOURCE_KEY, creditCard.getToken());

        Customer customer;
        try {
            customer = Customer.create(chargeParams);
        } catch (StripeException e) {
            throw new InvalidPaymentInformationException(e.getStripeError().getMessage());
        }

        CreditCardDTO dto = new CreditCardDTO(creditCard);
        dto.setOwner(userDTO);
        dto.setToken(customer.getId());
        dto = this.creditCardDAO.save(dto);
        CreditCard response = new CreditCard(dto);
        response.setToken(null);

        return response;
    }

    /**
     * @see CreditCardService#delete(int, String)
     */
    @Override
    public void delete(int creditCardId, String userId) {
        CreditCardDTO creditCardDTO = this.creditCardDAO.findById(creditCardId).orElse(null);
        if (creditCardDTO == null) {
            throw new NotFoundException(ResourceBundleLoader.getDefaultBundle()
                    .getString(CREDIT_CARD_NOT_FOUND_MSG));
        }

        UserDTO userDTO = this.userDAO.findById(userId).orElse(null);
        if (userDTO == null) {
            throw new NotFoundException(ResourceBundleLoader.getDefaultBundle()
                    .getString(USER_NOT_FOUND_MSG));
        }

        Set<CreditCardDTO> matches = userDTO.getCreditCards().stream()
                .filter(savedCreditCard -> savedCreditCard.getId() == creditCardId)
                .collect(Collectors.toSet());

        if (matches.size() < 1) {
            throw new NotAuthorizedException(ResourceBundleLoader.getDefaultBundle()
                    .getString(CREDIT_CARD_FOR_USER_NOT_AUTHORIZED_MSG));
        }

        userDTO.getCreditCards().remove(creditCardDTO);
        this.userDAO.save(userDTO);
    }
}
