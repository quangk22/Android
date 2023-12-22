package com.example.quanlysinhvien;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterStudent extends BaseAdapter {

    Context context;
    ArrayList<Student> list;
    SQLiteDatabase database;
    AdapterStudent adapter;
    String DATABASE_NAME = "quanlysinhvien.db";

    public AdapterStudent(Context context, ArrayList<Student> list, SQLiteDatabase database) {
        this.context = context;
        this.list = list;
        this.database = database;
    }
    public void setData(ArrayList<Student> newData) {
        this.list = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.student_activity, null);

        TextView txtTenSV = row.findViewById(R.id.textTenSV);
        TextView txtMaSV = row.findViewById(R.id.textMaSV);
        TextView txtMaLop = row.findViewById(R.id.textMaLops);
        TextView txtTenLop = row.findViewById(R.id.textViewTenLop);
        ImageView imgAnh = row.findViewById(R.id.imageStudent);
        ImageButton btnXoaST = row.findViewById(R.id.imageButtonDeleteStu);
        ImageButton btnSuaST = row.findViewById(R.id.imageButtonEditStu);

        Student student = list.get(position);
        txtTenSV.setText(student.nameStudent );
        txtMaSV.setText(student.codeStudent + "");
        txtMaLop.setText(String.valueOf(student.codeClass));
        txtTenLop.setText(student.nameClass);
        Student students = list.get(position);
        if (students.img != null && students.img.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(student.img, 0, student.img.length);
            imgAnh.setImageBitmap(bitmap);
        } else {
            // Xử lý trường hợp img là null hoặc trống
        }
        btnSuaST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context ,UpdateStudentActivity.class);
                intent.putExtra("ID",student.codeStudent);

                context.startActivity(intent);
            }
        });

        btnXoaST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn xóa không");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(String.valueOf(student.codeStudent));
                    }

                    private void delete(String codeStudent) {
                        database = Database.initDatabase((Activity) context, DATABASE_NAME);
                        database.delete("sinhvien", " masv = ?", new String[]{codeStudent + ""});
                        Cursor cursor = database.rawQuery("Select * from sinvien", null);
                        list.clear();
                        while (cursor.moveToNext()) {
                            int id = cursor.getInt(0);
                            int malop = cursor.getInt(1);
                            String ten  = cursor.getString(2);
                            byte[] anh = cursor.getBlob(3);
                            String tenlop =   txtTenLop.getText().toString();

                            list.add(new Student(id,malop,ten,anh,tenlop));
                        }
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }

        });
        return row;
    }
}
