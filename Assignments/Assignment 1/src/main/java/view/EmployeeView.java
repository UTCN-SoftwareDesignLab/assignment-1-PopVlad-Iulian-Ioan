package view;

import DTO.AccountDTO;
import DTO.ClientDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeView extends JFrame {
    private JButton btnAddClient;
    private JButton btnUpdateClient;
    private JButton btnViewClients;
    private JButton btnAddAccount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;
    private JButton btnViewAccounts;
    private JButton btnTransferMoney;
    private JButton btnBill;
    private JButton btnSelectClientForAccount;
    private JButton btnSelectAccountFrom;
    private JButton btnSelectAccountTo;

    private JTextField tfName;
    private JTextField tfAddress;
    private JTextField tfCnp;
    private JLabel lName;
    private JLabel lAddress;
    private JLabel lCnp;

    private JTextField tfIdCard;
    private JTextField tfAmountMoney;
    private JTextField tfType;
    private JTextField tfMoneyToTransfer;
    private JLabel lMoneyToTransfer;
    private JLabel lIdCard;
    private JLabel lAmountMoney;
    private JLabel lType;

    private JPanel panelNorth;
    private JPanel panelWest;
    private JPanel panelCenter;
    private JPanel panelEast;
    private JList<String> jlClient_Account;
    private DefaultListModel<String> client_AccountList = new DefaultListModel<>();
    public EmployeeView ()throws HeadlessException{
        setSize(1300,1000);
        setLayout(new BorderLayout());
        initializePanels();
        add(panelNorth,BorderLayout.NORTH);
        add(panelWest,BorderLayout.WEST);
        add ( panelCenter,BorderLayout.CENTER );
        add(panelEast,BorderLayout.EAST);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initializePanels() {
        initializePanelNorth();
        initializePanelWest ();
        initializePanelCenter ();
        initializePanelEast();
    }
    private void initializePanelNorth(){
        panelNorth = new JPanel();
        btnViewClients = new JButton("View all clients");
        btnAddClient = new JButton("Add client");
        btnUpdateClient = new JButton("Update client");
        btnViewAccounts = new JButton("View client accounts");
        btnAddAccount = new JButton("Add account");
        btnUpdateAccount = new JButton("Update account");
        btnDeleteAccount = new JButton("Delete account");
        btnTransferMoney = new JButton("Transfer");
        btnBill = new JButton("Utility bill");

        this.panelNorth.add(btnViewClients);
        this.panelNorth.add(btnAddClient);
        this.panelNorth.add(btnUpdateClient);
        this.panelNorth.add(btnViewAccounts);
        this.panelNorth.add(btnAddAccount);
        this.panelNorth.add(btnUpdateAccount);
        this.panelNorth.add(btnDeleteAccount);
        this.panelNorth.add(btnViewAccounts);
        this.panelNorth.add(btnTransferMoney);
        this.panelNorth.add(btnBill);

    }
    private void initializePanelWest(){
        panelWest=new JPanel ();
        lName=new JLabel ("name:");
        lAddress=new JLabel ("address:");
        lCnp=new JLabel ("cnp:");
        tfName=new JTextField ();
        tfAddress=new JTextField ();
        tfCnp=new JTextField ();
        btnSelectClientForAccount=new JButton ("Select Client For Account");
        this.panelWest.add(btnSelectClientForAccount);
        this.panelWest.add(lName);
        this.panelWest.add(tfName);
        this.panelWest.add(lAddress);
        this.panelWest.add(tfAddress);
        this.panelWest.add(lCnp);
        this.panelWest.add(tfCnp);
        this.panelWest.setLayout(new BoxLayout(panelWest,BoxLayout.Y_AXIS));
        this.panelWest.setPreferredSize(new Dimension(200,100));

    }
    private void initializePanelCenter(){
        panelCenter = new JPanel();

        jlClient_Account = new JList<>( client_AccountList );
        this.jlClient_Account.setModel( client_AccountList );
        jlClient_Account.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlClient_Account.setVisibleRowCount(-1);

        this.panelCenter.add( jlClient_Account );
    }
    private void initializePanelEast(){
        panelEast=new JPanel ();
        lIdCard=new JLabel ("Id Card:");
        lType=new JLabel ("Type:");
        lAmountMoney=new JLabel ("Amount of Money:");
        tfIdCard=new JTextField ();
        tfType=new JTextField ();
        tfAmountMoney=new JTextField ();
        tfMoneyToTransfer=new JTextField ();
        lMoneyToTransfer=new JLabel ("Amount of money to transfer");
        btnSelectAccountFrom=new JButton ("Select Account From");
        btnSelectAccountTo=new JButton ("Select Account To");

        this.panelEast.add(btnSelectAccountFrom);
        this.panelEast.add(btnSelectAccountTo);
        this.panelEast.add(lMoneyToTransfer);
        this.panelEast.add(tfMoneyToTransfer);
        this.panelEast.add(lIdCard);
        this.panelEast.add(tfIdCard);
        this.panelEast.add(lType);
        this.panelEast.add(tfType);
        this.panelEast.add(lAmountMoney);
        this.panelEast.add(tfAmountMoney);
        this.panelEast.setLayout(new BoxLayout(panelEast,BoxLayout.Y_AXIS));
        this.panelEast.setPreferredSize(new Dimension(200,100));

    }
    public void setJlClient_AccountList(List<String> list) {
        client_AccountList.clear ();
        for (String i : list) {
            client_AccountList.addElement ( i );
        }
    }
    public ClientDTO getClientDTO(){
        ClientDTO clientDTO=new ClientDTO ();
        clientDTO.setName (tfName.getText ());
        clientDTO.setAddress ( tfAddress.getText () );
        clientDTO.setCnp ( tfCnp.getText () );
        return clientDTO;
    }
    public AccountDTO getAccountDTO(){
        AccountDTO accountDTO=new AccountDTO ();
        accountDTO.setIdCard ( Long.getLong ( tfIdCard.getText () ) );
        accountDTO.setType ( tfType.getText () );
        accountDTO.setMoneyAmount ( Long.getLong ( tfAmountMoney.getText () )  );
        return accountDTO;
    }
    public Long getMoneyToTransfer(){
       return Long.getLong(tfMoneyToTransfer.getText ());
    }

    public void setAddUserListener(ActionListener addClientButtonListener){
        btnAddClient.addActionListener(addClientButtonListener);
    }
    public void setBtnUpdateClient(ActionListener addUpdateClient){
        btnUpdateClient.addActionListener ( addUpdateClient );
    }
    public void setBtnViewClients(ActionListener addViewClient){
        btnViewClients.addActionListener ( addViewClient );
    }
    public void setBtnAddAccount(ActionListener addAddAccount){
        btnAddAccount.addActionListener ( addAddAccount );
    }
    public void setBtnUpdateAccount(ActionListener addUpdateAccount){
        btnUpdateAccount.addActionListener ( addUpdateAccount );
    }
    public void setBtnDeleteAccount(ActionListener addDeleteAccount){
        btnDeleteAccount.addActionListener ( addDeleteAccount );
    }
    public void setBtnViewAccounts(ActionListener addViewAccounts){
        btnViewAccounts.addActionListener ( addViewAccounts );
    }
    public void setBtnTransferMoney(ActionListener addTransferMoney){
        btnTransferMoney.addActionListener ( addTransferMoney );
    }
    public void setBtnBill(ActionListener addBill){
        btnBill.addActionListener ( addBill );
    }
    public void setBtnSelectClientForAccount(ActionListener addSelectClientForAccount){
        btnSelectClientForAccount.addActionListener ( addSelectClientForAccount );
    }
    public void setBtnSelectAccountFrom(ActionListener addSelectAccountForm){
        btnSelectAccountFrom.addActionListener ( addSelectAccountForm );
    }
    public void setBtnSelectAccountTo(ActionListener addSelectAccountTo){
        btnSelectAccountTo.addActionListener ( addSelectAccountTo );
    }
}
