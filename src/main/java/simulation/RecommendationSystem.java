package simulation;

import function.Functions;
import model.Location;
import model.LocationType;
import model.Person;
import model.Weather;

import java.util.*;

/**
 * TODO
 *
 * @author      Patryk Zygmunt
 * @author      Grzegorz Puczkowski
 * @author      Hubert Rędzia
 */
public class RecommendationSystem {

private Weather  weather;
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param locationList locations list to set
     */
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
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      Weather
     */
    public Weather getWeather(){
        return weather;
    }
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param month month number
     * @param day day number
     * @param location location to count attractivness
     * @return indicator of weather
     */
public  double countAttractiveInCurrentWeather(int month, int day, Location location){
    double indicator = weather.countWeatherIndicator(month,day);
   // System.out.println(indicator);
    boolean covered =  location.getTypes().getLocationProperties().isCovered();
        return covered ? 100 : indicator;
}
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param person person
     * @param location location
     * @return Attractivness without cost
     */
public double countAttractiveForPersonWithoutCost(Person person,Location location){
    return Functions.attractiveForPersonWithoutCostFunction(
            person.getActivity(),location.getTypes().getLocationProperties().getActivity(),
            person.getArt(),location.getTypes().getLocationProperties().getArt(),
            person.getHistory(),location.getTypes().getLocationProperties().getHistory());
}
//bug
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param person person
     * @param location location
     * @return Cost indicator
     */
private double getCostIndicator( Person person,Location location){
    return Functions.expensiveIndicatorForPersonFunction
            (location.getTypes().getLocationProperties().getCost(),person.getRich());
}
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param location Location
     * @return place overflow
     */
private double getIndicatorForLocationOverflow(Location location){
    return location.countPlaceOverflow();
}

    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param person
     * @param location
     * @return Attractivness with cost
     */
  private double countAttractiveForPerson(Person person,Location location){
    return Functions.attractiveForPersonWithCostFunction(countAttractiveForPersonWithoutCost(person,location),getCostIndicator(person,location));
  }

  private double countDistanceIndicator(Person person,Location location){
      if(person.actualLocation ==null) return 100;
      double distance  = DIstanceCounter.distance(person.actualLocation.latitude,location.latitude,
                                            person.actualLocation.longitude,location.longitude);
      return ((3000-distance)/3000)*100;

  }
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param month month
     * @param day day
     * @param location location
     * @param person person
     * @return Attractivness
     */
public double countAttractive(int month,int day,Location location,Person person){
      return Functions.attractiveFunction(
              countAttractiveForPerson(person,location),countAttractiveInCurrentWeather(month,day,location)
              ,getIndicatorForLocationOverflow(location),countDistanceIndicator(person,location));
}

//TODO turyst moze nie wybrac zadnej lokalizacji
//liczy mape skumulowaną i ocena wszystkich okacji sumuje sie do 100
    // czyli <zamek,33>,<inny zamek, 66>
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param month month
     * @param day day
     * @param person person
     * @return map of rated locations
     */
public Map<Location,Double> rateLocations(int month, int day, Person person){
    Map<Location,Double> ratesMap = new LinkedHashMap<>();
    Map<Location,Double> comulatedRatesMap = new LinkedHashMap<>();
    double lastRate=0;
    double cumulatedRate=0;
   
    for (Location location:locationList
         ) {
        if(person.alreadyVisitedLocation.contains(location)) lastRate+= 0;
        else lastRate=countAttractive(month,day,location,person);

        cumulatedRate+=lastRate;
            ratesMap.put(location,lastRate);
    }
  //  System.out.println(ratesMap);
  final double finalCumulatedRate = cumulatedRate;
  locationList.stream().forEach(loc -> {
        ratesMap.computeIfPresent(loc, (l, d) -> (d / finalCumulatedRate) * 100);
    });
    double lastCumulatedRate=0;
    for (Map.Entry<Location, Double> entry : ratesMap.entrySet()) {
              lastCumulatedRate+=entry.getValue();
      comulatedRatesMap.put(entry.getKey(),lastCumulatedRate);
    }
       return comulatedRatesMap;
}


//rand wybiuera 0-100 i sprawdza pierwsza lokacje która   ma ta skumulowana wartość wieksa niż liczba wylosowana
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param month month
     * @param day day
     * @param person person
     * @return Recommend location
     */
public Location recommendLocation(int month, int day, Person person) {
    //lokacje dodane  testowe
    int iterator=0;
    Map<Location,Double> ratesMap = rateLocations(month,day,person);
    List<Location>  result = new ArrayList<>();
   double randomValue = Math.random()*100;

    for (Map.Entry<Location, Double> entry : ratesMap.entrySet()) {
        if (entry.getValue() > randomValue || ++iterator==ratesMap.size()) result.add(entry.getKey());
    }

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
