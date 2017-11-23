package lk.sliit.web;

import lk.sliit.domain.Customer;
import lk.sliit.domain.Payment;
import lk.sliit.domain.Ride;
import lk.sliit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/api")
public class RideController {

    /**
     * @Autowired Repositories for JPA
     */

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    RideRepository rideRepository;

    @GetMapping(path = "/statistics/payments/{id}")
    public List<Object> getPaymentSumOfACustomer(@PathVariable Long id) {
        return paymentRepository.getPaymentSum(id);
    }

    @GetMapping(path = "/statistics/rides/{id}")
    public List<Object> getJourneyCount(@PathVariable Long id) {
        return rideRepository.getJourneyCount(id);
    }

    @GetMapping(path = "/statistics/spending/{id}")
    public List<Object> getTotalSpent(@PathVariable Long id) {
        return rideRepository.getSpentAmount(id);
    }

}
