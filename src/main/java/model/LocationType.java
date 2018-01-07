package model;

/**
 * Created by Linus on 25.11.2017.
 */
public enum LocationType {
    //te nieuzupełnione typy trzeba podefiniowac i mozna dodac własne
	/*
	    this.covered = covered;
        this.history = history;
        this.activity = activity;
        this.art = art;
        this.cost = cost;
        this.averageTime = avgTime;
        this.maxCapacity = maxC;

        trzeba uzupelnic konstruktory, bo nie zawieraja wszystkich pol

	 */
    amusement_park(new LocationProperties(false,15,90,40,1)),
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
    old_town(new LocationProperties(false,80.0,30,80,10)),
    bridge(new LocationProperties(false,80.0,30,80,10)),
    monument(new LocationProperties(false,80.0,30,80,10)),
    route(new LocationProperties(false,70,20,90,1));

    private LocationProperties locationProperties;

    LocationType(LocationProperties locationProperties) {
        this.locationProperties = locationProperties;
    }

    public LocationProperties getLocationProperties() {
        return locationProperties;
    }
}
