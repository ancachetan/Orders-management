package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private ViewClient frameClients;
    private ViewProduct frameProduct;
    private ViewOrder frameOrder;
    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private OrderBLL orderBLL;

    public View(ClientBLL clientBLL, ProductBLL productBLL, OrderBLL orderBLL){
        this.clientBLL = clientBLL;
        this.productBLL = productBLL;
        this.orderBLL = orderBLL;

        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Database");

        frameClients = new ViewClient();
        frameClients.setActionListeners(this.clientBLL);
        frameProduct = new ViewProduct();
        frameProduct.setActionListeners(this.productBLL);
        frameOrder = new ViewOrder();
        frameOrder.setActionListeners(this.orderBLL, this.productBLL);

        JLabel label = new JLabel("Choose a table");
        this.button1 = new JButton("Client ");
        this.button2 = new JButton("Product");
        this.button3 = new JButton("Order  ");

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();

        panel6.add(label);

        panel1.setLayout(new GridLayout(3, 0));
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        panel3.add(button1);
        panel4.add(button2);
        panel5.add(button3);

        panel1.add(panel3);
        panel1.add(panel4);
        panel1.add(panel5);

        panel2.add(panel6);
        panel2.add(panel1);

        this.setContentPane(panel2);
        this.setVisible(true);
    }

    public void actionListeners(){
        this.button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameClients.setVisible(true);
            }
        });

        this.button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameProduct.setVisible(true);
            }
        });

        this.button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameOrder.setVisible(true);
            }
        });
    }

}
