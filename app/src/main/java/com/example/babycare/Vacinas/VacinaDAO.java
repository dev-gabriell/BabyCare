package com.example.babycare.Vacinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.babycare.CriaBanco;

import java.util.ArrayList;
import java.util.List;

public class VacinaDAO {

    private SQLiteDatabase db;
    private CriaBanco criaBanco;

    public VacinaDAO(Context context) {
        criaBanco = new CriaBanco(context);
    }

    // Inserir vacina
    public long inserir(String nome, String dataAplicacao, String dataPrevista, String obs, int usuarioId) {
        db = criaBanco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("data_aplicacao", dataAplicacao);
        values.put("data_prevista", dataPrevista);
        values.put("observacao", obs);
        values.put("usuario_id", usuarioId);

        long resultado = db.insert("vacinas", null, values);
        db.close();
        return resultado;
    }

    // Listar vacinas de um usuário
    public List<VacinaModel> listar(int usuarioId) {
        List<VacinaModel> lista = new ArrayList<>();
        db = criaBanco.getReadableDatabase();

        String sql = "SELECT * FROM vacinas WHERE usuario_id = ? ORDER BY id DESC";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(usuarioId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                String dataAplicacao = cursor.getString(cursor.getColumnIndexOrThrow("data_aplicacao"));
                String dataPrevista = cursor.getString(cursor.getColumnIndexOrThrow("data_prevista"));
                String observacao = cursor.getString(cursor.getColumnIndexOrThrow("observacao"));

                VacinaModel vacina = new VacinaModel(id, nome, dataAplicacao, dataPrevista, observacao, usuarioId);
                lista.add(vacina);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return lista;
    }

    // Excluir vacina
    public boolean excluir(int id, int usuarioId) {
        db = criaBanco.getWritableDatabase();

        int linhasAfetadas = db.delete("vacinas", "id = ? AND usuario_id = ?",
                new String[]{String.valueOf(id), String.valueOf(usuarioId)});

        db.close();
        return linhasAfetadas > 0;
    }
}
