package service.admin;

import model.Activity;
import model.User;
import repository.EntityNotFoundException;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService{
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    public AdminServiceImpl(UserRepository userRepository, ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.activityRepository=activityRepository;
    }

    @Override
    public boolean createUser(User user) {
        user.setPassword ( encodePassword ( user.getPassword () ) );
        return userRepository.save ( user );
    }

    @Override
    public boolean updateUser(User user) {
        user.setPassword ( encodePassword ( user.getPassword () ) );
        return userRepository.update ( user );
    }

    @Override
    public boolean deleteUser(User user) {
        return userRepository.delete ( user);
    }

    @Override
    public List<Activity> findActivityOfEmployeeBetween(Long idUser , LocalDate start , LocalDate end) throws EntityNotFoundException {
        List<Activity>activities=activityRepository.findByUser ( idUser );
        List<Activity>validActivities= new ArrayList<> ();
        for (int i=0;i<activities.size ();i++) {
            Activity activity=activities.get ( i );
            validActivities.add ( activity );
            if(activity.getDate ().before ( Date.valueOf(start) ) ||activity.getDate ().after ( Date.valueOf(end) ))
                validActivities.remove ( activity );
        }
        return  validActivities;
    }

    @Override
    public List<User> viewUsers() {
        return userRepository.findAll ();
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes( StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
