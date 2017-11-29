import api.consumer.LocationCreator;
import model.Location;
import model.LocationType;
import model.Person;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Comarch on 2017-11-27.
 */


//TODO
public class Simulation {
    private static final int amountOfTourists=200;
    RecommendationSystem recommendationSystem;
    List<Person> tourists;
    List<Location> locations;

    public Simulation() {
       locations = new LinkedList<>();
        Location l1 = new Location("Name","12", LocationType.old_town);
        Location l2 = new Location("sqr","12",LocationType.square);
        Location l3 = new Location("Park","12",LocationType.amusement_park);

        locations.add(l1);
        locations.add(l2);
        locations.add(l3);
       // locations = LocationCreator.getLocationList();
        this.recommendationSystem = new RecommendationSystem(locations);
        this.tourists = touristInit();
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


    public void checkAmountofTouristsInLocations(){
        locations.stream().forEach(l->System.out.println("  Location: " +l.getName() + ">>"+ l.getAmountOfTourists()));
    }

    public void endDayInLocations(){
        locations.stream().forEach(l->l.endDay());
    }


    public static void main(String[] args){
       Simulation simulation = new Simulation();

        for(int i=1;i<20;i++){
            System.out.println(">>DAY:"+i);
            simulation.simulateForDay(1,i);
            simulation.checkAmountofTouristsInLocations();
            simulation.endDayInLocations();
        }





    }



}
