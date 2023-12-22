package com.example.quanlysinhvien;

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


import java.util.ArrayList;

public class ManagerClassActivity extends AppCompatActivity {
    String DATABASE_NAME = "quanlysinhvien.db";
    SQLiteDatabase database;
    ListView viewClass;
    ArrayList<Lop> listClass;
    AdapterClass adapter;
    ImageButton addClass;
    EditText search;
    int selectedClassId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_class);

//        ánh xạ
        // Ánh xạ
        viewClass = (ListView) findViewById(R.id.listClass);
        addClass = (ImageButton) findViewById(R.id.imageButtonAddClass) ;
        search = (EditText) findViewById(R.id.editTextSearch);
        listClass = new ArrayList<>();
        adapter = new AdapterClass(ManagerClassActivity.this, listClass, database);
        viewClass.setAdapter(adapter);

        database = Database.initDatabase(ManagerClassActivity.this, DATABASE_NAME);

        Cursor cursor = database.rawQuery("Select * from lop", null);
        listClass.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int malopHoc = cursor.getInt(0);
            String tenlopHoc = cursor.getString(1);
            listClass.add(new Lop(malopHoc, tenlopHoc));
        }

        // Thêm dòng này để thông báo cho Adapter rằng dữ liệu đã thay đổi
        adapter.notifyDataSetChanged();
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerClassActivity.this, InsertClassActivity.class);
                startActivity(intent);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Không cần xử lý trước khi thay đổi văn bản
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Xử lý khi văn bản thay đổi
                filterData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Không cần xử lý sau khi thay đổi văn bản
            }
        });

    }
    private void filterData(String searchText) {
        ArrayList<Lop> filteredList = new ArrayList<>();

        for (Lop lop : listClass) {
            if (lop.tenlop.toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(lop);
            }
        }

        // Cập nhật danh sách hiển thị
        adapter.setData(filteredList);
    }
}