package repository;

import domain.Task;
import domain.User;

public interface ITaskRepository extends Repository<Integer, Task>{
    Iterable<Task>getTasks( User us);
    boolean complete(Task task);
    void addNewTask(Task task);
    boolean cancelTask(Task task);
}
