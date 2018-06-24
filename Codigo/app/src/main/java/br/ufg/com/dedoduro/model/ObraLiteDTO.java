package br.ufg.com.dedoduro.model;

public class ObraLiteDTO {
    String idObra;
    String nome;
    String local;

    public ObraLiteDTO() {

    }

    public ObraLiteDTO(String idObra, String nome, String local) {
        this.idObra = idObra;
        this.nome = nome;
        this.local = local;
    }

    public String getIdObra() {
        return idObra;
    }

    public void setIdObra(String idObra) {
        this.idObra = idObra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
