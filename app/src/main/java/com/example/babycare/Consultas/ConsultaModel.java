package com.example.babycare.Consultas;


public class ConsultaModel {
    private int id;
    private String nomeMed;
    private String especialidade;
    private String data;
    private String horario;
    private String local;
    private String observacao;

    public ConsultaModel(int id, String nomeMed, String especialidade, String data, String horario, String local,String observacao) {
        this.id = id;
        this.nomeMed = nomeMed;
        this.especialidade = especialidade;
        this.data = data;
        this.horario = horario;
        this.local = local;
        this.observacao = observacao;
    }

    public int getId() {
        return id;
    }

    public String getNomeMed() {
        return nomeMed;
    }

    public String getEspecialidade(){ return especialidade;}

    public String getData() {
        return data;
    }

    public String getHorario(){return horario;}

    public String getLocal(){ return local;}

    public String getObservacao() {
        return observacao;
    }
}
