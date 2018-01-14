package simulation;

import init.LocationCreator;
import model.Location;
import model.LocationType;
import model.Person;
import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;



//TODO
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

    public RecommendationSystem getRecommendationSystem(){
        return recommendationSystem;
    }

    public List<Person> getTourists(){
        return this.tourists;
    }

    public List<Location> getLocations() {
        return locations;
    }

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

    public void simulateForDay(int month,int day){
        Location pLocation;
        for(int i=0;i<amountOfTourists;i++){
            pLocation = recommendationSystem.recommendLocation(month,day,tourists.get(i));
            locations.get(locations.indexOf(pLocation)).addTourist();
        }
    }

    public String getDayInfoFromLocation(int i){
           return   locations.get(i).getName() + ", "+ locations.get(i).getAmountOfTourists() +  " + "+ locations.get(i).getQueue();
    }

    public String getLocationsName(int i){
        return   locations.get(i).getName();
    }

    public String getDayInfoFromLocationByName(String name){
      Location location =  locations.parallelStream().filter(l->l.getName().equals(name)).findFirst().get();
           return   location.getName() + " "+ location.getAmountOfTourists() +  " + "+ location.getQueue();
    }

    public Location getLocationByName(String name){
        return locations.parallelStream().filter(l->l.getName().equals(name)).findFirst().get();
    }

    public void checkAmountofTouristsInLocations(){
      locations.stream().forEach(l-> System.out.println("  Location: " +l.getName() + ">>"+ (l.getAmountOfTourists() +  " + Queue:"+ l.getQueue())));
    }

    public void endDayInLocations(){
        locations.stream().forEach(l->l.endDay());
    }


    public static void main(String[] args) throws IOException, JSONException {

    }



}
