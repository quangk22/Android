package com.example.quanlysinhvien;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListStudentActivity extends AppCompatActivity {
    String DATABASE_NAME = "quanlysinhvien.db";
    SQLiteDatabase database;
    ListView viewClass;
    ArrayList<Student> listStudents;
    AdapterStudent adapter;
    EditText search;
    ImageButton btnAdd;
    private static final int REQUEST_INSERT_STUDENT = 1;
    private static final int REQUEST_UPDATE_STUDENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_student);

        Intent intent = getIntent();
        int maLopHoc = intent.getIntExtra("MA_LOP", -1);
        search = (EditText) findViewById(R.id.editTextSearchST);
        viewClass = (ListView) findViewById(R.id.listStudent);
        btnAdd = (ImageButton) findViewById(R.id.imageButtonAddCStudent);
        database = Database.initDatabase(ListStudentActivity.this,DATABASE_NAME);


        listStudents = new ArrayList<>();
        adapter = new AdapterStudent(ListStudentActivity.this,listStudents,database);
        viewClass.setAdapter(adapter);

        loadData();
        adapter.notifyDataSetChanged();
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ListStudentActivity.this, InsertStudentActivity.class);
                    intent.putExtra("Ma_Lop", maLopHoc);
                    Toast.makeText(getApplicationContext(), String.valueOf(maLopHoc), Toast.LENGTH_LONG).show();

                    startActivityForResult(intent, REQUEST_INSERT_STUDENT);
                }
            });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void filterData(String searchText) {
        ArrayList<Student> filteredList = new ArrayList<>();

        for (Student student  : listStudents) {
            if (student.nameStudent.toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(student);
            }
        }

        // Cập nhật danh sách hiển thị
        adapter.setData(filteredList);
    }
    public void QuayLai(View view) {
        Intent intent = new Intent(this, ManagerClassActivity.class);
        startActivity(intent);
        finish();
    }
    private void loadData() {
        Intent intent = getIntent();
        int maLopHoc = intent.getIntExtra("MA_LOP", -1);
        String TenLopHoc = intent.getStringExtra("Ten_LOP");
        listStudents.clear();
        Cursor cursor = database.rawQuery("SELECT masv,sinhvien.malop,tensv,image,tenlop FROM sinhvien,lop WHERE sinhvien.malop = lop.malop AND sinhvien.malop = ?", new String[]{String.valueOf(maLopHoc)});
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int maSv = cursor.getInt(0);
            int maLop = cursor.getInt(1);
            String tenSv = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            String tenLop = cursor.getString(4);
            listStudents.add(new Student(maSv, maLop, tenSv, anh, tenLop));
        }
        adapter.notifyDataSetChanged();
    }

    public void onAddStudentClick(View view) {
        Intent intent = new Intent(this, InsertStudentActivity.class);
        startActivityForResult(intent, REQUEST_INSERT_STUDENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_INSERT_STUDENT && resultCode == RESULT_OK) {
            loadData();
            adapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_UPDATE_STUDENT && resultCode == RESULT_OK) {
            loadData();
            adapter.notifyDataSetChanged();
        }
    }

}