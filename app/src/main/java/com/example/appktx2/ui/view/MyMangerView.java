package com.example.appktx2.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.appktx2.data.model.itemCommon.ItemData;
import com.example.appktx2.databinding.ViewMyMangerViewBinding;
import com.example.appktx2.interfaces.ICallBack;

import java.util.List;

import lombok.NonNull;

public class MyMangerView extends LinearLayout {
    ViewMyMangerViewBinding binding;
    List<ItemData> itemDataListShow;
    List<ItemData> itemDataListAll;
    ICallBack onAction = (Object... ojbs) -> {
        Toast.makeText(getContext(), "Chua xet callback", Toast.LENGTH_SHORT).show();
    };
    ICallBack onSearch;

    public MyMangerView(Context context) {
        super(context);
        init(context, null);
    }
    public MyMangerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public MyMangerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public MyMangerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs){
        binding = ViewMyMangerViewBinding.inflate(LayoutInflater.from(context), this, true);

        setEvent();
    }
    private void setEvent() {
        reRenderManagerOnly();
    }
    private void filterData(){
        this.itemDataListShow = this.itemDataListAll;
    }
    public void reRenderListView(){
        filterData();
        binding.myListView.setItemDataList(this.itemDataListShow);
    }
    public void reRenderManagerOnly(){
        if(this.onAction != null){
            binding.action.setVisibility(VISIBLE);
            binding.action.setOnClickListener(v -> {
                this.onAction.action(v, "DA SET CALL BACK");
            });
        }
        else{
            binding.action.setVisibility(GONE);
        }

        if(this.onSearch != null){
            binding.searchBox.setVisibility(VISIBLE);
            binding.searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(onSearch!= null){
                        onSearch.action(newText);
                    }
                    return false;
                }
            });
        }
        else{
            binding.searchBox.setVisibility(GONE);
        }
    }
    public void setItemDataList(List<ItemData> itemDataList){
        this.itemDataListAll = itemDataList;
        reRenderListView();
    }
    public void setOnAction(ICallBack onAction){
        this.onAction = onAction;
        this.reRenderManagerOnly();
    }
    public void setOnSearch(ICallBack onSearch){
        this.onSearch = onSearch;
        this.reRenderManagerOnly();
    }
}
