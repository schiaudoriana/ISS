package controller;

import domain.Task;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.Service;
import service.utils.ObsEvent;
import service.utils.Observer;

import java.util.List;

public class ManagerController implements Observer<ObsEvent> {
    public TableView<User> tableViewEmployees;
    public TableColumn<User, String> columnEmployee;
    public TableColumn<User, String> columnTime;
    public TableView<Task> tableViewTasks;
    public TableColumn<Task,String> columnDescription;
    public TableColumn<Task,String> columnDate;
    public TableColumn<Task,String> columnStatus;
    private Service service;
    private User manager;

    ObservableList<User> modelEmployees = FXCollections.observableArrayList();
    ObservableList<Task>modelTasks=FXCollections.observableArrayList();


    public void setService( Service service, User us ) {
        this.service = service;
        this.manager = us;
        setEmployeesList();
        service.add(this);
    }

    @FXML
    public void initialize() {
        columnEmployee.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("hour"));
        tableViewEmployees.setItems(modelEmployees);

        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableViewTasks.setItems(modelTasks);
    }

    private void setEmployeesList() {
        modelTasks.clear();
        List<User> employees = service.getPresentEmployees();
        modelEmployees.clear();
        modelEmployees.setAll(employees);
    }

    private void setTasksList(){
        modelTasks.clear();
        User us=tableViewEmployees.getSelectionModel().getSelectedItem();
        if (us!=null){
            List<Task> tasks=service.getEmployeeTasks(us);
            modelTasks.setAll(tasks);
        }
    }

    @Override
    public void update( ObsEvent obsEvent ) {
        setEmployeesList();
        setTasksList();
    }

    public void handleClickAddTask( ActionEvent actionEvent ) {
        User us = tableViewEmployees.getSelectionModel().getSelectedItem();
        if (us == null)
            MessageBox.showMessage(null, Alert.AlertType.ERROR, "Error", "You must select an employee!");
        else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Form.fxml"));
                Parent root = loader.load();

                Form form = loader.getController();
                form.setService(service,us);

                Stage primaryStage=new Stage();
                primaryStage.setTitle("New Task");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public void handleClickTable( MouseEvent mouseEvent ) {
        setTasksList();
    }

    public void handleClickCancelTask( ActionEvent actionEvent ) {
        if(tableViewTasks.getSelectionModel().getSelectedItem()==null){
            MessageBox.showMessage(null, Alert.AlertType.ERROR,"Error","You must select a task!");
        }
        else{
            boolean cancelled=service.cancelTask(tableViewTasks.getSelectionModel().getSelectedItem());
            if(cancelled)
                MessageBox.showMessage(null, Alert.AlertType.INFORMATION,"Information","Task was cancelled!");
            else
                MessageBox.showMessage(null, Alert.AlertType.ERROR,"Error","The task was already completed!");
        }

    }

    public void handleLogout( ActionEvent actionEvent ) {
        service.logout(manager);
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }
}
