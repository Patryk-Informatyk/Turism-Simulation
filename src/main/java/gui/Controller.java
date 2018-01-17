package gui;

import function.Functions;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.DayWeather;
import model.Location;
import org.json.JSONException;
import simulation.Simulation;

import java.io.IOException;
import java.util.List;

/**
 * Główny kontroler symulacji
 *
 * @author      Patryk Zygmunt
 * @author      Grzegorz Puczkowski
 * @author      Hubert Rędzia
 * @version     1.0
 * @since       1.0
 */
public class Controller {
    Simulation simulation = new Simulation();
    List<Location> locations;
    private Stage stage;
    protected Location currentLocationDetails;
    Timeline time;
    int day   = 1;
    int month = 1;
    int hour = 0;

    /**
     * Konstruktor klasy Controller. Przy tworzeniu obiektu klasy, inicjuje czas konieczny do symulacji
     * @throws IOException
     * @throws JSONException
     */
    public Controller() throws IOException, JSONException {
        locations = simulation.getLocations();
        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(createKeyFrameLangtonAnt(400));
        time.play();
    }
    /**
     * setter pola Month
     * @param  month  Miesiąc do wyznaczenia daty symulacji
     */
    public void setMonth(int month){
        this.month = month;
    }
    /**
     * getter pola Month
     * @return      wartość pola Month
     */
    public int getMonth(){
        return this.month;
    }
    /**
     * setter pola Day
     * @param  day  Dzień miesiąca do wyznaczenia symulacji
     */
    public void setDay(int day){
        this.day = day;
    }
    /**
     * getter pola Day
     * @return      wartość pola Day
     */
    public int getDay(){
        return this.day;
    }

