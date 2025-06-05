package com.example.babycare.Consultas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.babycare.CriaBanco;

import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public ConsultaDAO(Context context) {
        banco = new CriaBanco(context);
    }

    public long inserir(String nomeMed, String especialidade, String data, String horario, String local, String observacao, int usuarioId) {
        db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nomeMed", nomeMed);
        valores.put("especialidade", especialidade);
        valores.put("data", data);
        valores.put("horario", horario);
        valores.put("local", local);
        valores.put("observacao", observacao);
        valores.put("usuario_id", usuarioId);

        long resultado = db.insert("consultas", null, valores);
        db.close();
        return resultado;
    }

    // Método para recuperar todos os registros em ordem decrescente de ID
    public List<ConsultaModel> getAll(int usuarioId) {
        List<ConsultaModel> consultaList = new ArrayList<>();
        db = banco.getReadableDatabase();

        String query = "SELECT * FROM consultas WHERE usuario_id = ? ORDER BY id DESC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(usuarioId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // (extrai colunas como antes)

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nome = cursor.getString(cursor.getColumnIndex("nomeMed"));
                String espec = cursor.getString(cursor.getColumnIndex("especialidade"));
                String data = cursor.getString(cursor.getColumnIndex("data"));
                String horario = cursor.getString(cursor.getColumnIndex("horario"));
                String local = cursor.getString(cursor.getColumnIndex("local"));
                String obs = cursor.getString(cursor.getColumnIndex("observacao"));

                ConsultaModel consulta = new ConsultaModel(id, nome, espec, data, horario, local, obs);
                consultaList.add(consulta);

            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return consultaList;
    }


    public String excluirDadosConsultas(int id) {
        String msg = "Registro Excluído";
        db = banco.getWritableDatabase();

        int linhas = db.delete("consultas", "id = ?", new String[]{String.valueOf(id)});

        if (linhas < 1) {
            msg = "Erro ao Excluir";
        }

        db.close();
        return msg;
    }
}
