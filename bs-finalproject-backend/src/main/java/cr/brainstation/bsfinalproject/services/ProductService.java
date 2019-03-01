package cr.brainstation.bsfinalproject.services;

import cr.brainstation.bsfinalproject.model.Product;
import cr.brainstation.bsfinalproject.enums.ProductCategory;
import cr.brainstation.bsfinalproject.model.ProductOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product create(Product product);
    Product getById(Integer id);
    Page<ProductOverview> getAll(Pageable pageable);
    Page<ProductOverview> getAllByCategory(ProductCategory productCategory, Pageable pageable);
    Page<ProductOverview> getAllBySearchTerm(String searchTerm, Pageable pageable);
    Page<ProductOverview> getAllByCategoryAndSearchTerm(String searchTerm, ProductCategory productCategory,
                                                        Pageable pageable);
    List<ProductOverview> getFeaturedProducts();

}
