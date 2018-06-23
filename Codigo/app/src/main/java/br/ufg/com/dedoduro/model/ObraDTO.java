package br.ufg.com.dedoduro.model;

public class ObraDTO {
    String idObra;
    String idUser;
    String imageURL;
    String nome;
    String local;
    String inicioObra;
    String previsaoDeConclusao;
    String descricao;
    String porcentagemDeConclusao;

    public ObraDTO() {

    }

    public ObraDTO(String idObra, String idUser, String imageURL, String nome, String local,
                   String inicioObra, String previsaoDeConclusao, String descricao,
                   String porcentagemDeConclusao) {
        this.idObra = idObra;
        this.idUser = idUser;
        this.imageURL = imageURL;
        this.nome = nome;
        this.local = local;
        this.inicioObra = inicioObra;
        this.previsaoDeConclusao = previsaoDeConclusao;
        this.descricao = descricao;
        this.porcentagemDeConclusao = porcentagemDeConclusao;
    }

}
