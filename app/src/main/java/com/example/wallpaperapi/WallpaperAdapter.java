package com.example.wallpaperapi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wallpaperapi.Models.Wallpaper;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViehHolder> {
    private Context mContext;
    private List<Wallpaper> wallpaperList;
   private RecyclerViewClickInterface recyclerViewClickInterface;

    public WallpaperAdapter(Context mContext, List<Wallpaper> wallpaperList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.mContext = mContext;
        this.wallpaperList = wallpaperList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public WallpaperAdapter.ViehHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //oncreate ke ander apne view ho attach kerna hoga layout inflater ki help se
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item,parent, false);
        return new ViehHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperAdapter.ViehHolder holder, int position) {
        Glide.with(mContext)
                .load(wallpaperList.get(position).getSrc().getMedium())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                     holder.mprogressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.mprogressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.mWallpaper);

    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    public class ViehHolder extends RecyclerView.ViewHolder {
        //view ko typeCast krung
        private ImageView mWallpaper;
        private ProgressBar mprogressBar;
        public ViehHolder(@NonNull View itemView) {
            super(itemView);
            //isko boltey h typeCast krna
            mWallpaper=itemView.findViewById(R.id.wallpaper);
            mprogressBar=itemView.findViewById(R.id.progressBar);
            mWallpaper.setOnClickListener(v -> {
                recyclerViewClickInterface.onItemClick(getAdapterPosition());
            });
          /* // mWallpaper.setOnClickListener(v -> {
               *//* recyclerViewClickInterface.onItemClick(getAdapterPosition());*//*
            });*/

        }
    }
}
