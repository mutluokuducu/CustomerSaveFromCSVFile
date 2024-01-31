package com.customer.Controller;


import com.customer.model.Customer;
import com.customer.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @PostMapping
  public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
    Customer savedCustomer = customerService.saveCustomer(customer);
    return ResponseEntity.ok(savedCustomer);
  }

  @GetMapping("/{customerRef}")
  public ResponseEntity<Customer> getCustomerByRef(@PathVariable String customerRef) {
    return customerService.findByCustomerRef(customerRef)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Customer>> getAllCustomers() {
    return ResponseEntity.ok(customerService.findAllCustomers());
  }

}
