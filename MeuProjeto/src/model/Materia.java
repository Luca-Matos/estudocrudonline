package model;

public class Materia {
    private int id;
    private String nome;
    private String cor;
    private int usuarioId;
    
    public Materia() {

    }

    public Materia(String nome, String cor, int usuarioId) {
        this.nome = nome;
        this.cor = cor;
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    

    
}

