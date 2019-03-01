package cr.brainstation.bsfinalproject.services;

import cr.brainstation.bsfinalproject.db.dtos.CreditCardDTO;
import cr.brainstation.bsfinalproject.db.dtos.OrderDTO;

public interface PaymentService {

    void makePayment(CreditCardDTO creditCard, OrderDTO order);

}
