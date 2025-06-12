package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {
    private Connection connection;

    public MateriaDAO(Connection connection) {
        this.connection = connection;
    }

    public void adicionar(Materia materia) throws SQLException {
        String sql = "INSERT INTO materias (nome, cor) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, materia.getNome());
            stmt.setString(2, materia.getCor());
            stmt.executeUpdate();
        }
    }

    public List<Materia> listarTodas() throws SQLException {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT * FROM materias";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        String sql = "SELECT * FROM materias WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        String sql = "DELETE FROM materias WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
