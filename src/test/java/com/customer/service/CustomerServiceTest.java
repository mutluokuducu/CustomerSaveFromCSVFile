package com.customer.service;

import static com.customer.ObjectFactory.CUSTOMER_ID;
import static com.customer.ObjectFactory.buildCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.customer.model.Customer;
import com.customer.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerService customerService;

  private Customer customer;

  @BeforeEach
  public void setUp() {
    customer = buildCustomer();
  }

  @Test
  void testSaveCustomer() {
    when(customerRepository.save(any(Customer.class))).thenReturn(customer);

    Customer savedCustomer = customerService.saveCustomer(customer);

    assertThat(savedCustomer).isNotNull();
    verify(customerRepository).save(customer);
  }

  @Test
  void testFindByCustomerRef() {
    when(customerRepository.findByCustomerRef(CUSTOMER_ID)).thenReturn(Optional.of(customer));

    Optional<Customer> foundCustomer = customerService.findByCustomerRef(CUSTOMER_ID);

    assertThat(foundCustomer).isNotEmpty();
    assertThat(foundCustomer.get().getCustomerRef()).isEqualTo(customer.getCustomerRef());
    verify(customerRepository).findByCustomerRef(CUSTOMER_ID);
  }

  @Test
  void testFindAllCustomers() {
    List<Customer> customers = List.of(buildCustomer());
    when(customerRepository.findAll()).thenReturn(customers);
    List<Customer> foundCustomers = customerService.findAllCustomers();

    assertThat(foundCustomers)
        .isNotEmpty()
        .hasSize(1);
    verify(customerRepository).findAll();
  }
}
