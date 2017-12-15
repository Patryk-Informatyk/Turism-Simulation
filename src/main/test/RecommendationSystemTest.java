import model.Location;
import model.LocationType;
import model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;



/**
 * Created by Comarch on 2017-11-28.
 */
class RecommendationSystemTest {

    RecommendationSystem rc ;
    Person pArt = new Person(42,20,80,10);
    Person pSport = new Person(42,80,20,10);


@BeforeAll
    void setLocation(){
    rc= new RecommendationSystem();
    List<Location> locations = new LinkedList<>();
    Location l1 = new Location("Name","12", LocationType.old_town);
    Location l2 = new Location("sqr","12",LocationType.square);
    Location l3 = new Location("Park","12",LocationType.amusement_park);
    locations.add(l1);
    locations.add(l2);
    locations.add(l3);
   rc.setLocationList(locations);
}

    @Test
    void rateLocations() {






    }

    @Test
    void recommendLocation() {
    }

   @Test
   void countAttractiveForPerson(){

      Location l3 = new Location("Park","12",LocationType.museum);
       Location lo = new Location("Park","12",LocationType.old_town);
        System.out.println(rc.countAttractive(2,2,l3,pArt));

     System.out.println(rc.countAttractive(2,2,l3,pArt));
   /*    Location ls = new Location("Park","12",LocationType.square);
       System.out.println(rc.countAttractiveForPerson(pArt,ls));
       System.out.println(rc.countAttractiveForPerson(pSport,ls));*/
   }

}
