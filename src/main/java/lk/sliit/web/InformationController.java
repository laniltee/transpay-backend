package lk.sliit.web;

import lk.sliit.domain.Journey;
import lk.sliit.domain.JourneyStop;
import lk.sliit.domain.Trip;
import lk.sliit.repository.JourneyRepository;
import lk.sliit.repository.StopRepository;
import lk.sliit.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api")
public class InformationController {
    @Autowired
    JourneyRepository journeyRepository;
    @Autowired
    StopRepository stopRepository;
    @Autowired
    TripRepository tripRepository;

    @GetMapping(path = "/journeys")
    public List<Journey> getAllJourneys() {
        return journeyRepository.findAll();
    }

    @GetMapping(path = "/journeys/{id}")
    public Journey getJourneyById(@PathVariable Long id) {
        return journeyRepository.findOne(id);
    }

    @PostMapping(path = "/journeys")
    public Journey createNewJourney(@Valid @RequestBody Journey journey) {
        return journeyRepository.save(journey);
    }

    @GetMapping(path = "/stops")
    public List<JourneyStop> getAllStops() {
        return stopRepository.findAll();
    }

    @GetMapping(path = "/stops/journeys/{id}")
    public List<JourneyStop> getAllStops(@PathVariable Long id) {
        return stopRepository.getAllByJourneyIdIs(id);
    }

    @PostMapping(path = "/stops")
    public JourneyStop createNewStop(@Valid @RequestBody JourneyStop journeyStop) {
        return stopRepository.save(journeyStop);
    }

    @GetMapping(path = "/trips")
    public List<Trip> getAllTrips(@RequestParam(value = "filter", required = true) String filter) {
        List<Trip> result = null;
        if (filter.equals("all")) {
            result = tripRepository.findAll();
        } else if (filter.equals("inactive")) {
            result = tripRepository.findAllByStatusIsFalse();
        } else if (filter.equals("active")) {
            result = tripRepository.findAllByStatusIsTrue();
        }
        return result;
    }

    @PostMapping(path = "/trips")
    public Trip createNewTrip(@Valid @RequestBody Trip trip) {
        return tripRepository.save(trip);
    }


}
