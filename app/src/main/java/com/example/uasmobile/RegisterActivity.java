package com.example.uasmobile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText edEmailRegister, edPasswordRegister, edNIMRegister, edNamaRegister, edGithubRegister, edPasswordCheckRegister;
    ImageView imgFoto;
    Button btnTambah;

    String user, pass, passCheck, nim, nama, github;

    ProgressDialog progressDialog;
    ActivityResultLauncher<Intent> activityResultLauncherPhoto;
    ActivityResultLauncher<String> activityResultLauncherGallery;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edEmailRegister = findViewById(R.id.edEmailRegister);
        edPasswordRegister = findViewById(R.id.edPasswordRegister);
        edPasswordCheckRegister = findViewById(R.id.edPasswordCheckRegister);
        edNIMRegister = findViewById(R.id.edNIMRegister);
        edNamaRegister = findViewById(R.id.edNamaRegister);
        edGithubRegister = findViewById(R.id.edGithubRegister);
        imgFoto = findViewById(R.id.imgFoto);
        btnTambah = findViewById(R.id.btnTambahRegister);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan data");

        btnTambah.setOnClickListener(v -> {
            user = edEmailRegister.getText().toString();
            pass = edPasswordRegister.getText().toString();
            passCheck = edPasswordCheckRegister.getText().toString();
            nim = edNIMRegister.getText().toString();
            nama = edNamaRegister.getText().toString();
            github = edGithubRegister.getText().toString();
            if (passCheck.equals(pass)) {
                buatAkun(user, pass);
                uploudImage(nim, nama, user, github);

            } else {
                Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show();
            }
        });

        imgFoto.setOnClickListener(v -> {
            selectImage();
        });


        // Receiver (menerima intent) --> mengambil foto dari kamera
        activityResultLauncherPhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Thread thread = new Thread(() -> {
                // Setiap intent yang mengakses aplikasi bawaan dari HP terdapat kode
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // open camera
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imgFoto.post(() -> imgFoto.setImageBitmap(bitmap));
                }
            });
            thread.start();
        });

        // Mengambil foto dari galeri
        activityResultLauncherGallery = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                imgFoto.setImageURI(result);
            }
        });
    }

    private void buatAkun(String email, String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivities(new Intent[]{intent});
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void buatUser(String nim, String nama, String email, String github, String foto) {
        Map<String, Object> user = new HashMap<>();
        user.put("nim", nim);
        user.put("nama", nama);
        user.put("email", email);
        user.put("github", github);
        user.put("foto", foto);
        db.collection("user")
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    void selectImage() {
        CharSequence[] itemImage = {"Take a photo", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

        builder.setItems(itemImage, (DialogInterface dialog, int i) -> {

            if (itemImage[i].equals("Take a photo")) {
                // intent untuk buka kamera
                Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Call intent
                activityResultLauncherPhoto.launch(intentPhoto);

            } else if (itemImage[i].equals("Choose from gallery")) {
                // Call activityResult
                activityResultLauncherGallery.launch("image/*");

            } else {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    void uploudImage(String nim, String nama, String email, String github) {
        imgFoto.setDrawingCacheEnabled(true);
        imgFoto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgFoto.getDrawable()).getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] byteData = outputStream.toByteArray();

        // Membuat penyimpanan/storage di Firestore
        StorageReference reference = storage.getReference("images").child("IMG" + new Date().getTime() + ".jpeg");
        UploadTask uploudTask = reference.putBytes(byteData);
        uploudTask.addOnFailureListener(e -> {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                })
                .addOnSuccessListener(taskSnapshot -> {
                    if (taskSnapshot.getMetadata().getReference() != null) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnCompleteListener(task -> {
                                    if (task.getResult() != null) {
                                        buatUser(nim, nama, email, github, task.getResult().toString());
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });
    }
}