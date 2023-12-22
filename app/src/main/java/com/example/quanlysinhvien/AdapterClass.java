package com.example.quanlysinhvien;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;

public class AdapterClass extends BaseAdapter {
    Context context;
    ArrayList<Lop> list;
    SQLiteDatabase database;
    AdapterClass adapter;
    String DATABASE_NAME = "quanlysinhvien.db";

    public AdapterClass(Context context, ArrayList<Lop> list, SQLiteDatabase database) {
        this.context = context;
        this.list = list;
        this.database = database;
    }
    public void setData(ArrayList<Lop> newData) {
        this.list = newData;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.class_activity, null);

        TextView txtMaLop = row.findViewById(R.id.textViewMalopClass);
        TextView txtTenLop = row.findViewById(R.id.textViewTenLopClass);
        ImageButton btnXoaLop = row.findViewById(R.id.imageButtonDelete);
        ImageButton btnSuaLop = row.findViewById(R.id.imageButtonUpdate);


        Lop classs = list.get(position);
        txtMaLop.setText(classs.maLop + "");
        txtTenLop.setText(classs.tenlop );
        btnXoaLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn xóa không");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(String.valueOf(classs.maLop));
                    }

                    private void delete(String maLop) {
                        database = Database.initDatabase((Activity) context, DATABASE_NAME);
                        database.delete("lop", " malop = ?", new String[]{maLop + ""});
                        Cursor cursor = database.rawQuery("Select * from lop", null);
                        list.clear();
                        while (cursor.moveToNext()) {
                            int ma = cursor.getInt(0);
                            String lop = cursor.getString(1);

                            list.add(new Lop(ma, lop));
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
        btnSuaLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(position);
            }
        });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListStudentActivity.class);
                intent.putExtra("MA_LOP", classs.maLop);
                intent.putExtra("TEN_LOP", classs.tenlop);
                context.startActivity(intent);

            }
        });
        return row;
    }
    private void updateList() {
        Cursor cursor = database.rawQuery("SELECT * FROM lop", null);
        ArrayList<Lop> newList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String lop = cursor.getString(1);
            newList.add(new Lop(ma, lop));
        }
        setData(newList);
    }
    private void showEditDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_edit_class, null);

        final EditText editMaLop = view.findViewById(R.id.editMaLop);
        final EditText editTenLop = view.findViewById(R.id.editTenLop);

        Lop classs = list.get(position);
        editMaLop.setText(String.valueOf(classs.maLop));
        editTenLop.setText(classs.tenlop);

        builder.setView(view)
                .setTitle("Sửa thông tin lớp")
                .setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String maLop = editMaLop.getText().toString();
                        String tenLop = editTenLop.getText().toString();
                        updateClass(position, maLop, tenLop);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.show();
    }

    private void updateClass(int position, String maLop, String tenLop) {
        Lop classs = list.get(position);
        database = Database.initDatabase((Activity) context, DATABASE_NAME);

        ContentValues values = new ContentValues();
        values.put("maLop", maLop);
        values.put("tenLop", tenLop);

        int rowsAffected = database.update("lop", values, "malop=?", new String[]{String.valueOf(classs.maLop)});

        if (rowsAffected > 0) {
            Toast.makeText(context, "Đã cập nhật thông tin lớp", Toast.LENGTH_SHORT).show();
            updateList();  // Cập nhật danh sách sau khi thực hiện UPDATE
        } else {
            Toast.makeText(context, "Cập nhật thông tin lớp thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
