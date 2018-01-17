package model;

/**
 * Enum to create locations mapping them with described locations' types.
 *
 * @author      Patryk Zygmunt
 * @author      Grzegorz Puczkowski
 * @author      Hubert Rędzia
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
    amusement_park(new LocationProperties(false,2,32,40,20)),
    aquarium(new LocationProperties(true, 0, 100, 5, 20)),
    art_gallery(new LocationProperties(true, 40, 5, 100, 20)),
    bar(new LocationProperties(true, 10, 40, 20, 30)),
    cafe(new LocationProperties(true,10, 10, 25, 25)),
    cemetery(new LocationProperties(false, 80, 10, 15, 1)),
    church(new LocationProperties(true, 70, 1, 60, 1)),
    city_hall(new LocationProperties(true, 5, 1, 1, 10)),
    natural_feature(new LocationProperties(false, 40, 30, 40, 10)),
    place_of_worship(new LocationProperties(false, 70, 1, 60, 1)),
    square(new LocationProperties(false,57,40,40,1)),
    museum(new LocationProperties(true,90,13,90,5)),
    old_town(new LocationProperties(false,90.0,10,30,10)),
    bridge(new LocationProperties(false,20.0,20,10,1)),
    monument(new LocationProperties(false,80.0,1,90,1)),
    route(new LocationProperties(false,10,20,1,1));

    private LocationProperties locationProperties;

    LocationType(LocationProperties locationProperties) {
        this.locationProperties = locationProperties;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      Location's properties
     */
    public LocationProperties getLocationProperties() {
        return locationProperties;
    }
}
