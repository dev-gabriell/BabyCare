package com.example.babycare.Fraldas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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

import com.example.babycare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdicionarFraldaDialog extends DialogFragment {

    private int usuarioId;

    public static AdicionarFraldaDialog novaInstancia(int usuarioId) {
        AdicionarFraldaDialog dialog = new AdicionarFraldaDialog();
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
        View view = inflater.inflate(R.layout.dialog_adicionar_fraldas, null);

        EditText editData = view.findViewById(R.id.edit_data_fralda);
        EditText editHorario = view.findViewById(R.id.edit_horario_fralda);
        EditText editQtd = view.findViewById(R.id.num_fralda);
        Spinner spinnerTipo = view.findViewById(R.id.spinner_tipo_fralda);
        Button btnAdicionar = view.findViewById(R.id.btn_adicionarFralda);

        // Configurar spinner com tipos de fralda
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.tipos_fraldas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();

        // Data - abrir DatePicker
        editData.setFocusable(false);
        editData.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(getContext(), (viewDate, year, month, day) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                editData.setText(sdf.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });

        // Horário - abrir TimePicker
        editHorario.setFocusable(false);
        editHorario.setOnClickListener(v -> {
            TimePickerDialog timePicker = new TimePickerDialog(getContext(), (viewTime, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                editHorario.setText(sdf.format(calendar.getTime()));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePicker.show();
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();

        btnAdicionar.setOnClickListener(v -> {
            String data = editData.getText().toString().trim();
            String hora = editHorario.getText().toString().trim();
            String quantidade = editQtd.getText().toString().trim();
            String tipo = spinnerTipo.getSelectedItem().toString().trim();

            if (data.isEmpty()) {
                Toast.makeText(getContext(), "Informe a data.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (hora.isEmpty()) {
                Toast.makeText(getContext(), "Informe o horário.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (quantidade.isEmpty()) {
                Toast.makeText(getContext(), "Informe a quantidade.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (tipo.equals("Selecione o tipo")) {
                Toast.makeText(getContext(), "Selecione o tipo de fralda.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Inserir no banco com usuário
            FraldaDAO dao = new FraldaDAO(getContext());
            long resultado = dao.inserir(tipo, data, hora, quantidade, usuarioId);

            if (resultado != -1) {
                Toast.makeText(getContext(), "Fralda registrada com sucesso!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                if (getActivity() instanceof Fraudas) {
                    ((Fraudas) getActivity()).carregarRegistros();
                }
            } else {
                Toast.makeText(getContext(), "Erro ao registrar fralda.", Toast.LENGTH_SHORT).show();
            }
        });

        return dialog;
    }
}
