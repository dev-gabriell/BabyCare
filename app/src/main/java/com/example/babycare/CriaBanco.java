package com.example.babycare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco_exemplo.db";
    private static final int VERSAO = 13;

    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUsuarios = "CREATE TABLE usuarios (" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "email TEXT," +
                "senha TEXT)";
        db.execSQL(sqlUsuarios);

        String sqlAlimentacao = "CREATE TABLE alimentacao (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo TEXT," +
                "data TEXT," +
                "observacao TEXT," +
                "usuario_id INTEGER," + // chave estrangeira
                "FOREIGN KEY(usuario_id) REFERENCES usuarios(codigo))";

        db.execSQL(sqlAlimentacao);

        String sqlVacinas = "CREATE TABLE vacinas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "data_aplicacao TEXT," +
                "data_prevista TEXT," +
                "observacao TEXT," +
                "usuario_id INTEGER," +
                "FOREIGN KEY(usuario_id) REFERENCES usuarios(codigo))";
        db.execSQL(sqlVacinas);

        String sqlSono = "CREATE TABLE sono (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "data TEXT NOT NULL," +
                "horario_inicio TEXT NOT NULL," +
                "horario_fim TEXT NOT NULL," +
                "observacao TEXT," +
                "usuario_id INTEGER," + // chave estrangeira
                "FOREIGN KEY(usuario_id) REFERENCES usuarios(codigo))";
        db.execSQL(sqlSono);

        String sqlConsultas = "CREATE TABLE consultas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomeMed TEXT NOT NULL," +
                "especialidade TEXT NOT NULL," +
                "data TEXT NOT NULL," +
                "horario TEXT NOT NULL," +
                "local TEXT NOT NULL," +
                "observacao TEXT," +
                "usuario_id INTEGER," +
                "FOREIGN KEY(usuario_id) REFERENCES usuarios(codigo))";
        db.execSQL(sqlConsultas);


        String sqlFraldas = "CREATE TABLE fraldas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "data TEXT NOT NULL," +
                "quantidade int NOT NULL," +
                "horario TEXT NOT NULL," +
                "tipo TEXT NOT NULL," +
                "usuario_id INTEGER," + // chave estrangeira
                "FOREIGN KEY(usuario_id) REFERENCES usuarios(codigo))";
        db.execSQL(sqlFraldas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS alimentacao");
        db.execSQL("DROP TABLE IF EXISTS vacinas");
        db.execSQL("DROP TABLE IF EXISTS sono");
        db.execSQL("DROP TABLE IF EXISTS consultas");
        db.execSQL("DROP TABLE IF EXISTS fraldas");
        onCreate(db);
    }
}
