package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import function.Functions;

/**
 * Created by Linus on 18.11.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    @JsonProperty("name")
    String name;
    @JsonProperty("place_id")
    String placeId;

    LocationType types;

  private int amountOfTourists = 0;



    private int maxSize;
  private int queue;
  private boolean isCovered;
  private double placeOverflow;

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
        setTypes(types);
    }


    public void setTypes(LocationType types) {
        this.types = types;
       isCovered = getTypes().getLocationProperties().isCovered();
    }

    public double countPlaceOverflow(){
        if(!isCovered) return Functions.queueToLocationFunctionMax();
        return Functions.queueToLocationFunction(maxSize,queue);
    }


    private boolean isMaxTuristAmount(){
        return maxSize>=amountOfTourists;
    }

    public Location() {
    }

    public void addTourist(){
        if(isMaxTuristAmount() && isCovered) queue++;
        else amountOfTourists++;
    }

    public void endDay(){
        this.amountOfTourists = 0;
        this.queue = 0;
    }

    public int getAmountOfTourists() {
        return amountOfTourists;
    }

    @Override

    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", placeId='" + placeId + '\'' +
                '}';
    }

    @Override public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Location location = (Location) o;
        if (placeId != null ? !placeId.equals(location.placeId) : location.placeId != null)
            return false;

        if (amountOfTourists != location.amountOfTourists)
            return false;
        if (name != null ? !name.equals(location.name) : location.name != null)
            return false;

        return types == location.types;
    }

    @Override public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (placeId != null ? placeId.hashCode() : 0);
        result = 31 * result + (types != null ? types.hashCode() : 0);
        result = 31 * result + amountOfTourists;
        return result;
    }
    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
