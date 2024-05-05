package com.example.appktx2.ui.activities.bill.manager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.appktx2.data.dto.BillDto;
import com.example.appktx2.data.dto.ElectricWaterDto;
import com.example.appktx2.data.dto.RoomCollectionDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.data.dto.SemesterDto;
import com.example.appktx2.data.dto.SemesterRoomNameDto;
import com.example.appktx2.data.model.itemCommon.ItemData;
import com.example.appktx2.interfaces.ICallBack;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.IBillService;
import com.example.appktx2.net.services.IRoomCollectionService;
import com.example.appktx2.net.services.IRoomService;
import com.example.appktx2.net.services.ISemesterService;
import com.example.appktx2.ui.dialog.MyDialog;
import com.example.appktx2.utils.MapperUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillManagerHandler {
    ActivityBillManager view;
    ISemesterService semesterService = RetrofitClient.GI().getRetrofit().create(ISemesterService.class);
    IBillService billService = RetrofitClient.GI().getRetrofit().create(IBillService.class);
    public BillManagerHandler(ActivityBillManager view){
        this.view = view;
    }
    /*
    * Get all semester gọi khi view resume
    * */
    public void getAllSemester(){
        semesterService.getAllSemester().enqueue(new Callback<List<SemesterDto>>() {
            @Override
            public void onResponse(Call<List<SemesterDto>> call, Response<List<SemesterDto>> response) {
                if(response.isSuccessful()){
                    BillManagerHandler.this.view.semesterDtoList = response.body();
                    view.binding.semesterSelector.setData(BillManagerHandler.this.view.semesterDtoList, (obj) -> {
                        SemesterDto semesterDto = (SemesterDto)obj;
                        return  semesterDto.getSemesterName();
                    });
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
    /*
    * Get all bill gọi khi chọn bill type là all
    * */
    public void getAllBill(Integer idSemester){
        billService.getAllBillBySemesterId(idSemester).enqueue(new Callback<List<BillDto>>() {
            @Override
            public void onResponse(Call<List<BillDto>> call, Response<List<BillDto>> response) {
                setBillDatas(response.body());
            }

            @Override
            public void onFailure(Call<List<BillDto>> call, Throwable t) {

            }
        });
    }
    /*
     * Get all bill gọi khi chọn bill type là room
     * */
    public void getAllRoomBill(Integer idSemester){
        billService.getAllRoomBillBySemesterId(idSemester).enqueue(new Callback<List<BillDto>>() {
            @Override
            public void onResponse(Call<List<BillDto>> call, Response<List<BillDto>> response) {
                setBillDatas(response.body());
            }

            @Override
            public void onFailure(Call<List<BillDto>> call, Throwable t) {

            }
        });
    }
    /*
     * Get all bill gọi khi chọn bill type là electric water
     * */
    public void getAllElectricWaterBill(Integer idSemester){
        billService.getAllElectricWaterBillBySemesterId(idSemester).enqueue(new Callback<List<BillDto>>() {
            @Override
            public void onResponse(Call<List<BillDto>> call, Response<List<BillDto>> response) {
                setBillDatas(response.body());
            }

            @Override
            public void onFailure(Call<List<BillDto>> call, Throwable t) {

            }
        });
    }
    /*
     * set data cho list bill nó sẽ tự lọc theo search text, bill status
     * Được gọi khi lấy data từ api
     * - get all
     * - get room bill
     * - get electric water bill
     * */
    public void setBillDatas(List<BillDto> bills){
        this.view.allBills = bills;
        setDisplayBills(bills);
    }
    public void setDisplayBills(List<BillDto> bills){
        view.displayBills = view.filterBillByStatus(bills);
        view.displayBills = view.filterBillBySearchText(view.displayBills);
        view.binding.viewManager.setItemDataList(MapperUtils.ConvertListBillDto2ItemData(view,view.displayBills));
    }
    /*
    * Lấy room name trong semester để lúc tạo bill cho người dùng chọn phòng
    * */
    public void getAllSemesterRoomNames(Integer idSemester, ICallBack onSuccess){
        semesterService.getAllRoomNameInSemester(idSemester).enqueue(new Callback<List<SemesterRoomNameDto>>() {
            @Override
            public void onResponse(Call<List<SemesterRoomNameDto>> call, Response<List<SemesterRoomNameDto>> response) {
                if(response.isSuccessful()){
                    onSuccess.action(response.body());
                }
                else {
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SemesterRoomNameDto>> call, Throwable t) {

            }
        });
    }
    /*
    * Tạo electric water bill
    * */
    public void createElectricWaterBill(ElectricWaterDto electricWaterDto){
        billService.createElectricWaterBill(electricWaterDto).enqueue(new Callback<BillDto>() {
            @Override
            public void onResponse(Call<BillDto> call, Response<BillDto> response) {
                if(response.isSuccessful()){
                    view.onResume();
                }
                else {
                    Toast.makeText(view, "Tạo bill thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BillDto> call, Throwable t) {

            }
        });
    }
}
