package com.nazarenko_by.easyregister;

public class AppUser {

    private String UserId;
    private String FirstName;
    private String SecondName;
    private String Patronymic;
    private String Group;
    private String Date;
    private String TelNumber;
    private String Type;
    private String Mail;
    private String Image;

    public AppUser(String UserId, String FirstName, String SecondName, String Patronymic, String Group,
            String Date, String TelNumber, String Type, String Mail, String Image){
        this.UserId = UserId;
        this.FirstName = FirstName;
        this.SecondName = SecondName;
        this.Patronymic = Patronymic;
        this.Group = Group;
        this.Date = Date;
        this.TelNumber = TelNumber;
        this.Type = Type;
        this.Mail = Mail;
        this.Image = Image;
    }

    public  String getUserId() {
        return UserId;
    }
    public  String getFirstName() {
        return FirstName;
    }
    public  String getSecondName() {
        return SecondName;
    }
    public  String getPatronymic() {
        return Patronymic;
    }
    public  String getGroup() {
        return Group;
    }
    public  String getDate() {
        return Date;
    }
    public  String getTelNumber() {
        return TelNumber;
    }
    public  String getType() {
        return Type;
    }
    public  String getMail() {
        return Mail;
    }
    public String getImage() {
        return Image;
    }

    private void setUserId(String UserId) {
        this.UserId = UserId;
    }
    private void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
    public  void setSecondName(String SecondName) {
        this.SecondName = SecondName;
    }
    public  void setPatronymic(String Patronymic) {
        this.Patronymic = Patronymic;
    }
    public  void setGroup(String Group) {
        this.Group = Group;
    }
    public  void setDate(String Date) {
        this.Date = Date;
    }
    public  void setTelNumber(String TelNumber) {
        this.TelNumber = TelNumber;
    }
    public  void setType(String Type) {
        this.Type = Type;
    }
    public  void setMail(String Mail) {
        this.Mail = Mail;
    }
    public  void setImage(String Image) {
        this.Image = Image;
    }

}



