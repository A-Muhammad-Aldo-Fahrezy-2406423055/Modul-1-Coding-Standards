package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTest {
    Map<String, String> paymentDataVoucher;
    Map<String, String> paymentDataCOD;
    Order order;

    @BeforeEach
    void setUp() {
        paymentDataVoucher = new HashMap<>();
        paymentDataVoucher.put("voucherCode", "ESHOP1234ABC5678");

        paymentDataCOD = new HashMap<>();
        paymentDataCOD.put("address", "Jalan Margonda Raya");
        paymentDataCOD.put("deliveryFee", "10000");

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("order-1", products, 12345678L, "Author");
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        Payment payment = new Payment("pay-1", "VOUCHER", paymentDataVoucher, order);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejected() {
        paymentDataVoucher.put("voucherCode", "ESHOP1234");
        Payment payment = new Payment("pay-2", "VOUCHER", paymentDataVoucher, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCODSuccess() {
        Payment payment = new Payment("pay-3", "CASH_ON_DELIVERY", paymentDataCOD, order);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentCODRejected() {
        paymentDataCOD.remove("address");
        Payment payment = new Payment("pay-4", "CASH_ON_DELIVERY", paymentDataCOD, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("pay-5", "MAGIC_BEANS", paymentDataCOD, order);
        });
    }
}