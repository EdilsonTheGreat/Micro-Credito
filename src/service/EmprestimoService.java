package service;


import dao.EmprestimoDAO;
import estrutura.ListaDuplamenteLigada;

import models.Emprestimo;

import java.sql.SQLException;
import java.time.LocalDate;


public class EmprestimoService {
    private EmprestimoDAO emprestimoDAO;
    private ListaDuplamenteLigada listaEmprestimo;
    private ClienteService clienteService;


    public EmprestimoService(EmprestimoDAO emprestimoDAO, ListaDuplamenteLigada listaEmprestimo,ClienteService clienteService ) throws SQLException {
        this.emprestimoDAO = emprestimoDAO;
        this.listaEmprestimo = listaEmprestimo;
        this.clienteService = clienteService;
       carregarEmprestimoDaBase();
    }



    public EmprestimoService()  {
        this.listaEmprestimo = new ListaDuplamenteLigada();
    }
    //cadastrar emprestimo
    public void cadastrarEmprestimo(Emprestimo emprestimo) throws SQLException {
        if (validarDados(emprestimo)){
            try {
                boolean inserido = emprestimoDAO.insert(emprestimo);
                if (inserido){listaEmprestimo.adcionaFim(emprestimo); System.out.println("Emprestimo " + emprestimo.getIdEmprestimo() + " cadastrado com sucesso (Lista)");}

            } catch (SQLException e) {
                System.out.println("Erro ao cadastrar Emprestimo na base de dados: " + e.getMessage());
                throw e;
            }
        }
    }


