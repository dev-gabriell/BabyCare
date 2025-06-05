package com.example.babycare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoControllerUsuarios {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoControllerUsuarios(Context context) {
        banco = new CriaBanco(context);
    }

    // Método para inserir novo usuário
    public String insereDados(String _nome, String _email, String _senha) {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("nome", _nome);
        valores.put("email", _email);
        valores.put("senha", _senha);

        resultado = db.insert("usuarios", null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao efetuar o Cadastro";
        else
            return "Cadastro efetuado com sucesso";
    }

    // Método atualizado para validar login e retornar o ID do usuário
    public int validaLogin(String email, String senha) {
        db = banco.getReadableDatabase();

        String[] campos = {"codigo"}; // agora buscamos o ID
        String where = "email = ? AND senha = ?";
        String[] argumentos = {email, senha};

        Cursor cursor = db.query("usuarios", campos, where, argumentos, null, null, null);

        int idUsuario = -1;

        if (cursor.moveToFirst()) {
            idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("codigo"));
        }

        cursor.close();
        db.close();

        return idUsuario;
    }

    public String buscarNomePorEmail(String email) {
        db = banco.getReadableDatabase();

        String[] campos = {"nome"};
        String where = "email = ?";
        String[] argumentos = {email};

        Cursor cursor = db.query("usuarios", campos, where, argumentos, null, null, null);

        String nome = "";

        if (cursor.moveToFirst()) {
            nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        }

        cursor.close();
        db.close();

        return nome;
    }
}
