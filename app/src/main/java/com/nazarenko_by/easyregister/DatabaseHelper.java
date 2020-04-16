package com.nazarenko_by.easyregister;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "userStore.db";
    public static final int VERSION = 2;
    public static final String TABLE = "Users";  // название таблицы в бд
    public static final String COLUMN_ID = "User_ID";
    public static final String COLUMN_FN = "First_name";
    public static final String COLUMN_SN = "Second_name";
    public static final String COLUMN_PT = "Patronymic";
    public static final String COLUMN_DB = "Date_of_birth";
    public static final String COLUMN_ML = "Mail";
    public static final String COLUMN_GP = "Groups";
    public static final String COLUMN_TN = "Tel_Number";
    public static final String COLUMN_TY = "Type";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SN + " TEXT, "
                + COLUMN_FN + " TEXT, "
                + COLUMN_PT + " TEXT, "
                + COLUMN_GP + " TEXT, "
                + COLUMN_TY + " TEXT, "
                + COLUMN_DB + " DATE, "
                + COLUMN_TN + " TEXT, "
                + COLUMN_ML + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

}
