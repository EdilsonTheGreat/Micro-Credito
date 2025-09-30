package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;



public class Login extends JFrame implements ActionListener {

	private Dimension tamanhoDaTela;
	private JPanel painel_imagem, painel_Login;
	private JLabel label_imagem, label_BemVindo, usuario, palavraChave, label_slogan, label_esqueceuCredenciais;
	private JTextField campo_usuario;
	private JPasswordField campo_palavraChave;
	private JButton butao_entrar, butao_recuperar;
	private ImageIcon imagem, resizedIcon, resizedGifIcon;
	private Image originalImage, resizedImage, gifImage;

	public Login() {
		// Frame

		tamanhoDaTela = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("Login");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds((tamanhoDaTela.width / 2) - 250, (tamanhoDaTela.height / 2) - 250, 600, 420);
		this.setLayout(new GridLayout(1, 2));

		ImageIcon img = new ImageIcon(
				"C:\\Users\\rushi\\OneDrive\\Documents\\Workspace - Rushil\\MicroCredito\\src\\telas\\user.png");
		this.setIconImage(img.getImage());

		// PainelEsquerdo
		// Slogan
		painel_imagem = new JPanel();
		painel_imagem.setBackground(Color.decode("#FFFFFF"));
		painel_imagem.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Animacao

		imagem = new ImageIcon(
				"C:\\Users\\rushi\\OneDrive\\Documents\\Workspace - Rushil\\MicroCredito\\src\\telas\\money.gif");
		gifImage = imagem.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
		resizedGifIcon = new ImageIcon(gifImage);
		label_imagem = new JLabel(resizedGifIcon);
		label_imagem.setBorder(null);
		painel_imagem.add(label_imagem);
		this.add(painel_imagem);

		// PainelDireito

		painel_Login = new JPanel();
		painel_Login.setBackground(Color.decode("#FFFFFF"));
		painel_Login.setLayout(null);

		// ComponentesPainelLogin
		// Slogan
		label_slogan = new JLabel("MozaCrédito");
		label_slogan.setFont(new Font("Dialog", Font.BOLD, 23));
		label_slogan.setBounds(35, -75, 350, 225);
		painel_Login.add(label_slogan);

		// LabelBemVindo
		label_BemVindo = new JLabel("Bem-vindo");
		label_BemVindo.setFont(new Font("Arial", Font.BOLD, 25));
		label_BemVindo.setBounds(35, -10, 350, 225);
		painel_Login.add(label_BemVindo);

		// LabelUsuario
		usuario = new JLabel("Usuario");
		usuario.setFont(new Font("Dialog", Font.PLAIN, 15));
		usuario.setBounds(35, 30, 350, 225);
		painel_Login.add(usuario);

		// CaixaUsuario
		campo_usuario = new JTextField();
		campo_usuario.setBounds(35, 160, 205, 25);
		campo_usuario.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		campo_usuario.setBorder(new LineBorder(Color.GRAY, 1));
		campo_usuario.setBackground(new Color(255, 255, 255, 200));
		campo_usuario.setOpaque(true);
		((AbstractDocument) campo_usuario.getDocument()).setDocumentFilter(new LimiteCaracteresFilter(15));
		painel_Login.add(campo_usuario);
		
		// LabelLogin
		palavraChave = new JLabel("Palavra-Chave");
		palavraChave.setFont(new Font("Dialog", Font.PLAIN, 15));
		palavraChave.setBounds(35, 90, 350, 225);
		painel_Login.add(palavraChave);
		
		// CaixaPassword
		campo_palavraChave = new JPasswordField();
		campo_palavraChave.setFont(new Font("Dialog", Font.BOLD, 13));
		campo_palavraChave.setBounds(35, 220, 205, 25);
		campo_palavraChave.setBorder(new LineBorder(Color.GRAY, 1));
		campo_palavraChave.setBackground(new Color(255, 255, 255, 200));
		campo_palavraChave.setOpaque(true);
		painel_Login.add(campo_palavraChave);
		
		// BarraRecuperarCadastro
		label_esqueceuCredenciais = new JLabel("Esqueceu as credenciais?");
		label_esqueceuCredenciais.setFont(new Font("Dialog", Font.PLAIN, 10));
		label_esqueceuCredenciais.setBounds(35, 145, 350, 225);
		painel_Login.add(label_esqueceuCredenciais);
		butao_recuperar = new JButton("Recupere-as");
		butao_recuperar.setBounds(165, 250, 75, 15);
		butao_recuperar.setFont(new Font("Dialog", Font.PLAIN, 10));
		butao_recuperar.setBorder(new LineBorder(Color.white, 0));
		butao_recuperar.setBackground(null);
		butao_recuperar.setOpaque(true);
		butao_recuperar.setForeground(Color.red);
		butao_recuperar.addActionListener(this);
		painel_Login.add(butao_recuperar);

		// butaoEntrar
		butao_entrar = new JButton("Entrar");
		butao_entrar.setForeground(Color.WHITE);
		butao_entrar.setBounds(35, 275, 205, 25);
		butao_entrar.setFocusPainted(false);
		butao_entrar.setBorder(new LineBorder(Color.GRAY, 1));
		butao_entrar.setBackground(Color.decode("#3ead40"));
		butao_entrar.addActionListener(this);
		painel_Login.add(butao_entrar);

		// IconNaTela
		imagem = new ImageIcon(
				"C:\\Users\\rushi\\OneDrive\\Documents\\Workspace - Rushil\\MicroCredito\\src\\telas\\wallet.png");
		originalImage = imagem.getImage();
		resizedImage = originalImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		resizedIcon = new ImageIcon(resizedImage);
		label_imagem = new JLabel(resizedIcon);
		label_imagem.setBounds(0, 10, 420, 200);
		painel_Login.add(label_imagem);
		this.add(painel_Login);

		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO:Butoes
		if (e.getSource() == butao_entrar) {
			if (campo_palavraChave.getText().equals("") && campo_usuario.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Login Efetuado com Sucesso", "Login",
						JOptionPane.INFORMATION_MESSAGE);
				new Dashboard();
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(butao_recuperar, "Usuario ou Palavra-Chave Incorreta");
			}
		} else if (e.getSource() == butao_recuperar) {
			JOptionPane.showMessageDialog(butao_recuperar, "Opção ainda não disponivel");
		}

	}

	public String getUser() {
		return campo_usuario.getText();
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
