package dao;

import connection.ConnectionFactory;
import model.Product;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO orders_managementdb.product (id, name, price, quantity)" + " VALUES (?,?,?,?)";
    private static final String deleteStatementString = "DELETE FROM orders_managementdb.product" + " WHERE id = ?";
    private static final String updateStatementString = "UPDATE orders_managementdb.product" + " SET quantity = ?" + " WHERE id = ?";
    private static final String selectAllStatementString = "SELECT * FROM orders_managementdb.product";
    private static final String findStatementString = "SELECT * FROM orders_managementdb.product WHERE id = ?";

    public static Product findById(int id) {
        Product toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;

        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, id);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            int price = rs.getInt("price");
            int quantity = rs.getInt("quantity");

            toReturn = new Product(id, name, price, quantity);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ProductDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static void insert(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;

        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, product.getId());
            insertStatement.setString(2, product.getName());
            insertStatement.setFloat(3, product.getPrice());
            insertStatement.setInt(4, product.getQuantity());
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
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
            LOGGER.log(Level.WARNING, "ProductDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static void update(int id, int quantity) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement updateStatement = null;

        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1, quantity);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:update " + e.getMessage());
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
                data[i][j] = result.getString("price");
                j++;
                data[i][j] = result.getString("quantity");
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
}
