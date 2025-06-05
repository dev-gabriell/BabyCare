package com.example.babycare.Alimentacao;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.babycare.R;

import java.util.List;

public class Alimentacao extends AppCompatActivity implements View.OnClickListener {

    private int idUsuario;  // <- mover pra atributo da classe

    TextView btVoltarAlimentacao;
    LinearLayout AddNovoRegistroAlimentacao;
    LinearLayout containerRegistros;
    AlimentacaoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        idUsuario = getIntent().getIntExtra("id_usuario", -1); // aqui preenche o atributo da classe

        dao = new AlimentacaoDAO(this);

        setContentView(R.layout.activity_alimentacao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btVoltarAlimentacao = findViewById(R.id.btVoltarAlimentacao);
        AddNovoRegistroAlimentacao = findViewById(R.id.AddNovoRegistroAlimentacao);
        containerRegistros = findViewById(R.id.containerRegistros);

        btVoltarAlimentacao.setOnClickListener(this);
        AddNovoRegistroAlimentacao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btVoltarAlimentacao) {
            finish();  // volta para Home
        }

        if (v.getId() == R.id.AddNovoRegistroAlimentacao) {
            AdicionarAlimentacaoDialog dialog = AdicionarAlimentacaoDialog.novaInstancia(idUsuario);
            dialog.show(getSupportFragmentManager(), "dialog_alimentacao");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarRegistros(); // recarrega a lista toda vez que volta para a tela
    }

    void carregarRegistros() {
        List<AlimentacaoModel> alimentacaoList = dao.getAll(idUsuario);
        AlimentacaoAdapter adapter = new AlimentacaoAdapter(this, alimentacaoList);
        containerRegistros.removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++) {
            View itemView = adapter.getView(i, null, containerRegistros);
            containerRegistros.addView(itemView);
        }
    }
}

