package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> listarResumo() throws SQLException {
    String sql = """
        SELECT r.data_estudo, m.nome AS materia, r.conteudo_titulo, r.horas_estudadas
        FROM resumos r
        JOIN materia m ON r.materia_id = m.id
        ORDER BY r.data_estudo DESC
    """;

    List<Map<String, Object>> lista = new ArrayList<>();
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Map<String, Object> linha = new HashMap<>();
            linha.put("dataEstudo", rs.getTimestamp("data_estudo").toString()); // ou .toLocalDateTime().toString()
            linha.put("materia", rs.getString("materia"));
            linha.put("conteudoTitulo", rs.getString("conteudo_titulo"));
            linha.put("horasEstudadas", rs.getInt("horas_estudadas"));
            lista.add(linha);
        }
    }
    return lista;
}



}
