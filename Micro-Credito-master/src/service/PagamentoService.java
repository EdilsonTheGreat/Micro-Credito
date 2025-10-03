package service;

import dao.EmprestimoDAO;
import dao.PagamentoDAO;
import estrutura.ListaDuplamenteLigada;
import models.Cliente;
import models.Emprestimo;
import models.Pagamento;
import java.sql.SQLException;
import java.time.LocalDate;

public class PagamentoService {
	private PagamentoDAO pagamentoDAO;
	private EmprestimoDAO emprestimoDAO;
	private ListaDuplamenteLigada listaPagamento;
	private EmprestimoService emprestimoService;
	private ClienteService clienteService;

	public PagamentoService(PagamentoDAO pagamentoDAO, ListaDuplamenteLigada listaPagameto,
			EmprestimoService emprestimoService, EmprestimoDAO emprestimoDAO, ClienteService clienteService)
			throws SQLException {
		this.pagamentoDAO = pagamentoDAO;
		this.listaPagamento = listaPagameto;
		this.emprestimoService = emprestimoService;
		this.emprestimoDAO = emprestimoDAO;
		this.clienteService = clienteService;
		carregarPagamentosDaBase();
	}

	
	
	public ListaDuplamenteLigada getListaPagamento() {
		return listaPagamento;
	}



	public void setListaPagamento(ListaDuplamenteLigada listaPagamento) {
		this.listaPagamento = listaPagamento;
	}



	// Registrar um novo pagamento
	public boolean registrarPagamento(String idEmprestimo, double valorPago) throws SQLException {
		if (!validarDados(idEmprestimo, valorPago)) {
			return false;
		}
		Emprestimo emprestimo = emprestimoService.buscarEmprestimoPorId(idEmprestimo);

		int proximaParcela = calcularProximaParcela(emprestimo.getIdEmprestimo());

		double valorSemAtraso = 0;
		if (emprestimo.getTipo().equalsIgnoreCase("prestacao")) {
			valorSemAtraso = emprestimo.getValorAPagar() / emprestimo.getNumeroPrestacoes();
		} else {
			valorSemAtraso = emprestimo.getValorAPagar();
		}

		// Juros por atraso (10% sobre o valor TOTAL com juros normais)
		double juroAtraso = 0;
		boolean isAtrasado = false;

		if (LocalDate.now().isAfter(emprestimo.getDataVencimento())) {
			juroAtraso = valorSemAtraso * 0.10; // 10% sobre 6500 = 650
			emprestimo.setEstado("atrasado");
			isAtrasado = true;
			System.out.println("Pagamento em atraso, juro aplicado: " + juroAtraso);
		}

		double valorMinimo = valorSemAtraso + juroAtraso;

		if (valorPago < valorMinimo) {
			System.out.println("Valor insuficiente. Mínimo esperado: " + valorMinimo + " (Valor sem atraso: "
					+ valorSemAtraso + ", Juro atraso: " + juroAtraso + ")");
			return false;
		}

		// Criar objeto Pagamento
		Pagamento pagamento = new Pagamento(null, // id gerado pelo banco
				emprestimo.getIdEmprestimo(), valorPago, LocalDate.now(), proximaParcela);
		// Atualizar o juros extra, pq no Pagamento nao recebe juro extra como parametro
		pagamento.setJuroAplicado(juroAtraso);

		boolean inserido = pagamentoDAO.insert(pagamento);

		if (inserido) {
			// Adicionar à lista ligada
			listaPagamento.adcionaFim(pagamento);
		}

		// Atualizar saldo do empréstimo
		double totalPago = calcularTotalPago(emprestimo.getIdEmprestimo());
		if (totalPago >= emprestimo.getValorAPagar()) {
			emprestimo.setEstado("liquidado");
			System.out.println(" Empréstimo liquidado com sucesso!");
		}

		emprestimoDAO.update(emprestimo);
		emprestimoService.atualizarEmprestimoPago(emprestimo);

		System.out.println("Pagamento registrado: " + pagamento);
		return true;
	}

	// Listar pagamentos por empréstimo
	public ListaDuplamenteLigada listarPagamentosPorEmprestimos(String idEmprestimo) {
		ListaDuplamenteLigada resultado = new ListaDuplamenteLigada();
		for (int i = 0; i < listaPagamento.tamanho(); i++) {
			Pagamento pagamento = (Pagamento) listaPagamento.pega(i);
			if (idEmprestimo.equalsIgnoreCase(pagamento.getIdEmprestimo())) {
				resultado.adcionaFim(pagamento);
			}
		}
		return ordenarPagamentosPorParcela(resultado);
	}

