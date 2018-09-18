package com.poorstudent.translator2;

import android.util.ArrayMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Languages {
    @SerializedName("dirs")
    @Expose
    private ArrayList<String> dirs;
    @SerializedName("langs")
    @Expose
    private ArrayMap<String, String> langs;

    public ArrayList<String> getDirs() {
        return dirs;
    }

    public void setDirs(ArrayList<String> dirs) {
        this.dirs = dirs;
    }

    public ArrayMap<String, String> getLangs() {
        return langs;
    }

    public void setLangs(ArrayMap<String, String> langs) {
        this.langs = langs;
    }
}
