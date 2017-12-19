package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Controller {
          @FXML
private Label pl;

    
          public void pray(int i){
    
                   pl.setText(Integer.toString(i));
              
          }
          
    @FXML protected void say(MouseEvent event ){
            pl.setText("nowy");
};
    
       
}
