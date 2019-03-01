package cr.brainstation.bsfinalproject.services.impl;

import cr.brainstation.bsfinalproject.constants.ResourceBundleConstants;
import cr.brainstation.bsfinalproject.db.daos.ProductDAO;
import cr.brainstation.bsfinalproject.db.dtos.ProductDTO;
import cr.brainstation.bsfinalproject.model.Product;
import cr.brainstation.bsfinalproject.enums.ProductCategory;
import cr.brainstation.bsfinalproject.model.ProductOverview;
import cr.brainstation.bsfinalproject.services.ProductService;
import cr.brainstation.bsfinalproject.utils.exceptions.EmptyFieldException;
import cr.brainstation.bsfinalproject.i18n.ResourceBundleLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public ProductServiceImpl() {
    }

    @Override
    public Product create(Product product) {

        if (StringUtils.isEmpty(product.getName())) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(ResourceBundleConstants.EMPTY_PRODUCT_NAME_MSG));
        }

        if (StringUtils.isEmpty(product.getImageUrl())) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(ResourceBundleConstants.EMPTY_PRODUCT_IMAGE_URL_MSG));
        }

        ProductDTO dto = this.productDAO.save(new ProductDTO(product));
        return new Product(dto);
    }

    @Override
    public Product getById(Integer id) {
        ProductDTO response = this.productDAO.findById(id).orElse(null);

        return response == null ? null : new Product(response);
    }

    @Override
    public Page<ProductOverview> getAll(Pageable pageable) {
        Page<ProductDTO> productDTOPage = this.productDAO.findAll(pageable);

        return productDTOPage.map(ProductOverview::new);
    }

    @Override
    public Page<ProductOverview> getAllByCategory(ProductCategory productCategory, Pageable pageable) {
        Page<ProductDTO> productDTOPage = this.productDAO.findAllByCategory(productCategory, pageable);

        return productDTOPage.map(ProductOverview::new);
    }

    @Override
    public Page<ProductOverview> getAllBySearchTerm(String searchTerm, Pageable pageable) {
        Page<ProductDTO> productDTOPage = this.productDAO.findAllByNameContaining(searchTerm, pageable);

        return productDTOPage.map(ProductOverview::new);
    }

    @Override
    public Page<ProductOverview> getAllByCategoryAndSearchTerm(String searchTerm, ProductCategory productCategory,
                                                               Pageable pageable) {
        Page<ProductDTO> productDTOPage = this.productDAO.findAllByCategoryAndNameContaining(productCategory,
                searchTerm, pageable);

        return productDTOPage.map(ProductOverview::new);
    }

    @Override
    public List<ProductOverview> getFeaturedProducts() {
        List<ProductDTO> productDAOList = this.productDAO.findFeaturedProducts();
        List<ProductOverview> response = new ArrayList<>();
        productDAOList.forEach(product -> response.add(new ProductOverview(product)));
        return response;
    }

}
