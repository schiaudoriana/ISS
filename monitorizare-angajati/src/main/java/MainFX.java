import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.ITaskRepository;
import repository.IUserRepository;
import repository.TaskRepository;
import repository.UserRepository;
import service.Service;

public class MainFX extends Application {
    @Override
    public void start( Stage primaryStage ) throws Exception {
        IUserRepository userRepository=new UserRepository();
        ITaskRepository taskRepository=new TaskRepository();
        Service service=new Service(userRepository,taskRepository);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
        Parent root=loader.load();

        LoginController login=loader.getController();
        login.setService(service);

        primaryStage.setTitle("Employee monitoring");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();

    }
}
