package com.paperplay.myformbuilder.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.paperplay.myformbuilder.R;
import com.paperplay.myformbuilder.model.AutocompleteData;

import java.util.ArrayList;

/**
 * Created by Ahmed Yusuf on 22/08/19.
 */
public class AutocompleteAdapter extends ArrayAdapter<AutocompleteData>{
    private ArrayList<AutocompleteData> dataList, tempDataList, suggestions;
    private Activity activity;
    private int resourceId;

    public AutocompleteAdapter(@NonNull Activity activity, int resource, ArrayList<AutocompleteData> dataList) {
        super(activity.getBaseContext(), resource, dataList);
        this.activity = activity;
        this.resourceId = resource;
        this.dataList = dataList;
        tempDataList = new ArrayList<>(dataList);
        suggestions = new ArrayList<>();
    }

    public void updateListDropdown(ArrayList<AutocompleteData> dataList){
        this.dataList = dataList;
        tempDataList.clear();
        tempDataList.addAll(dataList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try{
            if(convertView == null){
                LayoutInflater inflater = activity.getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            AutocompleteData autocompleteData = getItem(position);
            TextView itemTitle = view.findViewById(R.id.txtRowAtcTitle);
            itemTitle.setText(autocompleteData.getValue());
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public AutocompleteData getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    private Filter dataFilter = new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            AutocompleteData autocompleteData = (AutocompleteData) resultValue;
            return autocompleteData.getValue();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if(charSequence != null && !charSequence.toString().isEmpty()) {
                suggestions.clear();
                for (AutocompleteData autocompleteData : tempDataList) {
                    if(autocompleteData.getValue().contains(" ")){
                        String[] split = autocompleteData.getValue().toLowerCase().split(" ");
                        for(String sub : split){
                            if (sub.startsWith(charSequence.toString().toLowerCase())) {
                                suggestions.add(autocompleteData); //find for each word
                            }
                        }
                    }else if (autocompleteData.getValue().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(autocompleteData);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }else{
                suggestions.clear();
                suggestions.addAll(tempDataList);
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            ArrayList<AutocompleteData> tempValues = (ArrayList<AutocompleteData>)results.values;
            if(results.count > 0){
                clear();
                for (AutocompleteData autocompleteData : tempValues){
                    add(autocompleteData);
                    notifyDataSetChanged();
                }
            }else{
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
