package init;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Location;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class LocationCreator {


    ///https://developers.google.com/places/web-service/details dokumentacje


	/*
	// Za pomoca wspolrzednych z attractionstst.txt zapisuje do locationsid.txt place_id
	public static void getPlaceIdsFromCoords() throws IOException, JSONException{
		BufferedReader br = new BufferedReader(new FileReader("attractionstst.txt"));
		Writer writer = null;
		List<String> locationsList = getGoogleLocationList();
		JSONArray place = null;
		String place_id;
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("locationsid.txt"), "utf-8"));
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		        if (line == null) break;
		        System.out.println(line);
	            String content =
	                    makeGetRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng="+line+"&key=AIzaSyBt_Ggj3MMf-j92HsvACN5wfHvciHOrYys");
	            place = new JSONObject(content.toString()).getJSONArray("results");
	            place_id = place.getJSONObject(0).getString("place_id");
						writer.write(place_id + "\n");
						System.out.println(place_id);
		    }
		} finally {
		    br.close();
		    writer.close();
		}
	}
	*/

    //zwraca placeId do miejsc poleconych w google
    public static List<String> getGoogleLocationList(){

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

    public static String makeGetRequest(String url){
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

 // przy pomocy placeid POBIERA dane o jakiejś lokacji
    private static Location createLocationUsingPlaceId(String placeId){
        //TODO try with resource
        try {
         String content =   makeGetRequest("https://maps.googleapis.com/maps/api/place/details/json?placeid="
                 +placeId+"&key=AIzaSyBvmCmjFCsAPTzJ9AnEF7WIJ6dt4LFxrEY&language=pl");
            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonObject = new JSONObject(content.toString());
            Location location = mapper.readValue(jsonObject.get("result").toString(), Location.class);
          //  System.out.println(location);
            return location;
        }
        catch (JSONException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        finally {
        	// wyjebalbym to stad, bo i tak zwraca null to po co to?
        	return null;
        }
    }
    // tworzy lista lokacji na podstawie polecanych w gogle
    public static List<Location> getLocationList(){
      return   getGoogleLocationList().stream().map(
                id -> createLocationUsingPlaceId(id))
                .collect(Collectors.toList());
    }




    public static List<String> getLocationListFromFile() throws IOException{

    		BufferedReader br = new BufferedReader(new FileReader("locationsid.txt"));
		    StringBuilder sb = new StringBuilder();
		    List<String> locationsId = new ArrayList();
		try{
		    String placeId = br.readLine();
		    while (placeId != null) {
		        sb.append(placeId);
		        sb.append(System.lineSeparator());
		        placeId = br.readLine();
		        if (placeId == null) break;
				locationsId.add(placeId);
		    }
		    return locationsId;
		}catch (IOException ex){
            System.out.println(ex);
            ex.printStackTrace();
		} finally {
		    br.close();
		    for( int i=0; i< locationsId.size(); i++){
       System.out.println(locationsId.get(i));
		    }
		}
		return null;
    }


    public static void main(String[] args) throws IOException, JSONException  {
       LocationCreator lc =  new LocationCreator();
       lc.getGoogleLocationList().stream().forEach(
               id -> lc.createLocationUsingPlaceId(id)
       );
      // getPlaceIdsFromCoords();
    }
}
