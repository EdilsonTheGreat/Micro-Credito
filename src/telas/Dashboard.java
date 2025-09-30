package telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import dao.ClienteDAO;
import dao.EmprestimoDAO;
import dao.PagamentoDAO;
import estrutura.ListaDuplamenteLigada;




public class Dashboard extends JFrame implements ActionListener, MouseListener{

	private JFrame frame;
	private Dimension tamanhoDaTela;
	private JMenuBar menuBar;
	private ImageIcon img;
	private Image resizedImage;
	private JLabel nomeEmpresa, labelImagem, timeLabel;
	private JMenu jm_menu;
	private JButton jb_cliente, jb_emprestimo, jb_relatorio, jb_pagamento, jb_sair;
	private JMenuItem jmi_MenuPaginaIncial, jmi_login, jmi_sair;
	private JPanel painelPrincipal, painelEsquerda, painelDireita, painelDireitaCentral, painelNorteDireita;
	private GridBagConstraints gbc;
	public Dashboard() {

		tamanhoDaTela = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("MozaCredito");
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds((tamanhoDaTela.width / 2) - 570, (tamanhoDaTela.height / 2) - 350, 1200, 700);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.decode("#28fa2b"));

		// MenuBar
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		menuBar.setBorder(new LineBorder(Color.GRAY, 0));

		// MenuItem

		jm_menu = new JMenu("Menu");
		jm_menu.setBorder(new LineBorder(Color.GRAY, 0));
		menuBar.add(jm_menu);
		jmi_MenuPaginaIncial = new JMenuItem("Pagina Inicial");
		jmi_login = new JMenuItem("Login");
		jmi_sair = new JMenuItem("Sair");
		jm_menu.add(jmi_MenuPaginaIncial);
		jm_menu.add(jmi_login);
		jm_menu.add(jmi_sair);
		jmi_login.addActionListener(this);
		jmi_MenuPaginaIncial.addActionListener(this);

		jmi_sair.addActionListener(this);

