package com.example.appmonitorapplication;

import android.graphics.drawable.Drawable;

public class AppDetails {

    public Drawable appIcon;
    public String appName;
    public int usagePercentage;
    public String usageDuration;
    public String lastVisible;
    public String dataUsage;

    public AppDetails(Drawable appIcon, String appName, int usagePercentage, String usageDuration, String lastVisible, String dataUsage) {
        this.appIcon = appIcon;
        this.appName = appName;
        this.usagePercentage = usagePercentage;
        this.usageDuration = usageDuration;
        this.lastVisible = lastVisible;
        this.dataUsage = dataUsage;
    }
}