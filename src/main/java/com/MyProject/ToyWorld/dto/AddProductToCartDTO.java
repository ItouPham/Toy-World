package com.MyProject.ToyWorld.dto;

public class AddProductToCartDTO {
    private Long productId;
    private Integer quantity;

    public AddProductToCartDTO() {
    }

    public AddProductToCartDTO(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "AddProductToCartDTO{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
