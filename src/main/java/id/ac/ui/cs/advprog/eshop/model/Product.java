package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;

import java.util.UUID;

@Getter @Setter
public class Product {
    private UUID productId;
    private String productName;

    @Min(value=0, message="Quantity must be a positive number")
    private int productQuantity;

    public void setProductQuantity(int productQuantity) {
        if (productQuantity >= 0) {
            this.productQuantity = productQuantity;
        }
    }
}
