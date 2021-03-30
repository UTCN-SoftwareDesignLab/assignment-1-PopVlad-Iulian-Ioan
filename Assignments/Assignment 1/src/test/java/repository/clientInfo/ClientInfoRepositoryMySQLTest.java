package repository.clientInfo;

import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import model.ClientInfo;
import model.builder.ClientInfoBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;

import java.util.List;

import static org.junit.Assert.*;

public class ClientInfoRepositoryMySQLTest {

    private static ClientInfoRepositoryMySQL repository;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
        repository = new ClientInfoRepositoryMySQL (connectionWrapper.getConnection ());
    }

    @Before
    public void setup() {
        repository.removeAll();
    }

    @Test
    public void findAll() {
        repository.save(new ClientInfoBuilder()
                .setName ( "Vlad" )
                .setAddress ( "Gh Doja" )
                .setCnp ( "1045264748192" )
                .build ());
        repository.save(new ClientInfoBuilder()
                .setName ( "Alexandra" )
                .setAddress ( "Sf Paul" )
                .setCnp ( "1045264735167" )
                .build ());
        List<ClientInfo>clientInfos=repository.findAll ();
        Assert.assertEquals (2,clientInfos.size ()  );
    }

    @Test
    public void findById() throws EntityNotFoundException {
        repository.save(new ClientInfoBuilder()
                .setName ( "Vlad" )
                .setAddress ( "Gh Doja" )
                .setCnp ( "1045264748192" )
                .build ());
        List<ClientInfo>clientInfos=repository.findAll ();
       ClientInfo client=repository.findById ( clientInfos.get ( 0 ).getId () );
       Assert.assertEquals ( "Vlad",client.getName () );
    }

    @Test
    public void save() {
      ClientInfo clientInfo=new ClientInfoBuilder()
                .setName ( "Vlad" )
                .setAddress ( "Gh Doja" )
                .setCnp ( "1045264748192" )
                .build ();
        Assert.assertTrue(repository.save(clientInfo));
    }

    @Test
    public void update() {
        repository.save(new ClientInfoBuilder()
                .setName ( "Vlad" )
                .setAddress ( "Gh Doja" )
                .setCnp ( "1045264748192" )
                .build ());
        List<ClientInfo>clientInfos=repository.findAll ();
       ClientInfo client= clientInfos.get ( 0 );
       client.setName ( "Iulian" );
       repository.update ( client );
        List<ClientInfo>clientInfos1=repository.findAll ();
       Assert.assertEquals ( "Iulian",clientInfos1.get ( 0 ).getName () );


    }

    @Test
    public void removeAll() {
        repository.save(new ClientInfoBuilder()
                .setName ( "Vlad" )
        .setAddress ( "Gh Doja" )
        .setCnp ( "1045264748192" )
        .build ());
        repository.removeAll ();
        List<ClientInfo> noClient=repository.findAll ();
        Assert.assertTrue(noClient.isEmpty());
    }
}