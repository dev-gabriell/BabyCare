package com.example.babycare.Alimentacao;

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

public class AlimentacaoAdapter extends BaseAdapter {
    private Context context;
    private List<AlimentacaoModel> alimentacaoList;

    public AlimentacaoAdapter(Context context, List<AlimentacaoModel> alimentacaoList) {
        this.context = context;
        this.alimentacaoList = alimentacaoList;
    }

    @Override
    public int getCount() {
        return alimentacaoList.size();
    }

    @Override
    public Object getItem(int position) {
        return alimentacaoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itensalimentacao, parent, false);
        }

        AlimentacaoModel alimentacao = alimentacaoList.get(position);

        TextView nomeAlimento = convertView.findViewById(R.id.txt_nome_alimento);
        TextView dataAlimento = convertView.findViewById(R.id.txt_data_alimento);
        TextView obsAlimento = convertView.findViewById(R.id.txt_obs_alimento);
        ImageView btnExcluir = convertView.findViewById(R.id.btLixeiraAl); // Certifique-se de que existe esse ID no XML

        nomeAlimento.setText(alimentacao.getTipo());
        dataAlimento.setText(alimentacao.getData());
        obsAlimento.setText(alimentacao.getObservacao());

        btnExcluir.setOnClickListener(v -> {
            AlimentacaoDAO dao = new AlimentacaoDAO(context);
            String resultado = dao.excluirDados(alimentacao.getId());

            Toast.makeText(context, resultado, Toast.LENGTH_SHORT).show();

            if (context instanceof Alimentacao) {
                ((Alimentacao) context).carregarRegistros(); // Atualiza a tela após excluir
            }
        });

        return convertView;
    }
}
