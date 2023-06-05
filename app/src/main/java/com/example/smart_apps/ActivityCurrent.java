package com.example.smart_apps;

import androidx.appcompat.app.AppCompatActivity;

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

public class ActivityCurrent extends AppCompatActivity {
    private FloatingActionButton fhome;
    private TextView txtValue;
    private CombinedChart mChart;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);

        fhome = findViewById(R.id.fthomeCurrent);
        txtValue = findViewById(R.id.txtValueCurrent);
        mChart = findViewById(R.id.chartCurrent);
        spinner = findViewById(R.id.spinner_current);

        fhome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityElectricalHome.class)));

        // xoa du lieu
        Common.removeDataAfterNow(Common.keyCurrent);

        // tao mang de hien thi len spinner
        String[] listGios = Common.initValueSpinner();
        ArrayAdapter<String> adapter = Common.initAdaper(this, listGios);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Common.hienThiChartTheoGio(ActivityCurrent.this, listGios, i, Common.keyCurrent, mChart, "Current", "A");
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
                    Common.updateNow(Common.keyCurrent, txtValue, "A");
                });
            }
        },0, miliSecond);
    }
}