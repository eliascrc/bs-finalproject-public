package cr.brainstation.bsfinalproject.i18n;

import java.util.ListResourceBundle;

import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.ADDRESS_FOR_USER_NOT_AUTHORIZED_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.ADDRESS_NOT_FOUND_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.CREDIT_CARD_FOR_USER_NOT_AUTHORIZED_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.CREDIT_CARD_NOT_FOUND_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_ADDRESS_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_CREDIT_CARD_BRAND_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_CREDIT_CARD_EXPIRATION_DATE_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_CREDIT_CARD_TOKEN_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_CREDIT_CARD_USER_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_PRODUCT_IMAGE_URL_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_PRODUCT_NAME_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_USER_EMAIL_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_USER_NAME_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_USER_PASSWORD_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.INVALID_CREDIT_CARD_FOUR_DIGITS_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.USER_NOT_FOUND_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.USER_ALREADY_EXISTS_MSG;

public class Messages_en extends ListResourceBundle {

    private Object[][] contents = {
            { EMPTY_PRODUCT_NAME_MSG, "The product's name cannot be empty." },
            { EMPTY_PRODUCT_IMAGE_URL_MSG, "The product's image url cannot be empty." },
            { EMPTY_USER_EMAIL_MSG, "The user's email cannot be empty." },
            { EMPTY_USER_NAME_MSG, "The user's name cannot be empty." },
            { EMPTY_USER_PASSWORD_MSG, "The user's password cannot be empty." },
            { USER_ALREADY_EXISTS_MSG, "The user tried to be created already exists." },
            { EMPTY_ADDRESS_MSG, "The user's address cannot be empty" },
            { USER_NOT_FOUND_MSG, "The user was not found" },
            { ADDRESS_NOT_FOUND_MSG, "The address was not found" },
            { ADDRESS_FOR_USER_NOT_AUTHORIZED_MSG, "The address does not belong to the user" },
            { EMPTY_CREDIT_CARD_TOKEN_MSG, "The credit card token must not be empty" },
            { EMPTY_CREDIT_CARD_BRAND_MSG, "The credit card brand must not be empty" },
            { EMPTY_CREDIT_CARD_EXPIRATION_DATE_MSG, "The credit card expiration date must not be empty" },
            { INVALID_CREDIT_CARD_FOUR_DIGITS_MSG, "The credit card last four digits must not be empty and needs to be exactly four digits" },
            { EMPTY_CREDIT_CARD_USER_MSG, "The credit card user must not be empty" },
            { CREDIT_CARD_NOT_FOUND_MSG, "The credit card was not found" },
            { CREDIT_CARD_FOR_USER_NOT_AUTHORIZED_MSG, "The credit card does not belong to the user" },
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
