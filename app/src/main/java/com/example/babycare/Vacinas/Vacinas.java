package com.example.babycare.Vacinas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.babycare.R;

import java.util.List;

public class Vacinas extends AppCompatActivity implements View.OnClickListener {
    int usuarioLogadoId;
    TextView btVoltarVacinas;
    LinearLayout AddNovoRegistroVacinas, containerVacinasPendentes, containerVacinasAplicadas;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuarioLogadoId = getIntent().getIntExtra("id_usuario", -1);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacinas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btVoltarVacinas = findViewById(R.id.btVoltarVacinas);
        AddNovoRegistroVacinas = findViewById(R.id.AddNovoRegistroVacinas);
        containerVacinasPendentes = findViewById(R.id.containerVacinasPendentes);
        containerVacinasAplicadas = findViewById(R.id.containerVacinasAplicadas);

        btVoltarVacinas.setOnClickListener(this);
        AddNovoRegistroVacinas.setOnClickListener(this);

        listarVacinas();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btVoltarVacinas) {
            finish();
        }

        if (v.getId() == R.id.AddNovoRegistroVacinas) {
            AdicionarVacinasDialog dialog = AdicionarVacinasDialog.novaInstancia(usuarioLogadoId);
            dialog.show(getSupportFragmentManager(), "dialog_vacina");
        }
    }

    void listarVacinas() {
        containerVacinasPendentes.removeAllViews();
        containerVacinasAplicadas.removeAllViews();

        VacinaDAO dao = new VacinaDAO(this);
        List<VacinaModel> lista = dao.listar(usuarioLogadoId);
        VacinaAdapter adapter = new VacinaAdapter(this, lista, usuarioLogadoId);

        for (int i = 0; i < adapter.getCount(); i++) {
            View itemView = adapter.getView(i, null, null);
            VacinaModel vacina = lista.get(i);

            if (vacina.getDataAplicacao().isEmpty()) {
                containerVacinasPendentes.addView(itemView);
            } else {
                containerVacinasAplicadas.addView(itemView);
            }
        }
    }
}
