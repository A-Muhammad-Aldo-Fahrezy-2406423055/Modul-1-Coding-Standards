package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order("order-1", products, 123L, "Author");
        payment = new Payment("pay-1", "VOUCHER", new HashMap<>(), order);
    }

    @Test
    void testSaveAndFindById() {
        paymentRepository.save(payment);
        Payment found = paymentRepository.findById("pay-1");
        assertNotNull(found);
        assertEquals(payment.getId(), found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(paymentRepository.findById("invalid-id"));
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment);
        List<Payment> all = paymentRepository.findAll();
        assertEquals(1, all.size());
    }
}