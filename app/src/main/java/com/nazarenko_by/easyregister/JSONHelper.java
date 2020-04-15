package com.nazarenko_by.easyregister;

import android.content.Context;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class JSONHelper {

    private static final String FILE_NAME = "AppUserData.json";
    static boolean exportToJSON(Context context, AppUser appUser){

        Gson gson = new Gson();
        DataItem dataItem = new DataItem();
        dataItem.setAppUser(appUser);
        String jsonString = gson.toJson(dataItem);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    return false;
    }

   public static AppUser importFromJSON(Context context){
        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = context.openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataItem dataItem = gson.fromJson(streamReader, DataItem.class);
            return dataItem.getAppUser();
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private static class DataItem{
        private AppUser appUser;
        AppUser getAppUser(){
            return appUser;
        }
        void setAppUser(AppUser appUser){
            this.appUser = appUser;
        }
    }
}
