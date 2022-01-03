package com.example.appmonitorapplication;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class CustomAdaptor extends RecyclerView.Adapter<CustomAdaptor.ViewHolder> {


    private AppInformation[] listdata;
    public static List<AppInformation> selectedAppsList = new ArrayList<>();


    public CustomAdaptor(AppInformation[] listdata) {
        this.listdata=listdata;
        for(AppInformation app: listdata)
            System.out.println("List in adaptor is "+app.toString());
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem =layoutInflater.inflate(R.layout.activity_example_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( CustomAdaptor.ViewHolder holder, int position) {

         AppInformation myListData = listdata[position];
        System.out.println("List in vire is "+myListData.toString());
        int rowIndex = -1;

        holder.textView.setText(listdata[position].getAppName());
        holder.relativeLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast =Toast.makeText(view.getContext(), "Click on item " + myListData.getAppName(), Toast.LENGTH_SHORT);
                        //toast.setGravity(Gravity.TOP);
                        toast.show();
                        int rowIndex = position;
                        notifyDataSetChanged();
                        selectedAppsList.add(myListData);
                        System.out.println("After adding : "+selectedAppsList.toString());
                    }
                }
        );

    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }
    //public ViewHolder

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder( View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.listTextView);
            this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}
