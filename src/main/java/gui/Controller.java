package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Location;
import simulation.Simulation;

import java.util.List;

public class Controller {
    Simulation simulation = new Simulation();
    List<Location> locations;

    public Controller() {
              locations = simulation.getLocations();
    }
       //dodajemy w scence buliderze nowego labela nadajemy mu fx:id i tu zmienna musi miec taka sama nazwe jak to id
    @FXML private Label park;
    @FXML private Label oldtown;
    @FXML private Label museum;
    @FXML private Label date;
    
    
    
    
    public void simulation(int day,int month){
            date.setText("Date: " + month+"-" + day);
            simulation.simulateForDay(month,day);
            simulation.checkAmountofTouristsInLocations();
            locations.stream().forEach(l-> setTextToLabel(l.getName(),simulation.getDayInfoFromLocationByName(l.getName())));
            simulation.endDayInLocations();
        
    }
    
    //po dodoaniu labela jeli miejsce jest zdefiniowane w simulation.location przepisyjemy name stamtad i odpowiadajacaego mu labela 
    // jak nie to definiujemy w simulation nowy location
  private  void setTextToLabel(String location, String text){
        switch (location) {
            case "Park" :   park.setText(text);
           case "Old Town" :   oldtown.setText(text);
            case "Museum" :   museum.setText(text);
            default: System.err.println("NAZWA NIEPRZYPISANA DO LABELA " + location);
                
        }   
  }
  
       //testowy onclikck niepotrzebny
    @FXML protected void say(MouseEvent event) {
        park.setText("nowy");
    }


}
