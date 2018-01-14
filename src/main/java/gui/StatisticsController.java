package gui;

import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simulation.Simulation;

/**
 * Kontroller
 *
 * @author      Patryk Zygmunt
 * @author      Grzegorz Puczkowski
 * @author      Hubert Rędzia
 * @version     1.0
 * @since       1.0
 */
public class StatisticsController {
    Timeline time;
    Simulation simulation;
    Stage stage;

    public Stage getStage(){
        return this.stage;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public StatisticsController(){

    }

}
