package com.example.endoscopycard.Ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.endoscopycard.R;

import java.io.File;
import java.io.FileOutputStream;

public class Scan extends AppCompatActivity {

    String bytearray,scanName;
    ImageView scan,download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scan=findViewById(R.id.scan);
        download=findViewById(R.id.download);

        bytearray = getIntent().getStringExtra("encodedString");
        byte[] decodedString = Base64.decode(bytearray, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(getApplicationContext()).load(decodedByte).into(scan);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveImage(decodedByte,getIntent().getStringExtra("scanName"));
                Toast.makeText(getApplicationContext(),"Saving Scan...",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveImage(Bitmap finalBitmap, String image_name) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "Image-" + image_name+ ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}