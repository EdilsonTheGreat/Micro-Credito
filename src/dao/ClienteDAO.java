package dao;
import estrutura.ListaDuplamenteLigada;
import models.Cliente;
import java.sql.*;

public class ClienteDAO {
    private final Connection connection;

    public ClienteDAO() throws SQLException{
        this.connection = DBConnection.getConnection();
    }
    //cadastrar cliente na Base de dados
    public boolean insert (Cliente cliente) throws SQLException{
        String query = "INSERT INTO cliente (bi, nome,apelido,telefone,endereco,dataCadastro) " + "Values (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,cliente.getBi());
                ps.setString(2,cliente.getNome());
                ps.setString(3,cliente.getApelido());
                ps.setInt(4,cliente.getTelefone());
                ps.setString(5,cliente.getEndereco());
                ps.setDate(6,java.sql.Date.valueOf(cliente.getDataCadastro()));
                ps.executeUpdate();//executa a query
               System.out.println("Cliente " + cliente.getNome() + " Registrado (DB)");
                //Para pegar o id gerado automaticamente na base de dados
                try {
                    ResultSet result = ps.getGeneratedKeys();
                    if (result.next()){
                        int idGerado = result.getInt(1);
                        cliente.setId(idGerado);
                    }
                } catch (SQLException e) {
                   System.out.println("Falha ao pegar o Id gerado " + e.getMessage());
                }


        } catch (SQLException e) {
            System.out.println("Falha ao gravar na base de dados " + e.getMessage());
            return false;
        }
        return true;

    }
    public void update (Cliente cliente) throws SQLException {
        String query = "UPDATE cliente SET nome = ?, apelido = ?, telefone = ?, endereco = ? WHERE bi = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getApelido());
            ps.setInt(3, cliente.getTelefone());
            ps.setString(4, cliente.getEndereco());
            ps.setString(5, cliente.getBi());
            int linhasAtualizadas = ps.executeUpdate();
            if (linhasAtualizadas > 0) {
                System.out.println("Cliente atualizado com sucesso no base de dados: " + cliente.getNome());
            }

            } catch(SQLException e){
            System.out.println("Falha ao  actualizar cliente  no base de dados: " + cliente.getNome());
                throw new RuntimeException(e);
            }



    }
    public void delete (String bi){
        String sql = "DELETE FROM cliente WHERE bi = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bi);

            int linhasRemovidas = ps.executeUpdate();
            if (linhasRemovidas > 0) {
                System.out.println("Cliente removido com sucesso do banco: " + bi);
            } else {
                System.out.println("Nenhum cliente encontrado no banco com BI: " + bi);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao remover cliente: " + e.getMessage());
        }
    }



    //metodo auxiliar para Carregar clientes ja registrados da BD para a lista
    public ListaDuplamenteLigada buscarTodos() throws SQLException {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        String query = "SELECT * FROM cliente ORDER BY id";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("bi"),
                        rs.getString("nome"),
                        rs.getString("apelido"),
                        rs.getInt("telefone"),
                        rs.getString("endereco"),
                        rs.getDate("dataCadastro").toLocalDate()
                );
                lista.adcionaFim(cliente);
            }
        }
        return lista;
    }

}
