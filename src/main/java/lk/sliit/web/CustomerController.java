package lk.sliit.web;

import lk.sliit.domain.*;
import lk.sliit.repository.*;
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

    @Autowired
    RideRepository rideRepository;

    @Autowired
    TripRepository tripRepository;

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

    @PutMapping(path = "/tokens/{id}")
    public UserToken updateToken(@RequestParam(value = "balance", required = true) Float newBalance , @PathVariable Long id){
        UserToken oldToken = tokenRepository.findOne(id);
        oldToken.setBalance(newBalance);
        UserToken newToken = tokenRepository.save(oldToken);
        return newToken;
    }

    @GetMapping(path="/tokens/{id}")
    public UserToken findTokenById(@PathVariable Long id){
        return tokenRepository.findOne(id);
    }

    @GetMapping(path = "/tokens/customers/{id}")
    public UserToken getAllTokens(@PathVariable Long id) {
        return tokenRepository.findByCustomer_Id(id);
    }

    @PostMapping(path = "/payments")
    public Payment createPayment(@Valid @RequestBody Payment payment) {
        UserToken oldToken = tokenRepository.findOne(payment.getTokenId());

        oldToken.setBalance(oldToken.getBalance() + payment.getAmount());

        UserToken newToken = tokenRepository.save(oldToken);


        return paymentRepository.save(payment);
    }

    @GetMapping(path = "/payments/tokens/{id}")
    public List<Payment> getPaymentsByTokenId(@PathVariable Long id) {
        return paymentRepository.getAllByTokenId(id);
    }

    @PostMapping(path = "/rides")
    public Ride createNewRide(@Valid @RequestBody Ride ride, HttpServletResponse response) {
        UserToken oldToken = tokenRepository.findByCustomer_Id(ride.getCustomerId());
        Trip oldTrip =tripRepository.findOne(ride.getTripId());

        if (oldToken.getBalance() < ride.getAmount()) {
            response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
            return null;
        } else {
            oldToken.setBalance(oldToken.getBalance() - ride.getAmount());

            oldTrip.setPassengers(oldTrip.getPassengers() + 1);
            oldTrip.setIncome(oldTrip.getIncome() + ride.getAmount());

            Trip newTrip = tripRepository.save(oldTrip);
            UserToken newToken = tokenRepository.save(oldToken);
        }
        return rideRepository.save(ride);
    }

    @GetMapping(path = "/rides/customers/{id}")
    public List<Ride> getRidesByCustomer(@PathVariable Long id){
        return rideRepository.getAllByCustomerIdIs(id);
    }



}
