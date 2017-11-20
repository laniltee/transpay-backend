package lk.sliit.web;

import lk.sliit.domain.*;
import lk.sliit.repository.JourneyRepository;
import lk.sliit.repository.RideRepository;
import lk.sliit.repository.StopRepository;
import lk.sliit.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    @Autowired
    RideRepository rideRepository;

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

    @PutMapping(path = "/trips/{id}")
    public Trip updateTripById(@PathVariable Long id) {
        Trip oldTrip1 = tripRepository.findOne(id);
        oldTrip1.setStatus(false);
        Trip newTrip = tripRepository.save(oldTrip1);
        return newTrip;
    }

    @GetMapping(path = "/trips/{id}")
    public Trip findTripById(@PathVariable Long id) {
        return tripRepository.findOne(id);
    }

    @GetMapping(path = "/rides")
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    @GetMapping(path = "/tocken_test")
    public List<Ride> getValidTripTokens(@RequestParam(value = "trip", required = true) long trip){
        return rideRepository.getAllByTripIdIs(trip);
    }

    @GetMapping(path = "/stats/trips")
    public List<TripInfo> getTripInfo(@RequestParam(value = "filter", required = true) String filter) {
        List<Trip> result = this.getAllTrips(filter);
        List<TripInfo> tripResult = new ArrayList<>();


        for (int i = 0; i < result.size(); i++) {
            if(result.get(i).getJourneyId() == null){
                continue;
            }
            System.out.println("Infor Controller: " + result.get(i).getId());
            TripInfo tripInfo = new TripInfo(result.get(i).getId());
            tripResult.add(tripInfo);
        }
        return tripResult;
    }


}
