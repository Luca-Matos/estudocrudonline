package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection getConexao() throws SQLException {
        // Pega variáveis do Railway
        String host = System.getenv("MYSQLHOST");
        String port = System.getenv("MYSQLPORT");
        String database = System.getenv("MYSQLDATABASE");
        String user = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");

        // Verifica se variáveis estão presentes
        if (host == null || port == null || database == null || user == null || password == null) {
            throw new SQLException("❌ Variáveis de ambiente para o banco não estão definidas corretamente.");
        }

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC";
        System.out.println("🔗 Tentando conectar com: " + url);
        return DriverManager.getConnection(url, user, password);
    }
}
