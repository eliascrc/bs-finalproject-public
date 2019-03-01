package cr.brainstation.bsfinalproject.controllers;

import cr.brainstation.bsfinalproject.model.CustomResponse;
import cr.brainstation.bsfinalproject.model.Product;
import cr.brainstation.bsfinalproject.enums.ProductCategory;
import cr.brainstation.bsfinalproject.model.ProductOverview;
import cr.brainstation.bsfinalproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<CustomResponse> getProducts(@RequestParam(name="category", required = false) String productCategoryStr,
                                                      @RequestParam(name="searchTerm", required = false) String searchTerm,
                                                      Pageable pageable) {
        if (StringUtils.isEmpty(productCategoryStr) && StringUtils.isEmpty(searchTerm)) {
            Page<ProductOverview> responsePage = this.productService.getAll(pageable);
            return new ResponseEntity<>(new CustomResponse(responsePage), HttpStatus.OK);

        } else if (StringUtils.isEmpty(productCategoryStr)) {

            Page<ProductOverview> responsePage = this.productService.getAllBySearchTerm(searchTerm, pageable);
            return new ResponseEntity<>(new CustomResponse(responsePage), HttpStatus.OK);

        } else if (StringUtils.isEmpty(searchTerm)) {
            // Search by category
            ProductCategory productCategory;
            try {
                productCategory = ProductCategory.valueOf(productCategoryStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(new CustomResponse("Invalid category"), HttpStatus.BAD_REQUEST);
            }

            Page<ProductOverview> responsePage = this.productService.getAllByCategory(productCategory, pageable);
            return new ResponseEntity<>(new CustomResponse(responsePage), HttpStatus.OK);

        } else {
            // Search by category
            ProductCategory productCategory;
            try {
                productCategory = ProductCategory.valueOf(productCategoryStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(new CustomResponse("Invalid category"), HttpStatus.BAD_REQUEST);
            }

            Page<ProductOverview> responsePage = this.productService.getAllByCategoryAndSearchTerm(searchTerm,
                    productCategory, pageable);
            return new ResponseEntity<>(new CustomResponse(responsePage), HttpStatus.OK);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<CustomResponse> getProductById(@PathVariable String id) {
        Product response;
        try {
            response = this.productService.getById(Integer.parseInt(id));

            if (response == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch(NumberFormatException e) {
            return new ResponseEntity<>(new CustomResponse("The id is not a number"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new CustomResponse(response), HttpStatus.OK);
    }

    @GetMapping("/product/featured")
    public ResponseEntity<CustomResponse> getFeaturedProducts() {
        List<ProductOverview> responseList = this.productService.getFeaturedProducts();
        return new ResponseEntity<>(new CustomResponse(responseList), HttpStatus.OK);
    }

}
