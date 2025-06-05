package com.example.babycare.Sono;

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

import com.example.babycare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdicionarSonoDialog extends DialogFragment {

    private EditText editDataSono, editHoraInicio, editHoraFim, editObs;

    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_adicionar_sono, null);

        editDataSono = view.findViewById(R.id.edit_dataSono);
        editHoraInicio = view.findViewById(R.id.edit_horaInicio);
        editHoraFim = view.findViewById(R.id.edit_horaFim);
        editObs = view.findViewById(R.id.edit_obsSono);
        Button btnAdicionarSono = view.findViewById(R.id.btn_adicionarSono);

        // Desativa o teclado e abre o DatePicker ao clicar no campo de data
        editDataSono.setFocusable(false);
        editDataSono.setOnClickListener(v -> abrirDatePicker(editDataSono));

        // Desativa o teclado para os campos de hora
        editHoraInicio.setFocusable(false);
        editHoraFim.setFocusable(false);

        // Abre o TimePicker ao clicar nos campos de hora
        editHoraInicio.setOnClickListener(v -> abrirTimePicker(editHoraInicio));
        editHoraFim.setOnClickListener(v -> abrirTimePicker(editHoraFim));

        builder.setView(view);
        AlertDialog dialog = builder.create();

        btnAdicionarSono.setOnClickListener(v -> {
            String dataSono = editDataSono.getText().toString().trim();
            String horaInicio = editHoraInicio.getText().toString().trim();
            String horaFim = editHoraFim.getText().toString().trim();
            String observacao = editObs.getText().toString().trim();

            if (dataSono.isEmpty() || horaInicio.isEmpty() || horaFim.isEmpty()) {
                Toast.makeText(getContext(), "Preencha a data e os horários de início e término.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Recupera o ID do usuário passado como argumento
            int idUsuario = getArguments() != null ? getArguments().getInt("id_usuario", -1) : -1;
            if (idUsuario == -1) {
                Toast.makeText(getContext(), "Erro ao obter ID do usuário.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cria o modelo de sono
            SonoModel sonoModel = new SonoModel();
            sonoModel.setData(dataSono);
            sonoModel.setHorarioInicio(horaInicio);
            sonoModel.setHorarioFim(horaFim);
            sonoModel.setObservacao(observacao);

            SonoDAO dao = new SonoDAO(getContext());
            long resultado = dao.inserirSono(sonoModel, String.valueOf(idUsuario));

            if (resultado != -1) {
                Toast.makeText(getContext(), "Sono adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                ((Sono) getActivity()).carregarRegistrosSono();
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Erro ao salvar sono.", Toast.LENGTH_SHORT).show();
            }
        });

        return dialog;
    }

    private void abrirTimePicker(EditText campoDestino) {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                (view, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);

                    SimpleDateFormat formato = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    campoDestino.setText(formato.format(calendar.getTime()));
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void abrirDatePicker(EditText campoDestino) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    campoDestino.setText(formato.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}
