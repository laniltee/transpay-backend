package lk.sliit.web;

import lk.sliit.constants.InformationConstants;
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
@RequestMapping(path = InformationConstants.BASE_URL)
public class InformationController {
    @Autowired
    JourneyRepository journeyRepository;
    @Autowired
    StopRepository stopRepository;
    @Autowired
    TripRepository tripRepository;
    @Autowired
    RideRepository rideRepository;

    //Http method is 'get'.
    //When called returns all the journeys in the database.
    @GetMapping(path = InformationConstants.JOURNEYS_URL)
    public List<Journey> getAllJourneys() {
        return journeyRepository.findAll();
    }

    //Http method is 'get'.
    //When called returns a particular journey in the database.
    @GetMapping(path = "/journeys/{id}")
    public Journey getJourneyById(@PathVariable Long id) {
        return journeyRepository.findOne(id);
    }

    //Http method is 'get'.
    //When called returns a particular journey in the database.
    @PostMapping(path = InformationConstants.JOURNEYS_URL)
    public Journey createNewJourney(@Valid @RequestBody Journey journey) {
        return journeyRepository.save(journey);
    }

    //Http method is 'get'.
    //When called returns all the bus stops in the database.
    @GetMapping(path = InformationConstants.STOPS_URL)
    public List<JourneyStop> getAllStops() {
        return stopRepository.findAll();
    }

    //Http method is 'get'.
    //When called returns all the stops related to a particular journey in the database.
    @GetMapping(path = "/stops/journeys/{id}")
    public List<JourneyStop> getAllStops(@PathVariable Long id) {
        return stopRepository.getAllByJourneyIdIs(id);
    }

    //Http method is 'post'.
    //When called inserts a new bus stop into the database.
    @PostMapping(path = InformationConstants.STOPS_URL)
    public JourneyStop createNewStop(@Valid @RequestBody JourneyStop journeyStop) {
        return stopRepository.save(journeyStop);
    }

    //Http method is 'get'.
    //When called returns all the trips in the database.
    @GetMapping(path = InformationConstants.TRIPS_URL)
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

    //Http method is 'post'.
    //When called inserts a new trip into the database.
    @PostMapping(path = InformationConstants.TRIPS_URL)
    public Trip createNewTrip(@Valid @RequestBody Trip trip) {
        return tripRepository.save(trip);
    }

    //Http method is 'put'.
    //When called updates a particular trip in the database
    @PutMapping(path = "/trips/{id}")
    public Trip updateTripById(@PathVariable Long id) {
        Trip oldTrip1 = tripRepository.findOne(id);
        oldTrip1.setStatus(false);
        Trip newTrip = tripRepository.save(oldTrip1);
        return newTrip;
    }

    //Http method is 'get'.
    //When called returns a particular trip in the database.
    @GetMapping(path = "/trips/{id}")
    public Trip findTripById(@PathVariable Long id) {
        return tripRepository.findOne(id);
    }

    //Http method is 'get'.
    //When called returns all the rides in the database.
    @GetMapping(path = InformationConstants.RIDES_URL)
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    //Test whether a token is valid
    @GetMapping(path = "/tocken_test")
    public List<Ride> getValidTripTokens(@RequestParam(value = "trip", required = true) long trip){
        return rideRepository.getAllByTripIdIs(trip);
    }

    //This method is not used in the front end
    //
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