		// FrameCenter
		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new GridBagLayout());
		this.add("Center", painelPrincipal);

		// Definindo Layout a Esquerda
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		painelEsquerda = new JPanel();
		painelEsquerda.setBackground(Color.decode("#3ead40"));
		painelEsquerda.setLayout(null);
		painelEsquerda.setPreferredSize(new Dimension(45, 245)); // Define o tamanho fixo
		painelEsquerda.setMinimumSize(new Dimension(45, 245));
		painelEsquerda.setMaximumSize(new Dimension(45, 245));

		// Butoes

		// Clientes
		img = new ImageIcon(
				"C:\\Users\\rushi\\OneDrive\\Documents\\Workspace - Rushil\\Projecto\\src\\projecto_Novo\\cliente.png");
		resizedImage = img.getImage();
		resizedImage = resizedImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		img = new ImageIcon(resizedImage);
		jb_cliente = new JButton(" Clientes");
		jb_cliente.setIcon(img);
		jb_cliente.setForeground(Color.WHITE);
		jb_cliente.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 3));
		jb_cliente.setHorizontalAlignment(SwingConstants.LEFT);
		jb_cliente.setFont(new Font("Dialog", Font.BOLD, 16));
		jb_cliente.setFocusPainted(false);
		jb_cliente.setBounds(0, 0, 280, 65);
		jb_cliente.setBorder(new LineBorder(Color.GRAY, 0));
		jb_cliente.setBackground(Color.decode("#3ead40"));
		jb_cliente.setBorder(new EmptyBorder(5, 20, 5, 15));
		jb_cliente.addMouseListener(this);
		jb_cliente.addActionListener(this);
		painelEsquerda.add(jb_cliente);

		// Emprestimo
		img = new ImageIcon(
				"C:\\Users\\rushi\\OneDrive\\Documents\\Workspace - Rushil\\MicroCredito\\src\\telas\\loan.png");
		resizedImage = img.getImage();
		resizedImage = resizedImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		img = new ImageIcon(resizedImage);
		jb_emprestimo = new JButton("Emprestimo");
		jb_emprestimo.setIcon(img);
		jb_emprestimo.setForeground(Color.WHITE);
		jb_emprestimo.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 3));
		jb_emprestimo.setHorizontalAlignment(SwingConstants.LEFT);
		jb_emprestimo.setFont(new Font("Dialog", Font.BOLD, 16));
		jb_emprestimo.setFocusPainted(false);
		jb_emprestimo.setBounds(0, 65, 280, 65);// w=50
		jb_emprestimo.setBorder(new LineBorder(Color.GRAY, 0));
		jb_emprestimo.setBackground(Color.decode("#3ead40"));
		jb_emprestimo.setBorder(new EmptyBorder(5, 20, 5, 15));
		jb_emprestimo.addMouseListener(this);
		jb_emprestimo.addActionListener(this);
		painelEsquerda.add(jb_emprestimo);

		// Relatorio
		img = new ImageIcon(
				"C:\\Users\\rushi\\OneDrive\\Documents\\Workspace - Rushil\\MicroCredito\\src\\telas\\report.png");
		resizedImage = img.getImage();
		resizedImage = resizedImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		img = new ImageIcon(resizedImage);
		jb_relatorio = new JButton(" Relatorio");
		jb_relatorio.setIcon(img);
		jb_relatorio.setForeground(Color.WHITE);
		jb_relatorio.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 3));
		jb_relatorio.setHorizontalAlignment(SwingConstants.LEFT);
		jb_relatorio.setFont(new Font("Dialog", Font.BOLD, 16));
		jb_relatorio.setFocusPainted(false);
		jb_relatorio.setBounds(0, 130, 280, 65);
		jb_relatorio.setBorder(new LineBorder(Color.GRAY, 0));
		jb_relatorio.setBackground(Color.decode("#3ead40"));
		jb_relatorio.setBorder(new EmptyBorder(5, 20, 5, 15));
		jb_relatorio.addMouseListener(this);
		jb_relatorio.addActionListener(this);
		painelEsquerda.add(jb_relatorio);

		// Pagamento
		img = new ImageIcon(
				"C:\\Users\\rushi\\OneDrive\\Documents\\Workspace - Rushil\\MicroCredito\\src\\telas\\pay.png");
		resizedImage = img.getImage();
		resizedImage = resizedImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		img = new ImageIcon(resizedImage);
		jb_pagamento = new JButton("Pagamento");
		jb_pagamento.setIcon(img);
		jb_pagamento.setForeground(Color.WHITE);
		jb_pagamento.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 3));
		jb_pagamento.setHorizontalAlignment(SwingConstants.LEFT);
		jb_pagamento.setFont(new Font("Dialog", Font.BOLD, 16));
		jb_pagamento.setBounds(0, 195, 280, 65);
		jb_pagamento.setFocusPainted(false);
		jb_pagamento.setBorder(new LineBorder(Color.GRAY, 0));
		jb_pagamento.setBackground(Color.decode("#3ead40"));
		jb_pagamento.setBorder(new EmptyBorder(5, 20, 5, 15));
		jb_pagamento.addMouseListener(this);
		jb_pagamento.addActionListener(this);
		painelEsquerda.add(jb_pagamento);


		// Sair
		img = new ImageIcon(
				"C:\\Users\\rushi\\OneDrive\\Documents\\Workspace - Rushil\\Projecto\\src\\projecto_Novo\\deixar.png");
		resizedImage = img.getImage();
		resizedImage = resizedImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		img = new ImageIcon(resizedImage);
		jb_sair = new JButton("Sair");
		jb_sair.setIcon(img);
		jb_sair.setForeground(Color.WHITE);
		jb_sair.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 3));
		jb_sair.setHorizontalAlignment(SwingConstants.LEFT);
		jb_sair.setFont(new Font("Dialog", Font.BOLD, 16));
		jb_sair.setBounds(0, 600, 280, 45);
		jb_sair.setFocusPainted(false);
		jb_sair.setBorder(new LineBorder(Color.GRAY, 0));
		jb_sair.setBackground(Color.decode("#3ead40"));
		jb_sair.setBorder(new EmptyBorder(5, 20, 5, 15));
		jb_sair.addMouseListener(this);
		jb_sair.addActionListener(this);
		painelEsquerda.add(jb_sair);

		// Fim
		painelPrincipal.add(painelEsquerda, gbc);

		// Definindo Layout a Direita
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 3.7;
		painelDireita = new JPanel(new BorderLayout());
		painelPrincipal.add(painelDireita, gbc);

		// FrameNorth
		painelNorteDireita = new JPanel();
		painelNorteDireita.setLayout(new BorderLayout());

		// NomeEmpresa
		img = new ImageIcon(
				"C:\\\\Users\\\\rushi\\\\OneDrive\\\\Documents\\\\Workspace - Rushil\\\\MicroCredito\\\\src\\\\telas\\\\wallet.png");
		this.setIconImage(img.getImage());
		resizedImage = img.getImage();
		resizedImage = resizedImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		img = new ImageIcon(resizedImage);
		timeLabel = new JLabel("");
		timeLabel.setBorder(BorderFactory.createEmptyBorder(7, 0, 7, 0));
		nomeEmpresa = new JLabel("MozaCredito", img, SwingConstants.CENTER);
		nomeEmpresa.setForeground(Color.BLACK);
		nomeEmpresa.setFont(new Font("Segoe UI", Font.BOLD, 24));
		nomeEmpresa.setBorder(BorderFactory.createEmptyBorder(7, 50, 7, 0));
		painelNorteDireita.add("West", nomeEmpresa);
		painelNorteDireita.add("East", timeLabel);
		painelDireita.add(painelNorteDireita, BorderLayout.NORTH);

		// Painel a Direita Centralizado

		painelDireitaCentral = new JPanel();
		painelDireitaCentral.setLayout(new BorderLayout());
		painelDireitaCentral.setBackground(Color.decode("#FFFFFF"));
		labelImagem = new JLabel("Bem Vindo de Volta", SwingConstants.CENTER);
		labelImagem.setFont(new Font("Segoe UI", Font.BOLD, 24));
		painelDireitaCentral.add("North", labelImagem);
		img = new ImageIcon(
				"C:\\Users\\rushi\\OneDrive\\Documents\\Workspace - Rushil\\MicroCredito\\src\\telas\\money.gif");
		Image gifImage = img.getImage().getScaledInstance(550, 550, Image.SCALE_DEFAULT);
		ImageIcon resizedGifIcon = new ImageIcon(gifImage);
		labelImagem = new JLabel(resizedGifIcon);
		labelImagem.setBorder(null);
		painelDireitaCentral.add("Center", labelImagem);
		painelDireita.add("Center", painelDireitaCentral);


	    this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb_cliente) {
			resetarCoresBotoes();
			   try {
			        ClienteDAO dao = new ClienteDAO(); // lança SQLException
			        ListaDuplamenteLigada lista_cliente = new ListaDuplamenteLigada();
			        service.ClienteService clienteService = new service.ClienteService(dao, lista_cliente); // também pode lançar

			        EmprestimoDAO empDao = new EmprestimoDAO();
			        ListaDuplamenteLigada lista_emprestimo = new ListaDuplamenteLigada();
			        service.EmprestimoService empService = new service.EmprestimoService(empDao, lista_emprestimo, clienteService);
			        clienteService.setEmpService(empService);
			        
			        
			        ClienteTela clienteTela = new ClienteTela(clienteService);
			        actualizarPainel(clienteTela);  

			        actualizarMouseListeners(jb_cliente);
			        jb_cliente.setBackground(Color.decode("#F3F7EC"));
			        jb_cliente.setForeground(Color.BLACK);
			    } catch (Exception ex) {
			        ex.printStackTrace(); 
			        javax.swing.JOptionPane.showMessageDialog(
			            this,
			            "Não foi possível abrir Clientes: " + ex.getMessage(),
			            "Erro",
			            javax.swing.JOptionPane.ERROR_MESSAGE
			        );
			    }
		} else if (e.getSource() == jb_emprestimo) {
			resetarCoresBotoes();
			try {
		        ClienteDAO dao = new ClienteDAO(); // lança SQLException
		        ListaDuplamenteLigada lista_cliente = new ListaDuplamenteLigada();
		        service.ClienteService clienteService = new service.ClienteService(dao, lista_cliente); 

		        EmprestimoDAO empDao = new EmprestimoDAO();
		        ListaDuplamenteLigada lista_emprestimo = new ListaDuplamenteLigada();
		        service.EmprestimoService empService = new service.EmprestimoService(empDao, lista_emprestimo, clienteService);

		        
		        EmprestimoTela emprestimoTela = new EmprestimoTela(empService, clienteService);
		        actualizarPainel(emprestimoTela);  

		        actualizarMouseListeners(jb_emprestimo);
				jb_emprestimo.setBackground(Color.decode("#F3F7EC"));
				jb_emprestimo.setForeground(Color.BLACK);
		    } catch (Exception ex) {
		        ex.printStackTrace(); 
		        javax.swing.JOptionPane.showMessageDialog(
		            this,
		            "Não foi possível abrir Emprestimos: " + ex.getMessage(),
		            "Erro",
		            javax.swing.JOptionPane.ERROR_MESSAGE
		        );
		    }
		} else if (e.getSource() == jb_relatorio) {
			resetarCoresBotoes();
			actualizarPainel(new Relatorio());
			actualizarMouseListeners(jb_relatorio);
			jb_relatorio.setBackground(Color.decode("#F3F7EC"));
			jb_relatorio.setForeground(Color.BLACK);
		} else if (e.getSource() == jb_pagamento) {
			resetarCoresBotoes();
			try {
		    	EmprestimoDAO empDao = new EmprestimoDAO();
		 		ClienteDAO dao = new ClienteDAO();
		        PagamentoDAO pagdao = new PagamentoDAO();
		        ListaDuplamenteLigada lista_emprestimo = new ListaDuplamenteLigada();
		        ListaDuplamenteLigada lista_cliente = new ListaDuplamenteLigada();
		        ListaDuplamenteLigada lista_pagamento = new ListaDuplamenteLigada();
		        
		        service.ClienteService clienteService = new service.ClienteService(dao, lista_cliente); 
		        service.EmprestimoService empService = new service.EmprestimoService(empDao, lista_emprestimo, clienteService);
		        service.PagamentoService pagService = new service.PagamentoService(pagdao, lista_pagamento, empService, empDao, clienteService);
		        
		        PagamentoTela pagamentoTela = new PagamentoTela(pagService, empService);
		        actualizarPainel(pagamentoTela);  

		        actualizarMouseListeners(jb_pagamento);
		        jb_pagamento.setBackground(Color.decode("#F3F7EC"));
		        jb_pagamento.setForeground(Color.BLACK);
		    } catch (Exception ex) {
		        ex.printStackTrace(); 
		        javax.swing.JOptionPane.showMessageDialog(
		            this,
		            "Não foi possível abrir Clientes: " + ex.getMessage(),
		            "Erro",
		            javax.swing.JOptionPane.ERROR_MESSAGE
		        );
		    }
		} else if (e.getSource() == jb_sair || e.getSource() == jmi_sair) {
			System.exit(0);
		} else if (e.getSource() == jmi_login) {
			this.dispose();
			new Login();
		} else if (e.getSource() == jmi_MenuPaginaIncial) {
			this.dispose();
			new Dashboard();
		}
		
}
	
	private void resetarCoresBotoes() {
		JButton[] botoes = { jb_cliente, jb_emprestimo, jb_relatorio, jb_pagamento, jb_sair };
		for (JButton botao : botoes) {
			botao.setBackground(Color.decode("#3ead40"));
			botao.setForeground(Color.WHITE);
		}
	}

	
	private void actualizarPainel(JPanel novoPainel) {

		painelDireita.remove(painelDireitaCentral);
		painelDireitaCentral = novoPainel;
		painelDireitaCentral.setBackground(Color.decode("#F3F7EC"));
		painelDireitaCentral.setPreferredSize(
				new Dimension((int) (painelPrincipal.getWidth() * 0.65), painelPrincipal.getHeight()));
		painelDireita.add(painelDireitaCentral, BorderLayout.CENTER);
		painelDireita.revalidate();
		painelDireita.repaint();

	}
	
	private void actualizarMouseListeners(JButton botaoAtivo) {
		JButton[] botoes = { jb_cliente, jb_emprestimo, jb_relatorio, jb_pagamento, jb_sair };
		for (JButton botao : botoes) {
			if (botao == botaoAtivo) {
				botao.removeMouseListener(this);
			} else {
				botao.addMouseListener(this);
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == jb_sair) {
			jb_sair.setBackground(Color.decode("#ff3333"));
		} else {
			JButton botao = (JButton) e.getSource();
			botao.setBackground(Color.decode("#3ead40").brighter());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton botao = (JButton) e.getSource();
		if (botao.getBackground().equals(Color.decode("#F3F7EC"))) {
			return;
		}
		botao.setBackground(Color.decode("#3ead40"));
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}