	public void criarPlanoPagamento(String idEmprestimo) {
		Emprestimo emprestimo = emprestimoService.buscarEmprestimoPorId(idEmprestimo);

		if (emprestimo == null) {
			System.out.println("Empréstimo não encontrado: " + idEmprestimo);
			return;
		}

		String biCliente = emprestimo.getBiCliente();
		Cliente cliente = clienteService.buscarClientePorBi(biCliente);

		if (cliente == null) {
			System.out.println("Cliente não encontrado para o BI: " + biCliente);
			return;
		}

		// Calcular valores
		double valorEmprestado = emprestimo.getValorEmprestado();
		double valorAPagar = emprestimo.getValorAPagar();
		double jurosTotal = valorAPagar - valorEmprestado;
		String tipo = emprestimo.getTipo();
		int numPrestacoes = emprestimo.getNumeroPrestacoes();

		// Imprimir cabeçalho
		System.out.println("\n" + "=".repeat(60));
		System.out.println("              PLANO DE PAGAMENTO");
		System.out.println("=".repeat(60));

		// Informações do Cliente
		System.out.println("CLIENTE:");
		System.out.println("  Nome Completo: " + cliente.getNome() + " " + cliente.getApelido());
		System.out.println("  BI: " + cliente.getBi());
		System.out.println("  Telefone: " + cliente.getTelefone());
		System.out.println("  Endereco: " + cliente.getEndereco());
		System.out.println("  Data Cadastro: " + cliente.getDataCadastro());

		// Informações do Empréstimo
		System.out.println("\nEMPRÉSTIMO:");
		System.out.println("  ID: " + emprestimo.getIdEmprestimo());
		System.out.println("  Tipo: " + tipo.toUpperCase());
		System.out.println("  Valor Emprestado: " + valorEmprestado + " MT");
		System.out.println("  Taxa de Juro: " + emprestimo.getTAXA_JURO() * 100 + "%");
		System.out.println("  Valor a Pagar: " + valorAPagar + " MT");
		System.out.println("  Juros Total: " + jurosTotal + " MT");
		System.out.println("  Data Concessão: " + emprestimo.getDataConcessao());
		System.out.println("  Data Vencimento: " + emprestimo.getDataVencimento());
		System.out.println("  Estado: " + emprestimo.getEstado());

		System.out.println("\n" + "-".repeat(60));
		System.out.println("             DETALHES DO PAGAMENTO");
		System.out.println("-".repeat(60));

		if ("direto".equalsIgnoreCase(tipo)) {
			// Plano para pagamento único
			System.out.println("PAGAMENTO ÚNICO:");
			System.out.printf("  Valor Base: %.2f MT%n", valorEmprestado);
			System.out.printf("  Juros (%s%%): %.2f MT%n", emprestimo.getTAXA_JURO() * 100, jurosTotal);
			System.out.printf("  TOTAL A PAGAR: %.2f MT%n", valorAPagar);
			System.out.println("  Data de Vencimento: " + emprestimo.getDataVencimento());

		} else if ("prestacao".equalsIgnoreCase(tipo)) {
			// Plano para prestações
			double valorParcelaBase = valorEmprestado / numPrestacoes;
			double juroPorParcela = jurosTotal / numPrestacoes;
			double valorParcelaTotal = valorAPagar / numPrestacoes;

			System.out.println("PRESTAÇÕES: " + numPrestacoes + " parcelas");
			System.out.println();

			for (int i = 1; i <= numPrestacoes; i++) {
				LocalDate dataVencimentoParcela = emprestimo.getDataConcessao().plusMonths(i);

				System.out.println("PARCELA " + i + ":");
				System.out.printf("  Valor Base: %.2f MT%n", valorParcelaBase);
				System.out.printf("  Juros: %.2f MT%n", juroPorParcela);
				System.out.printf("  Total Parcela: %.2f MT%n", valorParcelaTotal);
				System.out.println("  Data Vencimento: " + dataVencimentoParcela);

				if (i < numPrestacoes) {
					System.out.println("  ---");
				}
			}

			System.out.println("\nRESUMO DAS PRESTAÇÕES:");
			System.out.printf("  Valor por Parcela: %.2f MT%n", valorParcelaTotal);
			System.out.printf("  Total a Pagar: %.2f MT%n", valorAPagar);
		}

		System.out.println("\n" + "=".repeat(60));
		System.out.println("         FIM DO PLANO DE PAGAMENTO");
		System.out.println("=".repeat(60) + "\n");
	}

