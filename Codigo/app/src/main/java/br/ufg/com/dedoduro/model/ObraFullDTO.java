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

    public String getIdObra() {
        return idObra;
    }

    public void setIdObra(String idObra) {
        this.idObra = idObra;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getInicioObra() {
        return inicioObra;
    }

    public void setInicioObra(String inicioObra) {
        this.inicioObra = inicioObra;
    }

    public String getPrevisaoDeConclusao() {
        return previsaoDeConclusao;
    }

    public void setPrevisaoDeConclusao(String previsaoDeConclusao) {
        this.previsaoDeConclusao = previsaoDeConclusao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPorcentagemDeConclusao() {
        return porcentagemDeConclusao;
    }

    public void setPorcentagemDeConclusao(String porcentagemDeConclusao) {
        this.porcentagemDeConclusao = porcentagemDeConclusao;
    }
}
