package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/estudocrud?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "LuigiFonini123";

    public static Connection getConexao() throws SQLException {
        Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        System.out.println("✅ Conectado ao banco de dados com sucesso!");
        return conexao;
    }
}
