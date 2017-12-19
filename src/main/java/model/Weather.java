package model;

import init.WeatherChecker;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Linus on 19.11.2017.
 */
public class Weather {
    final static int  MAX_RAIN = 30;
    private List<MonthWeather> monthWeathers = new LinkedList();



  public  Weather(){
      monthWeathers  = WeatherChecker.getWeatherForYear();
    }


 public DayWeather getWeatherInDay(int month,int day){
        return monthWeathers.get(month-1).getDay(day-1);
   }

///  najlepsza pogoda 0 najgorsza 100
   public double countWeatherIndicator(int month ,int day){
       DayWeather dayWeather = getWeatherInDay(month,day);
       double rainy  = countRainIndicator(dayWeather.getPrecipation());
       double temp = countTemperatureIndicator(dayWeather.getTemperature());
        return (200-((1.2*rainy)+(0.8*temp)))/2;

   }
/// duze opady 100 brak 0
    private double countRainIndicator(double precipation) {
       if(precipation>30) return 100;
       if(precipation==0) return 0.00001;
       return precipation/30.0 * 100;
    }
    /// zle  temp100 dobre  0
    private double countTemperatureIndicator(double temperature) {
       if(temperature >50|| temperature<-10) return 100;
        return Math.abs(temperature-20)/30 * 100;
    }


}
