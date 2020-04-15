package com.nazarenko_by.easyregister.ui.QR;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nazarenko_by.easyregister.AppUser;
import com.nazarenko_by.easyregister.AsyncQRGenerator;
import com.nazarenko_by.easyregister.JSONHelper;
import com.nazarenko_by.easyregister.R;

public class QRFragment extends Fragment {

    ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_qr, container, false);
        imageView = root.findViewById(R.id.QR_view);
        AppUser appUser;
        String userIdText = "", firstNameText = "", secondNameText = "", patronymicText = "", groupText = "",
                dateText = "", telNumberText = "", mailText = "", typeText = "";
        String QRUserInfoString = "-1";
        appUser = JSONHelper.importFromJSON(container.getContext());
        if(appUser != null) {
            userIdText = appUser.getUserId();
            firstNameText = appUser.getFirstName();
            secondNameText = appUser.getSecondName();
            patronymicText = appUser.getPatronymic();
            groupText = appUser.getGroup();
            dateText = appUser.getDate();
            telNumberText = appUser.getTelNumber();
            mailText = appUser.getMail();
            typeText = appUser.getType();

            QRUserInfoString = "|STRT|ID" + userIdText + "|FN" + firstNameText + "|SN" + secondNameText + "|PT" + patronymicText + "|GP" +
                    groupText + "|DT" + dateText + "|TN" + telNumberText + "|ML" + mailText + "|TP" + typeText + "|END";

        }
        Bitmap bitmap;
        AsyncQRGenerator asyncQRGenerator = new AsyncQRGenerator(getActivity());
       // new AsyncQRGenerator(getActivity()).execute(QRUserInfoString);
        asyncQRGenerator.execute(QRUserInfoString);
        bitmap = asyncQRGenerator.doInBackground(QRUserInfoString);
        imageView.setImageBitmap(bitmap);
        return root;
    }
}