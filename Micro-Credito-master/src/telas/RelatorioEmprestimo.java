package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.*;
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
import telas.ClienteTela.FormatacaoButton;
import telas.ClienteTela.FormatacaoLabel;
import telas.ClienteTela.FormatacaoTextField;
import telas.ClienteTela.LimiteCaracteresFilter;


public class RelatorioEmprestimo extends JPanel implements ActionListener {
    private final service.EmprestimoService emprestimoService;
    private final service.ClienteService clienteService;
    private JLabel jl_titulo,jl_jp1, jl_jp2, jl_jp3,jl_clienteBi ,jl_idEmprestimo, jl_bi, jl_clienteApelido, jl_clienteTelefone, jl_emprestimoEstado, jl_emprestimoData, jl_filtro;
    private JTextField tf_id, tf_clienteBi, tf_nome, tf_clienteApelido, tf_clienteTelefone, tf_emprestimoId, tf_emprestimoData;
    private JButton jb_ordenar,jb_clienteBi, jb_cliente2atribuitos, jb_emprestimoId, jb_emprestimoDataEstado;
    private JPanel jpc, jp1, jp2,jpCliente, jpEmprestimo, jpCliente1, jpEmprestimo1, jpCliente2,jpEmprestimo2;
    private JScrollPane jsp;
    private JComboBox<String> coBox, jcb_coEstado;
    private DefaultTableModel tabelaModelo;
    private JTable jt;

    public RelatorioEmprestimo( service.ClienteService clienteService,service.EmprestimoService emprestimoService){
        this.emprestimoService = emprestimoService;
        this.clienteService = clienteService;

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(600, 600));
        this.setMinimumSize(new Dimension(600, 600));
        this.setMaximumSize(new Dimension(600, 600));
// Painel Principal
        jpEmprestimo = new JPanel();
        jpEmprestimo.setLayout(new BorderLayout(10, 10));

// Painel superior para os formulários de pesquisa
        JPanel painelPesquisa = new JPanel();
        painelPesquisa.setLayout(new GridLayout(2, 1, 10, 10));

// Painel1 - Pesquisa por ID
        jpEmprestimo1 = new JPanel();
        jpEmprestimo1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.decode("#7d9af4"), 2), "Pesquisa por ID");
        titledBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        titledBorder.setTitleColor(Color.decode("#2c3e50"));
        jpEmprestimo1.setBorder(BorderFactory.createCompoundBorder(
                titledBorder,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        jl_idEmprestimo = new JLabel("Id:");
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        jpEmprestimo1.add(jl_idEmprestimo, gbc);

        tf_emprestimoId = new FormatacaoTextField(2);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1;
        jpEmprestimo1.add(tf_emprestimoId, gbc);

        jb_emprestimoId = new FormatacaoButton("Pesquisar", Color.decode("#3ead40"));
        jb_emprestimoId.setForeground(Color.WHITE);
        jb_emprestimoId.setFocusPainted(false);
        jb_emprestimoId.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0;
        jpEmprestimo1.add(jb_emprestimoId, gbc);

// Painel2 - Pesquisa por Estado e Data
        jpEmprestimo2 = new JPanel();
        jpEmprestimo2.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        TitledBorder titledBorder1 = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.decode("#7d9af4"), 2), "Pesquisa por Estado e Data");
        titledBorder1.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        titledBorder1.setTitleColor(Color.decode("#2c3e50"));
        jpEmprestimo2.setBorder(BorderFactory.createCompoundBorder(
                titledBorder1,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

// Linha 1 - Estado
        jl_emprestimoEstado = new JLabel("Estado:");
        gbc2.gridx = 0; gbc2.gridy = 0; gbc2.weightx = 0;
        jpEmprestimo2.add(jl_emprestimoEstado, gbc2);

        jcb_coEstado = new JComboBox<>();
        jcb_coEstado.setModel(new DefaultComboBoxModel<>(new String[] { "ativo", "liquidado", "atrasado" }));
        gbc2.gridx = 1; gbc2.gridy = 0; gbc2.weightx = 1;
        jpEmprestimo2.add(jcb_coEstado, gbc2);

        JLabel espacador1 = new JLabel(" ");
        gbc2.gridx = 2; gbc2.gridy = 0; gbc2.weightx = 0;
        jpEmprestimo2.add(espacador1, gbc2);

// Linha 2 - Data
        jl_emprestimoData = new JLabel("Data Concessao:");
        gbc2.gridx = 0; gbc2.gridy = 1; gbc2.weightx = 0;
        jpEmprestimo2.add(jl_emprestimoData, gbc2);

        tf_emprestimoData = new FormatacaoTextField(2);
        gbc2.gridx = 1; gbc2.gridy = 1; gbc2.weightx = 1;
        jpEmprestimo2.add(tf_emprestimoData, gbc2);

        jb_emprestimoDataEstado = new FormatacaoButton("Pesquisar", Color.decode("#3ead40"));
        jb_emprestimoDataEstado.setForeground(Color.WHITE);
        jb_emprestimoDataEstado.setFocusPainted(false);
        jb_emprestimoDataEstado.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        gbc2.gridx = 2; gbc2.gridy = 1; gbc2.weightx = 0;
        jpEmprestimo2.add(jb_emprestimoDataEstado, gbc2);

// Adicionar painéis de pesquisa ao painel principal
        painelPesquisa.add(jpEmprestimo1);
        painelPesquisa.add(jpEmprestimo2);

// Tabela (mantida exatamente como você tinha)
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

// Se desejar adicionar uma borda externa ao ScrollPane
        jsp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0),
                BorderFactory.createLineBorder(Color.decode("#7d9af4"), 1)));
        jsp.getViewport().setBackground(Color.WHITE);

