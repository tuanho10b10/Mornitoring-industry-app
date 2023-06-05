package com.example.smart_apps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ActivityProfile extends AppCompatActivity {
    private Button btnback;
    private TextView edtEmail, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edtEmail = findViewById(R.id.txtEmail);
        edtPass = findViewById(R.id.txtPass);
        btnback = findViewById(R.id.btnback);

        btnback.setOnClickListener(view -> finish());

        // doc thong tin o firebase
        Common.readEmailPassToFirebase("user/email", edtEmail);
        Common.readEmailPassToFirebase("user/pass", edtPass);
    }
}