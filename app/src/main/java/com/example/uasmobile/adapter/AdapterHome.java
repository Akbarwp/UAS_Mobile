package com.example.uasmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uasmobile.R;
import com.example.uasmobile.model.ModelDaftarKuliah;

import java.util.List;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.MyHolder> {

    private Context context;
    private List<ModelDaftarKuliah> list;


    public AdapterHome(Context context, List<ModelDaftarKuliah> list) {
        this.context = context;
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public List<ModelDaftarKuliah> getList() {
        return list;
    }
    public void setList(List<ModelDaftarKuliah> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_home, parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ModelDaftarKuliah m = list.get(position);
        holder.nama.setText(m.getNama());
        holder.kelas.setText(m.getKelas());
        holder.hari.setText(m.getHari());
        holder.jamAwal.setText(m.getJamAwal());
        holder.jamAkhir.setText(m.getJamAkhir());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView nama, kelas, hari, jamAwal, jamAkhir;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            hari = itemView.findViewById(R.id.txHari);
            jamAwal = itemView.findViewById(R.id.txJamAwal);
            jamAkhir = itemView.findViewById(R.id.txJamAkhir);
            nama = itemView.findViewById(R.id.txNama);
            kelas = itemView.findViewById(R.id.txKelas);
        }
    }
}
