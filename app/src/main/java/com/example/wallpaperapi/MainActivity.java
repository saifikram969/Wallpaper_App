package com.example.wallpaperapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wallpaperapi.Api.RetrofitClient;
import com.example.wallpaperapi.Models.Wallpaper;
import com.example.wallpaperapi.Models.WallpaperResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,RecyclerViewClickInterface {
    private RecyclerView imageRecyclerview;
    RecyclerView recyclerView;
    private final String API_KEY = "563492ad6f91700001000001796f3330077d41a9a2198027768b45d2";
    private int pageCount = 1;
    private static int perPage = 80;
    private List<Wallpaper> wallpaperList = new ArrayList<>();
    private NestedScrollView nestedScrollView;

    ProgressBar progressBar;

    CardView nature,wildlife,technology,education,love,popular;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //method create kr liya yha
        initRecyclerview();
        nestedScrollView = findViewById(R.id.nestedScroolview);

        progressBar = findViewById(R.id.progressBar);
        setUpPagination(true);


    }
    

    //pagination ye hota h ke data jo aara h album ki form aa raha i mean bhut sara data aa raha h to hum pagination ki help se usko block view me kr denge i mean first page pr aayega then second data second page pr isse kya benefit hoga data fetch hone me time nhi lega zada  or agr hum pagination ka use nhi krenge to data show hone me kaafi time lega isliye hum iska use krte h
    //isko third party library se bhi kr skte h but hum code ke through kra h isme
    private void setUpPagination(boolean isPaginationAllowed) {
        if (isPaginationAllowed) {
            nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    fetchData(++pageCount);
                    Toast.makeText(this, "" + pageCount, Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            });



        }

    }

    private void initRecyclerview() {
        imageRecyclerview = findViewById(R.id.recycler);
        imageRecyclerview.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        imageRecyclerview.setLayoutManager(gridLayoutManager);
        fetchData(pageCount);
    }

    private void fetchData(int pageCount) {
        Call<WallpaperResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getWallpaper(API_KEY, pageCount, perPage);
        call.enqueue(new Callback<WallpaperResponse>() {
            @Override
            public void onResponse(Call<WallpaperResponse> call, Response<WallpaperResponse> response) {
                WallpaperResponse wallpaperResponse = response.body();
                if (response.isSuccessful() && null != wallpaperResponse) {
                    wallpaperList.addAll(wallpaperResponse.getPhotosList());
                    WallpaperAdapter wallpaperAdapter = new WallpaperAdapter(getApplicationContext(),wallpaperList,MainActivity.this);
                    imageRecyclerview.setAdapter(wallpaperAdapter);
                    wallpaperAdapter.notifyDataSetChanged();

                } else
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<WallpaperResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(),FullImageActivity.class);
        intent.putExtra("imageUrl",wallpaperList.get(position).getSrc().getLarge());
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

    }



/*    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(),FullImageActivity.class);
        intent.putExtra("imageUrI",wallpaperList.get(position).getSrc().getLarge());
        startActivity(intent);
    }*/
}