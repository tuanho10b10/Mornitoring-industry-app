package com.example.smart_apps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityRegister extends AppCompatActivity {
    private Button btntaotk, btnthoat;
    private FirebaseAuth mAuth;
    private EditText edtpassword, edtemail, edtconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth       = FirebaseAuth.getInstance();
        edtpassword = findViewById(R.id.password);
        btntaotk    = findViewById(R.id.btntaotk);
        btnthoat    = findViewById(R.id.btnthoat);
        edtemail    = findViewById(R.id.email);
        edtconfirm  = findViewById(R.id.confim);


        btntaotk.setOnClickListener(view -> {
            String password = edtpassword.getText().toString();
            String email    = edtemail.getText().toString();
            String confirm  = edtconfirm.getText().toString();

            // check email empty
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Empty! Enter your email, please!", Toast.LENGTH_SHORT).show();
                edtemail.requestFocus();
                return;
            }

            // kiem tra email
            if (email.indexOf("@gmail.com") == -1) {
                Toast.makeText(this, "Email incorrect!", Toast.LENGTH_SHORT).show();
                edtemail.requestFocus();
                return;
            }

            // check password
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Empty! Enter your password, please!", Toast.LENGTH_SHORT).show();
                edtpassword.requestFocus();
                return;
            }

            // check confirm
            if (TextUtils.isEmpty(confirm)) {
                Toast.makeText(this, "Empty! Enter your confirm, please!", Toast.LENGTH_SHORT).show();
                edtconfirm.requestFocus();
                return;
            }

            // kiem tra trung pass
            if (!confirm.equals(password)) {
                Toast.makeText(this, "Password incorrect!", Toast.LENGTH_SHORT).show();
                edtconfirm.requestFocus();
                return;
            }

            // tao tai khoan minhngoan@gmail.com
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(ActivityRegister.this, ActivityLogin.class));
                } else {
                    Toast.makeText(this, "Account existed!", Toast.LENGTH_LONG).show();
                }
            });
        });

        btnthoat.setOnClickListener(view -> finish());
    }
}
