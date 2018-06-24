package br.ufg.com.dedoduro.model;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class ObraFullDTO {
    String idObra;
    String idUser;
    String link;
    String nome;
    String local;
    String inicioObra;
    String previsaoDeConclusao;
    String descricao;
    String porcentagemDeConclusao;

    public ObraFullDTO() {

    }

    public ObraFullDTO(String idObra, String idUser, String link, String nome, String local,
                       String inicioObra, String previsaoDeConclusao, String descricao,
                       String porcentagemDeConclusao) {
        this.idObra = idObra;
        this.idUser = idUser;
        this.link = link;
        this.nome = nome;
        this.local = local;
        this.inicioObra = inicioObra;
        this.previsaoDeConclusao = previsaoDeConclusao;
        this.descricao = descricao;
        this.porcentagemDeConclusao = porcentagemDeConclusao;
    }

}