	// ===== MÉTODOS AUXILIARES =====
	private boolean validarDados(String idEmprestimo, double valorPago) {
		Emprestimo emprestimo = emprestimoService.buscarEmprestimoPorId(idEmprestimo);
		if (emprestimo == null) {
			System.out.println("Empréstimo não encontrado.");
			return false;
		}
		if ("liquidado".equalsIgnoreCase(emprestimo.getEstado())) {
			System.out.println("Empréstimo liquidado, estado atual: " + emprestimo.getEstado());
			return false;
		}
		if (valorPago <= 0) {
			System.out.println("Valor inválido para pagamento.");
			return false;
		}
		return true;
	}

	// Calcular próxima parcela
	public int calcularProximaParcela(String idEmprestimo) throws SQLException {
		ListaDuplamenteLigada listaPagamentos = pagamentoDAO.buscarTodos();
		int maiorParcela = 0;

		for (int i = 0; i < listaPagamentos.tamanho(); i++) {
			Pagamento p = (Pagamento) listaPagamentos.pega(i);
			if (p.getIdEmprestimo().equals(idEmprestimo)) {
				if (p.getNumeroParcela() > maiorParcela) {
					maiorParcela = p.getNumeroParcela();
				}
			}
		}
		return maiorParcela + 1;
	}

	// Calcular total pago até agora
	private double calcularTotalPago(String idEmprestimo) throws SQLException {
		double soma = 0;

		for (int i = 0; i < listaPagamento.tamanho(); i++) {
			Pagamento p = (Pagamento) listaPagamento.pega(i);
			if (p.getIdEmprestimo().equals(idEmprestimo)) {
				soma += p.getValorPago();
			}
		}
		return soma;
	}

	// metodo auxilar para ordenar os pagamentos de um emprestimo
	private ListaDuplamenteLigada ordenarPagamentosPorParcela(ListaDuplamenteLigada pagamentos) {
		ListaDuplamenteLigada ordenada = new ListaDuplamenteLigada();

		// Enquanto ainda houver pagamentos para ordenar
		while (pagamentos.tamanho() > 0) {
			int menorIndice = 0;
			Pagamento menorPagamento = (Pagamento) pagamentos.pega(menorIndice);

			// Encontrar pagamento com menor número de parcela
			for (int j = 1; j < pagamentos.tamanho(); j++) {
				Pagamento atual = (Pagamento) pagamentos.pega(j);
				if (atual.getNumeroParcela() < menorPagamento.getNumeroParcela()) {
					menorPagamento = atual;
					menorIndice = j;
				}
			}

			// Adicionar à lista ordenada e remover da original
			ordenada.adcionaFim(menorPagamento);
			pagamentos.removePosicao(menorIndice);
		}

		return ordenada;
	}

	/*
	public ListaDuplamenteLigada carregarPagamentosDaBase() throws SQLException {
		ListaDuplamenteLigada pagamentosRetornados = pagamentoDAO.buscarTodos();
		for (int i = 0; i < pagamentosRetornados.tamanho(); i++) {
			Pagamento pagamento = (Pagamento) pagamentosRetornados.pega(i);
			listaPagameto.adcionaFim(pagamento);
		}
		System.out.println(this.listaPagameto.tamanho() + " pagamento carregados da base");
		return pagamentosRetornados;
	}
	*/
	
	public ListaDuplamenteLigada carregarPagamentosDaBase() throws SQLException {
	 	   ListaDuplamenteLigada listaPagamentoCarregado = pagamentoDAO.buscarTodos();
	 	    if (listaPagamentoCarregado == null) {
	 	    	listaPagamentoCarregado = new ListaDuplamenteLigada();
	 	    } 

	 	    // SUBSTITUI a lista interna (não acumula)
	 	    this.listaPagamento= listaPagamentoCarregado;

	 	    System.out.println(this.listaPagamento.tamanho() + " pagamentos carregados da base");
	 	    return this.listaPagamento;
	    }
	
}
