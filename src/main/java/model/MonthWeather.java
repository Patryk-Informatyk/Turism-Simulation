package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 19.11.2017.
 */
public class MonthWeather {
    String month;
    List<DayWeather> days= new ArrayList<>();

    public void addDay(DayWeather day){
        days.add(day);
    }
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

    public int getAmountOfDays(){
        return days.size();
    }

    public List<DayWeather> getDays() {
        return days;
    }
}
