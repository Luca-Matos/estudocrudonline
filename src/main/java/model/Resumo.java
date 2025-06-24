package model;

public class Resumo {
    private int id;
    private int materiaId;
    private String conteudoTitulo;
    private int horasEstudadas;
   

    // Construtores
    public Resumo() {}

    public Resumo(int materiaId, String conteudoTitulo, int horasEstudadas) {
        this.materiaId = materiaId;
        this.conteudoTitulo = conteudoTitulo;
        this.horasEstudadas = horasEstudadas;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(int materiaId) {
        this.materiaId = materiaId;
    }

    public String getConteudoTitulo() {
        return conteudoTitulo;
    }

    public void setConteudoTitulo(String conteudoTitulo) {
        this.conteudoTitulo = conteudoTitulo;
    }

    public int getHorasEstudadas() {
        return horasEstudadas;
    }

    public void setHorasEstudadas(int horasEstudadas) {
        this.horasEstudadas = horasEstudadas;
    }

   
}