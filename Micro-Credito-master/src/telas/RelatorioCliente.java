package telas;

import estrutura.ListaDuplamenteLigada;
import models.Cliente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class RelatorioCliente extends JPanel implements ActionListener {
    private final service.ClienteService clienteService;

    private JLabel jl_titulo,jl_jp1, jl_jp2, jl_jp3,jl_clienteBi ,jl_idEmprestimo, jl_bi, jl_clienteApelido, jl_clienteTelefone, jl_emprestimoEstado, jl_emprestimoData, jl_filtro;
    private JTextField tf_id, tf_clienteBi, tf_nome, tf_clienteApelido, tf_clienteTelefone, tf_emprestimoId, tf_emprestimoData;
    private JButton jb_clienteBi, jb_cliente2atribuitos, jb_emprestimoId, jb_emprestimoDataEstado, jb_ordenar;
    private JPanel jpc, jp1, jp2,jpCliente, jpEmprestimo, jpCliente1, jpEmprestimo1, jpCliente2,jpEmprestimo2;
    private JScrollPane jsp;
    private JComboBox<String> coBox, jcb_coEstado;
    private DefaultTableModel tabelaModelo;
    private JTable jt;

    public RelatorioCliente(service.ClienteService clienteService) {
        this.clienteService = clienteService;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(600, 600));
        this.setMinimumSize(new Dimension(600, 600));
        this.setMaximumSize(new Dimension(600, 600));
        jpCliente = new JPanel();
        jpCliente.setLayout(new BorderLayout(10, 10));

// Painel superior para os formulários de pesquisa
        JPanel painelPesquisa = new JPanel();
        painelPesquisa.setLayout(new GridLayout(2, 1, 10, 10));

// Painel BI - com melhor espaçamento
        jpCliente1 = new JPanel();
        jpCliente1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.decode("#7d9af4"), 2), "Pesquisa por BI");
        titledBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        titledBorder.setTitleColor(Color.decode("#2c3e50"));
        jpCliente1.setBorder(BorderFactory.createCompoundBorder(
                titledBorder,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        jl_clienteBi = new JLabel("BI: ");
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        jpCliente1.add(jl_clienteBi, gbc);

        tf_clienteBi = new FormatacaoTextField(2);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1;
        jpCliente1.add(tf_clienteBi, gbc);

        jb_clienteBi = new FormatacaoButton("Pesquisar", Color.decode("#3ead40"));
        jb_clienteBi.setForeground(Color.WHITE);
        jb_clienteBi.setFocusPainted(false);
        jb_clienteBi.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0;
        jpCliente1.add(jb_clienteBi, gbc);

// Painel de Apelido e Telefone Otimizado
        jpCliente2 = new JPanel();
        jpCliente2.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        TitledBorder titledBorder1 = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.decode("#7d9af4"), 2), "Pesquisa por Apelido e Telefone");
        titledBorder1.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        titledBorder1.setTitleColor(Color.decode("#2c3e50"));
        jpCliente2.setBorder(BorderFactory.createCompoundBorder(
                titledBorder1,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

// Linha 1 - Apelido
        jl_clienteApelido = new JLabel("Apelido: ");
        gbc2.gridx = 0; gbc2.gridy = 0; gbc2.weightx = 0;
        jpCliente2.add(jl_clienteApelido, gbc2);

        tf_clienteApelido = new FormatacaoTextField(2);
        gbc2.gridx = 1; gbc2.gridy = 0; gbc2.weightx = 1;
        jpCliente2.add(tf_clienteApelido, gbc2);

        JLabel espacador1 = new JLabel(" ");
        gbc2.gridx = 2; gbc2.gridy = 0; gbc2.weightx = 0;
        jpCliente2.add(espacador1, gbc2);

// Linha 2 - Telefone
        jl_clienteTelefone = new JLabel("Telefone: ");
        gbc2.gridx = 0; gbc2.gridy = 1; gbc2.weightx = 0;
        jpCliente2.add(jl_clienteTelefone, gbc2);

        tf_clienteTelefone = new FormatacaoTextField(2);
        gbc2.gridx = 1; gbc2.gridy = 1; gbc2.weightx = 1;
        jpCliente2.add(tf_clienteTelefone, gbc2);

        jb_cliente2atribuitos = new FormatacaoButton("Pesquisar", Color.decode("#3ead40"));
        jb_cliente2atribuitos.setForeground(Color.WHITE);
        jb_cliente2atribuitos.setFocusPainted(false);
        jb_cliente2atribuitos.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        gbc2.gridx = 2; gbc2.gridy = 1; gbc2.weightx = 0;
        jpCliente2.add(jb_cliente2atribuitos, gbc2);

// Adicionar painéis de pesquisa ao painel principal
        painelPesquisa.add(jpCliente1);
        painelPesquisa.add(jpCliente2);

// Tabela (mantida exatamente como você tinha)
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

// Se desejar adicionar uma borda externa ao ScrollPane
        jsp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0),
                BorderFactory.createLineBorder(Color.decode("#7d9af4"), 1)));
        jsp.getViewport().setBackground(Color.WHITE);

