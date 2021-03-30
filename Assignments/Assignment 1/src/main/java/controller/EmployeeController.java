package controller;


import model.ClientAccount;
import model.ClientInfo;
import model.User;
import model.builder.ActivityBuilder;
import model.builder.ClientAccountBuilder;
import model.builder.ClientInfoBuilder;
import repository.EntityNotFoundException;
import repository.activity.ActivityRepository;
import service.employee.EmployeeService;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final EmployeeService employeeService;
    private final ActivityRepository activityRepository;
    private ClientInfo client;
    private ClientAccount account;
    private ClientAccount from;
    private ClientAccount to;
    private User user;
    public EmployeeController(EmployeeView employeeView , EmployeeService employeeService , ActivityRepository activityRepository) {
        this.employeeView = employeeView;
        this.employeeService = employeeService;
        user=LoginController.getActiveUser ();
        this.activityRepository = activityRepository;
        from = new ClientAccount ();
        to = new ClientAccount ();
        employeeView.setAddUserListener ( new addClientButtonListener () );
        employeeView.setBtnUpdateClient ( new updateClientButtonListener () );
        employeeView.setBtnViewClients ( new viewClientButtonListener () );
        employeeView.setBtnAddAccount ( new addAccountButtonListener () );
        employeeView.setBtnUpdateClient ( new updateAccountButtonListener () );
        employeeView.setBtnDeleteAccount ( new deleteAccountButtonListener () );
        employeeView.setBtnViewAccounts ( new viewAccountsButtonListener () );
        employeeView.setBtnTransferMoney ( new transferButtonListener() );
        employeeView.setBtnSelectClientForAccount ( new selectClientForAccount () );
        employeeView.setBtnSelectAccountFrom ( new selectFrom () );
        employeeView.setBtnSelectAccountTo ( new selectTo () );
    }
    private void setClient(){
        client = new ClientInfoBuilder ().fromClientDTO ( employeeView.getClientDTO () ).build ();
    }
    private void setAccount(){

        account = new ClientAccountBuilder ().fromAccountDTO ( employeeView.getAccountDTO () ).build ();
    }

    private class addClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setClient();
            if (!employeeService.addClient ( client ))
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Adding client not successful, please try again later!" );
            else {
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Client added successfully!" );
                activityRepository.save ( new ActivityBuilder ()
                        .setIdUser ( user.getId () )
                        .setAction ( "Client added" )
                        .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
                .build ());
            }
        }
    }

    private class updateClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setClient();
            if (!employeeService.updateClient ( client ))
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Update client not successful, please try again later!" );
            else {
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Client updated successfully!" );
                activityRepository.save ( new ActivityBuilder ()
                        .setIdUser ( user.getId () )
                        .setAction ( "Update client" )
                        .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
                        .build ());
            }
        }
    }

    private class viewClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<ClientInfo> clientInfoList = employeeService.viewClients ();

            if (clientInfoList.isEmpty ()) {
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Something went wrong or there are no clients!" );
            } else {
                List<String> list = new ArrayList<> ();
                for (ClientInfo clientInfo : clientInfoList) {
                    list.add ( clientInfo.toString () );
                }
                employeeView.setJlClient_AccountList ( list );
                activityRepository.save ( new ActivityBuilder ()
                        .setIdUser ( user.getId () )
                        .setAction ( "View clients" )
                        .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
                        .build ());
            }

        }
    }

    private class addAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setAccount ();
            try {
                account.setIdClient ( employeeService.findByCNP ( client ).getId () );
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace ();
            }
            if (!employeeService.addAccount ( account ))
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Adding account not successful, please try again later!" );
            else {
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Account added successfully!" );
                activityRepository.save ( new ActivityBuilder ()
                        .setIdUser ( user.getId () )
                        .setAction ( "Account added" )
                        .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
                        .build ());
            }
        }

    }

    private class updateAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setAccount ();
            if (!employeeService.updateAccount ( account ))
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Updating account not successful, please try again later!" );
            else {
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Account update successfully!" );
                activityRepository.save ( new ActivityBuilder ()
                        .setIdUser ( user.getId () )
                        .setAction ( "Account update" )
                        .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
                        .build ());
            }
        }
    }

    private class deleteAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setAccount ();
            if (!employeeService.deleteAccount ( account ))
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Deleting account not successful, please try again later!" );
            else {
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Account deleted successfully!" );
                activityRepository.save ( new ActivityBuilder ()
                        .setIdUser ( user.getId () )
                        .setAction ( "Delete account" )
                        .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
                        .build ());
            }
        }
    }

    private class viewAccountsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<ClientAccount> accountsList = employeeService.viewAccounts ();

            if (accountsList.isEmpty ()) {
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Something went wrong or there are no accounts!" );
            } else {
                List<String> list = new ArrayList<> ();
                for (ClientAccount account : accountsList) {
                    list.add ( account.toString () );
                }
                employeeView.setJlClient_AccountList ( list );
                activityRepository.save ( new ActivityBuilder ()
                        .setIdUser ( user.getId () )
                        .setAction ( "View Accounts" )
                        .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
                        .build ());
            }
        }


    }
    private class transferButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!employeeService.transferMoney ( from , to , employeeView.getMoneyToTransfer () ))
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Transferring money not successful, please try again later!" );
            else {
                JOptionPane.showMessageDialog ( employeeView.getContentPane () , "Transferring money successfully!" );
                activityRepository.save ( new ActivityBuilder ()
                        .setIdUser ( user.getId () )
                        .setAction ( "Transfer Money" )
                        .setDate ( java.sql.Date.valueOf( LocalDate.now()) )
                        .build ());
            }
        }
    }
    private class selectClientForAccount implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            client = new ClientInfoBuilder ().fromClientDTO ( employeeView.getClientDTO () ).build ();
        }
    }
    private class selectFrom implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            from = new ClientAccountBuilder ().fromAccountDTO ( employeeView.getAccountDTO () ).build ();
        }
    }
    private class selectTo implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            to = new ClientAccountBuilder ().fromAccountDTO ( employeeView.getAccountDTO () ).build ();
        }
    }
    public EmployeeView getEmployeeView(){
        return employeeView;
    }
}


