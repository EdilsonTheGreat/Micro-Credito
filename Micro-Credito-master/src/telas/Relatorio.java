package telas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import estrutura.ListaDuplamenteLigada;
import models.Cliente;
import service.EmprestimoService;


public class Relatorio extends JPanel implements ActionListener {

	private final service.ClienteService clienteService;
	private final service.EmprestimoService emprestimoService;
	
	private JLabel jl_titulo,jl_jp1, jl_jp2, jl_jp3,jl_clienteBi ,jl_idEmprestimo, jl_bi, jl_clienteApelido, jl_clienteTelefone, jl_emprestimoEstado, jl_emprestimoData, jl_filtro;
	private JTextField tf_id, tf_clienteBi, tf_nome, tf_clienteApelido, tf_clienteTelefone, tf_emprestimoId, tf_emprestimoData;
	private JButton jb_clienteBi, jb_cliente2atribuitos, jb_emprestimoId, jb_emprestimoDataEstado;
	private JPanel jpc, jp1, jp2,jpCliente, jpEmprestimo, jpCliente1, jpEmprestimo1, jpCliente2,jpEmprestimo2;
	private JScrollPane jsp;
	private JComboBox<String> coBox, jcb_coEstado;
	private DefaultTableModel tabelaModelo;
	private JTable jt;
	
	

	public Relatorio(service.ClienteService clienteService, service.EmprestimoService emprestimoService) {

		this.clienteService = clienteService;
		this.emprestimoService = emprestimoService;
	this.setLayout(new BorderLayout());
	this.setPreferredSize(new Dimension(600, 600));
	this.setMinimumSize(new Dimension(600, 600));
	this.setMaximumSize(new Dimension(600, 600));


		jl_titulo = new JLabel("Relatorio", SwingConstants.CENTER);
	jl_titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
	jl_titulo.setForeground(Color.decode("#2C3E50"));
	jl_titulo.setBorder(
			BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.decode("#7d9af4")),
					BorderFactory.createEmptyBorder(7, 0, 7, 0)));
	this.add("North", jl_titulo);

	jpc = new JPanel();
	jpc.setBackground(Color.decode("#F3F7EC"));
	jpc.setLayout(new BorderLayout());

	//ComboBOX
		String[] opcoes = {"Selecione uma opção", "Clientes", "Emprestimos"};
		coBox = new JComboBox<>(opcoes);
		coBox.addActionListener(this);

	// inicio bloco 1
	jp1 = new JPanel();
	jp1.setBackground(Color.decode("#F3F7EC"));
	jp1.setLayout(new GridLayout(1,5));
	jl_filtro = new JLabel("Filtro:");
	jl_filtro.setFont(new Font("Segoe UI", Font.BOLD, 14));
	jp1.add(jl_filtro);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.decode("#7d9af4"), 1), "Pesquisa");
		titledBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
		jp1.setBorder(titledBorder);
		jp1.add(coBox);
	JLabel vazio = new JLabel("             ");
	jp1.add(vazio);
	jp1.add(vazio);
	// inicio bloco 2
	jp2 = new JPanel();
	jp2.setBackground(Color.decode("#F3F7EC"));
	jp2.setLayout(new BorderLayout());
	//painel1

	//painel2
		jpc.add("North",jp1);
		jpc.add("Center",jp2);

		this.add("Center",jpc);
		this.setVisible(true);


}
	public void actionPerformed(ActionEvent e){
		String opcaoSelecionada = (String) coBox.getSelectedItem();

		switch(opcaoSelecionada) {
			case "Clientes":
				acaoOpcaoClientes();
				break;
			case "Emprestimos":
				acaoOpcaoEmprestimos();
				break;
		}
	}

	//JanelasParaCadaOpcao
	private void acaoOpcaoClientes() {
		RelatorioCliente relatorioCliente = new RelatorioCliente(clienteService);
		atualizarTela(relatorioCliente);
	}

	private void acaoOpcaoEmprestimos() {
		RelatorioEmprestimo relatorioEmprestimo = new RelatorioEmprestimo(clienteService, emprestimoService);
		atualizarTela(relatorioEmprestimo);
	}


	private void carregarTabela() {
		try {
			tabelaModelo.setRowCount(0);

			// Chama o service para obter a lista de clientes
			ListaDuplamenteLigada clientes = clienteService.getListaCliente();

			for (int i = 0; i < clientes.tamanho(); i++) {
				Cliente c = (Cliente) clientes.pega(i);
				tabelaModelo.addRow(new Object[] { c.getId(), c.getBi(), c.getNome(), c.getApelido(), c.getTelefone(),
						c.getEndereco(), c.getDataCadastro() });
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage());
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
	private void atualizarTela(JPanel painel) {
		// Limpa o painel principal
		jp2.removeAll();

		// Define o layout do jp2 como BorderLayout para melhor organização
		jp2.setLayout(new BorderLayout());

		// Adiciona o painel recebido como parâmetro
		jp2.add(painel, BorderLayout.CENTER);

		// Atualiza a interface
		jp2.revalidate();
		jp2.repaint();
	}
}
