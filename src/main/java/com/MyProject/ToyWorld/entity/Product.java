package com.MyProject.ToyWorld.entity;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column
    private String productName;

    @Column
    private String productDescription;

    @Column
    private Integer size;

    @Column
    private BigDecimal price;

    @Column
    private Integer quantity;

    @Column
    private String productImage;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
        super();
    }

    public Product(String productName) {
        this.productName = productName;
    }

    public Product(String productName, Integer size, BigDecimal price, Integer quantity, String productImage, Category category) {
        this.productName = productName;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
        this.productImage = productImage;
        this.category = category;
    }

    public Product(Long id, String productName, String productDescription, Integer size, BigDecimal price, Integer quantity, Category category) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Integer getSize() {
        return size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }


    public Category getCategory() {
        return category;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public void setCategory(Category category) {
        this.category = category;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category=" + category +
                '}';
    }

    @Transient
    public String getProductImagePath(){
        if (productImage == null){
            return null;
        }
        return "/uploads/images/" + productImage;
    }
}