    //Busca De um registro(2.2.1 Dois atributos em separados)
    public Emprestimo buscarEmprestimoPorId(String idEmprestimo){
        if (idEmprestimo ==null || idEmprestimo.trim().isEmpty()){
            System.out.println("Bi não pode ser nulo  ou vazio");
            return null;
        }

        for (int i=0; i<listaEmprestimo.tamanho();i++){
            Emprestimo emprestimo = (Emprestimo) listaEmprestimo.pega(i);
            if (emprestimo.getIdEmprestimo().equalsIgnoreCase(idEmprestimo.trim())){
                return emprestimo;
            }
        }
        System.out.println("Não foi encontrado nenhum Emprestimo para Id: " + idEmprestimo);
        return null;
    }
    //Busca De um registro(2.2.1 Dois atributos juntos)
    public void buscarEmprestimoPorDataEstado(LocalDate data, String estado) {
        if (!estado.equalsIgnoreCase("ativo") &&
                !estado.equalsIgnoreCase("liquidado") &&
                !estado.equalsIgnoreCase("atrasado")) {
            System.out.println("Busca inválida. Atributo de pesquisa inválido");
            return;
        }

        boolean encontrado = false;
        for (int i = 0; i < listaEmprestimo.tamanho(); i++) {
            Emprestimo emprestimo = (Emprestimo) listaEmprestimo.pega(i);
            if (emprestimo.getDataConcessao().equals(data) &&
                    emprestimo.getEstado().equalsIgnoreCase(estado.trim())) {
                System.out.println(emprestimo);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Não foi encontrado nenhum registro para:\n" + data + "\n" + estado);
        }
    }

    //2.3 Alteração dos dados de um determinado registo
    public void AtualizarEmprestimo(String biEmprestimo,String biCliente, String novoTipo, int numeroPrestacoes ) {
        Emprestimo emprestimo = buscarEmprestimoPorId(biEmprestimo);
        if (emprestimo != null) {
            if (biCliente != null && !biCliente.trim().isEmpty() && clienteService.biJaExiste(biCliente)) {
                emprestimo.setBiCliente(biCliente);
            }
            if (novoTipo != null && !novoTipo.trim().isEmpty() && !novoTipo.equalsIgnoreCase(emprestimo.getTipo())) {
                emprestimo.setTipo(novoTipo);
            }
            if (numeroPrestacoes <= 0) {
                emprestimo.setNumeroPrestacoes(numeroPrestacoes);
            }
            try {
                emprestimoDAO.update(emprestimo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Emprestimo atualizado: " + emprestimo);
        }
    }

    public void atualizarEmprestimoPago(Emprestimo atual) throws SQLException {
        if (listaEmprestimo == null){ return;}

        for (int i = 0; i < listaEmprestimo.tamanho(); i++) {
            Emprestimo e = (Emprestimo) listaEmprestimo.pega(i);
            if (e.getIdEmprestimo().equals(atual.getIdEmprestimo())) {
                e.setEstado(atual.getEstado());
                return;
            }
        }

    }


    //2.4 Eliminação de um registo(2.4.1 Numa dada posição)
    public void removerEmprestimoPorPosicao(int posicao){
        Emprestimo emprestimo = (Emprestimo) listaEmprestimo.pega(posicao);
        listaEmprestimo.removePosicao(posicao);
        emprestimoDAO.delete(emprestimo.getIdEmprestimo());
    }

    //2.4.2 Com um certo código
    public void removerEmprestimoPorId(String id){
        if (id ==null || id.trim().isEmpty()){
            System.out.println("Bi não pode ser nulo  ou vazio");
            return ;
        }
        boolean encontrado = false;
        for (int i = 0; i < listaEmprestimo.tamanho(); i++) {
            Emprestimo emprestimo = (Emprestimo) listaEmprestimo.pega(i);
            if (emprestimo.getIdEmprestimo().equalsIgnoreCase(id.trim())){
                listaEmprestimo.removePosicao(i);
                emprestimoDAO.delete(id);    //remover na base de dados
                encontrado=true;
                break;
            }
        }
        if (!encontrado){
            System.out.println("Nenhum Emprestimo encontrado com Id: " + id);
        }
    }

    //2.5.1  Imprimir Todos os registos
    public void imprimirEmprestimos(){
        listaEmprestimo.imprimir();
    }
    // 2.5.2 Segundo um determinado critério (endereco)
    public void imprimirEmprestimosPorTipo(String tipo){
        if (!tipo.equalsIgnoreCase("prestacao") && !tipo.equalsIgnoreCase("direto")){
            System.out.println("Tipo Invalido ");
            return;
        }
        boolean encontrado=false;
        for (int i = 0; i < listaEmprestimo.tamanho(); i++) {
            Emprestimo emprestimo = (Emprestimo) listaEmprestimo.pega(i);
            if (emprestimo.getTipo().equalsIgnoreCase(tipo.trim())){
                System.out.println(emprestimo);
                encontrado = true;
            }
        }
        if (!encontrado){
            System.out.println("Nenhum Emprestimo do tipo: " + tipo );
        }

    }

    // 2.5.3 Os dados ordenados por um determinado atributo.
    public void imprimirListaOrdenadaPorData() {
        ListaDuplamenteLigada ordenada = selectionSort();
        ordenada.imprimir();
    }

    //Metodos auxiliares
    private ListaDuplamenteLigada selectionSort() {
        // Criar cópia da lista original
        ListaDuplamenteLigada copia = new ListaDuplamenteLigada();
        for (int i = 0; i < listaEmprestimo.tamanho(); i++) {
            copia.adcionaFim(listaEmprestimo.pega(i));
        }

        ListaDuplamenteLigada ordenada = new ListaDuplamenteLigada();

        // Enquanto ainda houver elementos na cópia
        while (copia.tamanho() > 0) {
            int indiceMaisRecente = 0;
            Emprestimo emprestimoMaisRecente = (Emprestimo) copia.pega(indiceMaisRecente);

            // Encontrar cliente mais recente
            for (int j = 1; j < copia.tamanho(); j++) {
                Emprestimo atual = (Emprestimo) copia.pega(j);
                if (atual.getDataConcessao().isAfter(emprestimoMaisRecente.getDataConcessao())) {
                    emprestimoMaisRecente = atual;
                    indiceMaisRecente = j;
                }
            }
            //  adiciona na lista ordenada e Remove da cópia
            ordenada.adcionaFim(copia.pega(indiceMaisRecente));
            copia.removePosicao(indiceMaisRecente);

        }
        return ordenada;
    }
    private void carregarEmprestimoDaBase() throws SQLException {
        ListaDuplamenteLigada clientesRetornados = emprestimoDAO.buscarTodos();
        for (int i = 0; i < clientesRetornados.tamanho(); i++) {
            Emprestimo emprestimo = (Emprestimo) clientesRetornados.pega(i);
            listaEmprestimo.adcionaFim(emprestimo);
        }
        System.out.println( this.listaEmprestimo.tamanho() + " Emprestimos carregados da base");
    }
    private boolean idEmprestimoExiste(String id){
        for (int i = 0; i < listaEmprestimo.tamanho(); i++) {
            Emprestimo actual = (Emprestimo) listaEmprestimo.pega(i);
            if (actual.getIdEmprestimo().equalsIgnoreCase(id.trim())) {
                return true;
            }
        }
        return false;
    }
    protected String gerarNovoIdEmprestimo() {
      Emprestimo ultimo = (Emprestimo) listaEmprestimo.pega(listaEmprestimo.tamanho()-1);
        int novoNumero = 1; // padrão, se a lista estiver vazia

        if (ultimo != null) {
            String idUltimo = ultimo.getIdEmprestimo(); // ex: EMP12
            if (idUltimo != null && idUltimo.startsWith("EMP")) {
                try {
                    int num = Integer.parseInt(idUltimo.substring(3));
                    novoNumero = num + 1;
                } catch (NumberFormatException _) {

                }
            }
        }

        return String.format("EMP%02d", novoNumero); // EMP01, EMP02, EMP12 ...
    }
    protected boolean validarDados(Emprestimo emprestimo){
        if (idEmprestimoExiste(emprestimo.getIdEmprestimo())){
            System.out.println("Esse emprestimo ja foi cadastrado");
            return false;
        }

        if (!clienteService.biJaExiste(emprestimo.getBiCliente())) {
            System.out.println("Cliente não foi encontrado. Não foi possivel cadastrar esse emprestimo " + emprestimo.getIdEmprestimo());
            return false;
        }

        if (emprestimo.getValorEmprestado() <=0) {
            System.out.println("Valor do emprestimo não pode ser 0 ou negativo");
            return false;
        }

        if (!emprestimo.getTipo().equalsIgnoreCase("prestacao") && !emprestimo.getTipo().equalsIgnoreCase("direto")) {
            System.out.println("Tipo de prestacão não é valido");
            return false;
        }

        if (emprestimo.getNumeroPrestacoes()<=0) {
            System.out.println("Prestação nao pode ser nula ou negativa");
            return false;
        }


        if (!emprestimo.getEstado().equalsIgnoreCase("ativo")) {
            System.out.println("Estado invalido");
            return false;
        }
        if (emprestimo.getDataVencimento() == null) {
            System.out.println("Data de vencimento não pode ser nula");
            return false;
        }

        if (emprestimo.getDataVencimento().isBefore(emprestimo.getDataConcessao())) {
            System.out.println("Data de vencimento não pode ser anterior à data de concessão");
            return false;
        }

        return true;

    }
    // Buscar empréstimos atrasados
    public void buscarEmprestimosAtrasados() {
        boolean encontrado = false;
        LocalDate hoje = LocalDate.now();

        for (int i = 0; i < listaEmprestimo.tamanho(); i++) {
            Emprestimo emprestimo = (Emprestimo) listaEmprestimo.pega(i);
            if (emprestimo.getDataVencimento().isBefore(hoje) &&
                    !"liquidado".equalsIgnoreCase(emprestimo.getEstado())) {
                System.out.println(emprestimo);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Nenhum empréstimo atrasado encontrado");
        }
    }
    // Metodo para verificar se cliente tem empréstimos ativos
    protected int clienteTemEmprestimosAtivos(String biCliente) {
        int empAtivos =0;
        for (int i = 0; i < listaEmprestimo.tamanho(); i++) {
            Emprestimo emprestimo = (Emprestimo) listaEmprestimo.pega(i);
            if (emprestimo.getBiCliente().equalsIgnoreCase(biCliente.trim()) &&
                    "ativo".equalsIgnoreCase(emprestimo.getEstado())) {
                    empAtivos++;

            }
        }
        return empAtivos;
    }
    //metodo para remover emprestimo na lista caso cliente seja removido
    protected void removerEmprestimoPorBiCliente(String bi){
       if (bi == null ||bi.trim().isBlank()) {
           System.out.println("BI não pode ser nulo");
           return;
       }
       int emprestimosRemovidos = 0;
        for (int i = listaEmprestimo.tamanho() - 1; i >= 0; i--) {
            Emprestimo emp = (Emprestimo) listaEmprestimo.pega(i);
            if (bi.equalsIgnoreCase(emp.getBiCliente())) {
                emprestimoDAO.delete(emp.getIdEmprestimo());
                listaEmprestimo.removePosicao(i);
                emprestimosRemovidos++;
            }
        }
        if (emprestimosRemovidos>0){
            System.out.println(emprestimosRemovidos + " empréstimo(s) removido(s) da lista em memória.");
        }else {
            System.out.println("Cliente " + bi + " não possui empréstimos para remover.");
        }

    }
}
