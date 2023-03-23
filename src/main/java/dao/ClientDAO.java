package dao;

import connection.ConnectionFactory;
import model.Client;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientDAO extends AbstractDAO<Client>{

    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO orders_managementdb.client (id, name, address)" + " VALUES (?,?,?)";
    private static final String deleteStatementString = "DELETE FROM orders_managementdb.client" + " WHERE id = ?";
    private static final String updateStatementString = "UPDATE orders_managementdb.client" + " SET address = ?" + " WHERE id = ?";
    private static final String selectAllStatementString = "SELECT * FROM orders_managementdb.client";
    private static final String findStatementString = "SELECT * FROM orders_managementdb.client WHERE id = ?";

    public static Client findById(int id) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;

        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, id);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            String address = rs.getString("address");

            toReturn = new Client(id, name, address);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static void insert(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;

        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, client.getId());
            insertStatement.setString(2, client.getName());
            insertStatement.setString(3, client.getAddress());
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static void delete(int id) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement deleteStatement = null;

        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static void update(int id, String address) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement updateStatement = null;

        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setString(1, address);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static String[][] selectAll() {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement selectAllStatement = null;
        ResultSet result = null;
        String[][] data = new String[20][20];

        try {
            selectAllStatement = dbConnection.prepareStatement(selectAllStatementString);
            result = selectAllStatement.executeQuery();
            int i = 0, j = 0;

            while (result.next()) {
                data[i][j] = result.getString("id");
                j++;
                data[i][j] = result.getString("name");
                j++;
                data[i][j] = result.getString("address");
                j = 0;
                i++;
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(selectAllStatement);
            ConnectionFactory.close(dbConnection);
        }

        return data;
    }

    public List<Client> findAll(){
        return (List<Client>) super.findAll();
    }
}
