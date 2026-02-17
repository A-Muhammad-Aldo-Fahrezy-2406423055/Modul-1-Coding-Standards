package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);
        Product createdProduct = productService.create(product);
        assertEquals(product.getProductId(), createdProduct.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindById() {
        when(productRepository.findById(product.getProductId())).thenReturn(product);
        Product foundProduct = productService.findById(product.getProductId());
        assertEquals(product.getProductId(), foundProduct.getProductId());
        verify(productRepository, times(1)).findById(product.getProductId());
    }

    @Test
    void testFindByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        when(productRepository.findById(nonExistentId)).thenReturn(null);
        Product foundProduct = productService.findById(nonExistentId);
        assertNull(foundProduct);
        verify(productRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void testEdit() {
        when(productRepository.edit(product)).thenReturn(product);
        Product editedProduct = productService.edit(product);
        assertEquals(product.getProductName(), editedProduct.getProductName());
        verify(productRepository, times(1)).edit(product);
    }

    @Test
    void testDelete() {
        doNothing().when(productRepository).delete(product.getProductId());
        productService.delete(product.getProductId());
        verify(productRepository, times(1)).delete(product.getProductId());
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Product> productList = new ArrayList<>();
        productList.add(product); // Add the product created in setUp()

        Product product2 = new Product();
        product2.setProductId(UUID.randomUUID());
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productList.add(product2);

        // Mock the repository to return the iterator of our list
        when(productRepository.findAll()).thenReturn(productList.iterator());

        // Act
        List<Product> result = productService.findAll();

        // Assert
        assertEquals(2, result.size()); // Verify list size
        assertEquals(product.getProductId(), result.get(0).getProductId()); // Verify first product
        assertEquals(product2.getProductId(), result.get(1).getProductId()); // Verify second product
        verify(productRepository, times(1)).findAll(); // Verify repository was called
    }
}