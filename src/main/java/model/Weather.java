package model;

import api.consumer.WeatherChecker;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Linus on 19.11.2017.
 */
public class Weather {
    final static int  MAX_RAIN = 30;
    private List<MonthWeather> monthWeathers = new LinkedList();

  public  Weather(){
        for(int i =1;i <= 1;i++){
            monthWeathers.add(WeatherChecker.getWeatherInMonth(i));

        }
    }


 public DayWeather getWeatherInDay(int month,int day){
        return monthWeathers.get(month-1).getDay(day-1);
   }

   public double countWeatherIndicator(int month ,int day){
       DayWeather dayWeather = getWeatherInDay(month,day);
       double rainy  = countRainIndicator(dayWeather.getPrecipation());
       double temp = countTemperatureIndicator(dayWeather.getTemperature());
       System.out.println(rainy +" " +temp);
      // System.out.println((200-(1.2*rainy)+(0.8*temp))/2);
        return (200-((1.2*rainy)+(0.8*temp)))/2;

   }

    private double countRainIndicator(double precipation) {
       if(precipation>30) return 100;
       if(precipation==0) return 0.00001;
       return precipation/30.0 * 100;
    }

    private double countTemperatureIndicator(double temperature) {
       if(temperature >50|| temperature<-10) return 100;
        return Math.abs(temperature-20)/30 * 100;
    }


}
