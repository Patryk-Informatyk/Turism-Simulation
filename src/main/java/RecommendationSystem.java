import api.consumer.LocationCreator;
import model.Location;
import model.LocationType;
import model.Person;
import model.Weather;

import java.util.*;

/**
 * Created by Linus on 25.11.2017.
 */
public class RecommendationSystem {

private Weather  weather;

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public RecommendationSystem() {
    }

    private List<Location> locationList;

    public RecommendationSystem( List<Location> locationList) {
        this.locationList = locationList;
        weather = new Weather();
    }



    /// wyższe lepiej
public  double countAttractiveInCurrentWeather(int month, int day, Location location){
    double indicator = weather.countWeatherIndicator(month,day);
   // System.out.println(indicator);
    boolean covered =  location.getTypes().getLocationProperties().isCovered();
        return covered ? 100 : indicator;
}

///jakoś liczy to bogactwo chyba dobrze
public double countAttractiveForPerson(Person person,Location location){
    return (((person.getActivity()/100)*location.getTypes().getLocationProperties().getActivity())
            +((person.getArt()/100)*location.getTypes().getLocationProperties().getArt())
            +(getCostIndicator(location.getTypes().getLocationProperties().getCost(),person.getRich())))/3;
}
//bug
private double getCostIndicator(double locationCost, double touristRich){
    return (100 -(locationCost * ((101-touristRich)/100)));

}

private double countAttractive(int month,int day,Location location,Person person){
    return (countAttractiveForPerson(person,location) + countAttractiveInCurrentWeather(month,day,location))/2;
}

//działa raczej dobrze
//liczy mape skumulowaną i ocena wszystkich okacji sumuje sie do 100
    // czyli <zamek,33>,<inny zamek, 66>
public Map<Location,Double> rateLocations(int month, int day, Person person){
    Map<Location,Double> ratesMap = new LinkedHashMap<>();
    double lastRate=0;
    double cumulatedRate=0;
    for (Location location:locationList
         ) {
        lastRate+=countAttractive(month,day,location,person);
        cumulatedRate+=lastRate;
            ratesMap.put(location,lastRate);
    }
  //  System.out.println(ratesMap);
  final double finalCumulatedRate = cumulatedRate;
    locationList.stream().forEach(loc->{
        ratesMap.computeIfPresent(loc,(l,d)->{
            return (d/finalCumulatedRate)*100;
        });
    });
    return  ratesMap;
}

//działa te zraczej dobrze
//rand wybiuera 0-100 i sprawdza pierwsza lokacje która   ma ta skumulowana wartość wieksa niż liczba wylosowana
public Location recommendLocation(int month, int day, Person person) {
    //lokacje dodane  testowe
    int iterator=0;
    Map<Location,Double> ratesMap = rateLocations(month,day,person);
    List<Location>  result = new ArrayList<>();
   double randomValue = Math.random()*100;

    for (Map.Entry<Location, Double> entry : ratesMap.entrySet()) {
        if (entry.getValue() > randomValue || ++iterator==ratesMap.size()) result.add(entry.getKey());
    }
/*  ratesMap.forEach((l,d)->{
       if (d > randomValue || ratesMap.get(l).==ratesMap.size()) result.add(l);

   });*/
   return result.get(0);
}




    public static void main(String[] args)  {
    RecommendationSystem rc = new RecommendationSystem(new LinkedList<>());
    Person person = new Person(12,40.0,12.0,40.0);
        Location l = new Location("Name","12",LocationType.old_town);
        System .out.println(rc.weather.getWeatherInDay(1,26));
        System .out.println(rc.countAttractiveInCurrentWeather(1,26,l));
        System .out.println(rc.countAttractiveForPerson(person,l));
        System.out.print(rc.recommendLocation(1,26,person));
    }






}
