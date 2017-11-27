import api.consumer.LocationCreator;
import model.Location;
import model.Person;

import java.util.List;

/**
 * Created by Comarch on 2017-11-27.
 */


//TODO
public class Simulation {
    RecommendationSystem recommendationSystem;
    List<Person> tourists;
    List<Location> locations;

    public Simulation() {
        locations = LocationCreator.getLocationList();
        this.recommendationSystem = new RecommendationSystem(locations);
        this.tourists = touristInit();
    }


    private List<Person> touristInit(){
        return null;
    }



}
