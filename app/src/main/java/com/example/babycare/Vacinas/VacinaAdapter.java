package com.example.babycare.Vacinas;

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

public class VacinaAdapter extends BaseAdapter {
    private final Context context;
    private final List<VacinaModel> vacinas;
    private final int usuarioId;

    public VacinaAdapter(Context context, List<VacinaModel> vacinas, int usuarioId) {
        this.context = context;
        this.vacinas = vacinas;
        this.usuarioId = usuarioId;
    }

    @Override
    public int getCount() {
        return vacinas.size();
    }

    @Override
    public Object getItem(int position) {
        return vacinas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vacinas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VacinaModel vacina = vacinas.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.itensvacinacao, parent, false);

        TextView nome = view.findViewById(R.id.txt_nome_vacina);
        TextView data = view.findViewById(R.id.txt_data_vacina);
        TextView obs = view.findViewById(R.id.txt_obs_vac);
        ImageView btnLixeira = view.findViewById(R.id.btLixeiraVac);

        nome.setText(vacina.getNome());
        obs.setText(vacina.getObservacao());

        String dataExibida = vacina.getDataAplicacao().isEmpty() ? vacina.getDataPrevista() : vacina.getDataAplicacao();
        data.setText(dataExibida);

        btnLixeira.setOnClickListener(v -> {
            VacinaDAO dao = new VacinaDAO(context);
            boolean sucesso = dao.excluir(vacina.getId(), usuarioId);
            if (sucesso && context instanceof Vacinas) {
                ((Vacinas) context).listarVacinas();
            } else {
                Toast.makeText(context, "Erro ao excluir vacina", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
