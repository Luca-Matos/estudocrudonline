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

        post("/register", (req, res) -> {
            try (Connection conn = Conexao.getConexao()) {
                UsuarioDAO dao = new UsuarioDAO(conn);
                Usuario u = new Usuario(req.queryParams("nome"), req.queryParams("email"), req.queryParams("senha"));
                dao.inserir(u);
                System.out.println("Usuário registrado: " + u.getEmail());
                res.redirect("/index");
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

        get("/materia", (req, res) -> {
            String idStr = req.queryParams("id");
            if (idStr == null) {
                res.status(400);
                return "ID da matéria não informado";
            }
        
            try (Connection conn = Conexao.getConexao()) {
                MateriaDAO dao = new MateriaDAO(conn);
                int id = Integer.parseInt(idStr);
                Materia m = dao.buscarPorId(id);
                if (m == null) {
                    res.status(404);
                    return "Matéria não encontrada";
                }
                res.type("application/json");
                return new Gson().toJson(m);
            }
        });

        post("/editar-materia", (req, res) -> {
            Usuario usuario = req.session().attribute("usuario");
            if (usuario == null) {
                res.status(401);
                return "Usuário não autenticado.";
            }
        
            try (Connection conn = Conexao.getConexao()) {
                MateriaDAO dao = new MateriaDAO(conn);
        
                // Parse do JSON
                Gson gson = new Gson();
                Materia materia = gson.fromJson(req.body(), Materia.class);
        
                dao.editar(materia);
        
                res.status(200);
                return "Matéria editada com sucesso.";
            } catch (Exception e) {
                res.status(500);
                return "Erro ao editar matéria: " + e.getMessage();
            }
        });
        
        
        // conteudos

        post("/criar-conteudo", (req, res) -> {
            Usuario usuario = req.session().attribute("usuario");
            if (usuario == null) {
                res.status(401);
                return "Não autorizado";
            }

            String titulo = req.queryParams("titulo");
            String descricao = req.queryParams("descricao");
            String cor = req.queryParams("cor");
            int horasPlanejadas = Integer.parseInt(req.queryParams("horasPlanejadas"));
            int materiaId = Integer.parseInt(req.queryParams("materiaId"));

            try (Connection conn = Conexao.getConexao()) {
                ConteudoDAO dao = new ConteudoDAO(conn);
                Conteudo c = new Conteudo();
                c.setTitulo(titulo);
                c.setDescricao(descricao);
                c.setCor(cor);
                c.setHorasPlanejadas(horasPlanejadas);
                c.setMateriaId(materiaId);
                c.setEstudado(false); // default

                dao.adicionar(c);

                res.status(201);
                return "Conteúdo criado com sucesso";
            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return "Erro ao criar conteúdo: " + e.getMessage();
            }
        });

        get("/conteudos", (req, res) -> {
            Usuario usuario = req.session().attribute("usuario");
            if (usuario == null) {
                res.status(401);
                return "Não autorizado";
            }

            String materiaIdStr = req.queryParams("materiaId");
            if (materiaIdStr == null) {
                res.status(400);
                return "Parâmetro materiaId é obrigatório";
            }

            int materiaId = Integer.parseInt(materiaIdStr);

            try (Connection conn = Conexao.getConexao()) {
                ConteudoDAO dao = new ConteudoDAO(conn);
                List<Conteudo> todos = dao.listarTodos();

                // filtrar por matéria
                List<Conteudo> filtrados = todos.stream()
                    .filter(c -> c.getMateriaId() == materiaId)
                    .toList();

                res.type("application/json");
                return new Gson().toJson(filtrados);
            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return "Erro ao buscar conteúdos: " + e.getMessage();
            }
        });

        post("/atualizar-estudado", (req, res) -> {
            try (Connection conn = Conexao.getConexao()) {
                int conteudoId = Integer.parseInt(req.queryParams("conteudoId"));
                boolean estudado = Boolean.parseBoolean(req.queryParams("estudado"));
        
                ConteudoDAO dao = new ConteudoDAO(conn);
                boolean sucesso = dao.atualizarEstudado(conteudoId, estudado);
        
                if (sucesso) {
                    res.status(200);
                    return "Atualizado com sucesso";
                } else {
                    res.status(500);
                    return "Erro ao atualizar";
                }
            } catch (Exception e) {
                res.status(400);
                return "Parâmetros inválidos: " + e.getMessage();
            }
        });
        
        post("/salvar-resultado-e-excluir", (req, res) -> {
            try (Connection conn = Conexao.getConexao()) {
                int conteudoId = Integer.parseInt(req.queryParams("conteudoId"));
        
                ConteudoDAO dao = new ConteudoDAO(conn);
                dao.salvarResultadoEExcluir(conteudoId);
    
                    return "Conteúdo salvo e excluído com sucesso";
            } catch (Exception e) {
                e.printStackTrace();
                res.status(400);
                return "Erro nos parâmetros ou na execução: " + e.getMessage();
            }
        });
        
        
        

    }
}