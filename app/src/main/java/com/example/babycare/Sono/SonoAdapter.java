package com.example.babycare.Sono;

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

public class SonoAdapter extends BaseAdapter {
    private Context context;
    private List<SonoModel> sonoList;
    private String idUsuario; // <- novo campo

    public SonoAdapter(Context context, List<SonoModel> sonoList, String idUsuario) {
        this.context = context;
        this.sonoList = sonoList;
        this.idUsuario = idUsuario;
    }

    @Override
    public int getCount() {
        return sonoList.size();
    }

    @Override
    public Object getItem(int position) {
        return sonoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itenssono, parent, false);
        }

        SonoModel sono = sonoList.get(position);

        TextView dataSono = convertView.findViewById(R.id.txt_SonoData);
        TextView inicioSono = convertView.findViewById(R.id.edit_horaInicio);
        TextView fimSono = convertView.findViewById(R.id.edit_horaFim);
        TextView obsSono = convertView.findViewById(R.id.txt_obs_Sono);
        ImageView btnExcluirSono = convertView.findViewById(R.id.btLixeiraSono);

        dataSono.setText(sono.getData());
        inicioSono.setText(sono.getHorarioInicio());
        fimSono.setText(sono.getHorarioFim());
        obsSono.setText(sono.getObservacao());

        btnExcluirSono.setOnClickListener(v -> {
            SonoDAO dao = new SonoDAO(context);
            String resultado = dao.excluirSono(sono.getId()); // <- id do usuário passado aqui
            Toast.makeText(context, resultado, Toast.LENGTH_SHORT).show();

            if (context instanceof Sono) {
                ((Sono) context).carregarRegistrosSono();
            }
        });

        return convertView;
    }
}
