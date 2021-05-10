package controller;

import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;
import service.utils.ObsEvent;
import service.utils.Observer;

import java.util.List;

public class ManagerController implements Observer<ObsEvent> {
    public TableView<User> tableViewEmployees;
    public TableColumn<User,String> columnEmployee;
    public TableColumn<User,String> columnTime;
    private Service service;
    private User manager;

    ObservableList<User> modelEmployees= FXCollections.observableArrayList();


    public void setService( Service service, User us) {
        this.service = service;
        this.manager=us;
        setEmployeesList();
        service.add(this);
    }

    @FXML
    public void initialize(){
        columnEmployee.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("hour"));
        tableViewEmployees.setItems(modelEmployees);
    }

    private void setEmployeesList(){
        List<User> employees=service.getPresentEmployees();
        modelEmployees.clear();
        modelEmployees.setAll(employees);
    }

    @Override
    public void update( ObsEvent obsEvent ) {
        setEmployeesList();
    }
}
