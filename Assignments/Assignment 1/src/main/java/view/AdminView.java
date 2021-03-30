package view;

import DTO.ActivityDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class AdminView extends JFrame {
    private JButton btnCreateUser;
    private JButton btnUpdateUser;
    private JButton btnDeleteUser;
    private JButton btnFindActivity;
    private JButton btnViewUsers;

    private JCheckBox adminCheckBox;

    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfStart;
    private JTextField tfEnd;

    private  JLabel lAdmin;
    private JLabel lUsername;
    private JLabel lPassword;
    private JLabel lStart;
    private JLabel lEnd;

    private JList<String> jlUsers_Activity;
    private DefaultListModel<String> usersList_Activity = new DefaultListModel<>();

    private JPanel panelNorth;
    private JPanel panelCenter;
    private JPanel panelWest;

    private ActivityDTO activityDTO;
    public AdminView()throws HeadlessException {
        setSize(1300,1000);
        setLayout(new BorderLayout());
        initializePanels();
        add(panelNorth,BorderLayout.NORTH);
        add ( panelCenter,BorderLayout.CENTER );
        add ( panelWest,BorderLayout.WEST );
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initializePanels() {

        initializePanelNorth ();
        initializePanelCenter ();
        initializePanelWest ();

    }
    private void initializePanelNorth(){

        panelNorth = new JPanel();

        btnCreateUser = new JButton("Create User");
        btnUpdateUser=  new JButton("Update User");
        btnDeleteUser =new JButton("Delete User");
        btnViewUsers = new JButton("View Users");
        btnFindActivity = new JButton("Find Activity");
        this.panelNorth.add(btnCreateUser);
        this.panelNorth.add(btnUpdateUser);
        this.panelNorth.add(btnDeleteUser);
        this.panelNorth.add(btnViewUsers);
        this.panelNorth.add(btnFindActivity);

    }
    private void initializePanelCenter(){
        panelCenter = new JPanel();

        jlUsers_Activity = new JList<>( usersList_Activity );
        this.jlUsers_Activity.setModel( usersList_Activity );
        jlUsers_Activity.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlUsers_Activity.setVisibleRowCount(-1);

        this.panelCenter.add( jlUsers_Activity );
    }
    private void initializePanelWest(){
        panelWest=new JPanel ();

        lAdmin=new JLabel ("admin: yes/no");
        lUsername=new JLabel ("username");
        lPassword=new JLabel ("password");
        lStart=new JLabel ("start date");
        lEnd=new JLabel ("end date");

        adminCheckBox=new JCheckBox ();
        tfUsername=new JTextField ();
        tfPassword=new JTextField ();
        tfStart=new JTextField ();
        tfEnd=new JTextField ();
        this.panelWest.setLayout(new BoxLayout(panelWest,BoxLayout.Y_AXIS));
        this.panelWest.setPreferredSize(new Dimension(200,100));

        this.panelWest.add(lAdmin);
        this.panelWest.add ( adminCheckBox );
        this.panelWest.add ( lUsername );
        this.panelWest.add ( tfUsername );
        this.panelWest.add(lPassword);
        this.panelWest.add(tfPassword);
        this.panelWest.add(lStart);
        this.panelWest.add(tfStart);
        this.panelWest.add(lEnd);
        this.panelWest.add(tfEnd);

    }


    public void setUser_ActivityList(List<String> list){
        usersList_Activity.clear();
        for(String i: list){
            usersList_Activity.addElement(i);
        }
    }
    public void setCreateUserListener(ActionListener createUserButtonListener){
        btnCreateUser.addActionListener(createUserButtonListener);
    }
    public void setUpdateUserListener(ActionListener updateUserButtonListener){
        btnUpdateUser.addActionListener(updateUserButtonListener);
    }
    public void setDeleteUserListener(ActionListener deleteUserButtonListener){
        btnDeleteUser.addActionListener(deleteUserButtonListener);
    }
    public void setViewUsersListener(ActionListener viewUsersButtonListener){
        btnViewUsers.addActionListener(viewUsersButtonListener);
    }
    public void setBtnFindActivityListener(ActionListener findActivityListenerButtonListener){
        btnFindActivity.addActionListener(findActivityListenerButtonListener);
    }

    public ActivityDTO getActivityDTO(){
        activityDTO=new ActivityDTO ();
        activityDTO.setUsername ( tfUsername.getText () );
        activityDTO.setPassword (tfPassword.getText ());
        activityDTO.setStart (LocalDate.parse ( tfStart.getText () ));
        activityDTO.setEnd ( LocalDate.parse ( tfEnd.getText () ) );
        activityDTO.setAdmin ( adminCheckBox.isSelected () );
        return activityDTO;
    }
}
