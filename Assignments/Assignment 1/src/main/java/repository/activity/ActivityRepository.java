package repository.activity;

import model.Activity;
import model.ClientAccount;
import model.User;
import repository.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ActivityRepository {
    List<Activity> findAll();
    public List<Activity>findByUser(Long idUser) throws EntityNotFoundException;
    boolean save(Activity activity);
    void removeAll();
}
