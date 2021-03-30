package repository.clientAccount;

import model.ClientAccount;
import model.builder.ClientAccountBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientAccountRepositoryMySQL implements ClientAccountRepository{
    private final Connection connection;

    public ClientAccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<ClientAccount> findAll() {
        List<ClientAccount> clientAccounts = new ArrayList<> ();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from ClientAccount";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clientAccounts.add(getClientAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientAccounts;
    }

    @Override
    public ClientAccount findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from ClientAccount where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getClientAccountFromResultSet (rs);
            } else {
                throw new EntityNotFoundException(id, ClientAccount.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, ClientAccount.class.getSimpleName());
        }
    }

    @Override
    public boolean save(ClientAccount clientAccount) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO ClientAccount values (null, ?, ?, ?, ?)");
            insertStatement.setLong(1, clientAccount.getIdClient ());
            insertStatement.setString(2, clientAccount.getType ());
            insertStatement.setLong(3, clientAccount.getMoneyAmount ());
            insertStatement.setDate ( 4, Date.valueOf ( LocalDate.now() ));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(ClientAccount clientAccount) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from ClientAccount where id ="+clientAccount.getId ();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(ClientAccount clientAccount) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE ClientAccount SET idClient = ?, type = ?, " +
                    "moneyAmount = ?  WHERE id = " + clientAccount.getId());
            updateStatement.setLong(1, clientAccount.getIdClient ());
            updateStatement.setString(2, clientAccount.getType ());
            updateStatement.setLong(3, clientAccount.getMoneyAmount ());
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from ClientAccount where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private ClientAccount getClientAccountFromResultSet(ResultSet rs) throws SQLException {
        return new ClientAccountBuilder ()
                .setId(rs.getLong("id"))
                .setIdClient (rs.getLong("idClient"))
                .setType ( rs.getString ( "type" ) )
                .setMoneyAmount (rs.getLong ("moneyAmount"))
                .setCreationDate (new Date(rs.getDate("creationDate").getTime()))
                .build();
    }
}
