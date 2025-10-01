package telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

import estrutura.ListaDuplamenteLigada;
import models.Cliente;
import models.Emprestimo;
import service.EmprestimoService;
import service.ClienteService;
import telas.ClienteTela.FormatacaoButton;
import telas.ClienteTela.FormatacaoLabel;
import telas.ClienteTela.FormatacaoTextField;
import telas.ClienteTela.LimiteCaracteresFilter;

public class EmprestimoTela extends JPanel implements ActionListener {

	private JLabel jl_vazio, jl_titulo, jl_id, jl_biCliente, jl_valorEmprestado, jl_tipo, jl_numeroPrestacoes,
			jl_estado, jl_dataConcessao, jl_dataVencimento, jl_bi, jl_posicao;
	private JTextField tf_valorEmprestado, tf_numeroPrestacoes, tf_dataConcessao, tf_dataVencimento, tf_id_remocao,
			tf_posicao, tf_idEmprestimo;
	private JButton jb_actualizar, jb_gravar, jb_removerCodigo, jb_removerPosicao;
	private JPanel jpc, jp1, jp2, jp3, jp4, jp5;
	private JComboBox jcb_tipo, jcb_estado;
	private JComboBox<ClienteItem> jcb_cliente;
	private JScrollPane jsp;
	private DefaultTableModel tabelaModelo;
	private JTable jt;
	private final service.EmprestimoService emprestimoService;
	private final service.ClienteService clienteService;

	public EmprestimoTela(service.EmprestimoService emprestimoService, service.ClienteService clienteService) {
		this.emprestimoService = emprestimoService;
		this.clienteService = clienteService;

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(600, 600));
		this.setMinimumSize(new Dimension(600, 600));
		this.setMaximumSize(new Dimension(600, 600));

