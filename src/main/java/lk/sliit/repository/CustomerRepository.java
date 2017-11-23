package lk.sliit.repository;

import lk.sliit.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    /**
     * This method is used to search Customers by their email addresses
     * @param email Search key is an email address
     * @return Customer object or NULL if there is no Customer with given email
     */
    Customer findByEmail(String email);
}
