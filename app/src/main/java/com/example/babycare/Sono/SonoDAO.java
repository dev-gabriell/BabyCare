package com.example.babycare.Sono;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.babycare.CriaBanco;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SonoDAO {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public SonoDAO(Context context) {
        banco = new CriaBanco(context);
    }

    public long inserir(String usuarioId, String editDataSono, String horaInicio, String horaFim, String observacao) {
        db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("usuario_id", usuarioId);
        valores.put("data", editDataSono);
        valores.put("horario_inicio", horaInicio);
        valores.put("horario_fim", horaFim);
        valores.put("observacao", observacao);

        long resultado = db.insert("sono", null, valores);
        db.close();
        return resultado;
    }

    public long inserirSono(SonoModel sono, String usuarioId) {
        db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("data", sono.getData());
        values.put("horario_inicio", sono.getHorarioInicio());
        values.put("horario_fim", sono.getHorarioFim());
        values.put("observacao", sono.getObservacao());
        values.put("usuario_id", usuarioId);

        long resultado = db.insert("sono", null, values);
        db.close();
        return resultado;
    }


    public List<SonoModel> getAll(String usuarioId) {
        List<SonoModel> lista = new ArrayList<>();

        db = banco.getReadableDatabase();  // <<< ESSA LINHA ESTÁ FALTANDO

        Cursor cursor = db.rawQuery("SELECT * FROM sono WHERE usuario_id = ? ORDER BY data DESC", new String[]{usuarioId});

        if (cursor.moveToFirst()) {
            do {
                SonoModel sono = new SonoModel();
                sono.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                sono.setData(cursor.getString(cursor.getColumnIndexOrThrow("data")));
                sono.setHorarioInicio(cursor.getString(cursor.getColumnIndexOrThrow("horario_inicio")));
                sono.setHorarioFim(cursor.getString(cursor.getColumnIndexOrThrow("horario_fim")));
                sono.setObservacao(cursor.getString(cursor.getColumnIndexOrThrow("observacao")));
                sono.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id")));

                lista.add(sono);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();  // Feche também aqui para não vazar conexões
        return lista;
    }


    public String excluirSono(int id){
        String msg = "Registro Excluído";

        db = banco.getReadableDatabase();

        String condicao = "id = ?";
        int linhas = db.delete("sono", condicao, new String[]{String.valueOf(id)});

        if (linhas < 1) {
            msg = "Erro ao Excluir";
        }

        db.close();
        return msg;
    }

    public int getTotalRegistros(String usuarioId){
        int total = 0;
        db = banco.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM sono WHERE usuario_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{usuarioId});

        if(cursor != null){
            if(cursor.moveToFirst()){
                total = cursor.getInt(0);
            }
            cursor.close();
        }
        db.close();
        return total;
    }

    public long getMediaSonoMillis(String usuarioId) {
        db = banco.getReadableDatabase();

        String query = "SELECT horario_inicio, horario_fim FROM sono WHERE usuario_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{usuarioId});

        long totalDuracao = 0;
        int totalRegistros = 0;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String inicio = cursor.getString(cursor.getColumnIndexOrThrow("horario_inicio"));
                String fim = cursor.getString(cursor.getColumnIndexOrThrow("horario_fim"));

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    Date horaInicio = sdf.parse(inicio);
                    Date horaFim = sdf.parse(fim);

                    long duracao = horaFim.getTime() - horaInicio.getTime();

                    if (duracao < 0) {
                        duracao += 24 * 60 * 60 * 1000; // adiciona 24h se passou da meia-noite
                    }

                    totalDuracao += duracao;
                    totalRegistros++;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();

        if (totalRegistros == 0) return 0;

        return totalDuracao / totalRegistros;
    }
}