// NOVO: Painel para a tabela e botão ordenar
        JPanel painelTabela = new JPanel(new BorderLayout(10, 10));

// NOVO: Botão Ordenar
        jb_ordenar = new FormatacaoButton("Ordenar por Data de concessao", Color.decode("#3ead40"));
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
        jpEmprestimo.add(painelPesquisa, BorderLayout.NORTH);
        jpEmprestimo.add(painelTabela, BorderLayout.CENTER); // Alterado para painelTabela

// Adicionar margem externa
        jpEmprestimo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(jpEmprestimo, BorderLayout.CENTER);
        jb_emprestimoDataEstado.addActionListener(this);
        jb_emprestimoId.addActionListener(this);
        carregarTabela();
        this.setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb_emprestimoDataEstado){
            java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String txtVenc = tf_emprestimoData.getText().trim();
            if (txtVenc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Indique a Data de Vencimento (dd/MM/yyyy).");
                return;
            }
            java.time.LocalDate data;
            try {
                data = java.time.LocalDate.parse(txtVenc, df);}
            catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data inválida. Use dd/MM/yyyy.");
                return;
            }
            String estado = String.valueOf(jcb_coEstado.getSelectedItem());;
            emprestimoService.buscarEmprestimoPorDataEstado(data, estado);
            carregarTabelaDataEstado();

        } else if (e.getSource()==jb_emprestimoId) {
            String id = tf_emprestimoId.getText().trim();
            JOptionPane.showMessageDialog(this, emprestimoService.buscarEmprestimoPorId(id));
        }else if(e.getSource()==jb_ordenar){
            emprestimoService.selectionSort();
            carregarTabelaOrdenada();
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
    private void carregarTabelaDataEstado() {
        try {
            tabelaModelo.setRowCount(0);

            // Chama o service para obter a lista de clientes
            ListaDuplamenteLigada emprestimos = emprestimoService.getListaDataEstado();
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
    private void carregarTabelaOrdenada() {
        try {
            tabelaModelo.setRowCount(0);

            // Chama o service para obter a lista de clientes
            ListaDuplamenteLigada emprestimos = emprestimoService.getOrdenada();
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
}
