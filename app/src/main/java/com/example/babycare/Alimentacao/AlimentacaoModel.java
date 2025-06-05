package com.example.babycare.Alimentacao;

public class AlimentacaoModel {
    private int id;
    private String tipo;
    private String data;
    private String observacao;

    public AlimentacaoModel(int id, String tipo, String data, String observacao) {
        this.id = id;
        this.tipo = tipo;
        this.data = data;
        this.observacao = observacao;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getData() {
        return data;
    }

    public String getObservacao() {
        return observacao;
    }
}
