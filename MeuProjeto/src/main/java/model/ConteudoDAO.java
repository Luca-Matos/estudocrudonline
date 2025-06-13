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
        String sql = "INSERT INTO conteudo (nome, horas_estudar, status, materia_id, descricao, cor) VALUES (?, ?, ?, ?, ?,? )";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, conteudo.getTitulo());
            stmt.setInt(2, conteudo.getHorasPlanejadas());
            stmt.setBoolean(3, conteudo.isEstudado());
            stmt.setInt(4, conteudo.getMateriaId());
            stmt.setString(5, conteudo.getDescricao());
            stmt.setString(6, conteudo.getCor());
            stmt.executeUpdate();
    
            // Recuperar o ID gerado automaticamente
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                conteudo.setId(rs.getInt(1));
            }
        }
    }

    public Conteudo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM conteudo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Conteudo c = new Conteudo();
                c.setId(rs.getInt("id"));
                c.setTitulo(rs.getString("nome"));
                c.setEstudado(rs.getBoolean("status"));
                c.setHorasPlanejadas(rs.getInt("horas_estudar"));
                c.setDescricao(rs.getString("descricao"));
                c.setMateriaId(rs.getInt("materia_id"));
                return c;
            }
        }
        return null;
    }

    public List<Conteudo> listarTodos() throws SQLException {
        String sql = "SELECT * FROM conteudo";
        List<Conteudo> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Conteudo c = new Conteudo();
                c.setId(rs.getInt("id"));
                c.setTitulo(rs.getString("nome"));
                c.setEstudado(rs.getBoolean("status"));
                c.setHorasPlanejadas(rs.getInt("horas_estudar"));
                c.setDescricao(rs.getString("descricao"));
                c.setMateriaId(rs.getInt("materia_id"));
                c.setCor(rs.getString("cor"));
                lista.add(c);
            }
        }
        return lista;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM conteudo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void salvarResultadoEExcluir(int conteudoId) throws SQLException {
        // Buscar os dados do conteúdo antes de excluir
        String selectSql = "SELECT * FROM conteudo WHERE id = ?";
        try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
            selectStmt.setInt(1, conteudoId);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                String titulo = rs.getString("nome");
                int horas = rs.getInt("horas_estudar");
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
