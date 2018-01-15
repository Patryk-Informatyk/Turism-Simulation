package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import function.Functions;

/**
 * Tutaj bedzie kozacki opis klasy
 *
 * @author      Patryk Zygmunt
 * @author      Grzegorz Puczkowski
 * @author      Hubert Rędzia
 * @version     1.0
 * @since       1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    @JsonProperty("name")
    String name;
    @JsonProperty("place_id")
    String placeId;
    @JsonProperty("types")
    LocationType types;
    @JsonProperty("capacity")
    private int maxSize;
    @JsonProperty("spend_time")
    private double averageSpendTime;
    public double latitude;
    public double longitude;

    public double getAverageSpendTime() {
        return averageSpendTime;
    }

    private int amountOfTourists = 0;
  // private int amountOfTouristsMultiplicator;
  private int queue;
  private boolean isCovered;
  private double placeOverflow;

    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      Queue to location
     */
    public int getQueue() {
        return queue;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      Location's name
     */

    public String getName() {
        return name;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      location's Google PlaceId
     */
    public String getPlaceId() {
        return placeId;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      Types of locations
     */
    public LocationType getTypes() {
        return types;
    }

    public Location(String name, String placeId, LocationType types) {
        this.name = name;
        this.placeId = placeId;
        this.setTypes(types);
    }

    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param types set Type of Location {@link LocationType}
     */
    public void setTypes(LocationType types) {
        this.types = types;
       isCovered = getTypes().getLocationProperties().isCovered();
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      overflow ??
     */
    public double countPlaceOverflow(){
        if(!isCovered) return Functions.queueToLocationFunctionMax();
        return Functions.queueToLocationFunction(maxSize,queue);
    }

    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      true if amount of tourists is greater than maximal size of place, false otherwise.
     */
    private boolean isMaxTouristAmount(){
        return maxSize<=amountOfTourists;
    }

    public Location() {
    }
    /**
     * If place is covered and maximal amount of tourists is achieved, person is added to queue
     * otherwise is added to place
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     */
    public void addTourist(){
        if(isMaxTouristAmount() && isCovered) queue++;
        else amountOfTourists++;
    }
    /**
     * "Close" day in locations, sets amountOfTourists and queue to number 0
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     */
    public void endDay(){
        this.amountOfTourists = 0;
        this.queue = 0;
       
    }

    public int getAmountOfTourists() {
        return amountOfTourists;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      true if place is covered, false otherwise
     */
    public boolean isCoveredf(){
        return isCovered;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", placeId='" + placeId + '\'' +
                ", types=" + types +
                ", maxSize=" + maxSize +
                ", averageSpendTime=" + averageSpendTime +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", amountOfTourists=" + amountOfTourists +
                ", queue=" + queue +
                ", isCovered=" + isCovered +
                ", placeOverflow=" + placeOverflow +
                '}';
    }

    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      Maximal amount of tourists in that place
     */
    public int getMaxSize() {
        return maxSize;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param maxSize Maximal number of tourists in place
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return placeId != null ? placeId.equals(location.placeId) : location.placeId == null;
    }

    @Override
    public int hashCode() {
        return placeId != null ? placeId.hashCode() : 0;
    }
}
