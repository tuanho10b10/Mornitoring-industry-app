package com.example.smart_apps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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

public class ActivityWalter extends AppCompatActivity {
    private FloatingActionButton fthome;
    private CombinedChart mChart;
    private TextView txtHigh, txtWalter;
    private ImageView iconHigh, iconHighLeft;
    private Spinner spinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walter);

        fthome = findViewById(R.id.fthome);
        txtHigh = findViewById(R.id.high);
        iconHigh = findViewById(R.id.iconHigh);
        iconHighLeft = findViewById(R.id.iconHighLeft);
        txtWalter = findViewById(R.id.txtWalter);
        mChart = findViewById(R.id.bieuDoNuoc);
        spinner = findViewById(R.id.spinner_water);

        fthome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityHome.class)));

        // cam bien
        iconHighLeft.setColorFilter(Color.WHITE);
        iconHigh.setColorFilter(Color.WHITE);

        // xoa du lieu
        Common.removeDataAfterNow(Common.keyChatLuongNuoc);

        // tao mang de hien thi len spinner
        String[] listGios = Common.initValueSpinner();
        ArrayAdapter<String> adapter = Common.initAdaper(this, listGios);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Common.hienThiChartTheoGio(ActivityWalter.this, listGios, i, Common.keyChatLuongNuoc, mChart, "Water", "ppm");
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
                    Common.updateNow(Common.keyChatLuongNuoc, txtWalter, txtHigh, iconHighLeft, iconHigh, "ppm", "water");
                });
            }
        },0, miliSecond);
    }
}


