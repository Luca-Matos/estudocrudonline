package model;

public class Conteudo {
    private int id;
    private String titulo;
    private boolean estudado;
    private int horasPlanejadas;
    private int materiaId;

    public Conteudo(String titulo, boolean estudado, int horasPlanejadas, int materiaId) {
        this.titulo = titulo;
        this.estudado = estudado;
        this.horasPlanejadas = horasPlanejadas;
        this.materiaId = materiaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isEstudado() {
        return estudado;
    }

    public void setEstudado(boolean estudado) {
        this.estudado = estudado;
    }

    public int getHorasPlanejadas() {
        return horasPlanejadas;
    }

    public void setHorasPlanejadas(int horasPlanejadas) {
        this.horasPlanejadas = horasPlanejadas;
    }

    public int getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(int materiaId) {
        this.materiaId = materiaId;
    }
    
}
