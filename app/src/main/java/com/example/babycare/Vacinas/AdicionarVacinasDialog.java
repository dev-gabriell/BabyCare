package com.example.babycare.Vacinas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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

public class AdicionarVacinasDialog extends DialogFragment {
    private int usuarioId;

    public static AdicionarVacinasDialog novaInstancia(int usuarioId) {
        AdicionarVacinasDialog dialog = new AdicionarVacinasDialog();
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
        View view = inflater.inflate(R.layout.dialog_add_vacina, null);

        EditText editNome = view.findViewById(R.id.txt_nome_vac);
        EditText editDataAplicacao = view.findViewById(R.id.edit_dataAplicacaoVac);
        EditText editDataPrevista = view.findViewById(R.id.edit_dataPrevistaVac);
        EditText editObs = view.findViewById(R.id.edit_obsVacina);
        Button btnAdicionar = view.findViewById(R.id.btn_adicionarVacina);

        Calendar calendar = Calendar.getInstance();

        editDataAplicacao.setFocusable(false);
        editDataAplicacao.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(getContext(), (view1, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                editDataAplicacao.setText(formato.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });

        editDataPrevista.setFocusable(false);
        editDataPrevista.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(getContext(), (view1, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                editDataPrevista.setText(formato.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();

        btnAdicionar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            String dataAplicacao = editDataAplicacao.getText().toString().trim();
            String dataPrevista = editDataPrevista.getText().toString().trim();
            String obs = editObs.getText().toString().trim();

            if (nome.isEmpty()) {
                Toast.makeText(getContext(), "Preencha o nome da vacina.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dataAplicacao.isEmpty() && dataPrevista.isEmpty()) {
                Toast.makeText(getContext(), "Preencha ao menos uma data.", Toast.LENGTH_SHORT).show();
                return;
            }

            VacinaDAO dao = new VacinaDAO(getContext());
            long resultado = dao.inserir(nome, dataAplicacao, dataPrevista, obs, usuarioId);

            if (resultado != -1) {
                Toast.makeText(getContext(), "Vacina adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                if (getActivity() instanceof Vacinas) {
                    ((Vacinas) getActivity()).listarVacinas();
                }
            } else {
                Toast.makeText(getContext(), "Erro ao adicionar vacina.", Toast.LENGTH_SHORT).show();
            }
        });

        return dialog;
    }
}
