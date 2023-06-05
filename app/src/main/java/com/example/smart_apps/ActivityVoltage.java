package com.example.smart_apps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityVoltage extends AppCompatActivity {
    private FloatingActionButton fthome;
    private CombinedChart mChart;
    private TextView txtValue;
    private Spinner spinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voltage);

        fthome = findViewById(R.id.fthome);
        txtValue = findViewById(R.id.txtValueVoltage);
        mChart = findViewById(R.id.chartVoltage);
        spinner = findViewById(R.id.spinner_voltage);

        fthome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityElectricalHome.class)));

        // xoa du lieu
        Common.removeDataAfterNow(Common.keyVoltage);

        // tao mang de hien thi len spinner
        String[] listGios = Common.initValueSpinner();
        ArrayAdapter<String> adapter = Common.initAdaper(ActivityVoltage.this, listGios);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Common.hienThiChartTheoGio(ActivityVoltage.this, listGios, i, Common.keyVoltage, mChart, "Voltage", "V");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        // cu 1 giay thi update
        int miliSecond = 1000;
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    // lay data tu FireBase
                    Common.updateNow(Common.keyVoltage, txtValue, "V");
                });
            }
        },0, miliSecond);
    }
}
