package lk.sliit.repository;

import lk.sliit.domain.JourneyStop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */
public interface StopRepository extends JpaRepository<JourneyStop, Long> {
    List<JourneyStop> getAllByJourneyIdIs(Long journeyId);
}
