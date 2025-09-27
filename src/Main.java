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
        Cliente cliente1 = new Cliente(0, "110202329077C", "Lukanga", "Ivandro", 822254160, "Mavalane A", LocalDate.of(2024, 3, 15));
        Cliente cliente2 = new Cliente(0, "110202329077D", "Edilson", "Manuel", 822254161, "Mavalane B", LocalDate.of(2024, 3, 10));
        Cliente cliente3 = new Cliente(0, "110202329077E", "Maria", "Silva", 822254162, "Mavalane C", LocalDate.of(2005, 1, 5));
        Cliente cliente4 = new Cliente(0, "110202329077F", "João", "Santos", 822254163, "Mavalane D", LocalDate.of(2023, 12, 20));
        Cliente cliente5 = new Cliente(0, "110202329077A", "Everson", "Marcos", 822254162, "Mavalane A", LocalDate.of(2025, 1, 5));
        Cliente cliente6 = new Cliente(0, "110202329077B", "Yusse", "Macamo", 822254163, "Mavalane G", LocalDate.of(2016, 12, 20));

        clienteService.cadastrarCliente(cliente4);
        clienteService.cadastrarCliente(cliente1);
        clienteService.cadastrarCliente(cliente2);
        clienteService.cadastrarCliente(cliente3);
        clienteService.cadastrarCliente(cliente5);
        clienteService.cadastrarCliente(cliente6);

        clienteService.imprimirClientes();
        System.out.println();
        clienteService.imprimirClientesPorEndereco("Mavalane A");
        System.out.println();
        clienteService.imprimirListaOrdenadaPorData();






    }
}