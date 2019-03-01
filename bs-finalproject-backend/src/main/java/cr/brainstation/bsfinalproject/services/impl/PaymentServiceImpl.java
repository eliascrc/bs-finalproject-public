package cr.brainstation.bsfinalproject.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import cr.brainstation.bsfinalproject.constants.StripeConstants;
import cr.brainstation.bsfinalproject.db.daos.OrderDAO;
import cr.brainstation.bsfinalproject.db.dtos.CreditCardDTO;
import cr.brainstation.bsfinalproject.db.dtos.OrderDTO;
import cr.brainstation.bsfinalproject.enums.OrderStatus;
import cr.brainstation.bsfinalproject.services.PaymentService;
import cr.brainstation.bsfinalproject.utils.exceptions.InvalidPaymentInformationException;
import cr.brainstation.bsfinalproject.utils.exceptions.PaymentChargeIncompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static cr.brainstation.bsfinalproject.constants.StripeConstants.APPLICATION_CURRENCY;
import static cr.brainstation.bsfinalproject.constants.StripeConstants.CREDIT_CARD_ERROR;
import static cr.brainstation.bsfinalproject.constants.StripeConstants.STRIPE_AMOUNT_KEY;
import static cr.brainstation.bsfinalproject.constants.StripeConstants.STRIPE_CURRENCY_KEY;
import static cr.brainstation.bsfinalproject.constants.StripeConstants.STRIPE_CUSTOMER_KEY;
import static cr.brainstation.bsfinalproject.constants.StripeConstants.STRIPE_SOURCE_KEY;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private OrderDAO orderDAO;

    public PaymentServiceImpl() {
    }

    @Autowired
    public PaymentServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void makePayment(CreditCardDTO creditCard, OrderDTO order) {
        Stripe.apiKey = StripeConstants.STRIPE_API_KEY;

        int amountToCharge = order.getTotal().multiply(new BigDecimal(100)).intValue();

        Map<String, Object> chargeMap = new HashMap<>();
        chargeMap.put(STRIPE_AMOUNT_KEY, amountToCharge);
        chargeMap.put(STRIPE_CURRENCY_KEY, APPLICATION_CURRENCY);
        chargeMap.put(STRIPE_CUSTOMER_KEY, creditCard.getToken()); // obtained via Stripe.js

        try {
            Charge charge = Charge.create(chargeMap);
            order.setOrderStatus(OrderStatus.PAYMENT_SUCCESS);
            order.setChargeId(charge.getId());
            this.orderDAO.save(order);

        } catch (StripeException e) {
            order.setOrderStatus(OrderStatus.PAYMENT_FAILED);
            this.orderDAO.save(order);

            if (e.getStripeError().getType().equals(CREDIT_CARD_ERROR)) {
                throw new InvalidPaymentInformationException(e.getStripeError().getMessage());
            } else {
                throw new PaymentChargeIncompleteException(e.getStripeError().getMessage());
            }
        }
    }
}
