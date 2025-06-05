package com.example.babycare;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teste.R;

import java.util.List;

public class AlimentacoesActivity extends AppCompatActivity {

    private ListView listViewAlimentacoes;
    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentacoes);

        idUsuario = SessionManager.getInstance(this).getIdUsuario(); // pega da sessão
        listViewAlimentacoes = findViewById(R.id.listViewAlimentacoes);

        com.example.babycare.AlimentacaoDAO dao = new com.example.babycare.AlimentacaoDAO(this);
        List<String> lista = dao.listarPorUsuario(idUsuario);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, lista);

        listViewAlimentacoes.setAdapter(adapter);
    }
}
