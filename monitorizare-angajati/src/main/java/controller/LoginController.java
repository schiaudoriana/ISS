package controller;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Service;

public class LoginController {
    public TextField textUsername;
    public PasswordField textPassword;
    private Service service;

    public void setService( Service service ){
        this.service=service;
    }

    public void handleLogin( ActionEvent actionEvent ) {
        String user=textUsername.getText();
        String pass=textPassword.getText();
        if(user!=null && pass!=null) {
            User us = service.login(user,pass);
            if(us!=null) {
                if (us.getJob().equals("null"))
                    managerLogin(us);
                else
                    employeeLogin(us);
            }
            else {
                MessageBox.showMessage(null, Alert.AlertType.ERROR,"Login error","Authentication failed!");

            }
        }
        else{
            MessageBox.showMessage(null, Alert.AlertType.ERROR,"Login error","Username and password must not be empty!");
        }
    }

    public void employeeLogin(User us){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmployeeView.fxml"));
            Parent root = loader.load();

            EmployeeController controller = loader.getController();
            controller.setService(service,us);

            Stage primaryStage=new Stage();
            primaryStage.setTitle("Employee window");
            primaryStage.setScene(new Scene(root, 660, 400));
            primaryStage.show();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void managerLogin(User us){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManagerView.fxml"));
            Parent root = loader.load();

            ManagerController managerController = loader.getController();
            managerController.setService(service,us);

            Stage primaryStage=new Stage();
            primaryStage.setTitle("Manager window");
            primaryStage.setScene(new Scene(root, 730, 400));
            primaryStage.show();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
