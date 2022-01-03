package com.example.appmonitorapplication;


import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Build;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class UStats extends AppCompatActivity {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
    public static final String TAG = UStats.class.getSimpleName();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("ResourceType")
    public static void getStats(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        int interval = UsageStatsManager.INTERVAL_YEARLY;
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();

        Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
        Log.d(TAG, "Range end:" + dateFormat.format(endTime));

        UsageEvents uEvents = usm.queryEvents(startTime,endTime);
        while (uEvents.hasNextEvent()){
            UsageEvents.Event e = new UsageEvents.Event();
            uEvents.getNextEvent(e);

            if (e != null){
                Log.d(TAG, "Event: " + e.getPackageName() + "\t" +  e.getTimeStamp());
            }
        }
    }

   /* @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static List<UsageStats> getUsageStatsList(Context context){
        UsageStatsManager usm = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();

        Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
        Log.d(TAG, "Range end:" + dateFormat.format(endTime));

        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,startTime,endTime);
        return usageStatsList;
    }*/


    @RequiresApi(api = Build.VERSION_CODES.N)
    public  static Map<String, UsageStats> getUsageStatsList(Context context, int duration){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(USAGE_STATS_SERVICE);
        long endTime;
        long startTime;

            duration = 0 - duration;
            Calendar calendar = Calendar.getInstance();
            endTime = calendar.getTimeInMillis();
            calendar.add(Calendar.HOUR_OF_DAY, duration);
            startTime= calendar.getTimeInMillis();

        System.out.println("Duration selected is "+duration+ "from :"+startTime + " and endTime "+endTime);



       /* List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, startTime ,
                endTime);
        System.out.println("Duration selected is "+duration+ "from :"+startTime + " and endTime "+endTime);
      //  appList = appList.stream().filter(app -> app.getTotalTimeInForeground() > 0).collect(Collectors.toList());
        Map<String, UsageStats> mySortedMap = new TreeMap<>();
        // Group the usageStats by application and sort them by total time in foreground
        if (appList.size() >= 0) {


            for (UsageStats usageStats : appList) {
                System.out.println("Adding in map "+usageStats.toString());
                mySortedMap.put(usageStats.getPackageName(), usageStats);
            }
           // showAppsUsage(mySortedMap);
        }*/

        Map<String, UsageStats> mySortedMap = usm.queryAndAggregateUsageStats(startTime,endTime);
        for(String pName : mySortedMap.keySet()){


            UsageStats u = mySortedMap.get(pName);
            System.out.println("Hello anmol app is "+u.getPackageName()+" time is "+u.getTotalTimeInForeground());
        }
        return mySortedMap;
    }
    public static void printUsageStats(List<UsageStats> usageStatsList){
        for (UsageStats u : usageStatsList){
            Log.d(TAG, "Pkg: " + u.getPackageName() +  "\t" + "ForegroundTime: "
                    + u.getTotalTimeInForeground()) ;
        }

    }

    public static void printCurrentUsageStatus(Context context){
        printUsageStats(new ArrayList<>(getUsageStatsList(context,24).values()));
    }
    @SuppressWarnings("ResourceType")
    private static UsageStatsManager getUsageStatsManager(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        return usm;
    }



    /**
     * check if the application info is still existing in the device / otherwise it's not possible to show app detail
     *
     * @return true if application info is available
     */
    private boolean isAppInfoAvailable(UsageStats usageStats) {
        try {
            getApplicationContext().getPackageManager().getApplicationInfo(usageStats.getPackageName(), 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * helper method to get string in format hh:mm:ss from miliseconds
     *
     * @param millis (application time in foreground)
     * @return string in format hh:mm:ss from miliseconds
     */
    private String getDurationBreakdown(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return (hours + " h " + minutes + " m " + seconds + " s");
    }




}