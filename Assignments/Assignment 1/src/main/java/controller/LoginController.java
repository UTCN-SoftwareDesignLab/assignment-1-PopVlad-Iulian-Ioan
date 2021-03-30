package controller;

import model.Role;
import model.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;


public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final AdminController adminController;
    private final EmployeeController employeeController;
    private static User activeUser;
    public LoginController(LoginView loginView, AuthenticationService authenticationService,AdminController adminController, EmployeeController employeeController) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.adminController=adminController;
        this.employeeController=employeeController;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                loginView.setVisible (false);
                User user=loginNotification.getResult ();
                activeUser=user;
                List<Role> roleList=user.getRoles ();
                if(roleList.contains ( ADMINISTRATOR )){
                    adminController.getAdminView ().setVisible ( true );
                }
                else {
                    employeeController.getEmployeeView ().setVisible ( true );
                }
            }

        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }

    public static User getActiveUser() {
        return activeUser;
    }
}
