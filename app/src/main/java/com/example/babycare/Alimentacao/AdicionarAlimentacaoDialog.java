package com.example.babycare.Alimentacao;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import com.example.babycare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdicionarAlimentacaoDialog extends DialogFragment {
    private int usuarioId;

    public static AdicionarAlimentacaoDialog novaInstancia(int usuarioId) {
        AdicionarAlimentacaoDialog dialog = new AdicionarAlimentacaoDialog();
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
        View view = inflater.inflate(R.layout.dialog_adicionar_alimentacao, null);

        Spinner spinnerTipo = view.findViewById(R.id.spinner_tipoAlimento);
        EditText editData = view.findViewById(R.id.edit_dataAlimento);
        EditText editObs = view.findViewById(R.id.edit_obsAlimento);
        Button btnAdicionarAlimento = view.findViewById(R.id.btn_adicionarAlimento);

        // Preencher o spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.tipos_alimentacao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        // Configurar calendário e hora no campo de data
        Calendar calendar = Calendar.getInstance();
        editData.setFocusable(false);
        editData.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(getContext(), (viewDate, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Após escolher a data, abre o relógio
                TimePickerDialog timePicker = new TimePickerDialog(getContext(), (viewTime, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);

                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd - HH:mm");

                    editData.setText(formato.format(calendar.getTime()));
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true); // formato 24h

                timePicker.show();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePicker.show();
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();

        btnAdicionarAlimento.setOnClickListener(v -> {
            String tipo = spinnerTipo.getSelectedItem().toString().trim();
            String data = editData.getText().toString().trim();
            String obs = editObs.getText().toString().trim();

            if (tipo.equals("Selecione um tipo") || tipo.isEmpty()) {
                Toast.makeText(getContext(), "Selecione um tipo de alimentação!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (data.isEmpty()) {
                Toast.makeText(getContext(), "Digite a data e hora!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Inserção no banco
            AlimentacaoDAO dao = new AlimentacaoDAO(getContext());
            long resultado = dao.inserir(tipo, data, obs, usuarioId);

            if (resultado != -1) {
                Toast.makeText(getContext(), "Alimentação adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                // Atualizar os registros na Activity
                ((Alimentacao) getActivity()).carregarRegistros();
            } else {
                Toast.makeText(getContext(), "Erro ao adicionar alimentação.", Toast.LENGTH_SHORT).show();
            }
        });

        return dialog;
    }
}
