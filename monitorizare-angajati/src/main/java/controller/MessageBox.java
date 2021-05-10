package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageBox {
    static void showMessage( Stage owner, Alert.AlertType type,String header, String text ){
        Alert message=new Alert(type);
        message.setTitle(header);
        message.initOwner(owner);
        message.setContentText(text);
        message.showAndWait();
    }
}
