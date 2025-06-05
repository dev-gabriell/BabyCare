package com.example.babycare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btEntrar, btRegistre;
    EditText btEmailLog, btSenhaLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btEntrar = findViewById(R.id.btEntrar);
        btRegistre = findViewById(R.id.btRegistre);
        btEmailLog = findViewById(R.id.btEmailLog);
        btSenhaLog = findViewById(R.id.btSenhaLog);

        btEntrar.setOnClickListener(this);
        btRegistre.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btEntrar) {
            String email = btEmailLog.getText().toString().trim();
            String senha = btSenhaLog.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(this, "Preencha o campo Email para acessar o app", Toast.LENGTH_LONG).show();
                return;
            }

            if (senha.isEmpty()) {
                Toast.makeText(this, "Preencha o campo Senha para acessar o app", Toast.LENGTH_LONG).show();
                return;
            }

            BancoControllerUsuarios bd = new BancoControllerUsuarios(getBaseContext());
            int idUsuario = bd.validaLogin(email, senha);

            if (idUsuario != -1) {
                // Buscar nome do usuário pelo e-mail
                String nomeUsuario = bd.buscarNomePorEmail(email);

                // Salvar o ID do usuário logado
                SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("id_usuario", idUsuario);
                editor.apply();

                // Ir para tela Home, levando o nome
                Intent tela = new Intent(this, Home.class);
                tela.putExtra("nomeUsuario", nomeUsuario);
                startActivity(tela);
            } else {
                Toast.makeText(this, "Email ou senha incorretos!", Toast.LENGTH_LONG).show();
            }

        } else if (v.getId() == R.id.btRegistre) {
            Intent telaCadastro = new Intent(this, Cadastre_Se.class);
            startActivity(telaCadastro);
        }
    }
}
