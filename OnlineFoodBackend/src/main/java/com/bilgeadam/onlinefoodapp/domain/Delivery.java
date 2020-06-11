package com.bilgeadam.onlinefoodapp.domain;

import javax.persistence.*;

@Entity
@Table(name = "DELIVERY")
public class Delivery {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DELIVERY_STATUS")
    private Boolean deliveryStatus;

    @Column(name = "TIP")
    private String tip;

    @Column(name = "DELIVERED_DATE")
    private String deliveredDate;

    @Column(name = "ORDER_DATE")
    private String orderDate;

    @OneToOne
    @JoinColumn(name = "CART_ID", referencedColumnName = "CART_ID")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
    private Employee employee;

    public Delivery() {
    }

    public Delivery(Boolean deliveryStatus, String tip, String deliveredDate, String orderDate, Cart cart, Employee employee) {
        this.deliveryStatus = deliveryStatus;
        this.tip = tip;
        this.deliveredDate = deliveredDate;
        this.orderDate = orderDate;
        this.cart = cart;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
