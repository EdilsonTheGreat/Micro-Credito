package telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

import estrutura.ListaDuplamenteLigada;
import models.Emprestimo;
import models.Pagamento;
import service.EmprestimoService;
import service.PagamentoService;
import telas.EmprestimoTela.ClienteItem;

public class PagamentoTela extends JPanel implements ActionListener {

	private JLabel jl_vazio, jl_titulo, jl_idEmprestimo, jl_valorPago, jl_numeroParcela, jl_valorPagoSugerido, jl_numeroParcelaSugerido;
	private JComboBox<EmprestimoItem> jcb_emprestimo;
	private JButton jb_gravar;
	private JPanel jpc, jp1, jp2;
	private JScrollPane jsp;
	private DefaultTableModel tabelaModelo;
	private JTable jt;
	private EmprestimoService emprestimoService;
	private PagamentoService pagamentoService;

	public PagamentoTela(service.PagamentoService pagamentoService, service.EmprestimoService emprestimoService) {
		this.pagamentoService = pagamentoService;
		this.emprestimoService = emprestimoService;

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
		jp2.setLayout(new GridLayout(3, 4, 1, 1));

		jl_idEmprestimo = new FormatacaoLabel("IdEmprestimo: ");
		jp2.add(jl_idEmprestimo);

		jcb_emprestimo = new JComboBox();
		jcb_emprestimo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		jcb_emprestimo.setBackground(Color.decode("#F8F9FA"));
		jcb_emprestimo.setForeground(Color.decode("#2C3E50"));
		jcb_emprestimo.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode("#CED4DA"), 1),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		jp2.add(jcb_emprestimo);

		jl_valorPago = new FormatacaoLabel("Valor Pago: ");
		jp2.add(jl_valorPago);

		jl_valorPagoSugerido = new FormatacaoLabel("-");
		jp2.add(jl_valorPagoSugerido);

		jl_numeroParcela = new FormatacaoLabel("Numero Parcela: ");
		jp2.add(jl_numeroParcela);

		jl_numeroParcelaSugerido = new FormatacaoLabel("-");
		jp2.add(jl_numeroParcelaSugerido);

		jl_vazio = new FormatacaoLabel("");
		jp2.add(jl_vazio);

		jl_vazio = new FormatacaoLabel("");
		jp2.add(jl_vazio);

		jl_vazio = new FormatacaoLabel("");
		jp2.add(jl_vazio);

		jl_vazio = new FormatacaoLabel("");
		jp2.add(jl_vazio);

		jl_vazio = new FormatacaoLabel("");
		jp2.add(jl_vazio);

		jb_gravar = new FormatacaoButton("Gravar", Color.decode("#3ead40"));
		jp2.add(jb_gravar);
		jb_gravar.addActionListener(this);

