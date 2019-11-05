package com.paperplay.myformbuilder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.paperplay.myformbuilder.model.SpinnerData;

import java.util.ArrayList;

/**
 * Created by Ahmed Yusuf on 22/08/19.
 */
public class SpinnerAdapter extends ArrayAdapter<SpinnerData>{
    private ArrayList<SpinnerData> dataList;


    public SpinnerAdapter(@NonNull Context context, int resource, ArrayList<SpinnerData> dataList) {
        super(context, resource, dataList);
        this.dataList = dataList;
    }

    @Nullable
    @Override
    public SpinnerData getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView txtItem = (TextView)super.getView(position, convertView, parent);
        txtItem.setText(dataList.get(position).getValue());
        return txtItem;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView txtItem = (TextView)super.getView(position, convertView,parent);
        txtItem.setText(dataList.get(position).getValue());
        return txtItem;
    }
}
