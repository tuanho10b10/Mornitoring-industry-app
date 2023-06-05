package com.example.smart_apps;

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

public class ActivityDust extends AppCompatActivity {
    private FloatingActionButton fhome;
    private TextView txtValue;
    private CombinedChart mChart;
    private Spinner spinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dust);

        fhome = findViewById(R.id.fthomeDust);
        txtValue = findViewById(R.id.txtValueDust);
        mChart = findViewById(R.id.chartDust);
        spinner = findViewById(R.id.spinner_dust);
        fhome.setOnClickListener(view -> startActivity(new Intent(ActivityDust.this, ActivityAir.class)));

        // xoa du lieu
        Common.removeDataAfterNow(Common.keyDust);

        // tao mang de hien thi len spinner
        String[] listGios = Common.initValueSpinner();
        ArrayAdapter<String> adapter = Common.initAdaper(this, listGios);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Common.hienThiChartTheoGio(ActivityDust.this, listGios, i, Common.keyDust, mChart, "Dust", "ug/m3");
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
                    Common.updateNow(Common.keyDust, txtValue, "ug/m3");
                });
            }
        },0, miliSecond);
    }
}