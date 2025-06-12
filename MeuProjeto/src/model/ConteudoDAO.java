package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConteudoDAO {
    private Connection connection;

    public ConteudoDAO(Connection connection) {
        this.connection = connection;
    }

    public void adicionar(Conteudo conteudo) throws SQLException {
        String sql = "INSERT INTO conteudos (titulo, estudado, horas_planejadas, descricao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, conteudo.getTitulo());
            stmt.setBoolean(2, conteudo.isEstudado());
            stmt.setInt(3, conteudo.getHorasPlanejadas());
            stmt.setString(4, conteudo.getDescricao());
            stmt.executeUpdate();
        }
    }

    public Conteudo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM conteudos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Conteudo c = new Conteudo();
                c.setId(rs.getInt("id"));
                c.setTitulo(rs.getString("titulo"));
                c.setEstudado(rs.getBoolean("estudado"));
                c.setHorasPlanejadas(rs.getInt("horas_planejadas"));
                c.setDescricao(rs.getString("descricao"));
                return c;
            }
        }
        return null;
    }

    public List<Conteudo> listarTodos() throws SQLException {
        String sql = "SELECT * FROM conteudos";
        List<Conteudo> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Conteudo c = new Conteudo();
                c.setId(rs.getInt("id"));
                c.setTitulo(rs.getString("titulo"));
                c.setEstudado(rs.getBoolean("estudado"));
                c.setHorasPlanejadas(rs.getInt("horas_planejadas"));
                c.setDescricao(rs.getString("descricao"));
                lista.add(c);
            }
        }
        return lista;
    }

    // Métodos para atualizar, deletar, etc, podem ser adicionados aqui
}
