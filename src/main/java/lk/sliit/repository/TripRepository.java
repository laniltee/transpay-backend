package lk.sliit.repository;

import lk.sliit.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByStatusIsFalse();

    List<Trip> findAllByStatusIsTrue();
}
