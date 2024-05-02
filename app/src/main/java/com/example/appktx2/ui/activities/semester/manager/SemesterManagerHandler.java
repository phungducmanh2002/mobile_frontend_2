package com.example.appktx2.ui.activities.semester.manager;

import android.widget.Toast;

import com.example.appktx2.data.dto.ItemDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.data.dto.SemesterDto;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.IRoomItemService;
import com.example.appktx2.net.services.ISemesterService;
import com.example.appktx2.ui.dialog.MyDialog;
import com.example.appktx2.utils.MapperUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SemesterManagerHandler {
    ActivitySemesterManager view;
    ISemesterService semesterService = RetrofitClient.GI().getRetrofit().create(ISemesterService.class);
    public SemesterManagerHandler(ActivitySemesterManager view){
        this.view = view;

        view.binding.viewManager.setOnSearch((Object... objs) -> {});
        view.binding.viewManager.setOnAction((Object... objs) -> {
            MyDialog myDialog = new MyDialog(this.view);
            myDialog.show();
        });
    }
    public void getAllSemester(){
        semesterService.getAllSemester().enqueue(new Callback<List<SemesterDto>>() {
            @Override
            public void onResponse(Call<List<SemesterDto>> call, Response<List<SemesterDto>> response) {
                if(response.isSuccessful()){
                    view.binding.viewManager.setItemDataList(MapperUtils.ConvertListSemesterDto2ItemData(view,response.body()));
                }
                else{
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SemesterDto>> call, Throwable t) {

            }
        });
    }
    public void createSemester(SemesterDto semesterDto){
        semesterService.createSemester(semesterDto).enqueue(new Callback<SemesterDto>() {
            @Override
            public void onResponse(Call<SemesterDto> call, Response<SemesterDto> response) {
                if(response.isSuccessful()){
                    view.onResume();
                }
                else{
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SemesterDto> call, Throwable t) {

            }
        });
    }
}
