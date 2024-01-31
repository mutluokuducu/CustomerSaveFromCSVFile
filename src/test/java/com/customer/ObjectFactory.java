package com.customer;

import com.customer.model.Customer;

public class ObjectFactory {

  public static final String CUSTOMER_ID = "CUST001";
  public static Customer buildCustomer() {
    return Customer.builder()
        .customerRef("CUST001")
        .customerName("Alice Smith")
        .addressLine1("123 Apple St")
        .addressLine2("Suite 101")
        .town("Fruitville")
        .county("Orange")
        .country("Fruitland")
        .postcode("12345")
        .build();
  }


}

