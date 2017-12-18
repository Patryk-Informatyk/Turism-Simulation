package model;

/**
 * Created by Linus on 25.11.2017.
 */
public enum LocationType {
    amusement_park(new LocationProperties(false,15,90,50,1)),
    aquarium(new LocationProperties(true)),
    art_gallery(new LocationProperties(true)),
    bar(new LocationProperties(true)),
    cafe(new LocationProperties(true)),
    cemetery(new LocationProperties(true)),
    church(new LocationProperties(true)),
    city_hall(new LocationProperties(true)),
    natural_feature(new LocationProperties(false)),
    place_of_worship(new LocationProperties(false)),
    square(new LocationProperties(false,77,60,20,1)),
    museum(new LocationProperties(true,70,20,90,1)),
    old_town(new LocationProperties(false,80.0,30,80,10));

    private LocationProperties locationProperties;

    LocationType(LocationProperties locationProperties) {
        this.locationProperties = locationProperties;
    }

    public LocationProperties getLocationProperties() {
        return locationProperties;
    }
}
