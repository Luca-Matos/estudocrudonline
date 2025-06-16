package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {
    private Connection conexao;

    public MateriaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void adicionar(Materia materia) throws SQLException {
        String sql = "INSERT INTO materia (nome, cor, usuario_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, materia.getNome());
            stmt.setString(2, materia.getCor());
            stmt.setInt(3, materia.getUsuarioId()); // Certifique-se de que o objeto Materia tem o usuarioId definido
            stmt.executeUpdate();
     
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                materia.setId(idGerado); // Agora o objeto tem o ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao adicionar matéria: " + e.getMessage());
        }
    }
    

    public List<Materia> listarTodas() throws SQLException {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT * FROM materia";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Materia m = new Materia();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setCor(rs.getString("cor"));
                lista.add(m);
            }
        }
        return lista;
    }

    public Materia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM materia WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Materia m = new Materia();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setCor(rs.getString("cor"));
                return m;
            }
        }
        return null;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM materia WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Materia> listarPorUsuario(int usuarioId) throws SQLException {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT * FROM materia WHERE usuario_id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Materia m = new Materia();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setCor(rs.getString("cor"));
                m.setUsuarioId(rs.getInt("usuario_id"));
                lista.add(m);
            }
        }
        return lista;
    }
    

}
