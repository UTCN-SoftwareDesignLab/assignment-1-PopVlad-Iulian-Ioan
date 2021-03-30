package service.admin;

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
import repository.activity.ActivityRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.Assert.*;

public class AdminServiceImplTest {
    private static ActivityRepositoryMySQL activityRepository;
    private static UserRepositoryMySQL userRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static AdminServiceImpl adminService;
    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
        activityRepository = new ActivityRepositoryMySQL (connectionWrapper.getConnection ());
        rightsRolesRepository=new RightsRolesRepositoryMySQL ( connectionWrapper.getConnection () );
        userRepository=new UserRepositoryMySQL ( connectionWrapper.getConnection (),rightsRolesRepository);
        adminService=new AdminServiceImpl ( userRepository,activityRepository );
    }

    @Before
    public void setup() {
        activityRepository.removeAll();
        userRepository.removeAll ();
    }
    @Test
    public void findActivityOfEmployeeBetween() throws EntityNotFoundException {
        LocalDate start=LocalDate.of ( 2020,4,29 );
        LocalDate end=LocalDate.of ( 2020,4,30 );
        LocalDate start1=LocalDate.of ( 2020,5,29 );
        LocalDate end1=LocalDate.of ( 2020,5,30 );
        userRepository.save ( new UserBuilder (  )
                .setUsername ( "user" )
                .setPassword ( "password" )
                .setRoles ( Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE) ))
                .build () );
        List<User> users=userRepository.findAll ();
        activityRepository.save ( new ActivityBuilder ()
                .setIdUser ( users.get ( 0 ).getId () )
                .setAction ( "test" )
                .setDate ( java.sql.Date.valueOf( start) )
                .build ());
        activityRepository.save ( new ActivityBuilder ()
                .setIdUser ( users.get ( 0 ).getId () )
                .setAction ( "test" )
                .setDate ( java.sql.Date.valueOf( end) )
                .build ());

        List<Activity>activities=adminService.findActivityOfEmployeeBetween ( users.get ( 0 ).getId (),start,end );
        Assert.assertEquals (2, activities.size () );
        List<Activity>activities1=adminService.findActivityOfEmployeeBetween ( users.get ( 0 ).getId (),start1,end1 );
        Assert.assertEquals (0, activities1.size () );
    }
}