package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

/**
 * Created by Alex on 11/03/2017.
 */
public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean delete(User user);

    boolean update(User user);

    boolean save(User user);

    void removeAll();

}
