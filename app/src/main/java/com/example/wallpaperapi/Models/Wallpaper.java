package com.example.wallpaperapi.Models;

import com.google.gson.annotations.SerializedName;

public class Wallpaper {
    @SerializedName("src")
    private ImageDiemensions src;

    public Wallpaper(ImageDiemensions src) {
        this.src = src;
    }

    public ImageDiemensions getSrc() {
        return src;
    }

    public void setSrc(ImageDiemensions src) {
        this.src = src;
    }


}
