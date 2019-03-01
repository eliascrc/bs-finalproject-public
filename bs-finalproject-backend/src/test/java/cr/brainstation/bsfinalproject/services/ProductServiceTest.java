package cr.brainstation.bsfinalproject.services;

import cr.brainstation.bsfinalproject.db.daos.ProductDAO;
import cr.brainstation.bsfinalproject.db.dtos.ProductDTO;
import cr.brainstation.bsfinalproject.model.Product;
import cr.brainstation.bsfinalproject.services.impl.ProductServiceImpl;
import cr.brainstation.bsfinalproject.utils.exceptions.EmptyFieldException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    private final String PRODUCT_NAME = "Test Name";
    private final int PRODUCT_ID = 1;
    private final String PRODUCT_IMG = "img.jpg";
    private final int PRODUCT_RANKING = 5;

    @Mock
    private ProductDAO productDAO;

    @InjectMocks
    public static ProductService productService;

    @BeforeClass
    public static void setup() {
        productService =  new ProductServiceImpl();
    }


    @Test
    public void test_BookCreateThenSuccessful() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setName(PRODUCT_NAME);
        product.setImageUrl(PRODUCT_IMG);
        product.setAverageRating(PRODUCT_RANKING);

        when(productDAO.save(any(ProductDTO.class))).thenReturn(new ProductDTO(product));
        Product productResponse = productService.create(product);

        verify(productDAO, times(1)).save(any(ProductDTO.class));
        Assert.assertEquals(productResponse, product);
    }

    @Test
    public void test_BookCreateWithEmptyNameThenException() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setName(null);

        try {
            Product productResponse = productService.create(product);
            Assert.fail();
        } catch (EmptyFieldException e) {
            // The test should enter here
        }
    }

    @Test
    public void test_BookCreateWithEmptyImageThenException() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setName(PRODUCT_NAME);
        product.setImageUrl("");

        try {
            Product productResponse = productService.create(product);
            Assert.fail();
        } catch (EmptyFieldException e) {
            // The test should enter here
        }
    }

}
