package repository;

import domain.User;

public interface IUserRepository extends Repository<Integer, User> {
    User findUser(String username,String password);
    void employeePresent(User user,String hour);
    void employeeLeft(User user);
    Iterable<User>getPresentEmployees();
}
