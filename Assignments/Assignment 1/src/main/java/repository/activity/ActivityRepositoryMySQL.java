package repository.activity;

import model.Activity;
import model.ClientAccount;
import model.builder.ActivityBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepositoryMySQL implements ActivityRepository{
    private final Connection connection;

    public ActivityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Activity> findAll() {
        List<Activity> activities = new ArrayList<> ();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from activity" ;
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                activities.add ( getActivityFromResultSet (rs) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activities;
    }

    @Override
    public List<Activity> findByUser(Long idUser) throws EntityNotFoundException {
        List<Activity> activities = new ArrayList<> ();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from activity where idUser=" + idUser;
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                activities.add ( getActivityFromResultSet (rs) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activities;
    }

    @Override
    public boolean save(Activity activity) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO activity values (null, ?, ?, ?)");
            insertStatement.setLong(1, activity.getIdUser ());
            insertStatement.setString (2, activity.getAction ());
            insertStatement.setDate ( 3, new java.sql.Date(activity.getDate().getTime()) );
            insertStatement.executeUpdate();
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
            String sql = "DELETE from activity where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Activity getActivityFromResultSet(ResultSet rs) throws SQLException {
        return new ActivityBuilder ()
                .setId(rs.getLong("id"))
                .setIdUser (rs.getLong("idUser"))
                .setAction ( "action" )
                .setDate (new Date(rs.getDate("date").getTime()))
                .build();
    }
}
