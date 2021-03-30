package service.employee;

import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import model.ClientAccount;
import model.ClientInfo;
import model.builder.ClientAccountBuilder;
import model.builder.ClientInfoBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.clientAccount.ClientAccountRepositoryMySQL;
import repository.clientInfo.ClientInfoRepositoryMySQL;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeServiceImplTest {
    private static ClientInfoRepositoryMySQL clientInfoRepositoryMySQL;
    private static ClientAccountRepositoryMySQL accountRepositoryMySQL;
    private static EmployeeServiceImpl employeeService;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
        clientInfoRepositoryMySQL = new ClientInfoRepositoryMySQL (connectionWrapper.getConnection ());
        accountRepositoryMySQL=new ClientAccountRepositoryMySQL ( connectionWrapper.getConnection ());
        employeeService=new EmployeeServiceImpl ( accountRepositoryMySQL,clientInfoRepositoryMySQL);
    }

    @Before
    public void setup() {
        clientInfoRepositoryMySQL.removeAll();
        accountRepositoryMySQL.removeAll ();
    }

    @Test
    public void transferMoney() {
        clientInfoRepositoryMySQL.save(new ClientInfoBuilder ()
                .setName ( "Vlad" )
                .setAddress ( "Gh Doja" )
                .setCnp ( "1045264748192" )
                .build ());
        clientInfoRepositoryMySQL.save(new ClientInfoBuilder()
                .setName ( "Alexandra" )
                .setAddress ( "Sf Paul" )
                .setCnp ( "1045264735167" )
                .build ());
        List<ClientInfo> clientInfos=clientInfoRepositoryMySQL.findAll ();
        accountRepositoryMySQL.save ( new ClientAccountBuilder ()
                .setIdClient ( clientInfos.get ( 0 ).getId () )
                .setType ( "personal" )
                .setIdCard ( 12L )
                .setMoneyAmount ( 300L )
                .setCreationDate ( java.sql.Date.valueOf(LocalDate.now()))
                . build ());
        accountRepositoryMySQL.save ( new ClientAccountBuilder ()
                .setIdClient ( clientInfos.get ( 1 ).getId () )
                .setType ( "personal" )
                .setIdCard ( 13L )
                .setMoneyAmount ( 400L )
                .setCreationDate ( java.sql.Date.valueOf(LocalDate.now()))
                . build ());

        List<ClientAccount> accounts=accountRepositoryMySQL.findAll ();
        employeeService.transferMoney ( accounts.get ( 0 ),accounts.get ( 1 ),100 );
        List<ClientAccount> accounts1=accountRepositoryMySQL.findAll ();
        Assert.assertEquals ( 200L, (long) accounts1.get ( 0 ).getMoneyAmount ());
        Assert.assertEquals ( 500L, (long) accounts1.get ( 1 ).getMoneyAmount ());
    }
}