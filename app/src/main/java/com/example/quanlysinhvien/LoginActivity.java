package com.example.quanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText etxtUser, etxtPassWord;
    Button btnLogin;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);

        etxtUser = findViewById(R.id.user);
        etxtPassWord = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);

        addEvent();
    }
    private void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = etxtUser.getText().toString().trim();
                String pass = etxtPassWord.getText().toString().trim();

                if(dbHelper.checkUser(user, pass)) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}