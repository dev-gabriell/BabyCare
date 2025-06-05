package com.example.babycare.Fraldas;

public class FraldaModel {
    private int id;
    private String tipo;
    private String data;
    private String horario;
    private int quantidade;

    public FraldaModel(int id, String tipo, String data, String horario, int quantidade) {
        this.id = id;
        this.tipo = tipo;
        this.data = data;
        this.horario = horario;
        this.quantidade = quantidade;
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

    public String getHorario() {
        return horario;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
