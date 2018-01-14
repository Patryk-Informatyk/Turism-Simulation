package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa zawierająca listę prognozy pogody dla każdego dnia w miesiącu.
 *
 * @author      Patryk Zygmunt
 * @author      Grzegorz Puczkowski
 * @author      Hubert Rędzia
 * @version     1.0
 * @since       1.0
 */
public class MonthWeather {
    String month;
    List<DayWeather> days= new ArrayList<>();

    /**
     * Dodaje dzień do listy
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param day current day to add
     */
    public void addDay(DayWeather day){
        days.add(day);
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param day number of day
     * @return specific day
     */
    public DayWeather getDay(int day){
        return days.get(day);
    }

    @Override
    public String toString() {
        return "MonthWeather{" +
                "month='" + month + '\'' +
                ", days=" + days +
                '}';
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return Amount of days
     */
    public int getAmountOfDays(){
        return days.size();
    }

    /**
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @return list of days
     */
    public List<DayWeather> getDays() {
        return days;
    }
}
