package com.example.babycare;

import android.content.Intent;
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

public class Cadastre_Se extends AppCompatActivity implements View.OnClickListener {
    EditText TxtNomeCad, TxtEmailCad, TxtSenhaCad, TxtConfSenhaCad;
    Button btCriarConta, btEntrarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastre_se);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialização dos componentes
        TxtNomeCad = findViewById(R.id.TxtNomeCad);
        TxtEmailCad = findViewById(R.id.TxtEmailCad); // Corrigido aqui
        TxtSenhaCad = findViewById(R.id.TxtSenhaCad);
        TxtConfSenhaCad = findViewById(R.id.TxtConfSenhaCad);
        btCriarConta = findViewById(R.id.btCriarConta);
        btEntrarCad = findViewById(R.id.btEntrarCad);

        btCriarConta.setOnClickListener(this);
        btEntrarCad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btEntrarCad) {
            // Clicou em "Entrar" → vai direto pra tela de login
            Intent telaEntrar = new Intent(this, MainActivity.class);
            startActivity(telaEntrar);
            finish();
            return;
        }

        if (id == R.id.btCriarConta) {
            // Criar Conta → só entra se passar pela validação
            String _nome = TxtNomeCad.getText().toString().trim();
            String _email = TxtEmailCad.getText().toString().trim();
            String _senha = TxtSenhaCad.getText().toString();
            String _confSenha = TxtConfSenhaCad.getText().toString();

            if (_nome.isEmpty()) {
                mostrarMensagem("O campo NOME deve ser preenchido!");
            } else if (_email.isEmpty()) {
                mostrarMensagem("O campo EMAIL deve ser preenchido!");
            } else if (_senha.isEmpty()) {
                mostrarMensagem("O campo SENHA deve ser preenchido!");
            } else if (_confSenha.isEmpty()) {
                mostrarMensagem("O campo CONFIRMAR SENHA deve ser preenchido!");
            } else if (!_senha.equals(_confSenha)) {
                mostrarMensagem("Os campos SENHA e CONFIRMAR SENHA devem ser iguais!");
            } else {
                BancoControllerUsuarios bd = new BancoControllerUsuarios(getBaseContext());
                String resultado = bd.insereDados(_nome, _email, _senha);
                mostrarMensagem(resultado);
                limpar();

                Intent telaLogin = new Intent(this, MainActivity.class);
                startActivity(telaLogin);
                finish();
            }
        }
    }

    public void limpar() {
        TxtNomeCad.setText("");
        TxtEmailCad.setText("");
        TxtSenhaCad.setText("");
        TxtConfSenhaCad.setText("");
    }

    private void mostrarMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }
}
