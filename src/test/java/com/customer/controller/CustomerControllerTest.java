package com.customer.controller;

import static com.customer.ObjectFactory.CUSTOMER_ID;
import static com.customer.ObjectFactory.buildCustomer;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.customer.Controller.CustomerController;
import com.customer.model.Customer;
import com.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

  private MockMvc mockMvc;

  @Mock
  private CustomerService customerService;

  @InjectMocks
  private CustomerController customerController;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
  }

  @Test
  void testCreateCustomer() throws Exception {
    Customer customer = buildCustomer();

    when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);

    mockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(customer)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.customerRef").value(
            customer.getCustomerRef()));
  }

  @Test
  void testGetCustomerByRef() throws Exception {
    Customer customer = buildCustomer();
    when(customerService.findByCustomerRef(CUSTOMER_ID)).thenReturn(Optional.of(customer));

    mockMvc.perform(get("/api/customers/{customerRef}", CUSTOMER_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.customerRef").value(CUSTOMER_ID));
  }

  @Test
  void testGetCustomerByRef_NotFound() throws Exception {
    String customerRef = "nonexistent";
    when(customerService.findByCustomerRef(customerRef)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/customers/{customerRef}", customerRef))
        .andExpect(status().isNotFound());
  }

  @Test
  void testGetAllCustomers() throws Exception {
    List<Customer> customers = Arrays.asList(new Customer(),
        new Customer()); // Set properties accordingly
    when(customerService.findAllCustomers()).thenReturn(customers);

    mockMvc.perform(get("/api/customers"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(customers.size()));
  }
}