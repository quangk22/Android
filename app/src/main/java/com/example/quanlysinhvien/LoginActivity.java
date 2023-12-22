package com.example.quanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText etxtUser, etxtPassWord;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etxtUser = (EditText) findViewById(R.id.user);
        etxtPassWord = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etxtUser.getText().toString().equals("") || etxtPassWord.getText().toString().equals("")){
                    Toast.makeText(getApplication(),"Vui lòng nhập đầy đủ thông tin!",Toast.LENGTH_LONG).show();

                }else if (etxtUser.getText().toString().equals("admin") && etxtPassWord.getText().toString().equals("123")){
                    Toast.makeText(getApplication(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this,ManagerClassActivity.class);
                    intent.putExtra("USERNAME",etxtUser.getText().toString());
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(getApplication(),"Sai tài khoản hoặc password",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}