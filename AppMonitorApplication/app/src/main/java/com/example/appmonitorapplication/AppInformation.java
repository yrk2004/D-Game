package com.example.appmonitorapplication;

public class AppInformation {

    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "AppInformation{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public AppInformation(String appName, String packageName) {
        this.appName = appName;
        this.packageName = packageName;
    }

    private String packageName;
    public AppInformation(){}
}
