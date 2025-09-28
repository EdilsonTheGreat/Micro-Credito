package dao;

import estrutura.ListaDuplamenteLigada;
import models.Emprestimo;
import java.sql.*;

public class EmprestimoDAO {
    private Connection connection;

    public EmprestimoDAO(Connection connection) throws SQLException {
        this.connection = DBConnection.getConnection();
    }
    //cadastrar emprestimo na Base de dados
    public boolean insert (Emprestimo emprestimo) throws SQLException{
        String query = "INSERT INTO Emprestimo "
                + "(idEmprestimo,biCliente, valorTotal, tipo, numeroPrestacoes, estado, dataConcessao) "
                + "VALUES (?, ?, ?, ?, ?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,emprestimo.getIdEmprestimo());
            ps.setString(2,emprestimo.getBiCliente());
            ps.setDouble(3,emprestimo.getValorTotal());
            ps.setString(4,emprestimo.getTipo());
            ps.setInt(5,emprestimo.getNumeroPrestacoes());
            ps.setString(6,emprestimo.getEstado());
            ps.setDate(7,java.sql.Date.valueOf(emprestimo.getDataConcessao()));
            ps.executeUpdate();//executa a query
            //Para pegar o id gerado automaticamente na base de dados
            System.out.println("Emprestimo " + emprestimo.getIdEmprestimo() + " Registrado (DB)");



        } catch (SQLException e) {
            System.out.println("Falha ao gravar na base de dados " + e.getMessage());
            return false;
        }
        return true;

    }

    public void update (Emprestimo emprestimo) throws SQLException {
        String query = "UPDATE Emprestimo SET biCliente = ?, tipo = ?, numeroPrestacoes = ? WHERE idEmprestimo = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, emprestimo.getBiCliente());
            ps.setString(2, emprestimo.getTipo());
            ps.setInt(3, emprestimo.getNumeroPrestacoes());
            ps.setString(4, emprestimo.getIdEmprestimo());
            int linhasAtualizadas = ps.executeUpdate();
            if (linhasAtualizadas > 0) {
                System.out.println("Emprestimo atualizado com sucesso no base de dados: " + emprestimo.getIdEmprestimo());
            }

        } catch(SQLException e){
            System.out.println("Falha ao  actualizar cliente  no base de dados: " + emprestimo.getIdEmprestimo());
            throw new RuntimeException(e);
        }



    }
    public void delete (String id){
        String sql = "DELETE FROM Emprestimo WHERE idEmprestimo = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,id);

            int linhasRemovidas = ps.executeUpdate();
            if (linhasRemovidas > 0) {
                System.out.println("Emprestimo removido com sucesso do banco: " + id);
            } else {
                System.out.println("Nenhum Emprestimo encontrado no banco com BI: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao remover cliente: " + e.getMessage());
        }
    }



    //metodo auxiliar para Carregar Emprestimos ja registrados da BD para a lista
    public ListaDuplamenteLigada buscarTodos() throws SQLException {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        String query = "SELECT * FROM Emprestimo ORDER BY idEmprestimo";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo(
                        rs.getString("idEmprestimo"),
                        rs.getString("biCliente"),
                        rs.getDouble("valorTotal"),
                        rs.getString("tipo"),
                        rs.getInt("numeroPrestacoes"),
                        rs.getDouble("taxaJuro"),
                        rs.getString("estado"),
                        rs.getDate("dataConcessao").toLocalDate()
                );
                lista.adcionaFim(emprestimo);
            }
        }
        return lista;
    }



}
