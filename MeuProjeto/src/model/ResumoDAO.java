package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResumoDAO {
    private Connection connection;

    public ResumoDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvarResumo(int materiaId, String tituloConteudo, int horasEstudadas) throws SQLException {
        String sql = "INSERT INTO resumos (materia_id, conteudo_titulo, horas_estudadas) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, materiaId);
            stmt.setString(2, tituloConteudo);
            stmt.setInt(3, horasEstudadas);
            stmt.executeUpdate();
        }
    }

    public List<String> listarResumo() throws SQLException {
    String sql = """
        SELECT r.data_estudo, m.nome AS materia, r.conteudo_titulo, r.horas_estudadas
        FROM resumos r
        JOIN materias m ON r.materia_id = m.id
        ORDER BY r.data_estudo DESC
    """;

    List<String> lista = new ArrayList<>();
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
