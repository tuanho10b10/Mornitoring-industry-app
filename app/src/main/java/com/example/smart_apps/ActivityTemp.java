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

public class ActivityTemp extends AppCompatActivity {
    private FloatingActionButton fthome;
    private TextView txtValue;
    private CombinedChart mChart;
    private Spinner spinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        // tham chieu ben giao dien
        txtValue = findViewById(R.id.txtValue);
        mChart = (CombinedChart) findViewById(R.id.ChartTemp);
        fthome = findViewById(R.id.fthome);
        spinner = findViewById(R.id.spinner_temp);

        // click tro ve
        fthome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityAir.class)));

        // xoa du lieu
        Common.removeDataAfterNow(Common.keyNhietDo);

        // tao mang de hien thi len spinner
        String[] listGios = Common.initValueSpinner();
        ArrayAdapter<String> adapter = Common.initAdaper(this, listGios);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Common.hienThiChartTheoGio(ActivityTemp.this, listGios, i, Common.keyNhietDo, mChart, "Temp", "Celsius");
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
                    Common.updateNow(Common.keyNhietDo, txtValue, "Celsius");
                });
            }
        },0, miliSecond);
    }
}