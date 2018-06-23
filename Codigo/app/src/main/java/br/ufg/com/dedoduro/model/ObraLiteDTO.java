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
}
