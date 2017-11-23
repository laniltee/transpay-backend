package lk.sliit.repository;

import lk.sliit.domain.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */
public interface RideRepository extends JpaRepository<Ride, Long> {

    final String CUSTOMER_ID = "customerId";

    List<Ride> getAllByCustomerIdIs(Long customerId);
    int countByTripIdIs(Long tripId);
    List<Ride> getAllByTripIdIs(Long tripId);
    //List<Ride> getAllByStartedAtIsGreaterThanEqualAndEndedAtLessThanEqualAAndTripIdIs(Long startedAt, Long endedAt, Long tripId);

    @Query(value = "select journey_name, count(id) from rides where customer_id = :customerId group by journey_name order by count(id) desc",
            nativeQuery = true)
    List<Object> getJourneyCount(@Param(CUSTOMER_ID) Long customerId);

    @Query(value = "select customer_id, sum(amount) from rides where customer_id = :customerId group by customer_id"
            , nativeQuery = true)
    List<Object> getSpentAmount(@Param("customerId") Long customerId);
}
