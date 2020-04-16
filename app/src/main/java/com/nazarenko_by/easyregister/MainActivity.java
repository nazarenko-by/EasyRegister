package com.nazarenko_by.easyregister;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_per_inf, R.id.nav_qr, R.id.nav_log, R.id.nav_add_user)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {} else {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.camera_error, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                createTableDialog();
                return true;
            case R.id.action_open:
                SQLiteDatabase db ;
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                db = databaseHelper.getWritableDatabase();
                Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

                if (c.moveToFirst()) {
                    while ( !c.isAfterLast() ) {
                        Toast.makeText(this, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
                        c.moveToNext();
                    }
                }
                db.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createTableDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle(getResources().getString(R.string.action_create));
        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton(getResources().getString(R.string.create_table_button),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),input.getText().toString(),Toast.LENGTH_SHORT).show();
                        createNewDB(input.getText().toString());
                    }
                });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private void createNewDB(String tableName){
        SQLiteDatabase db ;
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + DatabaseHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseHelper.COLUMN_SN + " TEXT, "
                + DatabaseHelper.COLUMN_FN + " TEXT, "
                + DatabaseHelper.COLUMN_PT + " TEXT, "
                + DatabaseHelper.COLUMN_GP + " TEXT, "
                + DatabaseHelper.COLUMN_TY + " TEXT, "
                + DatabaseHelper.COLUMN_DB + " DATE, "
                + DatabaseHelper.COLUMN_TN + " TEXT, "
                + DatabaseHelper.COLUMN_ML + " TEXT);");
        db.close();
        SharedPreferences preferences = getSharedPreferences("TABLE", MODE_PRIVATE);
        SharedPreferences.Editor saveTableName = preferences.edit();
        saveTableName.putString("OPEN", tableName);
        saveTableName.apply();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onResume(){
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView imageView = headerView.findViewById(R.id.imageHeaderView);
        TextView textHeaderName = headerView.findViewById(R.id.textHeaderName);
        TextView textHeaderMail = headerView.findViewById(R.id.textHeaderMail);
        AppUser appUser = JSONHelper.importFromJSON(this);
        if(appUser != null){
            if (appUser.getImage() != null) {
                byte[] decodedString = Base64.decode(appUser.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            }
            if(!appUser.getSecondName().equals("") && !appUser.getFirstName().equals("")){
                textHeaderName.setText(appUser.getSecondName() + " " + appUser.getFirstName());
            } else if(appUser.getSecondName().equals("") && !appUser.getFirstName().equals("")){
                textHeaderName.setText(appUser.getFirstName());
            }else if(!appUser.getSecondName().equals("") && appUser.getFirstName().equals("")){
                textHeaderName.setText(appUser.getSecondName());
            }
            if(!appUser.getMail().equals("")){
                textHeaderMail.setText(appUser.getMail());
            }
        }

    }

}
