package com.example.uasmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.uasmobile.fragment.FragmentAbout;
import com.example.uasmobile.fragment.FragmentDaftarKuliah;
import com.example.uasmobile.fragment.FragmentHome;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;
    FloatingActionButton fabTambah;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav = findViewById(R.id.bottom_navigation);
        fabTambah = findViewById(R.id.fabTambah);

        fragment = new FragmentHome();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getSimpleName()).commit();

        // Event click navigasi About
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // halaman fragment (berisi list data atau form atau dll.)

                // Membuat fragment kosong
                fragment = null;

                // Cek menu yang diklik
                switch (item.getItemId()) {
                    case R.id.home:
                        // Memanggil halaman fragment Home
                        fragment = new FragmentHome();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getSimpleName()).commit();
                        break;

                    case R.id.daftarKuliah:
                        // Memanggil halaman fragment Daftar Kuliah
                        fragment = new FragmentDaftarKuliah();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getSimpleName()).commit();
                        break;

                    case R.id.about:
                        // Memanggil halaman fragment About
                        fragment = new FragmentAbout();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getSimpleName()).commit();
                        break;
                }
                return true;
            }
        });
    }
}