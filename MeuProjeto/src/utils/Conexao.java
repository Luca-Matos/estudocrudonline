package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/estudocrud?useSSL=false&serverTimezone=UTC" + //
                ""; // nome do banco
    private static final String USUARIO = "root";    // seu usuário MySQL
    private static final String SENHA = "root"; // sua senha MySQL

    private static Connection conexao;

    public static Connection getConexao() {
        if (conexao == null) {
            try {
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                System.out.println("✅ Conectado ao banco de dados com sucesso!");
            } catch (SQLException e) {
                System.out.println("❌ Erro ao conectar ao banco de dados:");
                e.printStackTrace();
            }
        }
        return conexao;
    }

    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("🔌 Conexão fechada.");
            } catch (SQLException e) {
                System.out.println("⚠️ Erro ao fechar a conexão:");
                e.printStackTrace();
            }
        }
    }
}
