package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConteudoDAO {
    private Connection conexao;

    public ConteudoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void adicionar(Conteudo conteudo) throws SQLException {
        String sql = "INSERT INTO conteudo (nome, horas_estudar, status, materia_id, descricao, cor) VALUES (?, ?, ?, ?, ?,? )";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void salvarResultadoEExcluir(int conteudoId) throws SQLException {
        // Buscar os dados do conteúdo antes de excluir
        String selectSql = "SELECT * FROM conteudo WHERE id = ?";
        try (PreparedStatement selectStmt = conexao.prepareStatement(selectSql)) {
            selectStmt.setInt(1, conteudoId);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                String titulo = rs.getString("nome");
                int horas = rs.getInt("horas_estudar");
                int materiaId = rs.getInt("materia_id");
    
                // Salva no resumo
                Resumo resumo = new Resumo(materiaId, titulo, horas);
                ResumoDAO resumoDAO = new ResumoDAO(conexao);
                resumoDAO.salvarResumo(resumo);
                // Deleta o conteúdo
                deletar(conteudoId);
            }
        }
    }
    
    public boolean atualizarEstudado(int conteudoId, boolean estudado) {
        String sql = "UPDATE Conteudo SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setBoolean(1, estudado);
            stmt.setInt(2, conteudoId);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void atualizar(Conteudo conteudo) throws SQLException {
        String sql = "UPDATE conteudo SET nome = ?, descricao = ?, horas_estudar = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, conteudo.getTitulo());
            stmt.setString(2, conteudo.getDescricao());
            stmt.setInt(3, conteudo.getHorasPlanejadas());
            stmt.setInt(4, conteudo.getId());
            stmt.executeUpdate();
    }
}

    // Métodos para atualizar, deletar, etc, podem ser adicionados aqui
}
