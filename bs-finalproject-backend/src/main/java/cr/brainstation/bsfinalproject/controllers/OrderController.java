package cr.brainstation.bsfinalproject.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import cr.brainstation.bsfinalproject.model.CustomResponse;
import cr.brainstation.bsfinalproject.model.Order;
import cr.brainstation.bsfinalproject.model.ProductOrder;
import cr.brainstation.bsfinalproject.services.OrderService;
import cr.brainstation.bsfinalproject.services.ProductOrderService;
import cr.brainstation.bsfinalproject.utils.exceptions.InvalidFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.InvalidPaymentInformationException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotFoundException;
import cr.brainstation.bsfinalproject.utils.exceptions.PaymentChargeIncompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@CrossOrigin
@RestController
public class OrderController {

    private final OrderService orderService;
    private final ProductOrderService productOrderService;

    @Autowired
    public OrderController(OrderService orderService, ProductOrderService productOrderService) {
        this.orderService = orderService;
        this.productOrderService = productOrderService;
    }

    @PostMapping("/order")
    public ResponseEntity<CustomResponse> createOrder(@RequestBody Order order,
                                                      @RequestAttribute("decodedJWT") DecodedJWT decodedJWT) {

        if (order.getProductOrders() == null || order.getProductOrders().size() == 0) {
            return new ResponseEntity<>(new CustomResponse("Invalid order products"),
                    HttpStatus.BAD_REQUEST);
        }

        Order savedOrder;
        order.setBuyerId(decodedJWT.getClaim("cognito:username").asString());
        try {
            savedOrder = this.orderService.create(order);
        } catch (InvalidFieldException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

        } catch (NotFoundException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.NOT_FOUND);

        } catch (PaymentChargeIncompleteException | InvalidPaymentInformationException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(new CustomResponse(savedOrder), HttpStatus.OK);
    }

    @GetMapping("/order/product")
    public ResponseEntity<CustomResponse> getProductOrders(@RequestAttribute("decodedJWT") DecodedJWT decodedJWT) {
        String userId = decodedJWT.getClaim("cognito:username").asString();
        Set<ProductOrder> result = this.productOrderService.getProductOrdersForUser(userId);
        return new ResponseEntity<>(new CustomResponse(result), HttpStatus.OK);
    }

}
