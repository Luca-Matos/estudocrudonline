package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResumoDAO {
    private Connection conexao;

    public ResumoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void salvarResumo(Resumo resumo) throws SQLException {
        String sql = "INSERT INTO resumos (materia_id, conteudo_titulo, horas_estudadas) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, resumo.getMateriaId());
            stmt.setString(2, resumo.getConteudoTitulo());
            stmt.setInt(3, resumo.getHorasEstudadas());
            stmt.executeUpdate();
        }
    }

    public List<String> listarResumo() throws SQLException {
        String sql = """
            SELECT r.data_estudo, m.nome AS materia, r.conteudo_titulo, r.horas_estudadas
            FROM resumos r
            JOIN materia m ON r.materia_id = m.id
            ORDER BY r.data_estudo DESC
        """;
    
        List<String> lista = new ArrayList<>();
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String linha = String.format(
                    "%s | %s - %s (%dh)",
                    rs.getTimestamp("data_estudo").toLocalDateTime().toLocalDate(),
                    rs.getString("materia"),
                    rs.getString("conteudo_titulo"),
                    rs.getInt("horas_estudadas")
                );
                lista.add(linha);
            }
        }
        return lista;
    }


}
