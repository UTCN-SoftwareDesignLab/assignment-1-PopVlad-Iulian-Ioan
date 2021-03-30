package view;

import model.ClientAccount;
import model.ClientInfo;
import service.employee.EmployeeServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeView extends JFrame {
//    public boolean addClient(ClientInfo clientInfo);
//    public boolean updateClient(ClientInfo clientInfo);
//    public List<ClientInfo> viewClients();
//    public boolean addAccount(ClientAccount clientAccount);
//    public boolean updateAccount(ClientAccount clientAccount);
//    public boolean deleteAccount(ClientAccount clientAccount);
//    public List<ClientAccount>viewAccounts();
//    public boolean transferMoney(ClientAccount from,ClientAccount to,long amount);
    private JButton btnAddClient;
    private JButton btnUpdateClient;
    private JButton btnViewClients;
    private JButton btnAddAccount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;
    private JButton btnViewAccounts;
    private JButton btnTransferMoney;
    private JButton btnBill;
    public EmployeeView ()throws HeadlessException{
        setSize(1300,1000);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
