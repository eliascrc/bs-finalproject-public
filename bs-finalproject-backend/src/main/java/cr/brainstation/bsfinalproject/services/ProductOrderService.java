package cr.brainstation.bsfinalproject.services;

import cr.brainstation.bsfinalproject.model.ProductOrder;

import java.util.Set;

public interface ProductOrderService {

    Set<ProductOrder> getProductOrdersForUser(String userId);

}
