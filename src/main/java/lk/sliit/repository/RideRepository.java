package lk.sliit.repository;

import lk.sliit.domain.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> getAllByCustomerIdIs(Long customerId);
}
