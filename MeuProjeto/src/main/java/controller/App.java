package controller;

import java.sql.Connection;
import com.google.gson.Gson;
import java.util.List;
import static spark.Spark.*;
import spark.ModelAndView;
import model.Conteudo;
import model.ConteudoDAO;
import model.Materia;
import model.MateriaDAO;
import model.ResumoDAO;
import model.Usuario;
import model.UsuarioDAO;
import utils.Conexao;

public class App {
    public static void main(String[] args) {
        System.out.println("🚀 Iniciando o servidor Spark...");
        port(4567); // Define a porta explicitamente

        staticFiles.location("/public"); // Serve arquivos estáticos de /resources/public

        // Configura resposta para HTML
        after((req, res) -> res.type("text/html"));

        // Redireciona a rota raiz para o arquivo home.html
        get("/", (req, res) -> {
            System.out.println("Rota / acessada");
            res.redirect("/index.html");
            return null;
        });

        // Redireciona para login.html
        get("/login", (req, res) -> {
            System.out.println("Rota /login acessada");
            res.redirect("/login.html");
            return null;
        });

        // Redireciona para register.html
        get("/register", (req, res) -> {
            System.out.println("Rota /register acessada");
            res.redirect("/register.html");
            return null;
        });

        post("/register", (req, res) -> {
            try (Connection conn = Conexao.getConexao()) {
                UsuarioDAO dao = new UsuarioDAO(conn);
                Usuario u = new Usuario(req.queryParams("nome"), req.queryParams("email"), req.queryParams("senha"));
                dao.inserir(u);
                System.out.println("Usuário registrado: " + u.getEmail());
                res.redirect("/login");
            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return "Erro ao registrar usuário: " + e.getMessage();
            }
            return null;
        });

        post("/login", (req, res) -> {
            try (Connection conn = Conexao.getConexao()) {
                UsuarioDAO dao = new UsuarioDAO(conn);
                Usuario u = dao.buscarPorEmailSenha(req.queryParams("email"), req.queryParams("senha"));
                if (u != null) {
                    req.session(true).attribute("usuario", u);
                    System.out.println("Login efetuado: " + u.getEmail());
                    res.redirect("/estudo.html");
                } else {
                    System.out.println("Falha no login para email: " + req.queryParams("email"));
                    res.redirect("/login?erro=1");
                }
            }
            return null;
        });

        get("/index", (req, res) -> {
            Usuario u = req.session().attribute("usuario");
            if (u == null) {
                res.redirect("/login");
                return null;
            }
            System.out.println("Página index acessada pelo usuário: " + u.getEmail());
            res.redirect("/index.html");
            return null;
        });

        //materia

        post("/criar-materia", (req, res) -> {
            Usuario usuario = req.session().attribute("usuario");
            if (usuario == null) {
                res.redirect("/login");
                return null;
            }
        
            String nome = req.queryParams("nome");
            String cor = req.queryParams("cor");
        
            try (Connection conn = Conexao.getConexao()) {
                Materia materia = new Materia();
                materia.setNome(nome);
                materia.setCor(cor);
                materia.setUsuarioId(usuario.getId());
        
                MateriaDAO dao = new MateriaDAO(conn);
                dao.adicionar(materia);
        
                System.out.println("Matéria criada: " + nome + " (" + cor + ")");
                res.redirect("/estudo.html"); // redireciona de volta
            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return "Erro ao criar matéria: " + e.getMessage();
            }
        
            return null;
        });
        
        get("/materias", (req, res) -> {
            System.out.println("Rota /materias acessada");
            Usuario usuario = req.session().attribute("usuario");
            if (usuario == null) {
                res.status(401);
                return "Não autorizado";
            }
        
            try (Connection conn = Conexao.getConexao()) {
                MateriaDAO dao = new MateriaDAO(conn);
                List<Materia> materias = dao.listarPorUsuario(usuario.getId());
        
                res.type("application/json");
                return new Gson().toJson(materias);
            } catch (Exception e) {
                res.status(500);
                return "Erro ao buscar matérias: " + e.getMessage();
            }
        });


    }
}