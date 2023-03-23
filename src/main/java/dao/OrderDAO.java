package dao;

import connection.ConnectionFactory;
import model.Order;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {

    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO orders_managementdb.order (id, clientId, productId, quantity, price)" + " VALUES (?,?,?,?,?)";
    private static final String deleteStatementString = "DELETE FROM orders_managementdb.order" + " WHERE id = ?";
    private static final String updateStatementString = "UPDATE orders_managementdb.order" + " SET quantity = ?"+ ",price = ?" + " WHERE id = ?";
    private static final String selectAllStatementString = "SELECT * FROM orders_managementdb.order";
    private static final String findStatementString = "SELECT * FROM orders_managementdb.order WHERE id = ?";

    public static Order findById(int id) {
        Order toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;

        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, id);
            rs = findStatement.executeQuery();
            rs.next();

            int clientId = rs.getInt("clientId");
            int productId = rs.getInt("productId");
            int quantity = rs.getInt("quantity");
            int price = rs.getInt("price");

            toReturn = new Order(id, clientId, productId, quantity, price);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"OrderDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static void insert(Order order) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;

        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, order.getId());
            insertStatement.setInt(2, order.getClientId());
            insertStatement.setInt(3, order.getProductId());
            insertStatement.setInt(4, order.getQuantity());
            insertStatement.setInt(5, order.getPrice());
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
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
            LOGGER.log(Level.WARNING, "OrderDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static void update(int id, int price, int quantity) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement updateStatement = null;

        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1, quantity);
            updateStatement.setInt(2, price);
            updateStatement.setInt(3, id);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:update " + e.getMessage());
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
                data[i][j] = result.getString("clientId");
                j++;
                data[i][j] = result.getString("productId");
                j++;
                data[i][j] = result.getString("quantity");
                j++;
                data[i][j] = result.getString("price");
                j = 0;
                i++;
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(selectAllStatement);
            ConnectionFactory.close(dbConnection);
        }

        return data;
    }

}
