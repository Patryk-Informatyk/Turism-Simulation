package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    
    Timeline time;
    int day=0;
    int month=1;

    @Override
    public void start(Stage primaryStage) throws Exception{
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
         Parent root =  loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 675));
        primaryStage.show();
        Controller controller = (Controller)loader.getController();
        time = new Timeline();
         time.setCycleCount(Timeline.INDEFINITE);
         
    time.getKeyFrames().add(createKeyFrameLangtonAnt(900,controller));
    time.play();
  
    }

    private KeyFrame createKeyFrameLangtonAnt(int delay,Controller controller)
    {
        return new KeyFrame(Duration.millis(delay), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                day++;
                if(day>28){month++;day=1;}
                controller.simulation(day,month);            
            }
        });
    }

    public static void main(String[] args) {launch(args);
    }
}
