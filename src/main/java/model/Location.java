package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Linus on 18.11.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    @JsonProperty("name")
    String name;
    @JsonProperty("place_id")
    String placeId;
    //TODO ystwanie typu na podstawie typu z google

    LocationType types;

    public String getName() {
        return name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public LocationType getTypes() {
        return types;
    }

    public Location(String name, String placeId, LocationType types) {
        this.name = name;
        this.placeId = placeId;
        this.types = types;
    }

    public Location() {
    }

    @Override

    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", placeId='" + placeId + '\'' +
                '}';
    }
}
