package com.example.uasmobile.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uasmobile.InputActivity;
import com.example.uasmobile.R;
import com.example.uasmobile.model.ModelDaftarKuliah;

import java.util.List;

public class AdapterDaftarKuliah extends RecyclerView.Adapter<AdapterDaftarKuliah.MyHolder> {

    private Context context;
    private List<ModelDaftarKuliah> list;
    private Dialog dialogDelete;


    public AdapterDaftarKuliah(Context context, List<ModelDaftarKuliah> list) {
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

    public interface Dialog{
        void onClick(int pos);
    }
    public void setDialogDelete(Dialog dialog) {
        this.dialogDelete = dialog;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_daftar_kuliah, parent,false);
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
        ImageButton btnEdit, btnDelete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            hari = itemView.findViewById(R.id.txHari);
            jamAwal = itemView.findViewById(R.id.txJamAwal);
            jamAkhir = itemView.findViewById(R.id.txJamAkhir);
            nama = itemView.findViewById(R.id.txNama);
            kelas = itemView.findViewById(R.id.txKelas);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getBindingAdapterPosition();

                    // Mengambil data index melalui Pojo
                    ModelDaftarKuliah model = getList().get(i);
                    model.setNama(model.getNama());
                    model.setKelas(model.getKelas());
                    model.setHari(model.getHari());
                    model.setJamAwal(model.getJamAwal());
                    model.setJamAkhir(model.getJamAkhir());

                    Intent intent = new Intent(getContext(), InputActivity.class);
                    intent.putExtra("id", list.get(i).getId());
                    intent.putExtra("namaMK", list.get(i).getNama());
                    intent.putExtra("kelasMK", list.get(i).getKelas());
                    intent.putExtra("hari", list.get(i).getHari());
                    intent.putExtra("jamAwal", list.get(i).getJamAwal());
                    intent.putExtra("jamAkhir", list.get(i).getJamAkhir());
                    context.startActivities(new Intent[]{intent});
                }
            });

            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogDelete != null) {
                        dialogDelete.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
