package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockitoBean private PaymentService paymentService;
    private Payment payment;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order("order-1", products, 123456L, "Author");
        payment = new Payment("pay-1", "VOUCHER", new HashMap<>(), order);
    }

    @Test
    void testPaymentDetailFormPage() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/detail_form"));
    }

    @Test
    void testPaymentDetailPage() throws Exception {
        when(paymentService.getPayment("pay-1")).thenReturn(payment);
        mockMvc.perform(get("/payment/detail/pay-1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testPaymentAdminListPage() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void testPaymentAdminDetailPage() throws Exception {
        when(paymentService.getPayment("pay-1")).thenReturn(payment);
        mockMvc.perform(get("/payment/admin/detail/pay-1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testPaymentAdminSetStatusPost() throws Exception {
        when(paymentService.getPayment("pay-1")).thenReturn(payment);
        mockMvc.perform(post("/payment/admin/set-status/pay-1").param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));
    }
}