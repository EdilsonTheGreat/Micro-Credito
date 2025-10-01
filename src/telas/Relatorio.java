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
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import telas.ClienteTela.FormatacaoButton;
import telas.ClienteTela.FormatacaoLabel;
import telas.ClienteTela.FormatacaoTextField;
import telas.ClienteTela.LimiteCaracteresFilter;

public class Relatorio extends JPanel implements ActionListener {

	private final service.ClienteService clienteService;
	
	private JLabel jl_vazio, jl_titulo, jl_id, jl_bi, jl_nome, jl_apelido, jl_telefone, jl_endereco, jl_dataCadastro,
			jl_posicao;
	private JTextField tf_id, tf_bi, tf_nome, tf_apelido, tf_telefone, tf_endereco, tf_dataCadastro, tf_posicao,
			tf_bi_remocao;
	private JButton jb_actualizar, jb_gravar;
	private JPanel jpc, jp1, jp2;
	private JScrollPane jsp;
	private DefaultTableModel tabelaModelo;
	private JTable jt;
	
	

	public Relatorio(service.ClienteService clienteService) {
		
	
		this.clienteService = clienteService;
	this.setLayout(new BorderLayout());
	this.setPreferredSize(new Dimension(600, 600));
	this.setMinimumSize(new Dimension(600, 600));
	this.setMaximumSize(new Dimension(600, 600));

	jl_titulo = new JLabel("Clientes", SwingConstants.CENTER);
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

	jl_id = new FormatacaoLabel("Id: ");
	jp2.add(jl_id);

	tf_id = new FormatacaoTextField(3);
	((AbstractDocument) tf_id.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(10));
	jp2.add(tf_id);

	jl_bi = new FormatacaoLabel("Bi: ");
	jp2.add(jl_bi);

	tf_bi = new FormatacaoTextField(3);
	((AbstractDocument) tf_bi.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(10));
	jp2.add(tf_bi);

	jl_nome = new FormatacaoLabel("Nome: ");
	jp2.add(jl_nome);

	tf_nome = new FormatacaoTextField(3);
	((AbstractDocument) tf_nome.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(20));
	jp2.add(tf_nome);

	jl_apelido = new FormatacaoLabel("Apelido: ");
	jp2.add(jl_apelido);

	tf_apelido = new FormatacaoTextField(3);
	((AbstractDocument) tf_apelido.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(20));
	jp2.add(tf_apelido);

	jl_telefone = new FormatacaoLabel("Telefone: ");
	jp2.add(jl_telefone);

	tf_telefone = new FormatacaoTextField(3);
	((AbstractDocument) tf_telefone.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(20));
	jp2.add(tf_telefone);

	jl_endereco = new FormatacaoLabel("Endereco: ");
	jp2.add(jl_endereco);

	tf_endereco = new FormatacaoTextField(3);
	((AbstractDocument) tf_endereco.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(20));
	jp2.add(tf_endereco);

	jl_vazio = new FormatacaoLabel(" ");
	jp2.add(jl_vazio);
	
	jl_vazio = new FormatacaoLabel(" ");
	jp2.add(jl_vazio);
	
	

	jb_actualizar = new FormatacaoButton("Actualizar", Color.decode("#3ead40"));
	jp2.add(jb_actualizar);
	jb_actualizar.addActionListener(this);

	
	jb_gravar = new FormatacaoButton("Gravar", Color.decode("#3ead40"));
	jp2.add(jb_gravar);
	jb_gravar.addActionListener(this);
	


	jp1.add("Center", jp2);
	TitledBorder titledBorder = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.decode("#7d9af4"), 1), "Informações dos Clientes");
	titledBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
	jp1.setBorder(titledBorder);



	jpc.add("North", jp1);

	// fim bloco 1

	// inicio bloco 2

	String[] nomeColunas = { "Id", "BI", "Nome", "Apelido", "Telefone", "Endereco", "DataCadastro" };

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

	// Definir larguras preferidas das colunas
	jt.getColumnModel().getColumn(0).setPreferredWidth(80);
	jt.getColumnModel().getColumn(1).setPreferredWidth(100);
	jt.getColumnModel().getColumn(2).setPreferredWidth(100);
	jt.getColumnModel().getColumn(3).setPreferredWidth(90);
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
	carregarTabela();
	this.setVisible(true);


}


	private void carregarTabela() {
		try {
			tabelaModelo.setRowCount(0);

			// Chama o service para obter a lista de clientes
			ListaDuplamenteLigada clientes = clienteService.carregarClientesDaBase();

			for (int i = 0; i < clientes.tamanho(); i++) {
				Cliente c = (Cliente) clientes.pega(i);
				tabelaModelo.addRow(new Object[] { c.getId(), c.getBi(), c.getNome(), c.getApelido(), c.getTelefone(),
						c.getEndereco(), c.getDataCadastro() });
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
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
