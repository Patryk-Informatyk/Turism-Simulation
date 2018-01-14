package simulation;

import init.LocationCreator;
import model.Location;
import model.LocationType;
import model.Person;
import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;



/**
 * Klasa wykonująca symulację
 *
 * @author      Patryk Zygmunt
 * @author      Grzegorz Puczkowski
 * @author      Hubert Rędzia
 * @version     1.0
 * @since       1.0
 */
public class Simulation {
    private static final int amountOfTourists=5000;
    RecommendationSystem recommendationSystem;
    List<Person> tourists;
    List<Location> locations;

    public Simulation() throws IOException, JSONException {
        //jesli miejsce jest kryte trzeba mu ustawić setMaxSize(n);
        try {
            locations = LocationCreator.createLocationsFromFile();
            this.locations = locations;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

       /*
        Location l1 = new Location("Old Town","12", LocationType.old_town);
        Location l2 = new Location("Square","12",LocationType.square);
        Location l3 = new Location("Park","12",LocationType.amusement_park);
        Location l4 = new Location("Museum","12",LocationType.museum);
        l4.setMaxSize(30);
       locations.add(l2);
        locations.add(l3);
        locations.add(l1);
        locations.add(l4);
        */
        // locations = LocationCreator.getLocationList();
        this.recommendationSystem = new RecommendationSystem(locations);
        this.tourists = touristInit();
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      RecommendationSystem for this simulation
     */
    public RecommendationSystem getRecommendationSystem(){
        return recommendationSystem;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      List of tourists generated for this simulation
     */
    public List<Person> getTourists(){
        return this.tourists;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      List of locations
     */
    public List<Location> getLocations() {
        return locations;
    }
    /**
     * Creates number different of tourists, specified in amountOfTourists field
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      List of tourists
     */
    private List<Person> touristInit(){
        List<Person> touristList = new LinkedList<>();
        int age;
        double art,activity,rich;
        for(int i=0;i<amountOfTourists;i++){
            age = (int)(Math.random() * 80 +15);
            art = Math.random() *100;
            activity = Math.random() *100;
            rich = Math.random() *100;
            touristList.add(new Person(age,art,activity,rich));
        }
        return  touristList;
    }
    /**
     * Simulate how much tourists would be in specific location, on specific day, adds everyone to location they would go
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param month Month number in year
     * @param day   Day number in month
     */
    public void simulateForDay(int month,int day){
        Location pLocation;
        for(int i=0;i<amountOfTourists;i++){
            pLocation = recommendationSystem.recommendLocation(month,day,tourists.get(i));
            locations.get(locations.indexOf(pLocation)).addTourist();
        }
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param i     index of place in locations' list.
     * @return      String containing name, amount of tourists and queue to location.
     */
    public String getDayInfoFromLocation(int i){
           return   locations.get(i).getName() + ", "+ locations.get(i).getAmountOfTourists() +  " + "+ locations.get(i).getQueue();
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param i     index of place in locations' list.
     * @return      Location's name of that index
     */
    public String getLocationsName(int i){
        return   locations.get(i).getName();
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param name  Name of location
     * @return      String containing name, amount of tourists and queue to location.
     */
    public String getDayInfoFromLocationByName(String name){
      Location location =  locations.parallelStream().filter(l->l.getName().equals(name)).findFirst().get();
           return   location.getName() + " "+ location.getAmountOfTourists() +  " + "+ location.getQueue();
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param name  Name of location
     * @return      Location with specified name
     */
    public Location getLocationByName(String name){
        return locations.parallelStream().filter(l->l.getName().equals(name)).findFirst().get();
    }
    /**
     * Writes to stdout information about every location's name, amount of tourists and queue
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     */
    public void checkAmountofTouristsInLocations(){
      locations.stream().forEach(l-> System.out.println("  Location: " +l.getName() + ">>"+ (l.getAmountOfTourists() +  " + Queue:"+ l.getQueue())));
    }
    /**
     * Allows locations in list to end Day. Set amounts of tourists and queue to 0 for every location
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     */
    public void endDayInLocations(){
        locations.stream().forEach(l->l.endDay());
    }


    public static void main(String[] args) throws IOException, JSONException {

    }



}
