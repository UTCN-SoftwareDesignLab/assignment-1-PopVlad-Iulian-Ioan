package service.admin;

import model.Activity;
import model.User;
import repository.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface AdminService {
    public boolean createUser(User user);
    public boolean updateUser(User user);
    public boolean deleteUser(User user);
    public List<Activity> findActivityOfEmployeeBetween(Long idUser, LocalDate start, LocalDate end) throws EntityNotFoundException;
    public List<User> viewUsers();
}
