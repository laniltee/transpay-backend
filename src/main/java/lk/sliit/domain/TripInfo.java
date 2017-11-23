package lk.sliit.domain;

import lk.sliit.repository.JourneyRepository;
import lk.sliit.repository.RideRepository;
import lk.sliit.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Lanil Marasinghe on 18-Nov-17.
 */
public class TripInfo {
    private long id;
    private String journey;
    private String vehicle;
    private Date starting;
    private Date ending;
    private int noOfPassengers;
    private double income;
    private int invalid;


    @Autowired
    RideRepository rideRepository;
    @Autowired
    JourneyRepository journeyRepository;
    @Autowired
    TripRepository tripRepository;

    Trip trip = new Trip();


    public TripInfo(long id) {
        System.out.println("Received trip id at TripInfo: " + id);
        this.id = id;
        trip = tripRepository.findOne(id);
        journey = journeyRepository.findOne(trip.getJourneyId()).getName();

        this.vehicle = trip.getVehicleId();
        starting = trip.getCreatedAt();
        if(trip.isStatus()){
            ending = null;
        }else{
            ending = trip.getUpdatedAt();
        }

        // TODO: Get aggregated values
        noOfPassengers = rideRepository.countByTripIdIs(trip.getId());
        income = 0;

        invalid = 0;
    }

    public long getId() {
        return id;
    }

    public String getVehicle() {
        return vehicle;
    }

    public Date getStarting() {
        return starting;
    }

    public Date getEnding() {
        return ending;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public double getIncome() {
        return income;
    }

    public int getInvalid() {
        return invalid;
    }

    public String getJourney() {
        return journey;
    }

}
