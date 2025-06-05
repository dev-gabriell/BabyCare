package com.example.babycare.Fraldas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.babycare.CriaBanco;

import java.util.ArrayList;
import java.util.List;

public class FraldaDAO {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public FraldaDAO(Context context) {
        banco = new CriaBanco(context);
    }

    public long inserir(String tipo, String data, String horario, String quantidade, int usuarioId) {
        db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("tipo", tipo);
        valores.put("data", data);
        valores.put("horario", horario);
        valores.put("quantidade", Integer.parseInt(quantidade));
        valores.put("usuario_id", usuarioId);  // adiciona o usuário aqui

        long resultado = db.insert("fraldas", null, valores);
        db.close();
        return resultado;
    }

    public int getTotalFraldas(int usuarioId) {
        db = banco.getReadableDatabase();
        int total = 0;

        Cursor cursor = db.rawQuery("SELECT SUM(quantidade) AS total FROM fraldas WHERE usuario_id = ?", new String[]{String.valueOf(usuarioId)});
        if (cursor != null && cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
            cursor.close();
        }

        db.close();
        return total;
    }

    public List<FraldaModel> getAll(int usuarioLogadoId) {
        List<FraldaModel> fraldasList = new ArrayList<>();
        db = banco.getReadableDatabase();

        String query = "SELECT * FROM fraldas WHERE usuario_id = ? ORDER BY id DESC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(usuarioLogadoId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
                String horario = cursor.getString(cursor.getColumnIndexOrThrow("horario"));
                int quantidade = cursor.getInt(cursor.getColumnIndexOrThrow("quantidade"));

                FraldaModel fralda = new FraldaModel(id, tipo, data, horario, quantidade);
                fraldasList.add(fralda);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Log.w("FraldaDAO", "Nenhum registro encontrado na tabela fraldas para usuário " + usuarioLogadoId);
        }

        db.close();
        return fraldasList;
    }


    public String excluir(int id) {
        db = banco.getWritableDatabase();

        int linhasAfetadas = db.delete("fraldas", "id = ?", new String[]{String.valueOf(id)});
        db.close();

        if (linhasAfetadas > 0) {
            return "Registro Excluído";
        } else {
            return "Erro ao Excluir";
        }
    }
}
