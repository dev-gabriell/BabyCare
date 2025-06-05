package com.example.babycare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlimentacaoDAO {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public AlimentacaoDAO(Context context) {
        banco = new CriaBanco(context);
    }

    public long inserir(int idUsuario, String tipo, String data, String obs) {
        db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("id_usuario", idUsuario);
        valores.put("tipo", tipo);
        valores.put("data", data);
        valores.put("observacao", obs);

        long resultado = db.insert("alimentacao", null, valores);
        db.close();
        return resultado;
    }

    public List<String> listarPorUsuario(int idUsuario) {
        List<String> lista = new ArrayList<>();
        db = banco.getReadableDatabase();

        Cursor cursor = db.query("alimentacao", null, "id_usuario = ?",
                new String[]{String.valueOf(idUsuario)}, null, null, "data DESC");

        while (cursor.moveToNext()) {
            String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));
            String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
            String obs = cursor.getString(cursor.getColumnIndexOrThrow("observacao"));

            String item = "Tipo: " + tipo + "\nData: " + data + "\nObs: " + obs;
            lista.add(item);
        }

        cursor.close();
        db.close();
        return lista;
    }
}
