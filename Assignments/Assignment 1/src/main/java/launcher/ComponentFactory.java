package launcher;

import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.DBConnectionFactory;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.clientAccount.ClientAccountRepository;
import repository.clientAccount.ClientAccountRepositoryMySQL;
import repository.clientInfo.ClientInfoRepository;
import repository.clientInfo.ClientInfoRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.admin.AdminService;
import service.admin.AdminServiceImpl;
import service.employee.EmployeeService;
import service.employee.EmployeeServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;


public class ComponentFactory {

    private final LoginView loginView;

    private final LoginController loginController;

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final AdminView adminView;
    private final AdminController adminController;
    private final AdminService adminService;
    private final ActivityRepository activityRepository;
    private final EmployeeView employeeView;
    private final EmployeeController employeeController;
    private final EmployeeService employeeService;
    private final ClientAccountRepository accountRepository;
    private final ClientInfoRepository clientInfoRepository;
    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.loginView = new LoginView();
        this.adminView=new AdminView ();
        this.employeeView=new EmployeeView ();
        activityRepository=new ActivityRepositoryMySQL ( connection );
        adminService = new AdminServiceImpl ( userRepository, activityRepository);
        adminController = new AdminController ( adminView,adminService,userRepository,rightsRolesRepository);
        this.accountRepository=new ClientAccountRepositoryMySQL ( connection );
        this.clientInfoRepository=new ClientInfoRepositoryMySQL ( connection );
        this.employeeService=new EmployeeServiceImpl ( accountRepository,clientInfoRepository );
        employeeController=new EmployeeController ( employeeView,employeeService,activityRepository);
        this.loginController = new LoginController(loginView, authenticationService, adminController ,employeeController );


    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }


    public LoginController getLoginController() {
        return loginController;
    }
}
