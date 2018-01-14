package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;

public class Main extends Application {

    Timeline time;
    int day=1;
    int month=1;

    @FXML public DatePicker datePicker;
    @FXML public Button okButton;
    @FXML public Button cancelButton;

    @FXML public void okButtonOnAction() throws IOException {
        okButton.setDisable(true);
        datePicker.setDisable(true);
        LocalDate date = datePicker.getValue();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent troot = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Tourismulation");
        stage.setScene(new Scene(troot));

        Controller controller = (Controller) loader.getController();
        if (date != null) {
            month = date.getMonthValue();
            day = date.getDayOfMonth()-1;
            controller.setMonth(month);
            controller.setDay(day);
        }
        controller.setStage(stage);
        controller.getStage().show();
        /*
        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(createKeyFrameLangtonAnt(1000, controller));
        time.play();
        */
        controller.setTextToListView();
        controller.setEvents();
    }

    @FXML public void cancelButtonOnAction(){
        Platform.exit();
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader start = new FXMLLoader(getClass().getResource("/start.fxml"));
        try{
            Parent root = start.load();
            primaryStage.setTitle("Tourismulation");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /*
    Tutaj cos nie działa, to setEvents. Do poprawy
    */

    private KeyFrame createKeyFrameLangtonAnt(int delay,Controller controller)
    {
        return new KeyFrame(Duration.millis(delay), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                day++;
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
                controller.simulation(day,month);
            }
        });
    }

    public static void main(String[] args) {launch(args);
    }
}
