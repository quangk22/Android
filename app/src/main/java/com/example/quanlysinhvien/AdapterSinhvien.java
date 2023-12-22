package com.example.quanlysinhvien;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSinhvien extends BaseAdapter {
    Context context;
    ArrayList<Student> list;
    SQLiteDatabase database;
    AdapterStudent adapter;
    String DATABASE_NAME = "quanlysinhvien.db";

    public AdapterSinhvien(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
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
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.student2_activity,null);

        TextView txtMaSV = (TextView) row.findViewById(R.id.textMaSVs);
        TextView txtTenSV = (TextView) row.findViewById(R.id.textTenSVs);
        TextView txtMaLopStu = (TextView) row.findViewById(R.id.textMaLopss);
        ImageView imgAnh = (ImageView) row.findViewById(R.id.imageStudents);

        Student student = list.get(position);
        txtMaSV.setText(student.codeStudent +"");
        txtTenSV.setText(student.nameStudent);
        txtMaLopStu.setText(student.codeClass + "");
        Bitmap bitmap = BitmapFactory.decodeByteArray(student.img,0,student.img.length);
        imgAnh.setImageBitmap(bitmap);


        return row;
    }
}
