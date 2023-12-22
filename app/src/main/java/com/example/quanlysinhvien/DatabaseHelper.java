package com.example.quanlysinhvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "taikhoan.db";
    // Phiên bản cơ sở dữ liệu
    private static final int DATABASE_VERSION = 2;

    // Tên bảng và các cột
    private static final String TABLE_ACCOUNT = "taikhoan";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "user";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";
    // Câu truy vấn tạo bảng
    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_ACCOUNT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_ROLE + " TEXT"  // Thêm dấu phẩy ở đây
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCOUNT);

        insertSampleData(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Thêm tài khoản 1
        values.put(COLUMN_USERNAME, "user1");
        values.put(COLUMN_PASSWORD, "123");
        values.put(COLUMN_ROLE, "user");
        db.insert(TABLE_ACCOUNT, null, values);

        // Thêm tài khoản 2
        values.clear();
        values.put(COLUMN_USERNAME, "admin");
        values.put(COLUMN_PASSWORD, "123");
        values.put(COLUMN_ROLE, "admin");
        db.insert(TABLE_ACCOUNT, null, values);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        onCreate(db);
    }

    // Phương thức kiểm tra người dùng
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{username, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Phương thức lấy role của người dùng
    public String getUserRole(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String role = "";
        Cursor cursor = db.query(TABLE_ACCOUNT, new String[]{COLUMN_ROLE}, COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int roleIndex = cursor.getColumnIndex(COLUMN_ROLE);

            if (roleIndex != -1) {
                role = cursor.getString(roleIndex);
            }

            cursor.close();
        }

        return role;
    }
}