package dao;

import java.sql.*;

import estrutura.ListaDuplamenteLigada;
import models.Pagamento;
public class PagamentoDAO {
    private final Connection connection;

    public PagamentoDAO() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    // Cadastrar pagamento na Base de dados
    public boolean insert(Pagamento pagamento) throws SQLException {
        String sql = "INSERT INTO pagamento (id_emprestimo, valor_pago, data_pagamento, numero_parcela, juro_aplicado) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pagamento.getIdEmprestimo());
            ps.setDouble(2, pagamento.getValorPago());
            ps.setDate(3, java.sql.Date.valueOf(pagamento.getDataPagamento()));
            ps.setInt(4, pagamento.getNumeroParcela());
            ps.setDouble(5, pagamento.getJuroAplicado());

            ps.executeUpdate();

            // Recuperar o id gerado pelo banco
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pagamento.setIdPagamento(rs.getString(1));
                }
            }

            System.out.println(" Pagamento registrado no banco: " + pagamento.getIdPagamento());
            return true;

        } catch (SQLException e) {
            System.out.println("Falha ao gravar pagamento: " + e.getMessage());
            return false;
        }
    }


    // === Buscar todos os pagamentos ===
    public ListaDuplamenteLigada buscarTodos() throws SQLException {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        String sql = "SELECT * FROM pagamento ORDER BY data_pagamento DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pagamento pagamento = new Pagamento(
                        rs.getString("id_pagamento"),
                        rs.getString("id_emprestimo"),
                        rs.getDouble("valor_pago"),
                        rs.getDate("data_pagamento").toLocalDate(),
                        rs.getInt("numero_parcela")
                );

                lista.adcionaFim(pagamento);
            }
        }

        return lista;
    }


    // === Atualizar pagamento ===
    public boolean update(Pagamento pagamento) throws SQLException {
        String sql = "UPDATE pagamento SET id_emprestimo = ?, valor_pago = ?, data_pagamento = ?, "
                + "numero_parcela = ?, juro_aplicado = ? "
                + "WHERE id_pagamento = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pagamento.getIdEmprestimo());
            ps.setDouble(2, pagamento.getValorPago());
            ps.setDate(3, java.sql.Date.valueOf(pagamento.getDataPagamento()));
            ps.setInt(4, pagamento.getNumeroParcela());
            ps.setDouble(5, pagamento.getJuroAplicado());
            ps.setString(6, pagamento.getIdPagamento());

            int linhasAtualizadas = ps.executeUpdate();
            if (linhasAtualizadas > 0) {
                System.out.println("Pagamento atualizado no banco: " + pagamento.getIdPagamento());
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Falha ao atualizar pagamento: " + pagamento.getIdPagamento());
            throw e;
        }
        return false;
    }

    public boolean delete(String idPagamento) throws SQLException {
        String sql = "DELETE FROM pagamento WHERE id_pagamento = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idPagamento);

            int linhasRemovidas = ps.executeUpdate();
            if (linhasRemovidas > 0) {
                System.out.println("Pagamento removido do banco: " + idPagamento);
                return true;
            } else {
                System.out.println("Nenhum pagamento encontrado com ID: " + idPagamento);
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao remover pagamento: " + e.getMessage());
        }
    }
}


