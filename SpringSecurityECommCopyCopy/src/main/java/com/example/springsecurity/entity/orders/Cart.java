package com.example.springsecurity.entity.orders;

//@Entity
public class Cart {
        //CUSTOMER_USER_ID   foreign

    //PRODUCT_VARIATION_ID  foreign
    private Long customerUserId;

    public Long getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(Long customerUserId) {
        this.customerUserId = customerUserId;
    }
}
