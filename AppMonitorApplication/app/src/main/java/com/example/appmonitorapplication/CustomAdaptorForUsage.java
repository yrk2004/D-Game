package com.example.appmonitorapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdaptorForUsage extends RecyclerView.Adapter<CustomAdaptorForUsage.ViewHolder> {


    private AppUsageInformation[] listdata;


    public CustomAdaptorForUsage(AppUsageInformation[] listdata) {
        this.listdata=listdata;
        for(AppUsageInformation app: listdata)
            System.out.println("List in adaptor is "+app.toString());
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem =layoutInflater.inflate(R.layout.activity_usage_app_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdaptorForUsage.ViewHolder holder, int position) {

         AppUsageInformation myListData = listdata[position];
        System.out.println("List in vire is "+myListData.toString());
        int rowIndex = -1;

        holder.textViewAppName.setText(listdata[position].getAppName());
        holder.timeSpent.setText(String.valueOf(listdata[position].getTimeSpent()));

    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }
    //public ViewHolder

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewAppName;
        public TextView timeSpent;
        public RelativeLayout relativeLayout;
        public ViewHolder( View itemView) {
            super(itemView);
            this.textViewAppName = (TextView) itemView.findViewById(R.id.textViewAppName);
            this.timeSpent = (TextView) itemView.findViewById(R.id.timeSpent);
            this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutForUsage);
        }
    }
}
