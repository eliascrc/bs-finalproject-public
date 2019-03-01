package cr.brainstation.bsfinalproject.services;

import cr.brainstation.bsfinalproject.model.Order;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderService {

    Order create(Order order);
    void notifyEmailSent(int orderId);

}
