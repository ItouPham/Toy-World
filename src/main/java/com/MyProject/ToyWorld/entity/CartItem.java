package com.MyProject.ToyWorld.entity;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column
    private Integer quantity;

    public CartItem() {
        this.quantity = 0;
    }

    public CartItem(Product product, Cart cart) {
        this.product = product;
        this.cart = cart;
        this.quantity = 0;
    }

    public CartItem(Long id, Product product, Cart cart, Integer quantity) {
        this.id = id;
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Cart getCart() {
        return cart;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", product=" + product +
                ", cart=" + cart +
                ", quantity=" + quantity +
                '}';
    }
}
