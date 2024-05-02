package com.example.appktx2.ui.activities.semester.detail;

import android.util.Log;
import android.widget.Toast;

import com.example.appktx2.data.apiResponse.UpdateAvatarRes;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.data.dto.SemesterDto;
import com.example.appktx2.data.model.itemCommon.ItemResource;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.IRoomCollectionService;
import com.example.appktx2.net.services.IRoomService;
import com.example.appktx2.net.services.ISemesterService;
import com.example.appktx2.ui.item.ItemImage;
import com.example.appktx2.utils.DateUtils;
import com.example.appktx2.utils.NumberUtils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SemesterDetailHandler {
    ActivitySemesterDetail view;
    ISemesterService semesterService = RetrofitClient.GI().getRetrofit().create(ISemesterService.class);
    public SemesterDetailHandler(ActivitySemesterDetail view){
        this.view = view;

    }
    public void getSemesterDetail(Integer idSemester){
        semesterService.getDetailsById(idSemester).enqueue(new Callback<SemesterDto>() {
            @Override
            public void onResponse(Call<SemesterDto> call, Response<SemesterDto> response) {
                if(response.isSuccessful()){
                    SemesterDto semesterDto = response.body();
                    view.binding.semesterName.setText(semesterDto.getSemesterName());
                    view.binding.roomPrice.setValue(NumberUtils.GetMoney(semesterDto.getRoomPrice()));
                    view.binding.electricPrice.setValue(NumberUtils.GetMoney(semesterDto.getElectricPrice()));
                    view.binding.waterPrice.setValue(NumberUtils.GetMoney(semesterDto.getWaterPrice()));
                    view.binding.startAt.setValue(DateUtils.Date2String(semesterDto.getStartAt()));
                    view.binding.endAt.setValue(DateUtils.Date2String(semesterDto.getEndAt()));
                }
                else {
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SemesterDto> call, Throwable t) {

            }
        });
    }
}
