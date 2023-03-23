package bll;

import dao.ProductDAO;
import model.Product;

import java.util.NoSuchElementException;

public class ProductBLL {

    public Product findById(int id){
        Product p = ProductDAO.findById(id);
        if (p == null){
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }

        return p;
    }

    public void insert(Product product){
        ProductDAO.insert(product);
    }

    public void delete(int id){
        ProductDAO.delete(id);
    }

    public void update(int id, int quantity){
        ProductDAO.update(id, quantity);
    }

    public String[][] selectAll(){
        String[][] str = ProductDAO.selectAll();
        if (str == null){
            throw new NoSuchElementException("Table is empty!");
        }

        return str;
    }
}
