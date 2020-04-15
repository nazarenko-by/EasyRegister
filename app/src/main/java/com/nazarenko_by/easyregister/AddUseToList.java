package com.nazarenko_by.easyregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class AddUseToList {

    private String QRSting;
    private String UserId;
    private String FirstName;
    private String SecondName;
    private String Patronymic;
    private String Group;
    private String Date;
    private String TelNumber;
    private String Type;
    private String Mail;
    Context context;


    AddUseToList(Context context, String QRSting){
        this.QRSting = QRSting;
        this.context = context;
    }

    private  String getUserId() {
        return UserId;
    }
    private  String getFirstName() {
        return FirstName;
    }
    private  String getSecondName() {
        return SecondName;
    }
    private  String getPatronymic() {
        return Patronymic;
    }
    private  String getGroup() {
        return Group;
    }
    private  String getDate() {
        return Date;
    }
    private  String getTelNumber() {
        return TelNumber;
    }
    private  String getType() {
        return Type;
    }
    private  String getMail() {
        return Mail;
    }
    private void setUserId(String UserId) {
        this.UserId = UserId;
    }
    private void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
    private  void setSecondName(String SecondName) {
        this.SecondName = SecondName;
    }
    private  void setPatronymic(String Patronymic) {
        this.Patronymic = Patronymic;
    }
    private  void setGroup(String Group) {
        this.Group = Group;
    }
    private  void setDate(String Date) {
        this.Date = Date;
    }
    private  void setTelNumber(String TelNumber) {
        this.TelNumber = TelNumber;
    }
    private  void setType(String Type) {
        this.Type = Type;
    }
    private  void setMail(String Mail) {
        this.Mail = Mail;
    }

    public void addToBase(){

        if(QRSting.substring(0,6).equals("|STRT|") ) {
            DatabaseHelper databaseHelper;
            SQLiteDatabase db;
            databaseHelper = new DatabaseHelper(context.getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            ContentValues cv = new ContentValues();

            setUserId(QRSting.substring(QRSting.indexOf("ID")+2, QRSting.indexOf("|FN")));
            setFirstName(QRSting.substring(QRSting.indexOf("FN")+2, QRSting.indexOf("|SN")));
            setSecondName(QRSting.substring(QRSting.indexOf("SN")+2, QRSting.indexOf("|PT")));
            setPatronymic(QRSting.substring(QRSting.indexOf("PT")+2, QRSting.indexOf("|GP")));
            setGroup(QRSting.substring(QRSting.indexOf("GP")+2, QRSting.indexOf("|DT")));
            setDate(QRSting.substring(QRSting.indexOf("DT")+2, QRSting.indexOf("|TN")));
            setTelNumber(QRSting.substring(QRSting.indexOf("TN")+2, QRSting.indexOf("|ML")));
            setMail(QRSting.substring(QRSting.indexOf("ML")+2, QRSting.indexOf("|TP")));
            setType(QRSting.substring(QRSting.indexOf("TP")+2, QRSting.indexOf("|END")));
            cv.put(DatabaseHelper.COLUMN_ID, getUserId());
            cv.put(DatabaseHelper.COLUMN_FN, getFirstName());
            cv.put(DatabaseHelper.COLUMN_SN, getSecondName());
            cv.put(DatabaseHelper.COLUMN_PT, getPatronymic());
            cv.put(DatabaseHelper.COLUMN_GP, getGroup());
            cv.put(DatabaseHelper.COLUMN_DB, getDate());
            cv.put(DatabaseHelper.COLUMN_ML, getMail());
            cv.put(DatabaseHelper.COLUMN_TN, getTelNumber());
            cv.put(DatabaseHelper.COLUMN_TY, getType());
            db.insert(DatabaseHelper.TABLE, null, cv);
            db.close();
                    }
    }
}