// NOVO: Painel para a tabela e botão ordenar
        JPanel painelTabela = new JPanel(new BorderLayout(10, 10));

// NOVO: Botão Ordenar
        jb_ordenar = new FormatacaoButton("Ordenar por Data", Color.decode("#3ead40"));
        jb_ordenar.setForeground(Color.WHITE);
        jb_ordenar.setFocusPainted(false);
        jb_ordenar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        jb_ordenar.addActionListener(this);

// NOVO: Painel para o botão (alinha à direita)
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotao.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 10));
        painelBotao.setBackground(Color.WHITE);
        painelBotao.add(jb_ordenar);

// NOVO: Adicionar componentes ao painel da tabela
        painelTabela.add(painelBotao, BorderLayout.NORTH);
        painelTabela.add(jsp, BorderLayout.CENTER);

// Layout final
        jpCliente.add(painelPesquisa, BorderLayout.NORTH);
        jpCliente.add(painelTabela, BorderLayout.CENTER); // Alterado para painelTabela

// Adicionar margem externa
        jpCliente.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(jpCliente, BorderLayout.CENTER);
        jb_cliente2atribuitos.addActionListener(this);
        jb_clienteBi.addActionListener(this);
        carregarTabela();
        this.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb_cliente2atribuitos){
            String apelido = tf_clienteApelido.getText().trim();
            String telefoneTexto = tf_clienteTelefone.getText().trim();

            if (telefoneTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um telefone para pesquisar!");
                return;
            }

            int telefone = Integer.parseInt(telefoneTexto);
            clienteService.buscarClientePorTelefoneApelido(telefone,apelido);
            carregarTabelaTelfoneApelido();

        } else if (e.getSource()==jb_clienteBi) {
            String Bi = tf_clienteBi.getText().trim();
          //  clienteService.buscarClientePorBi(Bi);
            JOptionPane.showMessageDialog(this,  clienteService.buscarClientePorBi(Bi));
        }else if(e.getSource()==jb_ordenar){
           clienteService.selectionSort();
            carregarTabelaOrdenada();
        }
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

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }
    private void carregarTabelaTelfoneApelido() {
        try {
            tabelaModelo.setRowCount(0);

            // Chama o service para obter a lista de clientes
            ListaDuplamenteLigada clientes = clienteService.getClientesBusca();

            for (int i = 0; i < clientes.tamanho(); i++) {
                Cliente c = (Cliente) clientes.pega(i);
                tabelaModelo.addRow(new Object[] { c.getId(), c.getBi(), c.getNome(), c.getApelido(), c.getTelefone(),
                        c.getEndereco(), c.getDataCadastro() });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }
    private void carregarTabelaOrdenada() {
        try {
            tabelaModelo.setRowCount(0);

            // Chama o service para obter a lista de clientes
            ListaDuplamenteLigada clientes = clienteService.getOrdenada();

            for (int i = 0; i < clientes.tamanho(); i++) {
                Cliente c = (Cliente) clientes.pega(i);
                tabelaModelo.addRow(new Object[] { c.getId(), c.getBi(), c.getNome(), c.getApelido(), c.getTelefone(),
                        c.getEndereco(), c.getDataCadastro() });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
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
}
