package com.example.quanlysinhvien;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class InsertStudentActivity extends AppCompatActivity {
    String DATABASE_NAME = "quanlysinhvien.db";
    SQLiteDatabase database;
    EditText edtMaStudent,edtTenStudent,edtMalopStu;
    Button btnThemStudent,btnHuy,btnChonHinh, btnChupHinh;
    ImageView imgAnhThem;
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_student);
        addConTronl();
        addEvent();
    }
    private void addEvent() {
        btnThemStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insert();
            }
        });
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        btnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void Insert() {
        Intent intent = getIntent();
        int maLopStu = intent.getIntExtra("Ma_Lop", -1);
        String ma = edtMaStudent.getText().toString();
        String tenSV = edtTenStudent.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgAnhThem);
        ContentValues contentValues = new ContentValues();
        contentValues.put("masv", ma);
        contentValues.put("tensv", tenSV);
        contentValues.put("malop", maLopStu);
        contentValues.put("image",anh);
        database = Database.initDatabase(InsertStudentActivity.this ,DATABASE_NAME);
        database.insert("sinhvien",null,contentValues);
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void addConTronl() {
        edtMaStudent = (EditText) findViewById(R.id.editTextMaSV);
        edtTenStudent = (EditText) findViewById(R.id.editTextNhapTenThem);
//        edtMalopStu = (EditText) findViewById(R.id.editTextMaLopStu);
        btnHuy = (Button) findViewById(R.id.buttonQuyLaiThem);
        btnThemStudent = (Button) findViewById(R.id.buttonLuuThem);
        btnChonHinh = (Button) findViewById(R.id.buttonChonHinhThem);
        btnChupHinh = (Button) findViewById(R.id.buttonChupHinhThem);
        imgAnhThem = (ImageView) findViewById(R.id.imageAvatarThem);
    }
    public byte[] getByteArrayFromImageView(ImageView img){

        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {

                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgAnhThem.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAnhThem.setImageBitmap(bitmap);
            }
        }
    }
}