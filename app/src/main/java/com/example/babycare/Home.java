package com.example.babycare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.babycare.Alimentacao.Alimentacao;
import com.example.babycare.Consultas.Consultas;
import com.example.babycare.Fraldas.Fraudas;
import com.example.babycare.Sono.Sono;
import com.example.babycare.Vacinas.Vacinas;

public class Home extends AppCompatActivity implements View.OnClickListener {

    TextView txtUsuario;
    LinearLayout btAlimentacao, btVacina, btSono, btConsultas, btFrauda, btMenu;
    int idUsuario;  // variável para guardar o id do usuário logado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtUsuario = findViewById(R.id.txtUsuario);

        btAlimentacao = findViewById(R.id.btAlimentacao);
        btVacina = findViewById(R.id.btVacina);
        btSono = findViewById(R.id.btSono);
        btConsultas = findViewById(R.id.btConsultas);
        btFrauda = findViewById(R.id.btFrauda);
        btMenu = findViewById(R.id.btMenu);

        // Recebe o nome do usuário pelo Intent (se você estiver passando)
        String nomeUsuario = getIntent().getStringExtra("nomeUsuario");
        txtUsuario.setText(nomeUsuario);

        // Recupera o id do usuário logado do SharedPreferences
        idUsuario = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("id_usuario", -1);

        btAlimentacao.setOnClickListener(this);
        btVacina.setOnClickListener(this);
        btSono.setOnClickListener(this);
        btConsultas.setOnClickListener(this);
        btFrauda.setOnClickListener(this);
        btMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btAlimentacao) {
            Intent telaAlimentacao = new Intent(this, Alimentacao.class);
            // Passa o id do usuário para a tela Alimentacao
            telaAlimentacao.putExtra("id_usuario", idUsuario);
            startActivity(telaAlimentacao);
        }

        if (v.getId() == R.id.btVacina) {
            Intent telaVacinas = new Intent(this, Vacinas.class);
            telaVacinas.putExtra("id_usuario", idUsuario);
            startActivity(telaVacinas);
        }
        if (v.getId() == R.id.btSono) {
            Intent telaSono = new Intent(this, Sono.class);
            telaSono.putExtra("id_usuario", idUsuario);
            startActivity(telaSono);
        }

        if (v.getId() == R.id.btConsultas) {
            Intent telaConsultas = new Intent(this, Consultas.class);
            telaConsultas.putExtra("id_usuario", idUsuario);
            startActivity(telaConsultas);
        }

        if (v.getId() == R.id.btFrauda) {
            Intent telaFraudas = new Intent(this, Fraudas.class);
            telaFraudas.putExtra("id_usuario", idUsuario); // <-- importante
            startActivity(telaFraudas);
        }
        if(v.getId() == R.id.btMenu){
            Intent telaMenu = new Intent(this, Menu.class);
            telaMenu.putExtra("id_usuario", idUsuario);
            startActivity(telaMenu);
        }
    }
}
