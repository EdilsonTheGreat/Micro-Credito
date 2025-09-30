package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{
    private static final String URL = "jdbc:mysql://localhost:3306/microcredito";
    private static final String USER = "root";
    private static final String PASSWORD = "Oficina@123";
    private static  Connection connection = null;


    //metodo para obter coneão
    public static  Connection getConnection() throws SQLException {
        if (connection==null ||  connection.isClosed()){
            try {
                connection = DriverManager.getConnection(URL,USER,PASSWORD);
                System.out.println("Conexão estabelecida com sucesso com a base de dados");

            } catch (SQLException e) {
                System.out.println("Algo falhou :(" + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
    //metodo para fechar conexão
    public static void fecharConnection() throws SQLException {
        if (connection!=null){
            try {
                connection.close();
                System.out.println("Conexão Encerrada");
            } catch (SQLException e) {
                System.out.println("Falha ao Encerrar Conexão ");
                throw e;
            }
        }
    }


}




