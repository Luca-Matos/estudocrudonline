package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {
    
    private Connection conexao;

    public UsuarioDAO(Connection connection) {
        this.conexao = connection;
    }

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nome, email, senha) VALUES (?, ?, ?)";
    
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();
            

    
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    usuario.setId(idGerado);  
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario buscarPorEmailSenha(String email, String senha) throws Exception {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    return usuario;
                }
            }
        }
        return null;
    }
    
}
