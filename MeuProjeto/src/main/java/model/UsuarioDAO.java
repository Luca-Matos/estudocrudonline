package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

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
                    usuario.setId(idGerado);  // Certifique-se que sua classe Usuario tenha o método setId(int)
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // você pode adicionar métodos como buscarPorEmailSenha(), atualizar(), deletar(), etc.
}
