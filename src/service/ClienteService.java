package service;

import dao.ClienteDAO;
import estrutura.ListaDuplamenteLigada;
import models.Cliente;

import java.sql.SQLException;

public class ClienteService {
    private ClienteDAO clienteDAO;
    private ListaDuplamenteLigada listaCliente;

    public ClienteService(ClienteDAO clienteDAO, ListaDuplamenteLigada listaCliente) throws SQLException {
        this.clienteDAO = clienteDAO;
        this.listaCliente = listaCliente;
        carregarClientesDaBase();
    }
    // Metodo para cadastrar cliente
    public  void cadastrarCliente(Cliente cliente) throws SQLException {
        if (validarDados(cliente)) {
            try {
                boolean inserido = clienteDAO.insert(cliente); // Insere na base de dados
                if (inserido){
                    listaCliente.adcionaFim(cliente);// Insere na listaDuplamenteLigada
                    System.out.println("Cliente " + cliente.getId() + " cadastrado com sucesso (Lista)");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao cadastrar cliente na base de dados: " + e.getMessage());
                throw e;
            }
        }
    }

    //Busca De um registro(2.2.1 Dois atributos em separados)
    public Cliente buscarClientePorBi(String bi){
        if (bi ==null || bi.trim().isEmpty()){
          System.out.println("Bi não pode ser nulo  ou vazio");
          return null;
        }

        for (int i=0; i<listaCliente.tamanho();i++){
                Cliente cliente = (Cliente) listaCliente.pega(i);
                if (cliente.getBi().equalsIgnoreCase(bi.trim())){
                    return cliente;
                }
        }
        System.out.println("Não foi encontrado nenhum registro de BI: " + bi);
        return null;
    }

    //Busca De um registro(2.2.1 Dois atributos juntos)
    public void buscarClientePorTelefoneApelido(int telefone, String apelido) {
        if (apelido == null || apelido.trim().isEmpty()) {
            System.out.println("Busca inválida. Atributos de pesquisa não podem ser nulos ou vazios.");
            return;
        }

        boolean encontrado = false;
        for (int i = 0; i < listaCliente.tamanho(); i++) {
            Cliente cliente = (Cliente) listaCliente.pega(i);
            if (cliente.getTelefone() == telefone &&
                    cliente.getApelido().equalsIgnoreCase(apelido.trim())) {
                System.out.println(cliente);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Não foi encontrado nenhum registro para:\n" + apelido + "\n" + telefone);
        }
    }

    //2.3 Alteração dos dados de um determinado registo
    public void AtualizarCliente(String bi,String novoNome, String novoApelido, int novoTelefone, String novoEndereco) {
       Cliente cliente = buscarClientePorBi(bi);
        if (cliente != null) {
            if (novoNome != null && !novoNome.trim().isEmpty()) {
                cliente.setNome(novoNome);
            }
            if (novoApelido != null && !novoApelido.trim().isEmpty()) {
                cliente.setApelido(novoApelido);
            }
            if (novoTelefone != 0) {
                cliente.setTelefone(novoTelefone);
            }
            if (novoEndereco != null && !novoEndereco.trim().isEmpty()) {
                cliente.setEndereco(novoEndereco);
            }
            try {
                clienteDAO.update(cliente);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Cliente atualizado: " + cliente);
        }
    }


    //2.4 Eliminação de um registo(2.4.1 Numa dada posição)
   public void removerCliente(int posicao){
        listaCliente.removePosicao(posicao);
   }



    //2.4.2 Com um certo código





    private void carregarClientesDaBase() throws SQLException {
        ListaDuplamenteLigada clientesRetornados = clienteDAO.buscarTodos();
        for (int i = 0; i < clientesRetornados.tamanho(); i++) {
            Cliente cliente = (Cliente) clientesRetornados.pega(i);
            listaCliente.adcionaFim(cliente);
        }
        System.out.println( this.listaCliente.tamanho() + " clientes carregados da base");
    }


    private  boolean validarDados(Cliente cliente){
        if (cliente.getBi() == null || cliente.getBi().isBlank()) {
            System.out.println("BI não pode estar vazio");
            return false;
        }

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            System.out.println("Nome não pode estar vazio");
            return false;
        }

        if (cliente.getApelido() == null || cliente.getApelido().isBlank()) {
            System.out.println("Apelido não pode estar vazio");
            return false;
        }

        if (cliente.getTelefone() < 820000000 || cliente.getTelefone() > 879999999) {
            System.out.println("Telefone inválido para Moçambique");
            return false;
        }

        // Validação de endereço
        if (cliente.getEndereco() == null || cliente.getEndereco().isBlank()) {
            System.out.println("Endereço não pode estar vazio");
            return false;
        }

        if (biJaExiste(cliente.getBi())) {
            System.out.println("Já existe um cliente com o BI " + cliente.getBi());
            return false;
        }

        return true; // Se passar todas as validações

        }

    private boolean biJaExiste(String bi) {
        for (int i = 0; i < listaCliente.tamanho(); i++) {
          Cliente actual = (Cliente) listaCliente.pega(i);
           if (actual.getBi().equalsIgnoreCase(bi.trim())) {
                return true;
           }
      }
        return false;
    }
    }


