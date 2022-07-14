package com.example.uasmobile.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uasmobile.InputActivity;
import com.example.uasmobile.R;
import com.example.uasmobile.adapter.AdapterDaftarKuliah;
import com.example.uasmobile.model.ModelDaftarKuliah;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDaftarKuliah#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDaftarKuliah extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rvDaftarKuliah;
    ArrayList<ModelDaftarKuliah> list = new ArrayList<>();
    FloatingActionButton fabTambah;
    Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ModelDaftarKuliah model;
    AdapterDaftarKuliah adapter;

    ProgressDialog progressDialog;

    public FragmentDaftarKuliah() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDaftarKuliah.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDaftarKuliah newInstance(String param1, String param2) {
        FragmentDaftarKuliah fragment = new FragmentDaftarKuliah();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daftar_kuliah, container, false);

        rvDaftarKuliah = view.findViewById(R.id.fragmentDaftarKuliah);
        rvDaftarKuliah.setHasFixedSize(true);

        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setTitle("Loading");

        showRecyclerView();
        readData();

        fabTambah = view.findViewById(R.id.fabTambah);
        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fabTambah.getContext(), InputActivity.class));
            }
        });

        dialogDelete();

        return  view;
    }

    private void showRecyclerView() {
        rvDaftarKuliah.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rvDaftarKuliah.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new AdapterDaftarKuliah(this.getContext(), list);
        rvDaftarKuliah.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void readData() {
        progressDialog.setMessage("Mengambil data");
        progressDialog.show();
        db.collection("daftar_kuliah")
            .orderBy("hari", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener(task -> {
                // Jika task sukses
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        model = new ModelDaftarKuliah(
                                documentSnapshot.getString("namaMK"),
                                documentSnapshot.getString("kelasMK"),
                                documentSnapshot.getString("hari"),
                                documentSnapshot.getString("jamAwal"),
                                documentSnapshot.getString("jamAkhir"));
                        model.setId(documentSnapshot.getId());
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteData(String id) {
        progressDialog.setMessage("Menghapus data");
        progressDialog.show();
        db.collection("daftar_kuliah").document(id)
                .delete()
                .addOnCompleteListener(task -> {
                    // Jika task sukses
                    if (task.isSuccessful()) {
                        adapter.notifyDataSetChanged();
                    }
                    progressDialog.dismiss();
                });
    }

    private void dialogDelete() {
        adapter.setDialogDelete(new AdapterDaftarKuliah.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItems = {"Hapus", "Batal"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Apakah Anda yakin?");
                dialog.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        deleteData(list.get(pos).getId());
                    }
                });
                dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                dialog.show();
            }
        });
    }
}