		jl_titulo = new JLabel("Emprestimos", SwingConstants.CENTER);
		jl_titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		jl_titulo.setForeground(Color.decode("#2C3E50"));
		jl_titulo.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.decode("#7d9af4")),
						BorderFactory.createEmptyBorder(7, 0, 7, 0)));
		this.add("North", jl_titulo);

		jpc = new JPanel();
		jpc.setBackground(Color.decode("#F3F7EC"));
		jpc.setLayout(new BorderLayout());
		// inicio bloco 1
		jp1 = new JPanel();
		jp1.setBackground(Color.decode("#F3F7EC"));
		jp1.setLayout(new BorderLayout());

		jp2 = new JPanel();
		jp2.setBackground(Color.decode("#F3F7EC"));
		jp2.setLayout(new GridLayout(4, 4, 1, 1));

		jl_biCliente = new FormatacaoLabel("BiCliente: ");
		jp2.add(jl_biCliente);

		jcb_cliente = new JComboBox();
		jcb_cliente.setFont(new Font("Segoe UI", Font.BOLD, 13));
		jcb_cliente.setBackground(Color.decode("#F8F9FA"));
		jcb_cliente.setForeground(Color.decode("#2C3E50"));
		jcb_cliente.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode("#CED4DA"), 1),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		jp2.add(jcb_cliente);

		jl_valorEmprestado = new FormatacaoLabel("Valor Emprestado: ");
		jp2.add(jl_valorEmprestado);

		tf_valorEmprestado = new FormatacaoTextField(3);
		((AbstractDocument) tf_valorEmprestado.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(10));
		jp2.add(tf_valorEmprestado);

		jl_tipo = new FormatacaoLabel("Tipo: ");
		jp2.add(jl_tipo);

		jcb_tipo = new JComboBox();
		jcb_tipo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		jcb_tipo.setBackground(Color.decode("#F8F9FA"));
		jcb_tipo.setForeground(Color.decode("#2C3E50"));
		jcb_tipo.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode("#CED4DA"), 1),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		jp2.add(jcb_tipo);

		jcb_tipo.setModel(new DefaultComboBoxModel<>(new String[] { "Selecione o Tipo", "Directo", "Prestacao" }));
		jcb_tipo.addActionListener(this);

		jl_numeroPrestacoes = new FormatacaoLabel("Numero de Prestacoes: ");
		jp2.add(jl_numeroPrestacoes);

		tf_numeroPrestacoes = new FormatacaoTextField(3);
		((AbstractDocument) tf_numeroPrestacoes.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(10));
		jp2.add(tf_numeroPrestacoes);

		jl_estado = new FormatacaoLabel("Estado: ");
		jp2.add(jl_estado);

		jcb_estado = new JComboBox();
		jcb_estado.setFont(new Font("Segoe UI", Font.BOLD, 13));
		jcb_estado.setBackground(Color.decode("#F8F9FA"));
		jcb_estado.setForeground(Color.decode("#2C3E50"));
		jcb_estado.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode("#CED4DA"), 1),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		jcb_estado.disable();
		jp2.add(jcb_estado);

		jcb_estado.setModel(new DefaultComboBoxModel<>(new String[] { "ativo", "liquidado", "atrasado" }));

		jl_dataVencimento = new FormatacaoLabel("Data de Vencimento: ");
		jp2.add(jl_dataVencimento);

		tf_dataVencimento = new FormatacaoTextField(3);
		((AbstractDocument) tf_dataVencimento.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(10));
		jp2.add(tf_dataVencimento);

		jl_vazio = new FormatacaoLabel(" ");
		jp2.add(jl_vazio);

		jp5 = new JPanel();
		jp5.setBackground(Color.decode("#F3F7EC"));
		jp5.setLayout(new BorderLayout());
		TitledBorder titledBorder1 = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.decode("#7d9af4"), 1), "Actualizacao (IdEmprestimo)");
		titledBorder1.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
		jp5.setBorder(titledBorder1);

		tf_idEmprestimo = new FormatacaoTextField(3);
		((AbstractDocument) tf_idEmprestimo.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(10));
		jp5.add(tf_idEmprestimo);

		jp2.add(jp5);

		jb_actualizar = new FormatacaoButton("Actualizar", Color.decode("#3ead40"));
		jp2.add(jb_actualizar);
		jb_actualizar.addActionListener(this);

		jb_gravar = new FormatacaoButton("Gravar", Color.decode("#3ead40"));
		jp2.add(jb_gravar);
		jb_gravar.addActionListener(this);

		jp1.add("North", jp2);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.decode("#7d9af4"), 1), "Informações dos Emprestimos");
		titledBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
		jp1.setBorder(titledBorder);

		jp3 = new JPanel();
		jp3.setBackground(Color.decode("#F3F7EC"));
		jp3.setLayout(new BorderLayout());
		TitledBorder titledBorder2 = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.decode("#7d9af4"), 1), "Remocao dos Emprestimos");
		titledBorder2.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
		jp3.setBorder(titledBorder2);

		jp4 = new JPanel();
		jp4.setBackground(Color.decode("#F3F7EC"));
		jp4.setLayout(new GridLayout(2, 3, 1, 1));

		jl_id = new FormatacaoLabel("Id: ");
		jp4.add(jl_id);

		tf_id_remocao = new FormatacaoTextField(3);
		((AbstractDocument) tf_id_remocao.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(10));
		jp4.add(tf_id_remocao);

		jb_removerCodigo = new FormatacaoButton("Remocao por IdEmprestimo", Color.decode("#3ead40"));
		jp4.add(jb_removerCodigo);
		jb_removerCodigo.addActionListener(this);

		jl_posicao = new FormatacaoLabel("Posicao: ");
		jp4.add(jl_posicao);

		tf_posicao = new FormatacaoTextField(3);
		((AbstractDocument) tf_posicao.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(10));
		jp4.add(tf_posicao);

		jb_removerPosicao = new FormatacaoButton("Remocao por Posicao", Color.decode("#3ead40"));
		jp4.add(jb_removerPosicao);
		jb_removerPosicao.addActionListener(this);

		jp3.add("North", jp4);

		jp1.add("Center", jp3);

		jpc.add("North", jp1);

		// fim bloco 1

		// inicio bloco 2

		String[] nomeColunas = { "Id", "IdCliente", "ValorEmprestado", "ValorAPagar", "Tipo", "numeroPrestacoes",
				"taxaJuro", "estado", "dataConcessao", "dataVencimento" };

		tabelaModelo = new DefaultTableModel(nomeColunas, 0);
		jt = new JTable(tabelaModelo);
		jt.getTableHeader().setReorderingAllowed(false);
		jt.setEnabled(false);
		// Personalização da fonte e cores
		jt.setFont(new Font("Arial", Font.PLAIN, 12));
		jt.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		jt.getTableHeader().setBackground(Color.decode("#3ead40"));
		jt.getTableHeader().setForeground(Color.WHITE);
		jt.setRowHeight(25);
		jt.setGridColor(new Color(230, 230, 230));
		jt.setShowGrid(true);
		jt.setBackground(Color.WHITE);
		// Alinhamento e renderização das células
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		jt.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);

		// Definir larguras preferidas das colunas
		jt.getColumnModel().getColumn(0).setPreferredWidth(80);
		jt.getColumnModel().getColumn(1).setPreferredWidth(100);
		jt.getColumnModel().getColumn(2).setPreferredWidth(100);
		jt.getColumnModel().getColumn(3).setPreferredWidth(90);
		jt.getColumnModel().getColumn(4).setPreferredWidth(90);
		jt.getColumnModel().getColumn(5).setPreferredWidth(90);
		jt.getColumnModel().getColumn(6).setPreferredWidth(90);
		jt.getColumnModel().getColumn(7).setPreferredWidth(90);
		jt.getColumnModel().getColumn(8).setPreferredWidth(90);
		jt.getColumnModel().getColumn(9).setPreferredWidth(90);
		// Configuração do ScrollPane
		JScrollPane jsp = new JScrollPane(jt);
		jsp.setBorder(BorderFactory.createEmptyBorder());
		// jt.setEnabled(false);

		// Se desejar adicionar uma borda externa ao ScrollPane
		jsp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0),
				BorderFactory.createLineBorder(Color.decode("#7d9af4"), 1)));
		jsp.getViewport().setBackground(Color.WHITE);
		// jt.setEnabled(false);
		jpc.add("Center", jsp);
		this.add(jpc);

		// fim bloco 2

		carregarClientesNoCombo();
		carregarTabela();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb_gravar) {
			try {

				Object sel = jcb_cliente.getSelectedItem();
				if (sel == null) {
					JOptionPane.showMessageDialog(this, "Seleccione um cliente.");
					return;
				}
				String biCliente = String.valueOf(sel).trim();
				if (biCliente.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Valor de cliente vazio.");
					return;
				}
				String txtValorEmp = tf_valorEmprestado.getText().trim().replace(",", ".");
				if (txtValorEmp.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Indique o Valor Emprestado.");
					return;
				}
				double valorEmprestado = Double.parseDouble(txtValorEmp);

				String tipo = (String) jcb_tipo.getSelectedItem();
				if (tipo.equals("Selecione o Tipo")) {
					JOptionPane.showMessageDialog(this, "Seleccione o Tipo de empréstimo.");
					return;
				}

				int numeroPrestacoes = 0;
				if (tipo.equalsIgnoreCase("Prestacao")) {
					String txtPrest = tf_numeroPrestacoes.getText().trim();
					if (txtPrest.isEmpty()) {
						JOptionPane.showMessageDialog(this, "Indique o Número de Prestações.");
						return;
					}
					numeroPrestacoes = Integer.parseInt(txtPrest);
				} else {
					numeroPrestacoes = 1;
				}

				String estado = (String) jcb_estado.getSelectedItem();
				if (estado == null || estado.isBlank()) {
					JOptionPane.showMessageDialog(this, "Seleccione o Estado.");
					return;
				}

				java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String txtVenc = tf_dataVencimento.getText().trim();
				if (txtVenc.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Indique a Data de Vencimento (dd/MM/yyyy).");
					return;
				}
				java.time.LocalDate dataVencimento;
				try {
					dataVencimento = java.time.LocalDate.parse(txtVenc, df);
				} catch (java.time.format.DateTimeParseException ex) {
					JOptionPane.showMessageDialog(this, "Data de Vencimento inválida. Use dd/MM/yyyy.");
					return;
				}

				// 2) Valores calculados (no service/entidade)
				String novoId = emprestimoService.gerarNovoIdEmprestimo();
				java.time.LocalDate dataConcessao = java.time.LocalDate.now();

				// Se a tua classe Emprestimo calcula 30% internamente, basta criar o objecto.
				// Se preferires calcular aqui, usa uma função do service (ex.:
				// calcularValorAPagar).
				Emprestimo novo = new Emprestimo(novoId, biCliente, valorEmprestado, tipo, numeroPrestacoes, estado,
						dataConcessao, dataVencimento);

				// 3) Persistir
				if (emprestimoService.cadastrarEmprestimo(novo)) {
					carregarTabela();
					JOptionPane.showMessageDialog(this, "Empréstimo gravado com sucesso!");
				} else {
					JOptionPane.showMessageDialog(this, "Falha na gravacao!");
				}

				// 4) Actualizar a tabela (ordem das colunas que definiste)

				jcb_cliente.setSelectedIndex(-1);
				tf_valorEmprestado.setText("");
				if (tipo.equalsIgnoreCase("Prestacao"))
					tf_numeroPrestacoes.setText("");
				jcb_tipo.setSelectedIndex(0);
				jcb_estado.setSelectedIndex(0);
				tf_dataVencimento.setText("");

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Verifique números (valor emprestado/prestações).");
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, "Erro ao gravar: " + ex.getMessage());
			}
		} else if (e.getSource() == jb_actualizar) {
			try {
				// 1) BI do combo
				Object sel = jcb_cliente.getSelectedItem();
				if (sel == null) {
					JOptionPane.showMessageDialog(this, "Seleccione o cliente (BI) no combo.");
					return;
				}
				String biCliente = String.valueOf(sel).trim();
				if (biCliente.isEmpty()) {
					JOptionPane.showMessageDialog(this, "BI do cliente vazio.");
					return;
				}

				// 2) Tipo e nº prestações
				String tipoUI = String.valueOf(jcb_tipo.getSelectedItem());
				String tipo = (tipoUI == null) ? "" : tipoUI.trim();

				Integer numeroPrest = null;
				if ("prestacao".equalsIgnoreCase(tipo) || "prestação".equalsIgnoreCase(tipo)) {
					String txtPrest = tf_numeroPrestacoes.getText().trim();
					if (txtPrest.isEmpty()) {
						JOptionPane.showMessageDialog(this, "Indique o número de prestações.");
						return;
					}
					numeroPrest = Integer.parseInt(txtPrest);
				} else {
					numeroPrest = 1; 
				}

				// 3) Estado
				String estado = String.valueOf(jcb_estado.getSelectedItem());
				if (estado == null || estado.isBlank()) {
					JOptionPane.showMessageDialog(this, "Seleccione o estado.");
					return;
				}

				// 4) Data de vencimento
				java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String txtVenc = tf_dataVencimento.getText().trim();
				java.time.LocalDate dataVenc = null;
				if (!txtVenc.isEmpty()) {
					try {
						dataVenc = java.time.LocalDate.parse(txtVenc, df);
					} catch (java.time.format.DateTimeParseException ex2) {
						JOptionPane.showMessageDialog(this, "Data de vencimento inválida. Use dd/MM/yyyy.");
						return;
					}
				}

				// 5) Valor emprestado (opcional)
				Double novoValorEmp = null;
				String txtVal = tf_valorEmprestado.getText().trim().replace(",", ".");
				if (!txtVal.isEmpty()) {
					novoValorEmp = Double.parseDouble(txtVal);
					if (novoValorEmp <= 0) {
						JOptionPane.showMessageDialog(this, "Valor emprestado deve ser > 0.");
						return;
					}
				}else {
					novoValorEmp= 0.0;
				}

				String idEmprestimo = null;
				String txtEmprestimo = tf_idEmprestimo.getText().trim();
				if (txtEmprestimo.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Indique o IdEmprestimo");
					return;
				}
				idEmprestimo = txtEmprestimo;

				// 6) Chama o service (ATUALIZA POR BI)
				emprestimoService.AtualizarEmprestimo(idEmprestimo,biCliente, tipo.isBlank() ? null : tipo, numeroPrest, novoValorEmp, dataVenc);
				
				// 7) Refresh da tabela (pode usar cache)
				// Se não quiseres bater à BD, obtém a lista do service e repopula:

				carregarTabela();
				JOptionPane.showMessageDialog(this, "Empréstimo actualizado com sucesso!");

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Verifique os números (prestações/valor).");
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, iae.getMessage());
			} catch (Exception exSql) {
				JOptionPane.showMessageDialog(this, "Erro ao actualizar: " + exSql.getMessage());
			}
		} else if (e.getSource() == jcb_tipo) {
			if (jcb_tipo.getSelectedItem().equals("Directo")) {
				tf_numeroPrestacoes.setEnabled(false);
				tf_numeroPrestacoes.setText("1");
			} else {
				tf_numeroPrestacoes.setEnabled(true);
				tf_numeroPrestacoes.setText("");
			}
		} else if (e.getSource() == jb_removerCodigo) {
			String id = tf_id_remocao.getText().trim();

			if (id.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Informe o ID do emprestimo.");
				return;
			}

			try {
				emprestimoService.removerEmprestimoPorId(id);
				carregarTabela(); // refresca a JTable
				JOptionPane.showMessageDialog(this, "Emprestimo removido pelo ID.");
				tf_id_remocao.setText("");
			} catch (IllegalStateException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao remover: " + ex.getMessage());
			}
		} else if (e.getSource() == jb_removerPosicao) {
			try {
				int pos = Integer.parseInt(tf_posicao.getText().trim());
				emprestimoService.removerEmprestimoPorPosicao(pos);
				carregarTabela();
				JOptionPane.showMessageDialog(this, "Emprestimo removido pela posição." + pos);
				tf_posicao.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Posição inválida.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao remover: " + ex.getMessage());
			}
		}
	}

	private void carregarClientesNoCombo() {
		DefaultComboBoxModel<ClienteItem> model = new DefaultComboBoxModel<>();
		ListaDuplamenteLigada listaClientes = null;
		try {
			listaClientes = clienteService.carregarClientesDaBase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (listaClientes != null && listaClientes.tamanho() > 0) {
			// se quiseres mesmo manter temp como ListaDuplamenteLigada:
			ListaDuplamenteLigada temp = new ListaDuplamenteLigada();

			for (int i = 0; i < listaClientes.tamanho(); i++) {
				Object obj = listaClientes.pega(i);
				if (obj == null)
					continue;

				Cliente c = (Cliente) obj; // ajusta se a tua classe tiver outro nome
				String bi = c.getBi();
				String nome = c.getNome();

				if (bi != null && !bi.isBlank() && nome != null) {
					temp.adcionaFim(new ClienteItem(bi.trim(), nome.trim()));
				}
			}

			// Preencher o modelo (sem foreach; usa índice e cast)
			for (int i = 0; i < temp.tamanho(); i++) {
				ClienteItem ci = (ClienteItem) temp.pega(i);
				model.addElement(ci);
			}
		}

		jcb_cliente.setModel(model);
		jcb_cliente.setSelectedIndex(-1);
	}

	static class ClienteItem {
		final String chave; // BI ou outro identificador String
		final String nome;

		ClienteItem(String chave, String nome) {
			this.chave = chave;
			this.nome = nome;
		}

		@Override
		public String toString() {
			return chave;
		}
	}

	private void carregarTabela() {
		try {
			tabelaModelo.setRowCount(0);

			// Chama o service para obter a lista de clientes
			ListaDuplamenteLigada emprestimos = emprestimoService.getListaEmprestimo();
			;

			java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

			for (int i = 0; i < emprestimos.tamanho(); i++) {
				Emprestimo e = (Emprestimo) emprestimos.pega(i);

				tabelaModelo.addRow(new Object[] { e.getIdEmprestimo(), e.getBiCliente(),
						String.format(java.util.Locale.US, "%.2f", e.getValorEmprestado()),
						String.format(java.util.Locale.US, "%.2f", e.getValorAPagar()), e.getTipo(),
						e.getNumeroPrestacoes(), String.format(java.util.Locale.US, "%.2f", e.getTAXA_JURO()),
						e.getEstado(), df.format(e.getDataConcessao()), df.format(e.getDataVencimento()) });
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar emprestimos: " + ex.getMessage());
		}
	}

	class FormatacaoTextField extends JTextField {
		FormatacaoTextField(int columns) {
			super(columns);
			setFont(new Font("Segoe UI", Font.PLAIN, 13));
			setBackground(Color.decode("#F8F9FA"));
			setForeground(Color.decode("#2C3E50"));
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode("#CED4DA"), 1),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		}
	}

	class FormatacaoButton extends JButton {
		FormatacaoButton(String text, Color bgColor) {
			super(text);
			setFont(new Font("Segoe UI", Font.BOLD, 13));
			setBackground(bgColor);
			setForeground(Color.WHITE);
			setFocusPainted(false);
			setBorderPainted(false);
			setContentAreaFilled(true);
			setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			setCursor(new Cursor(Cursor.HAND_CURSOR));

			// Efeito hover
			addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					setBackground(bgColor.darker());
				}

				public void mouseExited(MouseEvent e) {
					setBackground(bgColor);
				}
			});
		}
	}

	class FormatacaoLabel extends JLabel {
		FormatacaoLabel(String text) {
			super(text);
			setFont(new Font("Segoe UI", Font.BOLD, 13));
			setForeground(Color.decode("#2C3E50"));
			setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		}
	}

	class LimiteCaracteresFilter extends DocumentFilter {
		private int maxChars;

		public LimiteCaracteresFilter(int maxChars) {
			this.maxChars = maxChars;
		}

		public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
				throws BadLocationException {
			if ((fb.getDocument().getLength() + string.length()) <= maxChars) {
				super.insertString(fb, offset, string, attr);
			}
		}

		public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
				throws BadLocationException {
			if ((fb.getDocument().getLength() - length + text.length()) <= maxChars) {
				super.replace(fb, offset, length, text, attrs);
			}
		}
	}
}
