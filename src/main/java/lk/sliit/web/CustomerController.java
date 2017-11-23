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
 * Represents all functions related to Customers of the system
 * CSSE Assignment 4
 * (C) 2017
 */

@CrossOrigin
@RestController
@RequestMapping(path = "/api")
public class CustomerController {

    /**
     * @Autowired Repositories for JPA
     */

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

    /**
     * This method returns all the customers available in the databse
     * @param No parameters are required
     * @return A JSON array of customer objects
     */
    @GetMapping(path = "/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * This method adds a new customer to the databse and returns it
     * @param customer This is the incoming POST payload. Should be a valid customer object
     * @return Newly created customer JSON object
     */
    @PostMapping(path = "/customers")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * This method returns the customer of given id
     * @param id Primary key of the customer
     * @return A Customer object
     */
    @GetMapping(path = "/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerRepository.findOne(id);
    }

    /**
     *
     * @param loginRequest Payload contains email and password
     * @param response HTTP Status 200 for valid credentials and 401 for invalid
     * @return HTTP status 401 OR 200
     * @throws IOException is HTTP 401 error if given credentials are unauthorized
     */
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

    /**
     * This method adds a new Token to the database and returns it
     * @param userToken Incoming POST payload of new Token
     * @return newly created Token
     */
    @PostMapping(path = "/tokens")
    public UserToken createToken(@Valid @RequestBody UserToken userToken) {
        return tokenRepository.save(userToken);
    }

    /**
     * All tokens available in the database is returned by this method
     * @return a JSON array of Tokens
     */
    @GetMapping(path = "/tokens")
    public List<UserToken> getAllTokens() {
        return tokenRepository.findAll();
    }

    /**
     * This method updates the Token. Specially used for deduct balance when paying
     * and Update balance when recharging
     * @param newBalance Updating balance
     * @param id ID of the updating Token
     * @return Updated Token object
     */
    @PutMapping(path = "/tokens/{id}")
    public UserToken updateToken(@RequestParam(value = "balance", required = true) Float newBalance , @PathVariable Long id){
        UserToken oldToken = tokenRepository.findOne(id);
        oldToken.setBalance(newBalance);
        UserToken newToken = tokenRepository.save(oldToken);
        return newToken;
    }

    /**
     * Returns only one Token with given ID
     * @param id Primary key of Token
     * @return Newly created Token
     */
    @GetMapping(path="/tokens/{id}")
    public UserToken findTokenById(@PathVariable Long id){
        return tokenRepository.findOne(id);
    }

    /**
     * Returns a Token owned by a given customer
     * @param id Primary key of a customer
     * @return Token object
     */
    @GetMapping(path = "/tokens/customers/{id}")
    public UserToken getAllTokens(@PathVariable Long id) {
        return tokenRepository.findByCustomer_Id(id);
    }

    /**
     * Creates a new payment
     * @param payment Incoming payload
     * @return Newly created Payment object
     */
    @PostMapping(path = "/payments")
    public Payment createPayment(@Valid @RequestBody Payment payment) {
        UserToken oldToken = tokenRepository.findOne(payment.getTokenId());

        oldToken.setBalance(oldToken.getBalance() + payment.getAmount());

        UserToken newToken = tokenRepository.save(oldToken);


        return paymentRepository.save(payment);
    }

    /**
     * Returns a list of Payment objects paid with a given Token
     * @param id ID of a Token object
     * @return JSON array of Payment objects
     */
    @GetMapping(path = "/payments/tokens/{id}")
    public List<Payment> getPaymentsByTokenId(@PathVariable Long id) {
        return paymentRepository.getAllByTokenId(id);
    }

    /**
     * This methods is not used from Front End
     * @param ride
     * @param response
     * @return
     */
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

    /**
     *
     * @param id ID of a Customer
     * @return
     */
    @GetMapping(path = "/rides/customers/{id}")
    public List<Ride> getRidesByCustomer(@PathVariable Long id){
        return rideRepository.getAllByCustomerIdIs(id);
    }




}
