package com.example.smart_apps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityGas extends AppCompatActivity {
    private FloatingActionButton fthome;
    private CombinedChart mChart;
    private TextView txtHigh, txtValue;
    private ImageView imgLeft, imgRight;
    private Spinner spinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);

        // tham chieu ben giao dien
        txtHigh = findViewById(R.id.highGas);
        txtValue = findViewById(R.id.txtValeGas);
        imgLeft = findViewById(R.id.imgLeftGas);
        imgRight = findViewById(R.id.imgRightGas);
        mChart = findViewById(R.id.bieuDoGas);
        fthome = findViewById(R.id.fthome);
        spinner = findViewById(R.id.spinner_gas);

        fthome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityAir.class)));

        // xoa du lieu
        Common.removeDataAfterNow(Common.keyGas);

        // tao mang de hien thi len spinner
        String[] listGios = Common.initValueSpinner();
        ArrayAdapter<String> adapter = Common.initAdaper(this, listGios);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Common.hienThiChartTheoGio(ActivityGas.this, listGios, i, Common.keyGas, mChart, "Gas", "ppm");
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
                    Common.updateNow(Common.keyGas, txtValue, txtHigh, imgLeft, imgRight, "ppm", "ssCo2");
                });
            }
        },0, miliSecond);
    }
}
