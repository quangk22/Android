package com.example.quanlysinhvien;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateStudentActivity extends AppCompatActivity {
    String DATABASE_NAME = "quanlysinhvien.db";
    SQLiteDatabase database;
    EditText txtTenSV, txtMaSV,txtMaLop;
    Button btnChonHinh, btnChupHinh, btnLuu, btnHuy;
    ImageView imgAnhSua;
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    int maSVFromIntent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        addConTronl();
        loadData();
        addEvent();
    }
    private void addEvent() {
        btnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }
    private  void update(){
        String tenSV = txtTenSV.getText().toString();
        String maSV = txtMaSV.getText().toString();
        String maLop = txtMaLop.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgAnhSua);
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensv",tenSV);
        contentValues.put("masv",maSV);
        contentValues.put("malop",maLop);
        contentValues.put("image",anh);
        database = Database.initDatabase(UpdateStudentActivity.this ,DATABASE_NAME);
        database.update("sinhvien", contentValues, "masv = ? ", new String[] {maSVFromIntent + ""});

        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    private void loadData() {
        Intent intent = getIntent();
        maSVFromIntent  = intent.getIntExtra("ID", -1);
        if (maSVFromIntent  != -1) {
            database = Database.initDatabase(UpdateStudentActivity.this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM sinhvien WHERE masv = ? ", new String[]{maSVFromIntent  + ""});
            cursor.moveToFirst();//con trỏ

            String maSV = cursor.getString(0);
            String maLop = cursor.getString(1);
            String tenSV = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            if (anh != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
                imgAnhSua.setImageBitmap(bitmap);
            }
            txtTenSV.setText(tenSV);
            txtMaSV.setText(maSV);
            txtMaLop.setText(maLop);

        }
    }
    public byte[] getByteArrayFromImageView(ImageView imageView){

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {

                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgAnhSua.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAnhSua.setImageBitmap(bitmap);
            }
        }
    }

    private void addConTronl() {
        txtTenSV = (EditText) findViewById(R.id.editTextNhapTenUpdate);
        txtMaSV = (EditText) findViewById(R.id.editTextMaSVUpdate);
        txtMaLop = (EditText) findViewById(R.id.editTextMaLopStuUpdate);
        btnChonHinh = (Button) findViewById(R.id.buttonChonHinhUpdate);
        btnChupHinh = (Button) findViewById(R.id.buttonChupHinhUpdate);
        btnLuu = (Button) findViewById(R.id.buttonLuuUpdate);
        btnHuy = (Button) findViewById(R.id.buttonQuyLaiUpdate);
        imgAnhSua = (ImageView) findViewById(R.id.imageAvatarUpdate);
    }
}