		jp1.add("Center", jp2);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.decode("#7d9af4"), 1), "Informações dos Pagamentos");
		titledBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
		jp1.setBorder(titledBorder);

		jpc.add("North", jp1);

		// fim bloco 1

		// inicio bloco 2

		String[] nomeColunas = { "Id", "IdEmprestimo", "ValorPago", "DataPagamento", "Numero Parcela",
				"Juro Aplicado" };

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

		// Definir larguras preferidas das colunas
		jt.getColumnModel().getColumn(0).setPreferredWidth(80);
		jt.getColumnModel().getColumn(1).setPreferredWidth(100);
		jt.getColumnModel().getColumn(2).setPreferredWidth(100);
		jt.getColumnModel().getColumn(2).setPreferredWidth(90);
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
		
		carregarEmprestimosNoCombo();
		inicializarSugestoesAuto();
		carregarTabela();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jb_gravar) {
			 try {
			        EmprestimoItem item = (EmprestimoItem) jcb_emprestimo.getSelectedItem();
			        if (item == null) {
			            JOptionPane.showMessageDialog(this, "Seleccione um Id de Empréstimo.", "Aviso", JOptionPane.WARNING_MESSAGE);
			            return;
			        }
			        String idEmprestimo = item.chave;

			        String txtValor = jl_valorPagoSugerido.getText();
			        if (txtValor == null || txtValor.trim().isEmpty() || "-".equals(txtValor.trim())) {
			            JOptionPane.showMessageDialog(this, "Valor sugerido indisponível.", "Aviso", JOptionPane.WARNING_MESSAGE);
			            return;
			        }
			        double valorPago = Double.parseDouble(txtValor.trim().replace(" ", "").replace(",", "."));
			        if (valorPago <= 0) {
			            JOptionPane.showMessageDialog(this, "O valor pago deve ser positivo.", "Aviso", JOptionPane.WARNING_MESSAGE);
			            return;
			        }

			        // Diagnóstico: confirma mínimo
			        int proximaParcela = pagamentoService.calcularProximaParcela(idEmprestimo);

			        Emprestimo emp = emprestimoService.buscarEmprestimoPorId(idEmprestimo);
			        double valorSemAtraso = emp.getTipo().equalsIgnoreCase("prestacao")
			                ? emp.getValorAPagar() / emp.getNumeroPrestacoes()
			                : emp.getValorAPagar();
			        double juroAtraso = LocalDate.now().isAfter(emp.getDataVencimento()) ? valorSemAtraso * 0.10 : 0.0;
			        double valorMinimo = valorSemAtraso + juroAtraso;

			        if (valorPago < valorMinimo) {
			            JOptionPane.showMessageDialog(this,
			                String.format(Locale.US,
			                    "Valor insuficiente.\nMínimo: %.2f (Sem atraso: %.2f, Juro atraso: %.2f)",
			                    valorMinimo, valorSemAtraso, juroAtraso),
			                "Aviso", JOptionPane.WARNING_MESSAGE);
			            return;
			        }

			        boolean sucesso = pagamentoService.registrarPagamento(idEmprestimo, valorPago);

			        if (sucesso) {
			            JOptionPane.showMessageDialog(this, "Pagamento registado com sucesso!",
			                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

			            // Limpa labels
			            jl_valorPagoSugerido.setText("-");
			            jl_numeroParcelaSugerido.setText("-");

			            // Recarrega do service e repinta a tabela (no EDT)
			            try {
			                pagamentoService.carregarPagamentosDaBase();
			                SwingUtilities.invokeLater(this::carregarTabela);
			            } catch (SQLException ex) {
			                JOptionPane.showMessageDialog(this, "Erro ao recarregar pagamentos: " + ex.getMessage(),
			                        "Erro", JOptionPane.ERROR_MESSAGE);
			            }

			            // Recalcular sugestões para o empréstimo seleccionado
			            preencherSugestoesPagamento();
			        } else {
			            JOptionPane.showMessageDialog(this,
			                    "Não foi possível registar o pagamento.\nVerifique o valor mínimo exigido (juros/atraso).",
			                    "Aviso", JOptionPane.WARNING_MESSAGE);
			        }

			    } catch (NumberFormatException ex) {
			        JOptionPane.showMessageDialog(this, "Formato numérico inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
			        ex.printStackTrace();
			    } catch (SQLException ex) {
			        JOptionPane.showMessageDialog(this, "Erro de base de dados: " + ex.getMessage(), "Erro",
			                JOptionPane.ERROR_MESSAGE);
			        ex.printStackTrace();
			    } catch (Exception ex) {
			      JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			      ex.printStackTrace();
			    }
		}
	}

	private void carregarEmprestimosNoCombo() {
		DefaultComboBoxModel<EmprestimoItem> model = new DefaultComboBoxModel<>();
		ListaDuplamenteLigada listaEmprestimos = null;

		try {
			// Deve devolver todos os empréstimos; se tiveres um método só dos abertos,
			// usa-o
			listaEmprestimos = emprestimoService.carregarEmprestimoDaBase();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (listaEmprestimos != null && listaEmprestimos.tamanho() > 0) {
			// temporário, mantendo o teu tipo de lista
			ListaDuplamenteLigada temp = new ListaDuplamenteLigada();

			for (int i = 0; i < listaEmprestimos.tamanho(); i++) {
				Object obj = listaEmprestimos.pega(i);
				if (obj == null)
					continue;

				Emprestimo emp = (Emprestimo) obj;

				String id = emp.getIdEmprestimo();
				String estado = emp.getEstado(); // opcional: filtrar
				String biCliente = null;
				try {
					// Se tiveres o nome do cliente associado ao empréstimo, usa-o
					// Ex.: nomeCliente = emp.getCliente().getNome();
					// ou se só tiveres BI: clienteService.buscarPorBi(emp.getBi()).getNome()
					biCliente = emp.getBiCliente(); // ajusta conforme o teu modelo
				} catch (Exception ignore) {
				}

				// Exemplo de filtro: só listar não liquidados
				if (estado != null && estado.equalsIgnoreCase("liquidado")) {
					continue;
				}

				if (id != null && !id.isBlank()) {

					String label = (biCliente != null && !biCliente.isBlank()) ? id.trim() + " | " + biCliente.trim()
							: id.trim();
					temp.adcionaFim(new EmprestimoItem(id.trim(), label));
				}
			}

			// Preencher o modelo
			for (int i = 0; i < temp.tamanho(); i++) {
				EmprestimoItem ei = (EmprestimoItem) temp.pega(i);
				model.addElement(ei);
			}
		}

		jcb_emprestimo.setModel(model);
		jcb_emprestimo.setSelectedIndex(-1);
	}

	// Item para o combo de empréstimos
	static class EmprestimoItem {
		final String chave; // idEmprestimo
		final String label; // texto mostrado no combo (ex.: "EMP-001 | João")

		EmprestimoItem(String chave, String label) {
			this.chave = chave;
			this.label = label;
		}

		@Override
		public String toString() {
			return label != null ? label : chave;
		}
	}
	
	private void inicializarSugestoesAuto() {
	    jcb_emprestimo.addItemListener(e -> {
	        if (e.getStateChange() == ItemEvent.SELECTED) {
	            preencherSugestoesPagamento();
	        }
	    });
	    // Força cálculo inicial se já houver itens
	    if (jcb_emprestimo.getItemCount() > 0) {
	        preencherSugestoesPagamento();
	    }
	}

	private void preencherSugestoesPagamento() {
	    try {
	        EmprestimoItem sel = (EmprestimoItem) jcb_emprestimo.getSelectedItem();
	        if (sel == null) return;

	        String idEmprestimo = sel.chave;
	        Emprestimo emp = emprestimoService.buscarEmprestimoPorId(idEmprestimo);

	        int proximaParcela = pagamentoService.calcularProximaParcela(emp.getIdEmprestimo());

	        double valorSemAtraso = emp.getTipo().equalsIgnoreCase("prestacao")
	                ? emp.getValorAPagar() / emp.getNumeroPrestacoes()
	                : emp.getValorAPagar();

	        double juroAtraso = LocalDate.now().isAfter(emp.getDataVencimento())
	                ? valorSemAtraso * 0.10
	                : 0.0;

	        double valorMinimo = valorSemAtraso + juroAtraso;

	        jl_numeroParcelaSugerido.setText(String.valueOf(proximaParcela));
	        jl_valorPagoSugerido.setText(String.format(Locale.US, "%.2f", valorMinimo));
	        jl_valorPagoSugerido.setToolTipText(String.format(Locale.US,
	                "Sem atraso: %.2f | Juro atraso: %.2f | Mínimo: %.2f",
	                valorSemAtraso, juroAtraso, valorMinimo));

	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	private void carregarTabela() {
		tabelaModelo.setRowCount(0);
	    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yy");

	    ListaDuplamenteLigada lista = null;
		try {
			lista = pagamentoService.carregarPagamentosDaBase();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	    for (int i = 0; i < lista.tamanho(); i++) {
	        Pagamento p = (Pagamento) lista.pega(i);
	        tabelaModelo.addRow(new Object[]{
	            p.getIdPagamento(),                                   // "Id"
	            p.getIdEmprestimo(),                                  // "IdEmprestimo"
	            String.format(Locale.US, "%.2f", p.getValorPago()),   // "ValorPago"
	            p.getDataPagamento() != null ? p.getDataPagamento().format(fmt) : "", // "DataPagamento"
	            p.getNumeroParcela(),                                 // "Numero Parcela"
	            String.format(Locale.US, "%.2f", p.getJuroAplicado()) // "Juro Aplicado"
	        });
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
