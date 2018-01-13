package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.DayWeather;
import model.Location;
import org.json.JSONException;
import simulation.Simulation;

import java.io.IOException;
import java.util.List;

public class Controller {
    Simulation simulation = new Simulation();
    List<Location> locations;

    public Controller() throws IOException, JSONException {

        locations = simulation.getLocations();
    }
       //dodajemy w scence buliderze nowego labela nadajemy mu fx:id i tu zmienna musi miec taka sama nazwe jak to id

    @FXML private Label currentDateAndHourLabel;
    @FXML private Label numberOfTouristsLabel;
    @FXML private Label numberOfTouristsText;
    @FXML private Label date;
    @FXML private Button changeParamsBtn;
    @FXML private Button stopBtn;
    @FXML private Button deselectBtn;
    @FXML private ListView locationsListView  = new ListView();
    @FXML private ListView detailsListView = new ListView();
    public ObservableList details = FXCollections.observableArrayList(
            "Name: ",
            "Type: ",
            "Amount of Tourists: ",
            "Queue: ",
            "Maximal size: ",
            "Covered: ",
            "Google place_id: ",
            "Temperature: ",
            "Solar: ",
            "Precipation: ",
            "Wind: "
    );
    public ObservableList items = FXCollections.observableArrayList();
    

    
    public void simulation(int day,int month){
            currentDateAndHourLabel.setText(month + "-" + day);
            simulation.simulateForDay(month,day);
            //simulation.checkAmountofTouristsInLocations();
            //setTextToListView();
            //locations.stream().forEach(l-> setTextToLabel(l.getName(),simulation.getDayInfoFromLocationByName(l.getName())));
            simulation.endDayInLocations();
            refreshDetails(day,month);
    }

    public void refreshDetails(int day, int month){
        if(details!=null){
            DayWeather weather = simulation.
                    getRecommendationSystem().
                    getWeather().
                    getWeatherInDay(month,day);
            details.set(7,"Temperature: " + weather.getTemperature());
            details.set(8,"Solar: " + weather.getSolar());
            details.set(9,"Precipation: " + weather.getPrecipation());
            details.set(10,"Wind: " + weather.getWind());
        }
    }

    // Póki co wszystkie sa bez znaczenia
    protected void setEvents(){
        stopBtn.setOnAction(e -> {
            changeParamsBtn.setDisable(false);
            stopBtn.setText("START");
            deselectBtn.setDisable(false);
        });
        changeParamsBtn.setOnAction(e -> {
            changeParamsBtn.setDisable(true);
            stopBtn.setText("STOP");
            deselectBtn.setDisable(true);
        });
        deselectBtn.setOnAction(e -> {
            changeParamsBtn.setDisable(false);
            stopBtn.setText("START");
            deselectBtn.setDisable(false);
        });
        numberOfTouristsLabel.setText(String.valueOf(simulation.getTourists().size()));
    }

    @FXML public void locationClickedForDetails(){
        String locationName = String.valueOf(
                locationsListView.
                getSelectionModel().
                getSelectedItem()
        );
        //System.out.println(locationName);
        setTextToDetailsList(locationName);
        detailsListView.refresh();
    }


    protected void setTextToDetailsList(String locationName){
        Location current = simulation.getLocationByName(locationName);
        //System.out.println(current.toString());
        details.set(0,"Name: " + current.getName());
        details.set(1,"Type: " + current.getTypes());
        details.set(2,"Amount of Tourists: " + current.getAmountOfTourists());
        details.set(3,"Queue: " + current.getQueue());
        details.set(4,"Maximal size: " + current.getMaxSize());
        details.set(5,"Covered: " + current.isCoveredf());
        details.set(6,"Google place_id: " + current.getPlaceId());
        detailsListView.setItems(details);
    }

    protected void setTextToListView(){
        for(int i = 0; i < simulation.getLocations().size() ; i++){
            items.add(simulation.getLocationsName(i));
        }
        locationsListView.setItems(items);
  }
  

}
