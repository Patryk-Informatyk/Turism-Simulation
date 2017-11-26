package model;

import java.time.LocalDate;

/**
 * Created by Linus on 19.11.2017.
 */
public class DayWeather {
    String day;
    int dayNumberInMonth;
    LocalDate date;
    double temperature;

    @Override
    public String toString() {
        return "DayWeather{" +
                "day='" + day + '\'' +
                ", dayNumberInMonth=" + dayNumberInMonth +
                ", date=" + date +
                ", temperature=" + temperature +
                ", precipation=" + precipation +
                ", solar=" + solar +
                ", wind=" + wind +
                '}';
    }

    double precipation;
    double solar;
    double wind;

    public DayWeather(String day, double temperature, double precipation, double solar, double wind) {
        this.day = day;
        this.temperature = temperature;
        this.precipation = precipation;
        this.solar = solar;
        this.wind = wind;
    }

    public String getDay() {
        return day;
    }

    public int getDayNumberInMonth() {
        return dayNumberInMonth;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPrecipation() {
        return precipation;
    }

    public double getSolar() {
        return solar;
    }

    public double getWind() {
        return wind;
    }
}
