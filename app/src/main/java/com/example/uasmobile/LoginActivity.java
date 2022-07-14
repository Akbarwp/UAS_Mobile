package com.example.uasmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ImageSlider slider;
    private ArrayList<SlideModel> listImg;

    EditText edUsernameLogin, edPasswordLogin;
    Button btnLogin, btnRegister;

    String user, pass;

    ProgressDialog progressDialog;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        slider = findViewById(R.id.imgLogin);
        listImg = new ArrayList<>();
        listImg.add(new SlideModel(R.drawable.mobile_login, "Login atau Register terlebih dahulu", ScaleTypes.CENTER_INSIDE));
        listImg.add(new SlideModel(R.drawable.reminder, "Apakah kamu selalu lupa dengan jadwal kuliah?", ScaleTypes.CENTER_INSIDE));
        listImg.add(new SlideModel(R.drawable.unlock, "Jangan khawatir, masih ada solusinya", ScaleTypes.CENTER_INSIDE));
        listImg.add(new SlideModel(R.drawable.kuliahku, ScaleTypes.CENTER_INSIDE));
        listImg.add(new SlideModel(R.drawable.appreciation, "Selamat Datang", ScaleTypes.CENTER_INSIDE));
        slider.setImageList(listImg);


        edUsernameLogin = findViewById(R.id.edUsernameLogin);
        edPasswordLogin = findViewById(R.id.edPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Memeriksa data");

        btnLogin.setOnClickListener(v -> {
            progressDialog.show();
            checkLogin();
            progressDialog.dismiss();
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivities(new Intent[]{intent});
        });

    }

    void checkLogin() {
        user = edUsernameLogin.getText().toString();
        pass = edPasswordLogin.getText().toString();

        mAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivities(new Intent[]{intent});
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}