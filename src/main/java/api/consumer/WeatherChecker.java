package api.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.DayWeather;
import model.MonthWeather;
import model.Weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Linus on 19.11.2017.
 */
public class WeatherChecker {


  private static  String token =  getAuthorizationToken();


    private static String getAuthorizationResponse(){
        try {
            URL urlToConnection = new URL("https://api.awhere.com/oauth/token");
            HttpURLConnection con = null;
            con = (HttpURLConnection) urlToConnection.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Authorization", "Basic bEdCUzZtZ294djdLa2UzUkdzR0g3OW1neVI2M2U2Qmg6aHhKZmVpN2lBYzdnQWN2QQ==");
            OutputStream os = con.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write("grant_type=client_credentials");
            osw.flush();
            osw.close();
            os.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            con.disconnect();
            in.close();

            return content.toString();
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();}
        return null;
    }



    private static String getAuthorizationToken(){
        try {
            JSONObject jsonAuth = new JSONObject(getAuthorizationResponse());
            return jsonAuth.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeatherInMonthResponse(String url){
        try {
            URL urlToConnection = new URL(url);
            HttpURLConnection con = null;
            con = (HttpURLConnection) urlToConnection.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            System.out.print(token);
            con.setRequestProperty("Authorization", "Bearer "+token);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            con.disconnect();
            in.close();
            return content.toString();
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();}
        return url;

    }

 public static MonthWeather getWeatherInMonth(int month){
        try {
            String monthUrl = month < 9 ? "0" + month: Integer.toString(month);
            JSONObject jsonMonth = new JSONObject(getWeatherInMonthResponse("https://api.awhere.com/v2/weather/fields/dfs45eg34522/norms/"+monthUrl+"-01," +monthUrl+"-30/years/2013,2016"));
             JSONArray jsonMonthArr = jsonMonth.getJSONArray("norms");
             MonthWeather monthWeather = new MonthWeather();
             for(int i=0;i<jsonMonthArr.length();i++){
                 ObjectMapper mapper = new ObjectMapper();
                 monthWeather.addDay(parseJsonToDay(i,jsonMonthArr.getJSONObject(i)));
             }
             return monthWeather;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static DayWeather parseJsonToDay(int i,JSONObject jsonObject) {
        final String[] propertiesArr ={"meanTemp","precipitation","solar","averageWind"};
        try {
            return new DayWeather(Integer.toString(i+1),jsonObject.getJSONObject("meanTemp").getDouble("average"),
                    jsonObject.getJSONObject("precipitation").getDouble("average"),
                    jsonObject.getJSONObject("solar").getDouble("average"),
                    jsonObject.getJSONObject("averageWind").getDouble("average"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;

    }


    public static void main(String[] args)  {

        System.out.println(WeatherChecker.token);
        System.out.println(WeatherChecker.getWeatherInMonth(5));

    }

}
