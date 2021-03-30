package repository.clientInfo;

import model.ClientInfo;
import model.builder.ClientInfoBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientInfoRepositoryMySQL implements ClientInfoRepository{
    private final Connection connection;

    public ClientInfoRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<ClientInfo> findAll() {
        List<ClientInfo> clientInfos = new ArrayList<> ();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from clientinfo";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clientInfos.add(getClientInfoFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientInfos;
    }

    @Override
    public ClientInfo findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from clientinfo where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getClientInfoFromResultSet (rs);
            } else {
                throw new EntityNotFoundException(id, ClientInfo.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, ClientInfo.class.getSimpleName());
        }
    }

    @Override
    public ClientInfo findByCNP(String cnp) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from clientinfo where cnp=" + cnp;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next())
                return getClientInfoFromResultSet (rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean save(ClientInfo clientInfo) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO clientinfo values (null, ?, ?, ?)");
            insertStatement.setString(1, clientInfo.getName ());
            insertStatement.setString(2, clientInfo.getAddress ());
            insertStatement.setString(3, clientInfo.getCnp ());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(ClientInfo clientInfo) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE clientinfo SET name = ?, address = ? " +
                    " WHERE cnp = " + clientInfo.getCnp ());
            updateStatement.setString(1, clientInfo.getName ());
            updateStatement.setString(2, clientInfo.getAddress ());
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
            String sql = "DELETE from clientinfo where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private ClientInfo getClientInfoFromResultSet(ResultSet rs) throws SQLException {
        return new ClientInfoBuilder ()
                .setId(rs.getLong("id"))
                .setName (rs.getString("name"))
                .setAddress (rs.getString("address"))
                .setCnp (rs.getString("cnp"))
                .build();
    }
}
