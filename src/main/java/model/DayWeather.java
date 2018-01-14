package model;

import java.time.LocalDate;

/**
 * Klasa przechowywująca informacje o prognozie pogody dla konkretnego dnia w roku
 *
 * @author      Patryk Zygmunt
 * @author      Grzegorz Puczkowski
 * @author      Hubert Rędzia
 * @version     1.0
 * @since       1.0
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

    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      specific day
     */
    public String getDay() {
        return day;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      number of day in month
     */
    public int getDayNumberInMonth() {
        return dayNumberInMonth;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      ?
     */
    public LocalDate getDate() {
        return date;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      temperature in day
     */
    public double getTemperature() {
        return temperature;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      Precipation in day
     */
    public double getPrecipation() {
        return precipation;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      Solar in day
     */
    public double getSolar() {
        return solar;
    }
    /**
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      Wind in day
     */
    public double getWind() {
        return wind;
    }
}
