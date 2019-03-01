package cr.brainstation.bsfinalproject.db.daos;

import cr.brainstation.bsfinalproject.db.dtos.ProductOrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * Interface in charge of the interaction with the database for the products orders of a user.
 */
public interface ProductOrderDAO extends JpaRepository<ProductOrderDTO, Integer> {

    @Query("from ProductOrderDTO p where p.order.buyer.id = :buyerId")
    Set<ProductOrderDTO> findAllByBuyerId(String buyerId);

}
