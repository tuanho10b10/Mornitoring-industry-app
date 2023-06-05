package com.example.smart_apps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityElectricalHome extends AppCompatActivity {
    private FloatingActionButton fhome;
    private TextView txtVoltage, txtCurrent, txtPower;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrical_home);

        fhome = findViewById(R.id.fthomeElectricalHome);
        txtCurrent = findViewById(R.id.txtCurrent);
        txtPower = findViewById(R.id.txtpower);
        txtVoltage = findViewById(R.id.txtVoltage);


        fhome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityHome.class)));
        txtVoltage.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityVoltage.class)));
        txtPower.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityPower.class)));
        txtCurrent.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityCurrent.class)));

    }
}