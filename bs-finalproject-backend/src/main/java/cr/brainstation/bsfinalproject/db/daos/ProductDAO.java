package cr.brainstation.bsfinalproject.db.daos;

import cr.brainstation.bsfinalproject.db.dtos.ProductDTO;
import cr.brainstation.bsfinalproject.enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface in charge of the interaction with the database for the products of a user.
 */
public interface ProductDAO extends JpaRepository<ProductDTO, Integer> {

    @Query("from ProductDTO where featured = true")
    List<ProductDTO> findFeaturedProducts();

    @Override
    Page<ProductDTO> findAll(Pageable pageable);

    Page<ProductDTO> findAllByCategory(ProductCategory category, Pageable pageable);

    Page<ProductDTO> findAllByNameContaining(String name, Pageable pageable);

    Page<ProductDTO> findAllByCategoryAndNameContaining(ProductCategory productCategory, String name, Pageable pageable);

}
