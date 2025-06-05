package com.example.babycare.Vacinas;

public class VacinaModel {
    private final int id;
    private final String nome;
    private final String dataAplicacao;
    private final String dataPrevista;
    private final String observacao;
    private final int usuarioId;

    public VacinaModel(int id, String nome, String dataAplicacao, String dataPrevista, String observacao, int usuarioId) {
        this.id = id;
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
        this.dataPrevista = dataPrevista;
        this.observacao = observacao;
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDataAplicacao() {
        return dataAplicacao;
    }

    public String getDataPrevista() {
        return dataPrevista;
    }

    public String getObservacao() {
        return observacao;
    }

    public int getUsuarioId() {
        return usuarioId;
    }
}
