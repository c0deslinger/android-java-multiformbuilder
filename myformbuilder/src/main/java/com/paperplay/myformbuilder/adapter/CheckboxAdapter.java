package com.paperplay.myformbuilder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.paperplay.myformbuilder.R;
import com.paperplay.myformbuilder.model.CheckboxData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Yusuf on 2020-02-26.
 */
public class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.CheckboxViewHolder> {
    private Context context;
    List<CheckboxData> checkboxDataList;

    public CheckboxAdapter(Context context, List<CheckboxData> checkboxDataList) {
        this.context = context;
        this.checkboxDataList = checkboxDataList;
    }

    @NonNull
    @Override
    public CheckboxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_checkbox,
                viewGroup, false);
        return new CheckboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckboxViewHolder checkboxViewHolder, int i) {
        checkboxViewHolder.rowTitle.setText(checkboxDataList.get(i).getValue());
        checkboxViewHolder.checkBox.setChecked(checkboxDataList.get(i).isChecked());
        checkboxViewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkboxDataList.get(i).setChecked(isChecked);
        });
    }

    public List<CheckboxData> getCheckboxListChecked() {
        List<CheckboxData> listChecked = new ArrayList<>();
        for(CheckboxData checkboxData: this.checkboxDataList){
            if(checkboxData.isChecked()){
                listChecked.add(checkboxData);
            }
        }
        return checkboxDataList;
    }

    @Override
    public int getItemCount() {
        return checkboxDataList.size();
    }

    class CheckboxViewHolder extends RecyclerView.ViewHolder{

        TextView rowTitle;
        CheckBox checkBox;

        CheckboxViewHolder(@NonNull View itemView) {
            super(itemView);
            rowTitle = itemView.findViewById(R.id.txtRowCheckboxTitle);
            checkBox = itemView.findViewById(R.id.checkboxSelected);
        }
    }
}
