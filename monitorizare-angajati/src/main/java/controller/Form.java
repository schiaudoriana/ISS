package controller;

import domain.Status;
import domain.Task;
import domain.User;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import service.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Form {
    public TextArea textArea;
    private Service service;
    private User user;
 
    public void setService(Service service,User user){
        this.service=service;
        this.user=user;
    }


    public void handleAddForm( ActionEvent actionEvent ) {
        String desc=textArea.getText();
        LocalDate date=LocalDate.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString=date.format(formatter);
        Task t=new Task(desc,dateString, Status.UNCOMPLETED,user.getId());
        service.addTask(t);
        MessageBox.showMessage(null, Alert.AlertType.INFORMATION,"Information","Task added!");
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }
}
