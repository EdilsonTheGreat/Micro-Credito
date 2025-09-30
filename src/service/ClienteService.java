package service;

import dao.ClienteDAO;

import estrutura.ListaDuplamenteLigada;
import models.Cliente;


import java.sql.SQLException;

public class ClienteService {
    private ClienteDAO clienteDAO;
    private ListaDuplamenteLigada listaCliente;
    private EmprestimoService empService;



    public ClienteService(ClienteDAO clienteDAO, ListaDuplamenteLigada listaCliente) throws SQLException {
        this.clienteDAO = clienteDAO;
        this.listaCliente = listaCliente;
        carregarClientesDaBase();
    }

    public void setEmpService(EmprestimoService empService) {
        this.empService = empService;
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
            if (novoTelefone < 820000000 || novoTelefone > 879999999) {
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
   public void removerClientePorPosicao(int posicao){
        Cliente cliente = (Cliente) listaCliente.pega(posicao);
       if (cliente == null) {
           System.out.println("Cliente não encontrado na posição: " + posicao);
           return;
       }
       int temEmprestimosAtivos = clienteTemEmprestimosAtivos(cliente.getBi());
       if (temEmprestimosAtivos>0) {
           System.out.println(" REMOÇÃO BLOQUEADA: Cliente possui empréstimos ATIVOS");
           System.out.println("   BI do Cliente: " + cliente.getBi());
           System.out.println("   Ação: Liquidar os empréstimos ativos primeiro");
           return;
       }

       empService.removerEmprestimoPorBiCliente(cliente.getBi());
       System.out.println("Empréstimos do cliente removidos.");
        listaCliente.removePosicao(posicao);
       System.out.println("Cliente removido da lista em memória.");
        clienteDAO.delete(cliente.getBi());



   }

    //2.4.2 Com um certo código
    public void removerClientePorBi(String bi) {
        if (bi == null || bi.trim().isEmpty()) {
            System.out.println("BI não pode ser nulo ou vazio");
            return;
        }
        // VERIFICAR SE CLIENTE POSSUI EMPRÉSTIMOS ATIVOS
        int temEmprestimos = clienteTemEmprestimosAtivos(bi);
        if (temEmprestimos>0) {
            System.out.println(" REMOÇÃO BLOQUEADA: Cliente possui empréstimos ATIVOS");
            System.out.println("   BI do Cliente: " + bi);
            System.out.println("   Ação: Liquidar os empréstimos ativos primeiro");
            return;
        }

        boolean encontrado = false;
        for (int i = 0; i < listaCliente.tamanho(); i++) {
            Cliente cliente = (Cliente) listaCliente.pega(i);
            if (cliente.getBi().equalsIgnoreCase(bi.trim())) {
               // EmprestimoService emprestimoService = new EmprestimoService();
            empService.removerEmprestimoPorBiCliente(bi);
            System.out.println("Empréstimos do cliente removidos.");

            listaCliente.removePosicao(i);
            System.out.println("Cliente removido da lista em memória.");

            clienteDAO.delete(bi);
            System.out.println("Cliente " + bi + " removido com sucesso da base de dados.");
            encontrado = true;
            break;
            }
        }

        if (!encontrado) {
            System.out.println("Nenhum cliente encontrado com BI: " + bi);
        }
    }

    //2.5.1  Imprimir Todos os registos
    public void imprimirClientes(){
        listaCliente.imprimir();
    }
   // 2.5.2 Segundo um determinado critério (endereco)
    public void imprimirClientesPorEndereco(String endereco){
        if (endereco==null ||endereco.trim().isEmpty()){System.out.println("Endereço Não pode ser Nulo (");return;}
        boolean encontrado=false;
        for (int i = 0; i < listaCliente.tamanho(); i++) {
            Cliente cliente = (Cliente) listaCliente.pega(i);
            if (cliente.getEndereco().equalsIgnoreCase(endereco.trim())){
                System.out.println(cliente);
                encontrado = true;
            }
        }
        if (!encontrado){
            System.out.println("Nenhum cliente encontrado com morada em: " + endereco );
        }

    }

   // 2.5.3 Os dados ordenados por um determinado atributo.
   public void imprimirListaOrdenadaPorData() {
       ListaDuplamenteLigada ordenada = selectionSort();
       ordenada.imprimir();
   }



    //Metodos auxiliares
    public ListaDuplamenteLigada carregarClientesDaBase() throws SQLException {
        ListaDuplamenteLigada clientesRetornados = clienteDAO.buscarTodos();
        for (int i = 0; i < clientesRetornados.tamanho(); i++) {
            Cliente cliente = (Cliente) clientesRetornados.pega(i);
            listaCliente.adcionaFim(cliente);
        }
        System.out.println( this.listaCliente.tamanho() + " clientes carregados da base");
		return clientesRetornados;
    }

    private ListaDuplamenteLigada selectionSort() {
        // Criar cópia da lista original
        ListaDuplamenteLigada copia = new ListaDuplamenteLigada();
        for (int i = 0; i < listaCliente.tamanho(); i++) {
            copia.adcionaFim(listaCliente.pega(i));
        }

        ListaDuplamenteLigada ordenada = new ListaDuplamenteLigada();

        // Enquanto ainda houver elementos na cópia
            while(copia.tamanho()>0){
                int indiceMaisRecente = 0;
                Cliente clienteMaisRecente = (Cliente) copia.pega(indiceMaisRecente);

                // Encontrar cliente mais recente
                for (int j = 1; j < copia.tamanho(); j++) {
                    Cliente atual = (Cliente) copia.pega(j);
                    if (atual.getDataCadastro().isAfter(clienteMaisRecente.getDataCadastro())) {
                        clienteMaisRecente = atual;
                        indiceMaisRecente = j;
                    }
                }

                //  adiciona na lista ordenada e Remove da cópia
                    ordenada.adcionaFim(copia.pega(indiceMaisRecente));
                    copia.removePosicao(indiceMaisRecente);

            }
        return ordenada;
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

    protected boolean biJaExiste(String bi) {
        for (int i = 0; i < listaCliente.tamanho(); i++) {
          Cliente actual = (Cliente) listaCliente.pega(i);
           if (actual.getBi().equalsIgnoreCase(bi.trim())) {
                return true;
           }
      }
        return false;
    }


// Metodo auxiliar para verificar se cliente tem empréstimos ativos
private int clienteTemEmprestimosAtivos(String biCliente) {
    return empService.clienteTemEmprestimosAtivos(biCliente);
}
}


