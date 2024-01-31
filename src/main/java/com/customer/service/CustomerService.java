package com.customer.service;

import com.customer.model.Customer;
import com.customer.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  public Customer saveCustomer(Customer customer) {
    log.info("Customer inserted DB: {} ", customer.getCustomerRef());
    return customerRepository.save(customer);
  }

  public Optional<Customer> findByCustomerRef(String customerRef) {
    return customerRepository.findByCustomerRef(customerRef);
  }

  public List<Customer> findAllCustomers() {
    return customerRepository.findAll();
  }
}
