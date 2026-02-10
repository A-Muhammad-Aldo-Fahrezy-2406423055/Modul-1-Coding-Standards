package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    Product product;

    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"), this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }

    @Test
    void testGetProductIdIfNull() {
        Product emptyProduct = new Product();
        assertNull(emptyProduct.getProductId());
    }

    @Test
    void testGetProductNameIfNull() {
        Product emptyProduct = new Product();
        assertNull(emptyProduct.getProductName());
    }

    @Test
    void testGetProductQuantityIfNegative() {
        this.product.setProductQuantity(-1);

        // Angka negatif dilarang, default back to previous data
        assertEquals(100, this.product.getProductQuantity());
    }
}