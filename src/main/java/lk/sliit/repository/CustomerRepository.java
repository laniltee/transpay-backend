package lk.sliit.repository;

import lk.sliit.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
