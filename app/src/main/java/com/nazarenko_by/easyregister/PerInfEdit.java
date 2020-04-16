package com.nazarenko_by.easyregister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class PerInfEdit extends AppCompatActivity {

    private EditText userIdText, firstNameText, secondNameText, patronymicText, groupText, dateText, telNumberText, mailText, typeText;
    private String imgString, userId;
    private ImageButton imageButton;
    private AppUser appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_inf_edit);
        imageButton = findViewById(R.id.per_inf_img_edit);
        userIdText = findViewById(R.id.user_id);
        firstNameText = findViewById(R.id.first_name_inf_edit);
        secondNameText = findViewById(R.id.second_name_inf_edit);
        patronymicText = findViewById(R.id.patronymic_inf_edit);
        groupText = findViewById(R.id.group_inf_edit);
        dateText = findViewById(R.id.birthday_inf_edit);
        telNumberText = findViewById(R.id.telephone_inf_edit);
        mailText = findViewById(R.id.mail_inf_edit);
        typeText = findViewById(R.id.type_id_edit);

        appUser = JSONHelper.importFromJSON(this);

        if(appUser != null) {
            imgString = appUser.getImage();
            userId = appUser.getUserId();
            if (appUser.getImage() != null) {
                byte[] decodedString = Base64.decode(appUser.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageButton.setImageBitmap(decodedByte);
            }
            if(appUser.getUserId() != null && !appUser.getUserId().equals("")){
                userIdText.setVisibility(View.INVISIBLE);
            }
            firstNameText.setText(appUser.getFirstName());
            secondNameText.setText(appUser.getSecondName());
            patronymicText.setText(appUser.getPatronymic());
            groupText.setText(appUser.getGroup());
            dateText.setText(appUser.getDate());
            telNumberText.setText(appUser.getTelNumber());
            mailText.setText(appUser.getMail());
            typeText.setText(appUser.getType());
        }

    }

    private void updateUserInfo(){
        if (userId == null)
            userId = userIdText.getText().toString();
        else if (userId.equals(""))
            userId = userIdText.getText().toString();
        String firstName = firstNameText.getText().toString();
        String secondName = secondNameText.getText().toString();
        String patronymic = patronymicText.getText().toString();
        String group = groupText.getText().toString();
        String date = dateText.getText().toString();
        String telNumber = telNumberText.getText().toString();
        String mail = mailText.getText().toString();
        String type = typeText.getText().toString();


        appUser = new AppUser(userId ,firstName, secondName, patronymic, group, date, telNumber, type, mail,imgString);
    }

    public void saveEdit(View view){
        updateUserInfo();
        Intent intent = new Intent(this, MainActivity.class);
        boolean result = JSONHelper.exportToJSON(this, appUser);
        if(result){
            Toast.makeText(this, R.string.per_inf_save_text_ok, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, R.string.per_inf_save_button_not, Toast.LENGTH_LONG).show();
        }
        this.finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void perImgEditClick(View view) {
        final CharSequence[] options = { getResources().getString(R.string.take_photo),
                getResources().getString(R.string.from_gallery),getResources().getString(R.string.cancel) };
        Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getResources().getString(R.string.photo_message));

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals(getResources().getString(R.string.take_photo))) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals(getResources().getString(R.string.from_gallery))) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals(getResources().getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream .toByteArray();
                        imgString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        imageButton.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int photoW = 800;
                        int photoH = 800;
                        bitmap = Bitmap.createScaledBitmap(bitmap, photoW, photoH, true);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream .toByteArray();
                        imgString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        imageButton.setImageBitmap(bitmap);
                    }
                    break;
            }
        }
    }
}
