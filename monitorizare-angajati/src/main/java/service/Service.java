package service;

import domain.Task;
import domain.User;
import repository.ITaskRepository;
import repository.IUserRepository;
import service.utils.ObsEvent;
import service.utils.Observable;
import service.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service implements Observable {
    private IUserRepository userRepo;
    private ITaskRepository taskRepo;

    public Service( IUserRepository userRepo, ITaskRepository taskRepo ) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    public User login( String username, String password ) {
        return userRepo.findUser(username, password);
    }

    public void present( User us, String hour ) {
        userRepo.employeePresent(us, hour);
        notifyObservers(new ObsEvent() {
        });
    }

    public void logout( User us ) {
        userRepo.employeeLeft(us);
        notifyObservers(new ObsEvent() {
        });
    }

    public List<Task> getEmployeeTasks( User us ) {
        return StreamSupport.stream(taskRepo.getTasks(us).spliterator(), false)
                .collect(Collectors.toList());

    }

    public List<User> getPresentEmployees() {
        return StreamSupport.stream(userRepo.getPresentEmployees().spliterator(),false)
                .collect(Collectors.toList());
    }

    private List<Observer<ObsEvent>>observers=new ArrayList<>();

    @Override
    public void add( Observer obs ) {
        observers.add(obs);
    }

    @Override
    public void notifyObservers( ObsEvent event ) {
        observers.forEach(x->x.update(event));
    }
}
