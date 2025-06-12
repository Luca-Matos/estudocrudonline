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
            stmt.setInt(5, conteudo.getMateriaId());
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
                c.setMateriaId(rs.getInt("materia_id"));
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
                c.setMateriaId(rs.getInt("materia_id"));
                lista.add(c);
            }
        }
        return lista;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM conteudos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void salvarResultadoEExcluir(int conteudoId) throws SQLException {
        // Buscar os dados do conteúdo antes de excluir
        String selectSql = "SELECT * FROM conteudos WHERE id = ?";
        try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
            selectStmt.setInt(1, conteudoId);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                String titulo = rs.getString("titulo");
                int horas = rs.getInt("horas_estudadas");
                int materiaId = rs.getInt("materia_id");
    
                // Salva no resumo
                ResumoDAO resumoDAO = new ResumoDAO(connection);
                resumoDAO.salvarResumo(materiaId, titulo, horas);
    
                // Deleta o conteúdo
                deletar(conteudoId);
            }
        }
    }
    
    

    // Métodos para atualizar, deletar, etc, podem ser adicionados aqui
}
