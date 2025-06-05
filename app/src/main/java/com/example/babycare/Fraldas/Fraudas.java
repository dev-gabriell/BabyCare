package com.example.babycare.Fraldas;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class Fraudas extends AppCompatActivity implements View.OnClickListener {

    int usuarioLogadoId;
    TextView btVoltarFraldas;
    LinearLayout AddNovaFralda, containerRegistrosFralda;

    FraldaDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuarioLogadoId = getIntent().getIntExtra("id_usuario", -1);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fraudas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btVoltarFraldas = findViewById(R.id.btVoltarFraldas);
        AddNovaFralda = findViewById(R.id.AddNovaFralda);
        containerRegistrosFralda = findViewById(R.id.containerRegistrosFralda);

        btVoltarFraldas.setOnClickListener(this);
        AddNovaFralda.setOnClickListener(this);

        dao = new FraldaDAO(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarRegistros();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btVoltarFraldas) {
            finish();
        } else if (id == R.id.AddNovaFralda) {
            AdicionarFraldaDialog dialog = AdicionarFraldaDialog.novaInstancia(usuarioLogadoId);
            dialog.show(getSupportFragmentManager(), "dialog_fraldas");
        }
    }

    void carregarRegistros() {
        Log.d("Fraudas", "Usuario Logado ID: " + usuarioLogadoId);
        containerRegistrosFralda.removeAllViews();

        List<FraldaModel> fraldaList = dao.getAll(usuarioLogadoId);
        FraldaAdapter adapter = new FraldaAdapter(this, fraldaList);

        for (int i = 0; i < adapter.getCount(); i++) {
            View itemView = adapter.getView(i, null, containerRegistrosFralda);
            containerRegistrosFralda.addView(itemView);
        }

        int total = dao.getTotalFraldas(usuarioLogadoId);
        TextView totalFraldasText = findViewById(R.id.totalFraldas);
        totalFraldasText.setText(total + " fraldas");
    }
}
