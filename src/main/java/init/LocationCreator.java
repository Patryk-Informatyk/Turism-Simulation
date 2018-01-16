package init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import model.Location;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tutaj bedzie kozacki opis klasy
 *
 * @author      Patryk Zygmunt
 * @author      Grzegorz Puczkowski
 * @author      Hubert Rędzia
 * @version     1.0
 * @since       1.0
 */
public class LocationCreator {

    /**
     * Wczytuje dane z pliku place2.json z głównego katalogu i zwraca je w liście, której elementy
     * odpowiadają poszczególnym lokacjom. Mają one formę JSONa, przykładowy element wygląda tak:
     * <p>
     * {
     * "html_attributions" : [],
     * "result" : {
     * "address_components" : [],
     * "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png",
     * "id" : "3cb926b117a6c10f4d0766f6a08d5653398db5e9",
     * "name" : "Ruiny hotelu",
     * "place_id" : "ChIJtahuEqRFFkcR_FvSEgFXEu4",
     * "reference" : "CmRbAAAAFZlZhq7ola5sNXueSmaxGyBWa_KHRlM_RNqks1VzAo5engbhaSl0I80CZcAfylnZ7Sf7jl0CfyyOY-elGV2mDb87gyloErWlxRBM3cniOn-JHpHvxdzwH9UonW7rijHVEhAFGaL-FJ7jO-lQrOnieiLGGhRfgOF1gYyjgOmRMPDQcmpfrz5aGg",
     * "scope" : "GOOGLE",
     * "types" : "old_town",
     * },
     * "status" : "OK"
     * }
     * <p>
     * Zwracana lista ma tyle elementów, ile wczytywany json zawiera lokacji
     *
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @throws IOException when cannot load place2.json
     * @throws JSONException when json fail
     * @return list od locations in json style wrapped in String
     */
    ///https://developers.google.com/places/web-service/details dokumentacje
    public static List<String> getLocationsListFromFile() throws IOException, JSONException{
        // ------ poniższy kod wczytuje do Stringa content plik ------------------
        BufferedReader br = new BufferedReader(new FileReader("place.json"));
        String content = "";
        try {
            String line = br.readLine();
            while (line != null) {
                content = content + line + "\n";
                line = br.readLine();
             }
        } finally {
            br.close();
        }

        JSONArray jsonPlaces = new JSONArray(content.toString());
        List<String> locationList = new ArrayList();
        for(int i=0;i<jsonPlaces.length();i++){
            JSONObject jsonObject = jsonPlaces.getJSONObject(i);
            locationList.add(jsonObject.toString());
        }
        return locationList;
    }

    public static List<String> getStringListFromJsonArray(JSONArray jArray) throws JSONException {
        List<String> returnList = new ArrayList<String>();
        for (int i = 0; i < jArray.length(); i++) {
            String val = jArray.getString(i);
            returnList.add(val);
        }
        return returnList;
    }

    /**
     * Mapuje wczytane z pliku json lokacje i mapuje je z przyjętymi typami {@link model.LocationType}
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @throws IOException when cannot load place2.json
     * @throws JSONException when json fail
     * @return list of created locations
     */
	public static List<Location> createLocationsFromFile() throws IOException, JSONException {

		List<String> jsonLocationList = getLocationsListFromFile();
		List<Location> locations = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();
        String currentLocation = null;

        for(int i = 0; i < jsonLocationList.size(); i++) {
            currentLocation = jsonLocationList.get(i);
            JSONObject jsonObject = new JSONObject(currentLocation);
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("open_hours");
            List<String> hours = getStringListFromJsonArray(jsonArray);

            System.out.println(hours.get(0) + " " + hours.get(1));

            Location location = mapper.readValue(jsonObject.get("result").toString(), Location.class);
            location.latitude = jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lat");
            location.longitude = jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lng");
            location.setOpeningHours(hours);

            locations.add(location);
        }
        return locations;
	}
    private List<String> parseFile() throws IOException {
        List<String> id = new ArrayList<>();
        Path path = Paths.get("locationsid.txt");
        StringBuilder data = new StringBuilder();
        Stream<String> lines = Files.lines(path);
        lines.forEach(line -> id.add(line));
        lines.close();
        System.out.print(id);
        return id;


    }

    /**
     * Wysyła zapytanie do Google Places o placeID lokacji w pobliżu Krakowa
     *
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @throws IOException on failure
     * @return list of placeIDs from Google Places Api
     */
    public static List<String> getGoogleLocationList() throws IOException {

            String content =
                    makeGetRequest("https://maps.googleapis.com/maps/api/place/textsearch/json" +
                            "?query=krakow+city+point+of+interest&language=pl&key=AIzaSyBvmCmjFCsAPTzJ9AnEF7WIJ6dt4LFxrEY");
        JSONArray places = null;
        try {
            places = new JSONObject(content.toString()).getJSONArray("results");

        List<String> locationsId = new ArrayList();

            for(int i=0;i<places.length();i++){
                    locationsId.add(places.getJSONObject(i).getString("place_id"));
            }
          //  System.out.println(locationsId);
            return locationsId;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
        }

    /**
     * Funkcja wysyłająca zapytanie na wskazany adres
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param url adres strony
     * @throws IOException when ?
     * @return response from site in String format
     */
    public static String makeGetRequest(String url) throws IOException{
        try {
            URL urlToConnection = new URL(url);
            HttpURLConnection con = null;
            con = (HttpURLConnection) urlToConnection.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
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
    public static String makeGetRequestToFile(String url, BufferedWriter writer){
        try {
            URL urlToConnection = new URL(url);
            HttpURLConnection con = null;
            con = (HttpURLConnection) urlToConnection.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                writer.append(inputLine);
                writer.newLine();
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

    /**
     * Funkcja pobiera dane z serwera, przy pomocy placeID
     * i tworzy lokacje na podstawie odpowiedzi i zwróconego typu
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param placeId ID lokacji
     * @throws IOException when cannot load place2.json
     * @throws JSONException when json fail
     * @return created location
     */
    private static Location createLocationUsingPlaceId(String placeId){
        //TODO try with resource
        try {
         String content =   makeGetRequest("https://maps.googleapis.com/maps/api/place/details/json?placeid="
                 +placeId+"&key=AIzaSyBvmCmjFCsAPTzJ9AnEF7WIJ6dt4LFxrEY&language=pl");
            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonObject = new JSONObject(content.toString());
            Location location = mapper.readValue(jsonObject.get("result").toString(), Location.class);
                return location;
            }
        catch (IOException e) {
                e.printStackTrace();
            }
        catch (JSONException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        finally {
        	// wyjebalbym to stad, bo i tak zwraca null to po co to?

        }
        return null;
    }
    /**
     * Funkcja tworzy listę lokacji, na podstawie odpowiedzi od serwera Google
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @throws IOException on failure
     * @return Created list of locations
     */
    public static List<Location> getLocationList() throws IOException {
      return   getGoogleLocationList().stream().map(
                id -> createLocationUsingPlaceId(id))
                .collect(Collectors.toList());
    }



    public void createPLaceFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("place2.json"));
        parseFile().stream().forEach(
                id -> makeGetRequestToFile("https://maps.googleapis.com/maps/api/place/details/json?placeid="
                        + id + "&key=AIzaSyBvmCmjFCsAPTzJ9AnEF7WIJ6dt4LFxrEY&language=pl", writer));
        writer.close();
    }

    public static void main(String[] args) throws IOException, JSONException {
       LocationCreator lc =  new LocationCreator();
       System.out.print(LocationCreator.createLocationsFromFile());
    }
}
