package com.example.smart_apps;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogin extends AppCompatActivity {
    private EditText edtuser, edtpassword;
    private TextView mtcreat;
    private Button btnlogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtuser     = findViewById(R.id.username);
        edtpassword = findViewById(R.id.password);
        mtcreat     = findViewById(R.id.mtcreate);
        btnlogin    = findViewById(R.id.btnlogin);
        mAuth       = FirebaseAuth.getInstance();

        mtcreat.setOnClickListener(view -> startActivity(new Intent(ActivityLogin.this, ActivityRegister.class)));
        btnlogin.setOnClickListener(view -> {
            // bỏ qua quá trình đăng nhập
            // ActivityHome.class
            // startActivity(new Intent(ActivityLogin.this, ActivityHome.class));

            String username = edtuser.getText().toString();
            String password = edtpassword.getText().toString();

            // check username
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(ActivityLogin.this, "Enter your Email, please!", Toast.LENGTH_SHORT).show();
                edtuser.requestFocus();
                return;
            }

            // check password
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(ActivityLogin.this, "Enter your Password, please!", Toast.LENGTH_SHORT).show();
                edtpassword.requestFocus();
                return;
            }

            //kiem tra dang nhap
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, task -> {
                if(task.isSuccessful()){
                    // day len firebase
                    Common.writeEmailPassToFirebase(username, password);
                    Intent homeIntent = new Intent(getApplicationContext(), ActivityHome.class);

                    startActivity(homeIntent);
                } else {
                    Toast.makeText(ActivityLogin.this, "Email/Password incorrectly!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    };
}