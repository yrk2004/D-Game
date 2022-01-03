package com.example.appmonitorapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAppsActivity extends AppCompatActivity {

    private static final String TAG = "anmol";

    public List<AppInformation> getListOfApps(Context context)
    {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities(mainIntent, PackageManager.GET_META_DATA);
        List<AppInformation> appList = new   ArrayList<AppInformation>(pkgAppsList.size());
        for(ResolveInfo app:pkgAppsList)
        {
            AppInformation myapp = new AppInformation();
            ApplicationInfo appInfo =app.activityInfo.applicationInfo;
            PackageManager packageManager = getPackageManager();
            Drawable packageIcon = packageManager.getApplicationIcon(appInfo);
            String packageName = appInfo.packageName;
            String packageLabel = String.valueOf(packageManager.getApplicationLabel(appInfo));
            myapp.setAppName(packageLabel);
            myapp.setPackageName(packageName);
            Log.i(TAG, "Insatlled app : "+packageLabel +"  packageInfo "+packageName);
            appList.add(myapp);
        }
        return  appList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_apps);
        List<AppInformation> appList = getListOfApps(ListAppsActivity.this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.abcView);
        Database d = new Database(this);
        System.out.println(recyclerView);
        AppInformation[] appArray = new AppInformation[appList.size()];
        System.out.println(appList.toString());
        CustomAdaptor adapter = new CustomAdaptor(appList.toArray(appArray));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Button saveBtn = (Button) findViewById(R.id.saveListOfApps);
        saveBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Hello anmol "+ CustomAdaptor.selectedAppsList);
                        d.populateListInDb(CustomAdaptor.selectedAppsList);
                        startActivity(new Intent(ListAppsActivity.this , AppUsageListActivity.class));
                    }
                }
        );
    }

    public void logout(View view) {
        startActivity(new Intent(ListAppsActivity.this, MainActivity.class));
    }
}
