package com.example.babycare.Alimentacao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.babycare.CriaBanco;

import java.util.ArrayList;
import java.util.List;

public class AlimentacaoDAO {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public AlimentacaoDAO(Context context) {
        banco = new CriaBanco(context);
    }

    public long inserir(String tipo, String data, String obs, int usuarioId) {
        db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("tipo", tipo);
        valores.put("data", data);
        valores.put("observacao", obs);
        valores.put("usuario_id", usuarioId);

        long resultado = db.insert("alimentacao", null, valores);
        db.close();
        return resultado;
    }

    public List<AlimentacaoModel> getAll(int usuarioId) {
        List<AlimentacaoModel> alimentacaoList = new ArrayList<>();
        db = banco.getReadableDatabase();

        String query = "SELECT * FROM alimentacao WHERE usuario_id = ? ORDER BY id DESC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(usuarioId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
                String observacao = cursor.getString(cursor.getColumnIndexOrThrow("observacao"));

                AlimentacaoModel alimentacao = new AlimentacaoModel(id, tipo, data, observacao);
                alimentacaoList.add(alimentacao);
            } while (cursor.moveToNext());
        }

        if (cursor != null) cursor.close();
        db.close();

        return alimentacaoList;
    }

    public String excluirDados(int id) {
        db = banco.getReadableDatabase();
        int linhas = db.delete("alimentacao", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return linhas > 0 ? "Registro Excluído" : "Erro ao Excluir";
    }
}

