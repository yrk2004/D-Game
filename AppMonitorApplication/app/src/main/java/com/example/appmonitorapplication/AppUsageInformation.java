package com.example.appmonitorapplication;

public class AppUsageInformation {

    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "AppUsageInformation{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", timeSpent='" + timeSpent + '\'' +
                '}';
    }

    public AppUsageInformation(String appName, String packageName, String timeSpent) {
        this.appName = appName;
        this.packageName = packageName;
        this.timeSpent = timeSpent;
    }

    private String packageName;

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    private String timeSpent;
    public AppUsageInformation(){}
}
