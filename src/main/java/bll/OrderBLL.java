package bll;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ClientDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.NoSuchElementException;

public class OrderBLL {
    private static int nrOrder = 1;

    public Order findById(int id){
        Order o = OrderDAO.findById(id);
        if (o == null){
            throw new NoSuchElementException("The order with id = " + id + " was not found!");
        }

        return o;
    }

    public void insert(Order order){
        if (ClientDAO.findById(order.getClientId()) == null){
            throw new NoSuchElementException("The client with id = " + order.getClientId() + " was not found!");
        }

        if (ProductDAO.findById(order.getProductId()) == null){
            throw new NoSuchElementException("The product with id = " + order.getProductId() + " was not found!");
        }

        if (ProductDAO.findById(order.getProductId()).getQuantity() < order.getQuantity()){
            throw new NoSuchElementException("The product with id = " + order.getProductId() + " has not enough stock");
        }

        OrderDAO.insert(order);
    }

    public void delete(int id){
        OrderDAO.delete(id);
    }

    public void update(int id, int quantity, int price){
        OrderDAO.update(id, price, quantity);
    }

    public String[][] selectAll(){
        String[][] str = OrderDAO.selectAll();
        if (str == null){
            throw new NoSuchElementException("Table is empty!");
        }

        return str;
    }
    public void generateBill(Order order) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("order" + nrOrder + ".pdf"));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
            Chunk chunk = new Chunk(order.toString(), font);
            document.add(chunk);
            document.close();
        }catch (FileNotFoundException exp){
            System.out.println(exp.getMessage());
        }catch (DocumentException exp){
            System.out.println(exp.getMessage());
        }
        nrOrder++;
    }
}
