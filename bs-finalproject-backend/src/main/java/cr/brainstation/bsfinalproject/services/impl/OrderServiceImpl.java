package cr.brainstation.bsfinalproject.services.impl;

import cr.brainstation.bsfinalproject.constants.CommonConstants;
import cr.brainstation.bsfinalproject.db.daos.AddressDAO;
import cr.brainstation.bsfinalproject.db.daos.CreditCardDAO;
import cr.brainstation.bsfinalproject.db.daos.OrderDAO;
import cr.brainstation.bsfinalproject.db.daos.ProductDAO;
import cr.brainstation.bsfinalproject.db.daos.ProductOrderDAO;
import cr.brainstation.bsfinalproject.db.daos.UserDAO;
import cr.brainstation.bsfinalproject.db.dtos.AddressDTO;
import cr.brainstation.bsfinalproject.db.dtos.CreditCardDTO;
import cr.brainstation.bsfinalproject.db.dtos.OrderDTO;
import cr.brainstation.bsfinalproject.db.dtos.ProductDTO;
import cr.brainstation.bsfinalproject.db.dtos.ProductOrderDTO;
import cr.brainstation.bsfinalproject.db.dtos.UserDTO;
import cr.brainstation.bsfinalproject.enums.OrderStatus;
import cr.brainstation.bsfinalproject.model.Address;
import cr.brainstation.bsfinalproject.model.CreditCard;
import cr.brainstation.bsfinalproject.model.Order;
import cr.brainstation.bsfinalproject.model.ProductOrder;
import cr.brainstation.bsfinalproject.services.EmailService;
import cr.brainstation.bsfinalproject.services.OrderService;
import cr.brainstation.bsfinalproject.services.PaymentService;
import cr.brainstation.bsfinalproject.utils.exceptions.InvalidFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotFoundException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static cr.brainstation.bsfinalproject.constants.CommonConstants.EMAIL_SUBJECT;
import static cr.brainstation.bsfinalproject.constants.CommonConstants.SELLER_EMAIL;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;
    private AddressDAO addressDAO;
    private ProductOrderDAO productOrderDAO;
    private ProductDAO productDAO;
    private UserDAO userDAO;
    private CreditCardDAO creditCardDAO;
    private PaymentService paymentService;
    private EmailService emailService;


    public OrderServiceImpl() {
    }

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, ProductOrderDAO productOrderDAO, ProductDAO productDAO, UserDAO userDAO,
                            AddressDAO addressDAO, CreditCardDAO creditCardDAO, PaymentService paymentService, EmailService emailService) {
        this.orderDAO = orderDAO;
        this.productOrderDAO = productOrderDAO;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.addressDAO = addressDAO;
        this.creditCardDAO = creditCardDAO;
        this.paymentService = paymentService;
        this.emailService = emailService;
    }

    @Override
    public void notifyEmailSent(int orderId) {
        OrderDTO savedOrder = this.orderDAO.findById(orderId).orElse(null);
        if (savedOrder != null) {
            savedOrder.setEmailSent(true);
            this.orderDAO.save(savedOrder);
        }
    }

    @Transactional
    @Override
    public Order create(Order order) {

        Address sentAddress = order.getAddress();
        if ( sentAddress == null || sentAddress.getId() < 1) {
            throw new InvalidFieldException("The address for the order is invalid");
        }

        CreditCard sentCreditCard = order.getCreditCard();
        if ( sentCreditCard == null || sentCreditCard.getId() < 1) {
            throw new InvalidFieldException("The credit card for the order is invalid");
        }

        AddressDTO addressDTO = this.addressDAO.findById(sentAddress.getId()).orElse(null);
        if (addressDTO == null) {
            throw new NotFoundException("The specified address was not found. Address id:"
                    + sentAddress.getId());
        }

        CreditCardDTO creditCardDTO = this.creditCardDAO.findById(sentCreditCard.getId()).orElse(null);
        if (creditCardDTO == null) {
            throw new NotFoundException("The specified credit card was not found. CreditCard id:"
                    + sentCreditCard.getId());
        }

        UserDTO storedUser = this.userDAO.findById(order.getBuyerId()).orElse(null);
        if (storedUser == null) {
            throw new NotFoundException("The specified user was not found. User id:"
                    + order.getBuyerId());
        }

        Set<ProductOrderDTO> savedProductOrders = new HashSet<>();
        for (ProductOrder productOrder : order.getProductOrders()) {

            if (productOrder.getQuantity() < 1) {
                throw new InvalidFieldException("Invalid product quantity: " + productOrder.getQuantity() +
                        " for a product order with product id " + productOrder.getProductId());
            }

            ProductDTO storedProduct = this.productDAO.findById(productOrder.getProductId()).orElse(null);
            if (storedProduct == null) {
                throw new NotFoundException("The specified product was not found. Product id:"
                        + productOrder.getProductId());
            }

            if (productOrder.getQuantity() > storedProduct.getInStock()) {
                throw new InvalidFieldException("The specified quantity surpasses the amount in stock. Product id: "
                        + productOrder.getProductId());
            }

            ProductOrderDTO productOrderDTO = new ProductOrderDTO();
            productOrderDTO.setQuantity(productOrder.getQuantity());
            productOrderDTO.setPrice(storedProduct.getPrice());
            productOrderDTO.setAmount(storedProduct.getAmount());
            productOrderDTO.setProduct(storedProduct);

            savedProductOrders.add(productOrderDTO);

            storedProduct.setInStock(storedProduct.getInStock() - productOrderDTO.getQuantity());
            this.productDAO.save(storedProduct);
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setShoppedAt(LocalDateTime.now());
        orderDTO.setBuyer(storedUser);
        orderDTO.setAddress(addressDTO);
        orderDTO.setOrderStatus(OrderStatus.WAITING_FOR_PAYMENT);

        BigDecimal total = BigDecimal.ZERO;
        for (ProductOrderDTO productOrderDTO : savedProductOrders) {
            productOrderDTO.setTotal(
                    productOrderDTO.getPrice().multiply(new BigDecimal(productOrderDTO.getQuantity()))
                            .setScale(2, BigDecimal.ROUND_FLOOR));
            total = total.add(productOrderDTO.getTotal());
        }
        orderDTO.setTotal(total);
        orderDTO = this.orderDAO.save(orderDTO);

        this.paymentService.makePayment(creditCardDTO, orderDTO);

        for (ProductOrderDTO productOrderDTO : savedProductOrders) {
            productOrderDTO.setOrder(orderDTO);
            this.productOrderDAO.save(productOrderDTO);
        }
        orderDTO.setProductOrders(savedProductOrders);
        orderDTO = this.orderDAO.save(orderDTO);

        Thread emailDispatcher = new Thread(new EmailDispatcher(this, this.emailService, orderDTO));
        emailDispatcher.start();

        return new Order(orderDTO);
    }

    class EmailDispatcher implements Runnable {

        private Logger LOG = LoggerFactory.getLogger(EmailDispatcher.class);

        private OrderService orderService;
        private EmailService emailService;
        private OrderDTO order;

        EmailDispatcher(OrderService orderService, EmailService emailService, OrderDTO order) {
            this.orderService = orderService;
            this.emailService = emailService;
            this.order = order;
        }

        @Override
        public void run() {
            VelocityContext context = new VelocityContext();
            context.put("name", order.getBuyer().getName());
            context.put("address", order.getAddress().getAddress());
            context.put("order", order.toString());

            try {

                this.emailService.sendEmail(SELLER_EMAIL, order.getBuyer().getEmail(), EMAIL_SUBJECT,
                        "templates/userNewOrder.vm", context);
                this.emailService.sendEmail(SELLER_EMAIL, SELLER_EMAIL, EMAIL_SUBJECT,
                        "templates/userNewOrder.vm", context);
                this.orderService.notifyEmailSent(order.getId());

            } catch (ResourceNotFoundException e) {
                LOG.error("The template for the order email was not found.");
            }
        }

    }

}
