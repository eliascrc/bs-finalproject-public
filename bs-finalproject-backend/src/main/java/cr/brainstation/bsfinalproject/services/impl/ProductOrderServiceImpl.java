package cr.brainstation.bsfinalproject.services.impl;

import cr.brainstation.bsfinalproject.db.daos.ProductOrderDAO;
import cr.brainstation.bsfinalproject.db.dtos.ProductOrderDTO;
import cr.brainstation.bsfinalproject.model.ProductOrder;
import cr.brainstation.bsfinalproject.services.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service("productOrderService")
@Transactional
public class ProductOrderServiceImpl implements ProductOrderService {

    private ProductOrderDAO productOrderDAO;

    public ProductOrderServiceImpl() {
    }

    @Autowired
    public ProductOrderServiceImpl(ProductOrderDAO productOrderDAO) {
        this.productOrderDAO = productOrderDAO;
    }

    @Override
    public Set<ProductOrder> getProductOrdersForUser(String userId) {
        Set<ProductOrderDTO> orders = this.productOrderDAO.findAllByBuyerId(userId);
        Set<ProductOrder> result = new HashSet<>();
        orders.forEach( productOrderDTO -> {
            ProductOrder productOrder = new ProductOrder(productOrderDTO);
            productOrder.setImageUrl(productOrderDTO.getProduct().getImageUrl());
            result.add(productOrder);
        });

        return result;
    }
}
