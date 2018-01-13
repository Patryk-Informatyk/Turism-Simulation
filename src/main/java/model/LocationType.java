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
        this.maxCapacity = maxC;

        trzeba uzupelnic konstruktory, bo nie zawieraja wszystkich pol

	 */
    amusement_park(new LocationProperties(false,15,90,40,20)),
    aquarium(new LocationProperties(true, 0, 100, 5, 20)),
    art_gallery(new LocationProperties(true, 40, 5, 100, 20)),
    bar(new LocationProperties(true, 10, 20, 20, 30)),
    cafe(new LocationProperties(true,10, 10, 25, 25)),
    cemetery(new LocationProperties(true, 80, 10, 15, 1)),
    church(new LocationProperties(true, 70, 1, 60, 1)),
    city_hall(new LocationProperties(true, 5, 30, 20, 90)),
    natural_feature(new LocationProperties(false, 80, 30, 40, 10)),
    place_of_worship(new LocationProperties(false, 70, 1, 60, 1)),
    square(new LocationProperties(false,77,60,20,1)),
    museum(new LocationProperties(true,90,20,90,1)),
    old_town(new LocationProperties(false,90.0,30,60,20)),
    bridge(new LocationProperties(false,80.0,2,35,10)),
    monument(new LocationProperties(false,80.0,1,90,1)),
    route(new LocationProperties(false,70,20,90,1));

    private LocationProperties locationProperties;

    LocationType(LocationProperties locationProperties) {
        this.locationProperties = locationProperties;
    }

    public LocationProperties getLocationProperties() {
        return locationProperties;
    }
}
