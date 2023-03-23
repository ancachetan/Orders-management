package model;

public class Order {
    private int id;
    private int clientId;
    private int productId;
    private int quantity;
    private int price;

    public Order(int id, int clientId, int productId, int quantity, int price) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order [id = " + id + ", client id = " + clientId + ", product id = " + productId + ", quantity = " + quantity + ", price = " + price + "]";
    }
}
