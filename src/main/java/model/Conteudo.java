package model;

public class Conteudo {
    private int id;
    private String titulo;
    private boolean estudado;
    private int horasPlanejadas;
    private String descricao;
    private int materiaId;
    private String cor;
    
        public Conteudo() {}
    
        public String getDescricao() {
            return descricao;
        }
    
        public void setDescricao(String desc) {
            this.descricao = desc;
        }

        public String getCor() {
            return cor;
        }
    
        public void setCor(String cor) {
            this.cor = cor;
        }
    
        
    
        public Conteudo(String titulo, boolean estudado, int horasPlanejadas, String descricao, String cor, int materiaId) {
            this.titulo = titulo;
            this.estudado = estudado;
            this.horasPlanejadas = horasPlanejadas;
            this.materiaId = materiaId;
            this.cor = cor;
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
