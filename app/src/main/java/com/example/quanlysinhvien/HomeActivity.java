package com.example.quanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    Button quanlylop,taikhoan,website,danhsach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        quanlylop = (Button) findViewById(R.id.buttonQLLH);
        website = (Button) findViewById(R.id.buttonQlWEB);
        taikhoan = (Button) findViewById(R.id.buttonQLINTK) ;
        danhsach =(Button) findViewById(R.id.buttonQlSV);
        quanlylop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ManagerClassActivity.class);
                startActivity(intent);
                finish();
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl = "https://www.facebook.com/cnhuevn";

                // Tạo Intent để mở trình duyệt web
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                startActivity(intent);
            }
        });
        taikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        danhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ManagerStudentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}