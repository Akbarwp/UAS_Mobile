package com.example.uasmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.uasmobile.fragment.FragmentDaftarKuliah;
import com.example.uasmobile.model.ModelDaftarKuliah;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InputActivity extends AppCompatActivity {

    Spinner spinKelas, spinHari;
    String kelas, hari;
    EditText edNamaInput;

    Button btnJamAwal, btnJamAkhir, btnTambah;
    int jamAwal, menitAwal;
    int jamAkhir, menitAkhir;

    String id = "";
    TextView txClock, txDate;

    ArrayList<String> arrayHari;

    ProgressDialog progressDialog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // Spinner Kelas
        spinKelas = findViewById(R.id.spinKelas);
        ArrayAdapter<CharSequence> adapterKelas = ArrayAdapter
                .createFromResource(this, R.array.kelasMK, android.R.layout.simple_spinner_item);
        adapterKelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKelas.setAdapter(adapterKelas);
        spinKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kelas = adapterKelas.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Hari
        arrayHari = new ArrayList<>();
        arrayHari.add("Senin");
        arrayHari.add("Selasa");
        arrayHari.add("Rabu");
        arrayHari.add("Kamis");
        arrayHari.add("Jum'at");

        spinHari = findViewById(R.id.spinHari);
        ArrayAdapter<String> adapterHari = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayHari);
        adapterHari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHari.setAdapter(adapterHari);
        spinHari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hari = adapterHari.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Timepicker Jam
        btnJamAwal = findViewById(R.id.btnJamAwal);
        btnJamAkhir = findViewById(R.id.btnJamAkhir);

        edNamaInput = findViewById(R.id.edNamaInput);
        progressDialog = new ProgressDialog(InputActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan data");

        // Tambah Data
        ModelDaftarKuliah model = new ModelDaftarKuliah();
        btnTambah = findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setNama(edNamaInput.getText().toString());
                model.setKelas(kelas);
                model.setHari(hari);
                model.setJamAwal(btnJamAwal.getText().toString());
                model.setJamAkhir(btnJamAkhir.getText().toString());

                insert(model.getNama(), model.getKelas(), model.getHari(), model.getJamAwal(), model.getJamAkhir());
            }
        });

        // Edit Data
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            edNamaInput.setText(intent.getStringExtra("namaMK"));
            spinKelas.setSelection(adapterKelas.getPosition(intent.getStringExtra("kelasMK")));
            spinHari.setSelection(adapterHari.getPosition(intent.getStringExtra("hari")));
            btnJamAwal.setText(intent.getStringExtra("jamAwal"));
            btnJamAkhir.setText(intent.getStringExtra("jamAkhir"));

        }
    }

    public void timePickerAwal(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                jamAwal = selectedHour;
                menitAwal = selectedMinute;
                btnJamAwal.setText(String.format(Locale.getDefault(), "%02d:%02d", jamAwal, menitAwal));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, jamAwal, menitAwal, true);
        timePickerDialog.setTitle("Pilih Jam");
        timePickerDialog.show();
    }

    public void timePickerAkhir(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                jamAkhir = selectedHour;
                menitAkhir = selectedMinute;
                btnJamAkhir.setText(String.format(Locale.getDefault(), "%02d:%02d", jamAkhir, menitAkhir));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, jamAkhir, menitAkhir, true);
        timePickerDialog.setTitle("Pilih Jam");
        timePickerDialog.show();
    }

    public void insert(String nama, String kelas, String hari, String jamAwal, String jamAkhir) {
        Map<String, Object> user = new HashMap<>();
        user.put("namaMK", nama);
        user.put("kelasMK", kelas);
        user.put("hari", hari);
        user.put("jamAwal", jamAwal);
        user.put("jamAkhir", jamAkhir);

        // Membuat collection (tabel), document (kumpulan field-field + value)
        progressDialog.show();
        if (id != null) {
            db.collection("daftar_kuliah").document(id)
                    .set(user)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Data telah diubah", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });

        } else {
            db.collection("daftar_kuliah")
                    .add(user)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Data telah ditambahkan", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
        }
    }
}