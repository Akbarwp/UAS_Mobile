package com.example.uasmobile.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uasmobile.R;
import com.example.uasmobile.adapter.AdapterDaftarKuliah;
import com.example.uasmobile.adapter.AdapterHome;
import com.example.uasmobile.model.ModelDaftarKuliah;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rvHome;
    ArrayList<ModelDaftarKuliah> list = new ArrayList<>();
    ModelDaftarKuliah model;
    AdapterHome adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvHome = view.findViewById(R.id.fragmentHome);
        rvHome.setHasFixedSize(true);

        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setTitle("Loading");

        showRecyclerView();
        readData();

        return  view;
    }

    private void showRecyclerView() {
        rvHome.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rvHome.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new AdapterHome(this.getContext(), list);
        rvHome.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void readData() {
        progressDialog.setMessage("Mengambil data");
        progressDialog.show();

        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("EEEE");

        db.collection("daftar_kuliah")
                .whereEqualTo("hari", dateFormat.format(new Date()))
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
}