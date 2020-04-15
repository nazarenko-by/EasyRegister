package com.nazarenko_by.easyregister.ui.perInf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nazarenko_by.easyregister.AppUser;
import com.nazarenko_by.easyregister.JSONHelper;
import com.nazarenko_by.easyregister.PerInfEdit;
import com.nazarenko_by.easyregister.R;

public class PerInfFragment extends Fragment{

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView;
        View root;
        TextView firstNameText, secondNameText, patronymicText, groupText, dateText, telNumberText, mailText, typeText;
        AppUser appUser;

        root = inflater.inflate(R.layout.fragment_per_inf, container, false);
        Button editButton = root.findViewById(R.id.ind_edit_button);
        imageView = root.findViewById(R.id.per_inf_img);
        firstNameText = root.findViewById(R.id.first_name_inf);
        secondNameText = root.findViewById(R.id.second_name_inf);
        patronymicText = root.findViewById(R.id.patronymic_inf);
        groupText = root.findViewById(R.id.group_inf);
        dateText = root.findViewById(R.id.birthday_inf);
        telNumberText = root.findViewById(R.id.telephone_inf);
        mailText = root.findViewById(R.id.mail_inf);
        typeText = root.findViewById(R.id.type_id);
        appUser = JSONHelper.importFromJSON(container.getContext());

        if(appUser != null) {
            if (appUser.getImage() != null) {
                byte[] decodedString = Base64.decode(appUser.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            }
            if(!appUser.getFirstName().equals(""))
                firstNameText.setText(appUser.getFirstName());
            if(!appUser.getSecondName().equals(""))
                secondNameText.setText(appUser.getSecondName());
            if(!appUser.getPatronymic().equals(""))
                patronymicText.setText(appUser.getPatronymic());
            if(!appUser.getGroup().equals(""))
                groupText.setText(appUser.getGroup());
            if(!appUser.getDate().equals(""))
                dateText.setText(appUser.getDate());
            if(!appUser.getTelNumber().equals(""))
                telNumberText.setText(appUser.getTelNumber());
            if(!appUser.getMail().equals(""))
                mailText.setText(appUser.getMail());
            if(!appUser.getType().equals(""))
                typeText.setText(appUser.getType());
        }
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PerInfEdit.class);
                startActivity(intent);
            }

        });

        return root;
    }

}