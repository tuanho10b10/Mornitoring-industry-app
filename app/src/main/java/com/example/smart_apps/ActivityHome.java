package com.example.smart_apps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityHome extends AppCompatActivity {
    private ImageButton btndien;
    private TextView txtdien,txtnuoc,txtmua ;
    private FloatingActionButton flthongtin,fthome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btndien = findViewById(R.id.btndien);
        flthongtin = findViewById(R.id.flthongtin);
        fthome = findViewById(R.id.fthome);
        txtdien = findViewById(R.id.txtdien);
        txtmua = findViewById(R.id.txtmua);
        txtnuoc = findViewById(R.id.txtnuoc);

        txtdien.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityElectricalHome.class)));
        txtnuoc.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityWalter.class)));
        txtmua.setOnClickListener(view ->  startActivity(new Intent(getApplicationContext(), ActivityAir.class)));

        flthongtin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ActivityProfile.class);
            startActivity(intent);
        });

        btndien.setOnClickListener(view -> startActivity(new Intent(ActivityHome.this, ActivityElectricalHome.class)));
        fthome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityLogin.class)));
    }
}