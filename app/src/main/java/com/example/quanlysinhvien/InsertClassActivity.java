package com.example.quanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class InsertClassActivity extends AppCompatActivity {
    String DATABASE_NAME = "quanlysinhvien.db";
    SQLiteDatabase database;
    EditText edtMaLopThem,edtTenLopThem;
    Button btnThemLop,btnHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_class);
        addConTronl();
        addEvent();
    }
    private void addEvent() {
        btnThemLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insert();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertClassActivity.this,ManagerClassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Insert() {
        String maLop = edtMaLopThem.getText().toString();
        String tenLop = edtTenLopThem.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("malop", maLop);
        contentValues.put("tenlop", tenLop);
        database = Database.initDatabase(InsertClassActivity.this ,DATABASE_NAME);
        database.insert("lop",null,contentValues);
        Intent intent = new Intent(InsertClassActivity.this,ManagerClassActivity.class);
        startActivity(intent);
    }

    private void addConTronl() {
        edtMaLopThem = (EditText) findViewById(R.id.editTextMaLop);
        edtTenLopThem = (EditText) findViewById(R.id.editTextTenLop);
        btnThemLop = (Button) findViewById(R.id.buttonThemClass);
        btnHuy = (Button) findViewById(R.id.buttonHuy);
    }
}