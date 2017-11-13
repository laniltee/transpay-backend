package lk.sliit.web;

import com.sun.media.jfxmedia.logging.Logger;
import lk.sliit.domain.Customer;
import lk.sliit.domain.LoginRequest;
import lk.sliit.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */

@CrossOrigin
@RestController
@RequestMapping(path = "/api")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping(path = "/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping(path = "/customers")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping(path = "/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerRepository.findOne(id);
    }

    @PostMapping(path = "/authenticate")
    public Customer authenticate(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException {
        Customer customer = customerRepository.findByEmail(loginRequest.getEmail());
        if(customer == null || !customer.getPassword().equals(loginRequest.getPassword())){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return customer;
    }
}
