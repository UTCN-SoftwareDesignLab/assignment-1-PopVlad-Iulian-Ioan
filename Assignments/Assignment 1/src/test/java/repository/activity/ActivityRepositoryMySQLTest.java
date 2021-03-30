package repository.activity;

import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import model.Activity;
import model.User;
import model.builder.ActivityBuilder;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class ActivityRepositoryMySQLTest {
    private static ActivityRepositoryMySQL activityRepository;
    private static UserRepositoryMySQL userRepository;
    private static RightsRolesRepository rightsRolesRepository;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
       activityRepository = new ActivityRepositoryMySQL (connectionWrapper.getConnection ());
        rightsRolesRepository=new RightsRolesRepositoryMySQL ( connectionWrapper.getConnection () );
        userRepository=new UserRepositoryMySQL ( connectionWrapper.getConnection (),rightsRolesRepository);
    }

    @Before
    public void setup() {
        activityRepository.removeAll();
        userRepository.removeAll ();
    }
    @Test
    public void findAll(){

        userRepository.save ( new UserBuilder (  )
                .setUsername ( "user" )
                .setPassword ( "password" )
                .setRoles ( Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE) ))
                .build () );
        List<User>users=userRepository.findAll ();
        activityRepository.save ( new ActivityBuilder ()
                .setIdUser ( users.get ( 0 ).getId () )
                .setAction ( "test" )
                .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
                .build ());
        List<Activity>activities=activityRepository.findAll ();
        Assert.assertEquals (1, activities.size ());
    }
    @Test
    public void findByUser() throws EntityNotFoundException {
        userRepository.save ( new UserBuilder (  )
                .setUsername ( "user" )
                .setPassword ( "password" )
                .setRoles ( Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE) ))
                .build () );
        List<User>users=userRepository.findAll ();
        activityRepository.save ( new ActivityBuilder ()
                .setIdUser ( users.get ( 0 ).getId () )
                .setAction ( "test" )
                .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
                .build ());
        List<Activity>activities=activityRepository.findByUser ( users.get ( 0 ).getId () );
        Assert.assertEquals ( users.get ( 0 ).getId (),activities.get ( 0 ).getIdUser () );
    }

    @Test
    public void save() {
        userRepository.save ( new UserBuilder (  )
                .setUsername ( "user" )
                .setPassword ( "password" )
                .setRoles ( Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE) ))
                .build () );
        List<User>users=userRepository.findAll ();
        Assert.assertTrue ( activityRepository.save ( new ActivityBuilder ()
                .setIdUser ( users.get ( 0 ).getId () )
                .setAction ( "test" )
                .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
                .build ()) );

    }

    @Test
    public void removeAll() {
        userRepository.save ( new UserBuilder (  )
                .setUsername ( "user" )
                .setPassword ( "password" )
                .setRoles ( Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE) ))
                .build () );
        List<User>users=userRepository.findAll ();
        activityRepository.save ( new ActivityBuilder ()
        .setIdUser ( users.get ( 0 ).getId () )
        .setAction ( "test" )
                .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
        .build ());
        activityRepository.removeAll ();
        List<Activity>activities=activityRepository.findAll ();
        Assert.assertTrue ( activities.isEmpty () );
    }
}