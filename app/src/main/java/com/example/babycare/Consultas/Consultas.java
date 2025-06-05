package com.example.babycare.Consultas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.babycare.Alimentacao.AdicionarAlimentacaoDialog;
import com.example.babycare.Alimentacao.AlimentacaoAdapter;
import com.example.babycare.Alimentacao.AlimentacaoDAO;
import com.example.babycare.Alimentacao.AlimentacaoModel;
import com.example.babycare.R;

import java.util.List;

public class Consultas extends AppCompatActivity implements View.OnClickListener {

    private int idUsuario;
    TextView btVoltarConsultas;
    LinearLayout AddNovoRegistroConsultas, containerRegistrosConsultas;

    ConsultaDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        idUsuario = getIntent().getIntExtra("id_usuario", -1);

        setContentView(R.layout.activity_consultas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btVoltarConsultas = findViewById(R.id.btVoltarConsultas);
        AddNovoRegistroConsultas = findViewById(R.id.AddNovoRegistroConsultas);
        containerRegistrosConsultas = findViewById(R.id.containerRegistrosConsultas);

        dao = new ConsultaDAO(this);

        btVoltarConsultas.setOnClickListener(this);
        AddNovoRegistroConsultas.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btVoltarConsultas) {
            finish();  // Redireciona para a tela Home novamente
        }

        if (v.getId() == R.id.AddNovoRegistroConsultas) {
            AdicionarConsultaMed dialog = AdicionarConsultaMed.novaInstancia(idUsuario);
            dialog.show(getSupportFragmentManager(), "dialog_consultas");
        }
    }
    protected void onResume() {
        super.onResume();
        carregarRegistros(); // Carregar os registros sempre que a activity for retomada
    }

    void carregarRegistros() {
        List<ConsultaModel> consultaList = dao.getAll(idUsuario);
        ConsultaAdapter adapter = new ConsultaAdapter(this, consultaList);
        containerRegistrosConsultas.removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++) {
            View itemView = adapter.getView(i, null, containerRegistrosConsultas);
            containerRegistrosConsultas.addView(itemView);
        }
    }
}