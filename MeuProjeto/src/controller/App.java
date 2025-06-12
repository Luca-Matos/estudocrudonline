package controller;

import model.Materia;
import model.MateriaDAO;
import model.Conteudo;
import model.ConteudoDAO;
import model.ResumoDAO;
import model.Usuario;
import model.UsuarioDAO;
import utils.Conexao;

import java.sql.Connection;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try (Connection conn = Conexao.getConexao()) {

            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            Usuario usuario = new Usuario("Teste", "teste@email.com", "1234");
            usuarioDAO.inserir(usuario); 
            System.out.println(usuario.getId());
            
            // Testar inserção de matéria
            MateriaDAO materiaDAO = new MateriaDAO(conn);
            Materia m = new Materia();
            m.setNome("História");
            m.setUsuarioId(usuario.getId());
            materiaDAO.adicionar(m);

            // Listar matérias
            for (Materia mat : materiaDAO.listarTodas()) {
                System.out.println("Matéria: " + mat.getNome());
            }

            // Testar inserção de conteúdo
            ConteudoDAO conteudoDAO = new ConteudoDAO(conn);
            Conteudo c = new Conteudo( "Idade Média", false, 2, "Estudo da sociedade feudal", m.getId());
            conteudoDAO.adicionar(c);

            // Testar salvar resultado e excluir
            conteudoDAO.salvarResultadoEExcluir(c.getId());

            // Testar mostrar resumo
            ResumoDAO resumoDAO = new ResumoDAO(conn);
            for (String r : resumoDAO.listarResumo()) {
                System.out.println("Resumo: " + r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
