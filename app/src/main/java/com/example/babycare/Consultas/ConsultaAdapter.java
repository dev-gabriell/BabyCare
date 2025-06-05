package com.example.babycare.Consultas;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Consultas.Consultas;
import com.example.babycare.Consultas.ConsultaDAO;
import com.example.babycare.Consultas.ConsultaModel;
import com.example.babycare.R;

import java.util.List;

public class ConsultaAdapter extends BaseAdapter {
    private Context context;
    private List<ConsultaModel> consultaList;

    public ConsultaAdapter(Context context, List<ConsultaModel> consultaList) {
        this.context = context;
        this.consultaList = (consultaList != null) ? consultaList : new java.util.ArrayList<>();
    }

    @Override
    public int getCount() {
        return consultaList.size();
    }

    @Override
    public Object getItem(int position) {
        return consultaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itensconsultas, parent, false);
        }

        ConsultaModel consulta = consultaList.get(position);

        TextView nomeMed = convertView.findViewById(R.id.txt_nome_medico);
        TextView especialidade = convertView.findViewById(R.id.txt_especialidade);
        TextView dataConsulta = convertView.findViewById(R.id.txt_data_consulta);
        TextView horarioConsulta = convertView.findViewById(R.id.txt_horarioConsulta);
        TextView local = convertView.findViewById(R.id.txt_local);
        TextView obs = convertView.findViewById(R.id.txt_obs_consulta);
        ImageView btnExcluirConsulta = convertView.findViewById(R.id.btLixeiraConsulta); // Certifique-se de que existe esse ID no XML

        nomeMed.setText(consulta.getNomeMed());
        especialidade.setText(consulta.getEspecialidade());
        dataConsulta.setText(consulta.getData());
        horarioConsulta.setText(consulta.getHorario());
        local.setText(consulta.getLocal());
        obs.setText(consulta.getObservacao());

        btnExcluirConsulta.setOnClickListener(v -> {
            ConsultaDAO dao = new ConsultaDAO(context);
            String resultado = dao.excluirDadosConsultas(consulta.getId());

            Toast.makeText(context, resultado, Toast.LENGTH_SHORT).show();

            if (context instanceof Consultas) {
                ((Consultas) context).carregarRegistros(); // Atualiza a tela após excluir
            }
        });

        return convertView;
    }
}
