package start;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import dao.ClientDAO;
import model.Client;
import model.Product;
import presentation.View;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Start {
    public static void main(String[] args) {
        ClientBLL clientBLL = new ClientBLL();
        ProductBLL productBLL = new ProductBLL();
        OrderBLL orderBLL = new OrderBLL();

        View frame = new View(clientBLL, productBLL, orderBLL);
        frame.actionListeners();

    }
}
