package controller;

import model.Activity;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import service.admin.AdminService;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class AdminController {
    private final AdminView adminView;
    private final AdminService adminService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private User user;
    public AdminController(AdminView adminView , AdminService adminService , UserRepository userRepository , RightsRolesRepository rightsRolesRepository) {
        this.adminView = adminView;
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        adminView.setCreateUserListener ( new createUserButtonListener() );
        adminView.setUpdateUserListener ( new updateUserButtonListener () );
        adminView.setDeleteUserListener ( new deleteUserButtonListener () );
        adminView.setViewUsersListener ( new viewUserButtonListener () );
        adminView.setBtnFindActivityListener ( new findActivity () );
    }
    private void setData(){
        user=new UserBuilder ().fromActivityDTO ( adminView.getActivityDTO () ).build ();
    }
    private class createUserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setData();
            if(adminView.getActivityDTO ().isAdmin ())
                user.setRoles ( Collections.singletonList(rightsRolesRepository.findRoleByTitle(ADMINISTRATOR)) );
            else
                user.setRoles ( Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE)) );
            boolean registerNotification = adminService.createUser (user);
                if(registerNotification){
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Registration not successful, please try again later!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "User created successfully!");
                }
        }
        }
        private class updateUserButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                setData();
                boolean registerNotification = adminService.updateUser ( user );
                if(registerNotification){
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Update not successful, please try again later!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "User updated successfully!");
                }
            }
        }
        private class deleteUserButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                setData();
                boolean registerNotification = adminService.deleteUser ( user );
                if(registerNotification){
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Delete not successful, please try again later!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "User deleted successfully!");
                }
            }
        }
        private class viewUserButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                setData();
                List<User> userList = adminService.viewUsers ();

                if(userList.isEmpty ()){
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Something went wrong or there are no users!");
                }
                    else {
                    List<String> list = new ArrayList<> ();
                    for(User user: userList){
                        list.add(user.toString ());
                    }
                    adminView.setUser_ActivityList ( list );
                }

            }

            }
            private class findActivity implements ActionListener{
                @Override
                public void actionPerformed(ActionEvent e) {
                    setData();
                    Notification<User> userAc=userRepository.findByUsernameAndPassword ( user.getUsername (), user.getPassword () );
                    try {
                        List<Activity>activityList=adminService.findActivityOfEmployeeBetween ( userAc.getResult ().getId (),
                                adminView.getActivityDTO ().getStart (), adminView.getActivityDTO ().getEnd () );

                        List<String> list = new ArrayList<> ();
                        for(Activity activity: activityList){
                            list.add(activity.toString ());
                        }
                        adminView.setUser_ActivityList ( list );
                    } catch (EntityNotFoundException entityNotFoundException) {
                        entityNotFoundException.printStackTrace ();
                    }

                }
        }
        public AdminView getAdminView(){
        return adminView;
        }
    }
