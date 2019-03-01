package cr.brainstation.bsfinalproject.constants;

/**
 * Constants used within the stripe API in order to authenticated and create charges to a credit card.
 */
public interface StripeConstants {

    String STRIPE_API_KEY = "sk_test_TsppTsFtxgXP2iGaHqbVCdLG";
    String STRIPE_AMOUNT_KEY = "amount";
    String STRIPE_CURRENCY_KEY = "currency";
    String STRIPE_SOURCE_KEY = "source";
    String STRIPE_CUSTOMER_KEY = "customer";
    String APPLICATION_CURRENCY = "usd";
    String CREDIT_CARD_ERROR = "card_error";


}
