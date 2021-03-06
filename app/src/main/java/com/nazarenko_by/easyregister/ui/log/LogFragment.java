package com.nazarenko_by.easyregister.ui.log;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.nazarenko_by.easyregister.AppUser;
import com.nazarenko_by.easyregister.DatabaseHelper;
import com.nazarenko_by.easyregister.R;
import com.nazarenko_by.easyregister.AppUserAdapter;

import java.util.ArrayList;
import java.util.List;

public class LogFragment extends Fragment {

    private List<AppUser> appUsers = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor userCursor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final AppUserAdapter appUserAdapter;
        databaseHelper = new DatabaseHelper(inflater.getContext().getApplicationContext());
        final View root = inflater.inflate(R.layout.fragment_log, container, false);
        setInitialData();
        final ListView list = root.findViewById(R.id.list);
        appUserAdapter = new AppUserAdapter(inflater.getContext(), R.layout.list_item, appUsers);
        list.setAdapter(appUserAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] options = { getResources().getString(R.string.delete),
                        getResources().getString(R.string.delete_all),getResources().getString(R.string.cancel) };
                Context context = getActivity();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.delete_message);
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals(getResources().getString(R.string.delete))) {
                            delInitialData(appUserAdapter.getItem(position).getUserId(), root);
                        }else if (options[item].equals(getResources().getString(R.string.delete_all))) {
                            delAllInitialData(root);
                        }else if (options[item].equals(getResources().getString(R.string.cancel))) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                return false;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder userInfo = new AlertDialog.Builder(getActivity());
                userInfo.setTitle(appUserAdapter.getItem(position).getSecondName() + " "
                        + appUserAdapter.getItem(position).getFirstName() + "\n"
                        + appUserAdapter.getItem(position).getPatronymic());
                String user = getResources().getString(R.string.birthdayDate) +  appUserAdapter.getItem(position).getDate() +
                        getResources().getString(R.string.type) + appUserAdapter.getItem(position).getType() +
                        getResources().getString(R.string.group) + appUserAdapter.getItem(position).getGroup() +
                        getResources().getString(R.string.telNumber) + appUserAdapter.getItem(position).getTelNumber() +
                        getResources().getString(R.string.mail) + appUserAdapter.getItem(position).getMail() +
                        getResources().getString(R.string.id) + appUserAdapter.getItem(position).getUserId();
                userInfo.setMessage(user);
                userInfo.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                userInfo.show();
            }
        });
        return root;
    }

    private void setInitialData() {
        db = databaseHelper.getReadableDatabase();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("TABLE",Context.MODE_PRIVATE);
        String tableName = preferences.getString("OPEN", DatabaseHelper.TABLE);

        userCursor = db.rawQuery("SELECT * FROM " + tableName + " ORDER BY "
                + DatabaseHelper.COLUMN_GP + " ASC;", null);
        if (userCursor.moveToFirst()) {
            for (int i = 0; i < userCursor.getCount(); i++) {
                appUsers.add(new AppUser (userCursor.getString(0),userCursor.getString(2),
                        userCursor.getString(1),userCursor.getString(3),userCursor.getString(4),
                        userCursor.getString(6),userCursor.getString(7),userCursor.getString(5),
                        userCursor.getString(8), null));
                userCursor.moveToNext();
            }
        }

    }

    private void delAllInitialData(View root){
        db.delete(DatabaseHelper.TABLE, null,null);
        getActivity().onBackPressed();
        Navigation.findNavController(root).navigate(R.id.nav_log);
    }

    private void delInitialData(String userId, View root){
        db.delete(DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID +" =?", new String[] {String.valueOf(userId)});
        getActivity().onBackPressed();
        Navigation.findNavController(root).navigate(R.id.nav_log);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        userCursor.close();
    }
}