    /**
     * getter pola Stage, na którym wyświetlana jest symulacja
     * @return      aktualny Stage
     */
    public Stage getStage(){
        return this.stage;
    }
    /**
     * setter pola Stage
     * @param  stage  Stage, w którym wyświetlany jest efekt symulacji
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }
       //dodajemy w scence buliderze nowego labela nadajemy mu fx:id i tu zmienna musi miec taka sama nazwe jak to id

    @FXML private Label currentDateAndHourLabel;
    @FXML private Label numberOfTouristsLabel;
    @FXML private Label numberOfTouristsText;
    @FXML private Label date;
    @FXML private Button stopBtn;
    @FXML private Button resumeBtn;
    @FXML private Button deselectBtn;
    @FXML private Button showStatisticsBtn;
    @FXML private ListView locationsListView  = new ListView();
    @FXML private ListView detailsListView = new ListView();
    @FXML private DatePicker dateChanger;
    @FXML private Button confirmBtn;
    public ObservableList details = FXCollections.observableArrayList(
            "Name: ",
            "Type: ",
            "Address",
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
    public ObservableList defaultLocationsView = defaultLocationsViewInit();

    public ObservableList defaultLocationsViewInit(){
        ObservableList list = FXCollections.observableArrayList();
        for(int i = 0; i < simulation.getLocations().size(); i++){
            list.add(simulation.getDayInfoFromLocation(i));
        }
        return list;
    }


    /**
     * Simulates tourists in specific day and set texts to labels. Refreshing detailsListView also.
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param day   number of day in month
     * @param month number of month in year
     * @param hour  number of hour in day
     */
    public void simulation(int day,int month, int hour){
            currentDateAndHourLabel.setText(day + "." + month + ", "+ hour + ":00 - " + (hour+1) + ":00");
            simulation.simulate(month,day,hour);
            simulation.checkAmountofTouristsInLocations();
            //setTextToListView();
          //  locations.stream().forEach(l-> System.out.println(simulation.getDayInfoFromLocationByName(l.getName())));
            //locations = simulation.getLocations();
            //simulation.endDayInLocations();
            // sztuczka z buttonem. Jak jest odblokowany, to wyswietla szczegoly lokacji
            if(deselectBtn.isDisabled()){
                setDefaultTextToListView();
            }
            else{
                refreshDetails(day,month,hour);
            }

    }
    /**
     * Refreshing weather and tourists information in detailsListView using details ObservableList.
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param day   Day on month
     * @param month Month of year
     * @param hour  Hour of day
     */
    public void refreshDetails(int day, int month, int hour){
        if(details!=null){
            DayWeather weather = simulation.
                    getRecommendationSystem().
                    getWeather().
                    getWeatherInDay(month,day);
            details.set(3,"Amount of Tourists: " +
                    (int)(currentLocationDetails.getAmountOfTourists()
                            *
                            Functions.touristsPerMonthIndicator(month, hour, simulation.getTourists().size()))
            );
            details.set(4,"Queue: " + currentLocationDetails.getQueue());
            details.set(8,"Temperature: " + weather.getTemperature());
            details.set(9,"Solar: " + weather.getSolar());
            details.set(10,"Precipation: " + weather.getPrecipation());
            details.set(11,"Wind: " + weather.getWind());
        }
    }
    /**
     * Sets events to buttons and refresh the label with number of tourists
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     */
    protected void setEvents(){
        stopBtn.setOnAction(e -> {
            stopBtn.setDisable(true);
            stopBtn.setVisible(false);
            resumeBtn.setDisable(false);
            resumeBtn.setVisible(true);
            confirmBtn.setDisable(false);
            time.stop();
        });
        resumeBtn.setOnAction(e -> {
            stopBtn.setDisable(false);
            stopBtn.setVisible(true);
            resumeBtn.setDisable(true);
            resumeBtn.setVisible(false);
            confirmBtn.setDisable(true);
            time.play();
        });
        deselectBtn.setOnAction(e -> {
            deselectBtn.setDisable(true);
            setDefaultTextToListView();
        });
        detailsListView.setOnContextMenuRequested(e -> {
            if(deselectBtn.isDisabled()){
                locationClickedForDetailsFromDetailsListView();
            }
        });
        confirmBtn.setOnAction(e -> {
            simulation.endDayInLocations();
            month = dateChanger.getValue().getMonthValue();
            day = dateChanger.getValue().getDayOfMonth();
            hour = 7;
            currentDateAndHourLabel.setText(day + "." + month + ", "+ hour + ":00 - " + (hour+1) + ":00");
        });

        numberOfTouristsLabel.setText(String.valueOf(simulation.getTourists().size()));

    }
    /**
     * Sets properties of location to detailsListView using details ObservableList
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param locationName  Name of location
     */
    protected void setTextToDetailsList(String locationName){
        currentLocationDetails = simulation.getLocationByName(locationName);
        details.set(0,"Name: " + currentLocationDetails.getName());
        details.set(1,"Type: " + currentLocationDetails.getTypes());
        details.set(2,"Address: " + currentLocationDetails.getAddress());
        details.set(3,"Amount of Tourists: " +
                (int)(currentLocationDetails.getAmountOfTourists()
                        *Functions.touristsPerMonthIndicator(month, hour, simulation.getTourists().size()))
        );
        details.set(4,"Queue: " + currentLocationDetails.getQueue());
        details.set(5,"Maximal size ~ " + (int)(currentLocationDetails.getMaxSize()*7.15));
        details.set(6,"Covered: " + currentLocationDetails.isCoveredf());
        details.set(7,"Google place_id: " + currentLocationDetails.getPlaceId());
        detailsListView.setItems(details);
    }
    /**
     * Sets default view of detailsListView. Default view is list of all locations with informations about
     * amount of tourists and queue
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     */
    protected void setDefaultTextToListView(){
        for(int i = 0; i < simulation.getLocations().size() ; i++){
            defaultLocationsView.set(i,
                    simulation.getLocationsName(i)
                            +", "+
                            (int)(simulation.getLocations().get(i).getAmountOfTourists()
                            *Functions.touristsPerMonthIndicator(month,hour,simulation.getTourists().size()))
                            +" + "+
                            simulation.getLocations().get(i).getQueue()
            );
        }
        detailsListView.setItems(defaultLocationsView);
    }
    /**
     * Runs on mouse click for detail information of location
     * change detailsListView to show properties of chosen location. Sets deselectBtn able to click.
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     */
    @FXML public void locationClickedForDetails(){
        String locationName = String.valueOf(
                locationsListView.
                        getSelectionModel().
                        getSelectedItem()
        );
        setTextToDetailsList(locationName);
        deselectBtn.setDisable(false);
        detailsListView.refresh();
    }
    /**
     * Runs when mouse right-click on chosen location. Works the same as locationClickedForDetails()
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     */
    @FXML public void locationClickedForDetailsFromDetailsListView(){
        String locationName = String.valueOf(
                locations.get(
                        detailsListView.
                        getSelectionModel().
                        getSelectedIndex()
                ).getName().toString()
        );
        setTextToDetailsList(locationName);
        deselectBtn.setDisable(false);
        detailsListView.refresh();
    }
    /**
     * Sets locations' names to locationsListView
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     */
    protected void setTextToListView(){
        for(int i = 0; i < simulation.getLocations().size() ; i++){
            items.add(simulation.getLocationsName(i));
        }
        locationsListView.setItems(items);
  }
    /**
     * Controls the date and simulate for specific time
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param delay delay in millis
     * @return      new KeyFrame to animate timeline
     */
    private KeyFrame createKeyFrameLangtonAnt(int delay){
        return new KeyFrame(Duration.millis(delay), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //day++;
                if(hour < 23){
                    hour++;
                }
                else{
                    simulation.endDayInLocations();
                    hour = 0;
                    day++;
                }

                if((day > 31) && (month == 12))
                {
                    month = 1;
                    day = 1;
                }
                else if((day > 27) && (month == 2))
                {
                    month++;
                    day = 1;
                }
                else if((day > 31) && (
                        (month == 1)||(month == 3)||(month == 5)||(month == 7)||(month == 8)||(month == 10)
                ))
                {
                    month++;
                    day = 1;
                }
                else if((day > 30) && (
                        (month == 4)||(month == 6)||(month == 9)||(month == 11)
                ))
                {
                    month++;
                    day = 1;
                }
                simulation(day,month,hour);
            }
        });
    }
  

}
