package com.example.appktx2.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appktx2.R;
import com.example.appktx2.interfaces.IGetItemText;

import java.util.List;

public class AdapterSelector<T> extends BaseAdapter {
    private Context context;
    private List<T> dataList;
    private IGetItemText getItemText;
    public AdapterSelector(Context context, List<T> dataList){
        this.context = context;
        this.dataList  = dataList;
    }
    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }
    @Override
    public Object getItem(int position) {
        return dataList != null ? dataList.get(position) : null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_selector_selected, parent,false);
        T data = dataList.get(position);

        TextView roomCollectionName = rootView.findViewById(R.id.itemName);
        if(getItemText != null){
            String text = getItemText.getItemText(data);
            roomCollectionName.setText(text);
        }
        else{
            roomCollectionName.setText("CALL BACK GET ITEM TEXT NOT DEFIND");
        }
        return rootView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_selector_dropdown, parent,false);
        T data = dataList.get(position);

        TextView roomCollectionName = rootView.findViewById(R.id.itemName);
        if(getItemText != null){
            String text = getItemText.getItemText(data);
            roomCollectionName.setText(text);
        }
        else{
            roomCollectionName.setText("CALL BACK GET ITEM TEXT NOT DEFIND");
        }
        return rootView;
    }

    public void setEventShowText(IGetItemText getItemText){
        this.getItemText = getItemText;
    }
}
