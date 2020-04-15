package com.nazarenko_by.easyregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppUserAdapter extends ArrayAdapter<AppUser> {

    private LayoutInflater inflater;
    private int layout;
    private List<AppUser> appUsers;

    public AppUserAdapter(Context context, int resource, List<AppUser> appUsers) {
        super(context, resource, appUsers);
        this.appUsers = appUsers;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView groupView = (TextView) view.findViewById(R.id.group_text);
        TextView firstNameView = (TextView) view.findViewById(R.id.first_name_text);
        TextView secondNameView = (TextView) view.findViewById(R.id.second_name_text);
        AppUser appUser = appUsers.get(position);
        firstNameView.setText(appUser.getFirstName() + " ");
        secondNameView.setText(appUser.getSecondName());
        groupView.setText(appUser.getGroup());

        return view;
    }
}