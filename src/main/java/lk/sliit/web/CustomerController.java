package lk.sliit.web;

import lk.sliit.domain.Customer;
import lk.sliit.domain.LoginRequest;
import lk.sliit.domain.Payment;
import lk.sliit.domain.UserToken;
import lk.sliit.repository.CustomerRepository;
import lk.sliit.repository.PaymentRepository;
import lk.sliit.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PaymentRepository paymentRepository;

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

    @PostMapping(path = "/tokens")
    public UserToken createToken(@Valid @RequestBody UserToken userToken) {
        return tokenRepository.save(userToken);
    }

    @GetMapping(path = "/tokens")
    public List<UserToken> getAllTokens() {
        return tokenRepository.findAll();
    }

    @GetMapping(path = "/tokens/customers/{id}")
    public UserToken getAllTokens(@PathVariable Long id) {
        return tokenRepository.findByCustomer_Id(id);
    }

    @PostMapping(path = "/payments")
    public Payment createPayment(@Valid @RequestBody Payment payment) {
        UserToken userToken = tokenRepository.findOne(payment.getTokenId());
        userToken.setBalance(userToken.getBalance() + payment.getAmount());
        UserToken updatedToken = tokenRepository.save(userToken);
        return paymentRepository.save(payment);
    }

    @GetMapping(path = "/payments/tokens/{id}")
    public List<Payment> getPaymentsByTokenId(@PathVariable Long id) {
        return paymentRepository.getAllByTokenId(id);
    }



}
