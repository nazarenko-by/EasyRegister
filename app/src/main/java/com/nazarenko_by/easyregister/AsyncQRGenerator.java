package com.nazarenko_by.easyregister;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class AsyncQRGenerator extends AsyncTask<String, Integer, Bitmap> {

    private static final int WIDTH = 400;
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFF;
    Activity activity;

    private ProgressDialog dialog;

    public AsyncQRGenerator(Activity a) {
        this.activity = a;
    }

    @Override
    public Bitmap doInBackground(String... params) {
        Bitmap bitmap;
        BitMatrix matrix = null;
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            matrix = new QRCodeWriter().encode(
                    params[0],
                    com.google.zxing.BarcodeFormat.QR_CODE,
                    WIDTH, WIDTH,hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        bitmap = matrixToBitmap(matrix);


        return bitmap;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(activity);
        dialog.setTitle("Generating...");
        dialog.setMessage("Generating QR code, please wait...");
        dialog.setCancelable(false);
        dialog.show();
        super.onPreExecute();
    }


    private Bitmap matrixToBitmap(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setPixel(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        try {
            /* Закрываем диалоговое окно */
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Оповещаем пользователя об успешном завершении */
        String message = "QR codes generated successfully!";
        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);

        toast.show();

        super.onPostExecute(bitmap);
    }

}
