package com.example.wallpaperapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class FullImageActivity extends AppCompatActivity {
    private ImageView fullImage;
    private ProgressBar progressBar;
    Button setButton,downloadButton;
    private String imageUrl,dirPath, fileName;
    File file;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        //getSupportActionBar().hide();//isse actionbar hide ho jyga
       // this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//status bar bhi hide ho jyga isse

        fullImage = findViewById(R.id.full_image);
        progressBar = findViewById(R.id.progressBar);
        setButton = findViewById(R.id.setbutton);
        // downloadButton = findViewById(R.id.downloadbutton);


        imageUrl=getIntent().getStringExtra("imageUrl");
        loadImage(imageUrl);




        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { setHomeScreen();

            }

            private void setHomeScreen() {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                Bitmap bitmap = ((BitmapDrawable)fullImage.getDrawable()).getBitmap();
                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(FullImageActivity.this, "Wallpaper Set", Toast.LENGTH_SHORT).show();

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

    }

    private void loadImage(String imageUrI) {

        Glide.with(this)
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(fullImage);
    }

   /* public void DownloadWallpaperEvent(View view) {

        DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(imageUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
        Toast.makeText(this, "Downloading Start", Toast.LENGTH_SHORT).show();


    }*/

}