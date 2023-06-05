package com.example.smart_apps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityAir extends AppCompatActivity {
    private FloatingActionButton fthome;
    private TextView txthum,txtgas,txttem, txtdust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air);

        fthome = findViewById(R.id.fthome);
        txtgas = findViewById(R.id.txtgas);
        txthum = findViewById(R.id.txthum);
        txttem = findViewById(R.id.txttem);
        txtdust = findViewById(R.id.txtdust);

        fthome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityHome.class)));
        txtgas.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityGas.class)));
        txttem.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityTemp.class)));
        txthum.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityHumidity.class)));
        txtdust.setOnClickListener((view -> startActivity(new Intent(getApplicationContext(), ActivityDust.class))));
    }
}