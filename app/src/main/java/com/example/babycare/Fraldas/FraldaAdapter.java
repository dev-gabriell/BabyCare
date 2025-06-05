package com.example.babycare.Fraldas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.R;

import java.util.List;

public class FraldaAdapter extends BaseAdapter {
    private Context context;
    private List<FraldaModel> fraldaList;

    public FraldaAdapter(Context context, List<FraldaModel> fraldaList) {
        this.context = context;
        this.fraldaList = (fraldaList != null) ? fraldaList : new java.util.ArrayList<>();
    }

    @Override
    public int getCount() {
        return fraldaList.size();
    }

    @Override
    public Object getItem(int position) {
        return fraldaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itensfraldas, parent, false);
        }

        FraldaModel fralda = fraldaList.get(position);

        TextView txtData = convertView.findViewById(R.id.txt_data_fralda);
        TextView txtHorario = convertView.findViewById(R.id.txt_hora_fralda);
        TextView txtTipo = convertView.findViewById(R.id.txt_tipo_frauda);
        TextView txtQuantidade = convertView.findViewById(R.id.txt_qtd);
        ImageView btnExcluir = convertView.findViewById(R.id.btLixeiraFr); // Certifique-se que esse ID exista no XML

        txtData.setText(fralda.getData());
        txtHorario.setText(fralda.getHorario());
        txtTipo.setText(fralda.getTipo());
        txtQuantidade.setText(fralda.getQuantidade() + "x");

        btnExcluir.setOnClickListener(v -> {
            FraldaDAO dao = new FraldaDAO(context);
            String resultado = dao.excluir(fralda.getId());

            Toast.makeText(context, resultado, Toast.LENGTH_SHORT).show();

            // Chama o método carregarRegistros da activity se ela implementar
            if (context instanceof Fraudas) { // Ajuste o nome da Activity conforme seu projeto
                ((Fraudas) context).carregarRegistros();
            }
        });

        return convertView;
    }
}
