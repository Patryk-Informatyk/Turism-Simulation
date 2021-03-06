package init;

import model.DayWeather;
import model.MonthWeather;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Klasa obsługująca pogodę
 *
 * @author      Patryk Zygmunt
 * @author      Grzegorz Puczkowski
 * @author      Hubert Rędzia
 * @version     1.0
 * @since       1.0
 */
public class WeatherChecker {


 private static  String token = null;// getAuthorizationToken();


   /// http://developer.awhere.com/api/reference/weather/norms
    //do kożdego zapytania trzeb adodać token con.setRequestProperty("Authorization", "Bearer "+token);
    //token ważny godzine


    /**
     * Co to wlasciwie robi?
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @throws IOException
     * @throws ProtocolException
     * @return Authorization response
     */
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


    /**
     * Funkcja pobierająca token autoryzujący. Token jest ważny godzinę
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @throws JSONException
     * @return Authorization token
     */
    private static String getAuthorizationToken(){
        try {
            JSONObject jsonAuth = new JSONObject(getAuthorizationResponse());
            return jsonAuth.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Funkcja zwraca odpowiedź serwera Google przy zapytaniu o pogodę. Odpowiedź zapisuje do pliku
     * weather.json w formie json.
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param url adres strony
     * @throws IOException on connection faiure
     * @return Actual weather
     */
    public static String getWeatherResponse(String url) throws IOException{
        try {
            URL urlToConnection = new URL(url);
            HttpURLConnection con = null;
            con = (HttpURLConnection) urlToConnection.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer "+token);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            BufferedWriter writer = new BufferedWriter(new FileWriter("weather.json"));
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                writer.append(inputLine);
                writer.newLine();
            }
            con.disconnect();
            in.close();
            writer.close();
            return content.toString();
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();}
        return url;

    }




    /**
     * costam
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param month pożądany miesiąc
     * @throws IOException on getWeatherResponse failure
     * @return sth
     */
 public static MonthWeather getWeatherInMonth(int month) throws IOException {
        try {
            String monthUrl = month < 9 ? "0" + month: Integer.toString(month);
           JSONObject jsonMonth = new JSONObject(
                    getWeatherResponse("https://api.awhere.com/v2/weather/fields/dfs45eg34522/norms/"+monthUrl+"-01," +monthUrl+"-30/years/2013,2016"));
        /*    JSONParser parser = new JSONParser();

            JSONObject jsonMonth = (JSONObject)parser.parse(new FileReader("weather.json"));*/
             JSONArray jsonMonthArr = jsonMonth.getJSONArray("norms");
             MonthWeather monthWeather = new MonthWeather();
             for(int i=0;i<jsonMonthArr.length();i++){
                 monthWeather.addDay(parseJsonToDay(i,jsonMonthArr.getJSONObject(i)));
             }
             return monthWeather;
        } catch (JSONException e) {
            e.printStackTrace();
        }
     return null;
    }


    /**
     * Wczytuje informacje na temat pogody z pliku weather.json dla całego roku
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return list of MonthWeather elements
     */
    public static List<MonthWeather> getWeatherForYear(){
        try {
          //  JSONObject jsonYear = new JSONObject(
                //    getWeatherResponse("https://api.awhere.com/v2/weather/fields/dfs45eg34522/norms/01-01,12-30/years/2013,2016"));
            Path path = Paths.get("weather.json");

            StringBuilder data = new StringBuilder();
            Stream<String> lines = Files.lines(path);
            lines.forEach(line -> data.append(line));
            lines.close();
            JSONObject jsonYear = new JSONObject(data.toString());
            JSONArray jsonYearArr = jsonYear.getJSONArray("norms");
            List<MonthWeather> months = new LinkedList<>();
            int month,day;
            for(int i=0;i<jsonYearArr.length();i++){
                month = Integer.parseInt(jsonYearArr.getJSONObject(i).getString("day").split("-")[0]);
                if(month > months.size()){
                    months.add(new MonthWeather());
                }
                months.get(month-1).addDay(parseJsonToDay(i,jsonYearArr.getJSONObject(i)));
            }

            return  months;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } return  null;
    }

  /*  private static MonthWeather parseJsonToMont(int month,JSONObject jsonObject){


    }*/



    /**
     * Parsuje JSONa weather.json i przypisuje poszczególnym dniom prognozy pogody
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     *
     * @param i index
     * @param jsonObject object to parse
     * @throws IOException
     * @throws ProtocolException
     * @return new element of DayWeather
     */
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
        List<MonthWeather>   m = WeatherChecker.getWeatherForYear();;
        for (MonthWeather mw:m
             ) {
            System.out.println(mw.getAmountOfDays());

        }


    }

}
