package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import com.itextpdf.text.pdf.PdfWriter;
import model.Client;
import model.Order;
import model.Product;
import start.ReflectionRetrieveProperties;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.List;
import java.util.NoSuchElementException;

public class ViewOrder extends JFrame {

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;

    public ViewOrder (){
        this.setSize(700, 450);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Order");

        JLabel label1 = new JLabel("Choose an operation: ");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();
        JPanel p = new JPanel();

        panel5.setLayout(new GridLayout(4, 0));
        panel6.setLayout(new FlowLayout());
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        this.button1 = new JButton("Select All");
        this.button2 = new JButton("Insert");
        this.button3 = new JButton("Update");
        this.button4 = new JButton("Delete");

        panel1.add(button1);
        panel2.add(button2);
        panel3.add(button3);
        panel4.add(button4);

        panel5.add(panel1);
        panel5.add(panel2);
        panel5.add(panel3);
        panel5.add(panel4);

        JLabel label2 = new JLabel("id = ");
        JLabel label3 = new JLabel("clientId = ");
        JLabel label4 = new JLabel("productId = ");
        JLabel label5 = new JLabel("quantity = ");
        JLabel label6 = new JLabel("price = ");

        this.textField1 = new JTextField(10);
        this.textField2 = new JTextField(10);
        this.textField3 = new JTextField(10);
        this.textField4 = new JTextField(10);
        this.textField5 = new JTextField(10);

        panel6.add(label2);
        panel6.add(textField1);
        panel6.add(label3);
        panel6.add(textField2);
        panel6.add(label4);
        panel6.add(textField3);
        panel6.add(label5);
        panel6.add(textField4);
        //panel6.add(label6);
        //panel6.add(textField5);

        p.add(label1);
        p.add(panel5);
        p.add(panel6);

        this.setContentPane(p);
    }

    public void setActionListeners(OrderBLL orderBLL, ProductBLL productBLL){
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order o = new Order(1, 2, 3, 20, 5);

                List<String> list = ReflectionRetrieveProperties.retrieveProperties(o);

                String[] columnNames = new String[list.size()];

                for (int i = 0; i < list.size();i++){
                    columnNames[i] = list.get(i);
                }

                try {
                    String[][] data = orderBLL.selectAll();
                    JFrame frame = new JFrame();
                    frame.setTitle("Client table");
                    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    frame.setSize(500, 300);
                    JTable j = new JTable(data, columnNames);
                    JScrollPane sp = new JScrollPane(j);
                    frame.add(sp);
                    frame.setVisible(true);
                }catch (NoSuchElementException exp){
                    System.out.println(exp.getMessage());
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField1.getText());
                int clientId = Integer.parseInt(textField2.getText());
                int productId = Integer.parseInt(textField3.getText());
                int quantity = Integer.parseInt(textField4.getText());

                try {
                    Product product = productBLL.findById(productId);
                    int priceUnit = product.getPrice();
                    int quantityProduct = product.getQuantity();

                    int newQuantity = quantityProduct - quantity;

                    int price = priceUnit * quantity;
                    Order order = new Order(id, clientId, productId, quantity, price);

                    orderBLL.insert(order);
                    productBLL.update(productId, newQuantity);
                    orderBLL.generateBill(order);

                }catch (NoSuchElementException exp){
                    System.out.println(exp.getMessage());
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField1.getText());
                int quantity = Integer.parseInt(textField4.getText());

                Product product = productBLL.findById(orderBLL.findById(id).getProductId());
                int price = product.getPrice();
                int newPrice = quantity * price;

                orderBLL.update(id, quantity, newPrice);
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField1.getText());
                orderBLL.delete(id);
            }
        });
    }
}
