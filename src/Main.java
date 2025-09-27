import dao.ClienteDAO;
import dao.DBConnection;
import estrutura.ListaDuplamenteLigada;
import models.Cliente;
import service.ClienteService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getConnection();
        ClienteDAO clienteDAO = new ClienteDAO(connection);
        ListaDuplamenteLigada listaClientes = new ListaDuplamenteLigada();
        ClienteService clienteService= new ClienteService(clienteDAO,listaClientes);
        Cliente cliente1 = new Cliente(0, "110202329077C","Lukanga",
                "Ivandro",822254160,"Mavalane A",LocalDate.now() );

        Cliente cliente2 = new Cliente(0, "110202329077D","Edilson",
                "Manuel",822254160,"Mavalane A",LocalDate.now() );


        clienteService.cadastrarCliente(cliente1);
        clienteService.cadastrarCliente(cliente2);
        listaClientes.imprimir();
        System.out.println();
        clienteService.AtualizarCliente("110202329077C", "Luks",null,0,"Marracuene");
        System.out.println("Ola");






    }
}