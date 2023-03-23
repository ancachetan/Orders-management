package bll;

import dao.ClientDAO;
import model.Client;

import java.util.List;
import java.util.NoSuchElementException;


public class ClientBLL {

    public Client findById(int id){
       Client c = ClientDAO.findById(id);
       if (c == null){
           throw new NoSuchElementException("The client with id = " + id + " was not found!");
       }

       return c;
    }

    public void insert(Client client){
        ClientDAO.insert(client);
    }

    public void delete(int id){
        ClientDAO.delete(id);
    }

    public void update(int id, String address){
        ClientDAO.update(id, address);
    }

    public String[][] selectAll(){
        String[][] str = ClientDAO.selectAll();
        if (str == null){
            throw new NoSuchElementException("Table is empty!");
        }

        return str;
    }

    public List<Client> findAll(){
        ClientDAO clientDAO = new ClientDAO();
        return clientDAO.findAll();
    }
}
