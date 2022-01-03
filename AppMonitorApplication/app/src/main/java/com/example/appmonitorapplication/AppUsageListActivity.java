package com.example.appmonitorapplication;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

public class AppUsageListActivity extends AppCompatActivity {

    private static final String TAG = "anmol";
    Database d;
    Button homeButton;
    Button addAppsButton;
    Spinner usageTv;
     //List<AppUsageInformation> appList;

    public List<AppUsageInformation> getListOfAppsAndUsage(Context context, int value)
    {

        Map<String, UsageStats> appUsageMap = UStats.getUsageStatsList(this,value);

        List<UsageStats> appUsage =  new ArrayList<>(appUsageMap.values());
        System.out.println("Apps from usage are "+appUsage.toString());
        List<AppInformation> appList = d.getAppsFromDb();
        List<AppUsageInformation> fList = new ArrayList<>();

            for(AppInformation app: appList){
                    if(appUsageMap.get(app.getPackageName())!=null){
                    UsageStats u = appUsageMap.get(app.getPackageName());
                    String usageDuration = getDurationBreakdown(u.getTotalTimeInForeground());
                    fList.add(new AppUsageInformation(app.getAppName(),app.getPackageName(),usageDuration));
                }
            }




        Collections.reverse(fList);
        Log.i(TAG, "FinalList  : "+fList.toString());
        return  fList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_usage_apps);
        d = new Database(this);
        //Check if permission enabled
        if (!getGrantStatus()){
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
        homeButton = (Button)findViewById(R.id.home);
        addAppsButton = (Button)findViewById(R.id.addApps);
        usageTv = (Spinner)findViewById(R.id.usage_tv);
        List<AppUsageInformation>  appList= getListOfAppsAndUsage(AppUsageListActivity.this,24);
        String[] duration_array = {"Apps Usage For Last 24 Hours","Apps Usage For Last 1 Week","Apps Usage For Last 1 Month"};
        ArrayAdapter<CharSequence> adapterSpinner = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,duration_array);
// Specify the layout to use when the list of choices appears
        adapterSpinner.setDropDownViewResource(R.layout.spinner_value);
// Apply the adapter to the spinner
        usageTv.setAdapter(adapterSpinner);
        usageTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Apps Usage For Last 24 Hours")){
                    List<AppUsageInformation> appList=getListOfAppsAndUsage(AppUsageListActivity.this,24);
                    System.out.println("Duration is 24 and list is "+appList.toString());
                    loadList(appList);

                }

                else if (parent.getItemAtPosition(position).equals("Apps Usage For Last 1 Week")) {
                    List<AppUsageInformation> appList = getListOfAppsAndUsage(AppUsageListActivity.this, 168);
                    System.out.println("Duration is 168 and list is " + appList.toString());
                    loadList(appList);
                }
                else {
                    List<AppUsageInformation> appList = getListOfAppsAndUsage(AppUsageListActivity.this, 720);
                    System.out.println("Duration is 720 and list is " + appList.toString());
                    loadList(appList);
                }

                adapterSpinner.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       // this.loadStatistics(168);
       /* loadList(List<AppUsageInformation> appList);
       // List<AppUsageInformation> appList = getListOfAppsAndUsage(AppUsageListActivity.this);
        System.out.println("Setting appList as "+appList.toString());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listViewForUsage);

        System.out.println(recyclerView);
        AppUsageInformation[] appArray = new AppUsageInformation[appList.size()];
        System.out.println(appList.toString());
        CustomAdaptorForUsage adapter = new CustomAdaptorForUsage(appList.toArray(appArray));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);*/

    }

    private void loadList(List<AppUsageInformation> appList){
        System.out.println("Setting appList as "+appList.toString());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listViewForUsage);

        System.out.println(recyclerView);
        AppUsageInformation[] appArray = new AppUsageInformation[appList.size()];
        System.out.println(appList.toString());
        CustomAdaptorForUsage adapter = new CustomAdaptorForUsage(appList.toArray(appArray));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void addApplications(View view) {
        startActivity(new Intent(AppUsageListActivity.this, ListAppsActivity.class));
    }


    public void logout(View view) {
        startActivity(new Intent(AppUsageListActivity.this, MainActivity.class));
    }

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

    private boolean getGrantStatus() {
        AppOpsManager appOps = (AppOpsManager) getApplicationContext()
                .getSystemService(Context.APP_OPS_SERVICE);

        int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getApplicationContext().getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            return (getApplicationContext().checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            return (mode == MODE_ALLOWED);
        }
    }


}
