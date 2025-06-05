package com.example.babycare.Sono;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.babycare.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Sono extends AppCompatActivity implements View.OnClickListener {

    private int idUsuario;
    LinearLayout containerRegistrosSono;
    TextView MediaSono, NumRegistros, btVoltarSono;
    Button IniciarSono, addSono;

    SonoDAO dao;

    private boolean sonoIniciado = false;
    private long inicioSonoMillis;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        idUsuario = getIntent().getIntExtra("id_usuario", -1);

        setContentView(R.layout.activity_sono);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btVoltarSono = findViewById(R.id.btVoltarSono);
        MediaSono = findViewById(R.id.MediaSono);
        NumRegistros = findViewById(R.id.NumRegistros);
        IniciarSono = findViewById(R.id.btnIniciarSono);
        addSono = findViewById(R.id.btnAdicionarSono);
        containerRegistrosSono = findViewById(R.id.containerRegistrosSono);

        dao = new SonoDAO(this);

        IniciarSono.setOnClickListener(this);
        addSono.setOnClickListener(this);
        btVoltarSono.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btVoltarSono) {
            finish();
        }

        if (v.getId() == R.id.btnIniciarSono) {
            if (!sonoIniciado) {
                // Inicia o sono
                inicioSonoMillis = System.currentTimeMillis();
                sonoIniciado = true;
                IniciarSono.setText("Finalizar Sono");
                IniciarSono.setBackgroundColor(getResources().getColor(R.color.vermelho_finalizar));
                Toast.makeText(this, "Sono iniciado!", Toast.LENGTH_SHORT).show();
            } else {
                // Finaliza o sono e salva no banco
                IniciarSono.setBackgroundColor(getResources().getColor(R.color.white));
                long fimSonoMillis = System.currentTimeMillis();
                sonoIniciado = false;
                IniciarSono.setText("Iniciar Sono");

                String data = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(inicioSonoMillis));
                String horaInicio = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(inicioSonoMillis));
                String horaFim = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(fimSonoMillis));

                SonoModel sonoModel = new SonoModel();
                sonoModel.setData(data);
                sonoModel.setHorarioInicio(horaInicio);
                sonoModel.setHorarioFim(horaFim);
                sonoModel.setObservacao(""); // Pode adicionar campo de input depois se quiser

                String resultado = String.valueOf(dao.inserirSono(sonoModel, String.valueOf(idUsuario)));
                Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();

                carregarRegistrosSono(); // Atualiza tela
            }
        }

        if (v.getId() == R.id.btnAdicionarSono) {
            AdicionarSonoDialog dialog = new AdicionarSonoDialog();
            Bundle args = new Bundle();
            args.putInt("id_usuario", idUsuario); // passa o ID para o dialog
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "adicionarSono");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarRegistrosSono(); // Atualiza os registros ao voltar
    }

    // Carrega os registros e atualiza o layout
    void carregarRegistrosSono() {
        List<SonoModel> sonoList = dao.getAll(String.valueOf(idUsuario));
        containerRegistrosSono.removeAllViews();

        if (sonoList == null || sonoList.isEmpty()) {
            TextView vazio = new TextView(this);
            vazio.setText("Nenhum registro de sono encontrado.");
            vazio.setPadding(16, 16, 16, 16);
            containerRegistrosSono.addView(vazio);
        } else {
            SonoAdapter adapter = new SonoAdapter(this, sonoList, String.valueOf(idUsuario));

            for (int i = 0; i < adapter.getCount(); i++) {
                View itemView = adapter.getView(i, null, containerRegistrosSono);
                containerRegistrosSono.addView(itemView);
            }
        }

        // Atualiza o número de registros
        int total = dao.getTotalRegistros(String.valueOf(idUsuario));
        NumRegistros.setText(String.valueOf(total));

        long mediaMillis = dao.getMediaSonoMillis(String.valueOf(idUsuario));
        long horas = mediaMillis / (1000 * 60 * 60);
        long minutos = (mediaMillis / (1000 * 60)) % 60;
        MediaSono.setText(String.format("%02dh %02dm", horas, minutos));
    }
}
