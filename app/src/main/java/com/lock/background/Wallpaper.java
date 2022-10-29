package com.lock.background;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class Wallpaper implements Serializable {
    @SerializedName("album_images")
    ArrayList<ImageList> album_images;
    @SerializedName("status")
    String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public ArrayList<ImageList> getAlbum_images() {
        return this.album_images;
    }

    public void setAlbum_images(ArrayList<ImageList> arrayList) {
        this.album_images = arrayList;
    }
}
