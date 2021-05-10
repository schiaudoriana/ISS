package controller;

import domain.Task;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;

import java.util.List;

public class EmployeeController {
    public TextField textHour;
    public TableView<Task> tableViewTasks;
    public TableColumn<Task,String> columnDescription;
    public TableColumn<Task,String> columnDate;
    public TableColumn<Task, String> columnStatus;
    private Service service;
    private User current;

    ObservableList<Task> modelTask= FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableViewTasks.setItems(modelTask);
    }


    private void setTasksList(){
        List<Task> tasks=service.getEmployeeTasks(current);
        modelTask.clear();
        modelTask.setAll(tasks);
    }

    public void setService( Service service,User user ) {
        this.service = service;
        this.current=user;
    }

    public void handlePresent( ActionEvent actionEvent ) {
        String hour=textHour.getText();
        if(hour!=null){
            service.present(current,hour);
            setTasksList();
        }
        else{
            MessageBox.showMessage(null, Alert.AlertType.ERROR,"Error","You must type the hour");
        }
    }

    public void handleLogout( ActionEvent actionEvent ) {
        service.logout(current);
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void pressCancel(ActionEvent actionEvent){
        service.logout(current);
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

}
