package repository.clientAccount;

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
import repository.EntityNotFoundException;
import repository.clientInfo.ClientInfoRepositoryMySQL;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

public class ClientAccountRepositoryMySQLTest {
    private static ClientInfoRepositoryMySQL clientInfoRepositoryMySQL;
    private static ClientAccountRepositoryMySQL accountRepositoryMySQL;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
        clientInfoRepositoryMySQL = new ClientInfoRepositoryMySQL (connectionWrapper.getConnection ());
        accountRepositoryMySQL=new ClientAccountRepositoryMySQL ( connectionWrapper.getConnection ());
    }

    @Before
    public void setup() {
        clientInfoRepositoryMySQL.removeAll();
        accountRepositoryMySQL.removeAll ();
    }
    @Test
    public void findAll() {
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
         .setMoneyAmount ( 300L )
          .setCreationDate ( java.sql.Date.valueOf(LocalDate.now()))
        . build ());
        accountRepositoryMySQL.save ( new ClientAccountBuilder ()
                .setIdClient ( clientInfos.get ( 1 ).getId () )
                .setType ( "personal" )
                .setMoneyAmount ( 400L )
                .setCreationDate ( java.sql.Date.valueOf(LocalDate.now()))
                . build ());

        List<ClientAccount> accounts=accountRepositoryMySQL.findAll ();
        Assert.assertEquals (2,accounts.size ()  );
    }

    @Test
    public void findById() throws EntityNotFoundException {
        clientInfoRepositoryMySQL.save(new ClientInfoBuilder()
                .setName ( "Vlad" )
                .setAddress ( "Gh Doja" )
                .setCnp ( "1045264748192" )
                .build ());
        List<ClientInfo>clientInfos=clientInfoRepositoryMySQL.findAll ();
        accountRepositoryMySQL.save ( new ClientAccountBuilder ()
                .setIdClient ( clientInfos.get ( 0 ).getId () )
                .setType ( "personal" )
                .setMoneyAmount ( 300L )
                .setCreationDate ( java.sql.Date.valueOf(LocalDate.now()))
                . build ());
        List<ClientAccount> accounts=accountRepositoryMySQL.findAll ();
        ClientAccount account=accountRepositoryMySQL.findById ( accounts.get ( 0 ).getId () );
        ClientInfo client=clientInfoRepositoryMySQL.findById ( clientInfos.get ( 0 ).getId () );
        Assert.assertEquals ( account.getIdClient (),client.getId () );
    }

    @Test
    public void save() {
        clientInfoRepositoryMySQL.save(new ClientInfoBuilder ()
                .setName ( "Vlad" )
                .setAddress ( "Gh Doja" )
                .setCnp ( "1045264748192" )
                .build ());
        List<ClientInfo> clientInfos=clientInfoRepositoryMySQL.findAll ();
        ClientAccount clientAccount= new ClientAccountBuilder ()
                .setIdClient ( clientInfos.get ( 0 ).getId () )
                .setType ( "personal" )
                .setMoneyAmount ( 300L )
                . build ();

        Assert.assertTrue(accountRepositoryMySQL.save (clientAccount));
    }

    @Test
    public void delete() {
        clientInfoRepositoryMySQL.save(new ClientInfoBuilder()
                .setName ( "Vlad" )
                .setAddress ( "Gh Doja" )
                .setCnp ( "1045264748192" )
                .build ());
        List<ClientInfo> clientInfos=clientInfoRepositoryMySQL.findAll ();
       accountRepositoryMySQL.save ( new ClientAccountBuilder ()
               .setIdClient ( clientInfos.get ( 0 ).getId () )
               .setType ( "personal" )
               .setMoneyAmount ( 300L )
               .setCreationDate ( java.sql.Date.valueOf(LocalDate.now()))
               . build () ) ;
        List<ClientAccount> accounts=accountRepositoryMySQL.findAll ();
        Assert.assertTrue(accountRepositoryMySQL.delete ( accounts.get ( 0 )) );


    }

    @Test
    public void update() {
        clientInfoRepositoryMySQL.save(new ClientInfoBuilder ()
                .setName ( "Vlad" )
                .setAddress ( "Gh Doja" )
                .setCnp ( "1045264748192" )
                .build ());
        List<ClientInfo> clientInfos=clientInfoRepositoryMySQL.findAll ();
        ClientAccount clientAccount= new ClientAccountBuilder ()
                .setIdClient ( clientInfos.get ( 0 ).getId () )
                .setType ( "personal" )
                .setMoneyAmount ( 300L )
                .setCreationDate ( java.sql.Date.valueOf(LocalDate.now()))
                . build ();
        accountRepositoryMySQL.save ( clientAccount );
        List<ClientAccount> accounts=accountRepositoryMySQL.findAll ();
        accounts.get ( 0 ).setMoneyAmount ( 400L );
        accountRepositoryMySQL.update ( accounts.get ( 0 ) );
        List<ClientAccount> accounts1=accountRepositoryMySQL.findAll ();
        Assert.assertEquals ( 400L,(long)(accounts1.get ( 0 ).getMoneyAmount ()) );
    }

    @Test
    public void removeAll() {
        clientInfoRepositoryMySQL.save(new ClientInfoBuilder()
                .setName ( "Vlad" )
                .setAddress ( "Gh Doja" )
                .setCnp ( "1045264748192" )
                .build ());
        List<ClientInfo> clientInfos=clientInfoRepositoryMySQL.findAll ();
        ClientAccount clientAccount= new ClientAccountBuilder ()
                .setIdClient ( clientInfos.get ( 0 ).getId () )
                .setType ( "personal" )
                .setMoneyAmount ( 300L )
                .setCreationDate ( java.sql.Date.valueOf(LocalDate.now()))
                . build ();
       accountRepositoryMySQL.removeAll ();
        List<ClientAccount> noAccount=accountRepositoryMySQL.findAll ();
        Assert.assertTrue(noAccount.isEmpty());
    }
}