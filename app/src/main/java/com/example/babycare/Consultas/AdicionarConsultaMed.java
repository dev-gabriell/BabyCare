package com.example.babycare.Consultas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.babycare.Alimentacao.AdicionarAlimentacaoDialog;
import com.example.babycare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdicionarConsultaMed extends DialogFragment {
    private int usuarioId;

    public static AdicionarConsultaMed novaInstancia(int usuarioId) {
        AdicionarConsultaMed dialog = new AdicionarConsultaMed();
        Bundle args = new Bundle();
        args.putInt("usuario_id", usuarioId);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        usuarioId = getArguments().getInt("usuario_id", -1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_adicionar_consultas, null);

        EditText editNomeMed = view.findViewById(R.id.edit_nome_med);
        EditText editEspecialidade = view.findViewById(R.id.edit_especialidade);
        EditText editData = view.findViewById(R.id.edit_data_consulta);
        EditText editHora = view.findViewById(R.id.edit_hora_consulta);
        EditText editLocal = view.findViewById(R.id.edit_local);
        EditText editObs = view.findViewById(R.id.edit_obsConsulta);
        Button btnAdicionar = view.findViewById(R.id.btn_adicionarConsultas);

        Calendar calendar = Calendar.getInstance();

        editData.setFocusable(false);
        editData.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(getContext(), (view1, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                editData.setText(dateFormat.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });

        editHora.setFocusable(false);
        editHora.setOnClickListener(v -> {
            TimePickerDialog timePicker = new TimePickerDialog(getContext(), (view12, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                editHora.setText(timeFormat.format(calendar.getTime()));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePicker.show();
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();

        btnAdicionar.setOnClickListener(v -> {
            String nome = editNomeMed.getText().toString().trim();
            String especialidade = editEspecialidade.getText().toString().trim();
            String data = editData.getText().toString().trim();
            String hora = editHora.getText().toString().trim();
            String local = editLocal.getText().toString().trim();
            String obs = editObs.getText().toString().trim();

            if (nome.isEmpty()) {
                Toast.makeText(getContext(), "Informe o nome do médico.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (data.isEmpty()) {
                Toast.makeText(getContext(), "Informe a data da consulta.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (hora.isEmpty()) {
                Toast.makeText(getContext(), "Informe o horário da consulta.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (local.isEmpty()) {
                Toast.makeText(getContext(), "Informe o local da consulta.", Toast.LENGTH_SHORT).show();
                return;
            }

            ConsultaDAO dao = new ConsultaDAO(getContext());
            long resultado = dao.inserir(nome, especialidade, data, hora, local, obs, usuarioId);

            if (resultado != -1) {
                Toast.makeText(getContext(), "Consulta adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                if (getActivity() instanceof Consultas) {
                    ((Consultas) getActivity()).carregarRegistros();
                }
            } else {
                Toast.makeText(getContext(), "Erro ao adicionar consulta.", Toast.LENGTH_SHORT).show();
            }
        });

        return dialog;
    }
}
