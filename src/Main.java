import dao.ClienteDAO;
import dao.DBConnection;
import dao.EmprestimoDAO;
import estrutura.ListaDuplamenteLigada;
import models.Cliente;
import models.Emprestimo;
import service.ClienteService;
import service.EmprestimoService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getConnection();
        ClienteDAO clienteDAO = new ClienteDAO(connection);
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO(connection);
        ListaDuplamenteLigada listaClientes = new ListaDuplamenteLigada();
        ListaDuplamenteLigada listaEmprestimos = new ListaDuplamenteLigada();
        ClienteService clienteService= new ClienteService(clienteDAO,listaClientes);
        EmprestimoService emprestimoService = new EmprestimoService(emprestimoDAO,listaEmprestimos,clienteService);
        clienteService.setEmpService(emprestimoService);
        Emprestimo eF1 = new Emprestimo("EMP01", "110202329077F", 5000,"prestacao",3, "ativo", LocalDate.of(2023, 2, 5));
        Emprestimo eF = new Emprestimo("EMP05", "110202329077F", 5000,"prestacao",3, "liquidado", LocalDate.of(2023, 2, 5));
        Emprestimo eZ = new Emprestimo("EMP06", "110202329077Z", 5000,"prestacao",3, "liquidado", LocalDate.of(2023, 2, 5));


        Cliente cliente4 = new Cliente(0, "110202329077F", "Euller", "Teixeira", 822254163, "Mavalane D", LocalDate.of(2023, 12, 20));
        Cliente cliente5 = new Cliente(0, "110202329077Z", "João", "Santos", 822254163, "Mavalane D", LocalDate.of(2023, 12, 20));
        clienteService.cadastrarCliente(cliente4);
        clienteService.cadastrarCliente(cliente5);

        emprestimoService.cadastrarEmprestimo(eF1);
        emprestimoService.cadastrarEmprestimo(eF);
        emprestimoService.cadastrarEmprestimo(eZ);
        System.out.println("\n📌 Lista de Clientes ");
        clienteService.imprimirClientes();

        System.out.println("\n📌 Lista de Empréstimos ");
        emprestimoService.imprimirEmprestimos();

        clienteService.removerClientePorPosicao(1);
        clienteService.removerClientePorBi("110202329077F");
        System.out.println("\n📌 Lista de Clientes ");
        clienteService.imprimirClientes();

        System.out.println("\n📌 Lista de Empréstimos ");
        emprestimoService.imprimirEmprestimos();

      // clienteService.cadastrarCliente(cliente5);


       // clienteService.removerClientePorBi("110202329077F");
        //emprestimoService.cadastrarEmprestimo(e2);
       // emprestimoService.cadastrarEmprestimo(e3);
       //emprestimoService.cadastrarEmprestimo(e4);
/*

       clienteService.removerClientePorBi("110202329077A");


        System.out.println("\n📌 Lista de Empréstimos por tipo");
        emprestimoService.imprimirEmprestimos();

*/

      /*  System.out.println("\n📌 Lista de Empréstimos:");
        emprestimoService.imprimirEmprestimos();

        emprestimoService.AtualizarEmprestimo("EMP02", null, "prestacao",15);
        System.out.println("\n📌 Lista de Empréstimos:");
        emprestimoService.imprimirEmprestimos();
        // 4. Buscar empréstimo por estado
        System.out.println("\n🔍 Buscar Empréstimos Ativos na data 2025-02-05:");
        emprestimoService.buscarEmprestimoPorDataEstado(LocalDate.of(2025, 2, 5), "ativo");


        // 6. Remover cliente + empréstimos
        System.out.println("\n🗑️ Remover Cliente 110202329077C e seus empréstimos:");
        clienteService.removerClientePorBi("110202329077C");

        System.out.println("\n📌 Lista de Emprestimos após remoção:");
        emprestimoService.imprimirEmprestimos();


        // 7. Ordenar empréstimos por data
        System.out.println("\n📅 Empréstimos ordenados por data de concessão:");
        emprestimoService.imprimirListaOrdenadaPorData();*/
















        /*Cliente cliente1 = new Cliente(0, "110202329077C", "Lukanga", "Ivandro", 822254160, "Mavalane A", LocalDate.of(2024, 3, 15));
        Cliente cliente2 = new Cliente(0, "110202329077D", "Edilson", "Manuel", 822254161, "Mavalane B", LocalDate.of(2024, 3, 10));
        Cliente cliente3 = new Cliente(0, "110202329077E", "Maria", "Silva", 822254162, "Mavalane C", LocalDate.of(2005, 1, 5));
        Cliente cliente4 = new Cliente(0, "110202329077F", "João", "Santos", 822254163, "Mavalane D", LocalDate.of(2023, 12, 20));
        Cliente cliente5 = new Cliente(0, "110202329077A", "Everson", "Marcos", 822254162, "Mavalane A", LocalDate.of(2025, 1, 5));
        Cliente cliente6 = new Cliente(0, "110202329077B", "Yusse", "Macamo", 822254163, "Mavalane G", LocalDate.of(2016, 12, 20));*/

        /*lienteService.cadastrarCliente(cliente4);
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
*/





    }
}