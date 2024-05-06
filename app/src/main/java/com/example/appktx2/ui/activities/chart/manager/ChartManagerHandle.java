package com.example.appktx2.ui.activities.chart.manager;

import android.widget.Toast;

import com.example.appktx2.AppKtx;
import com.example.appktx2.data.dto.BillDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.data.dto.UserDto;
import com.example.appktx2.interfaces.IPDM;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.IBillService;
import com.example.appktx2.net.services.IHomeApiService;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartManagerHandle implements IPDM.Handler {
    ActivityChartManager view;
    IBillService billService = RetrofitClient.GI().getRetrofit().create(IBillService.class);
    @Override
    public void setView(IPDM.View  view) {
        this.view = (ActivityChartManager)view;
    }
    public void getRoomBillChart(Date fromDate, Date toDate){
        billService.getChartRoomBill(fromDate, toDate).enqueue(new Callback<List<BillDto>>() {
            @Override
            public void onResponse(Call<List<BillDto>> call, Response<List<BillDto>> response) {
                if(response.isSuccessful()){
                    List<BillDto> billDtoList = response.body();

                    if(billDtoList.size() > 0){
                        Integer[] money = getRoomBillChartData(billDtoList);
                        Integer paid = money[0];
                        Integer unpaid = money[1];

                        view.setChartData(paid, unpaid);
                    }
                    else{
                        Toast.makeText(view, "Không có data", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(view, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BillDto>> call, Throwable t) {
                Toast.makeText(view, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getElectricWaterBillChart(Date fromDate, Date toDate){
        billService.getChartElectricWaterBill(fromDate, toDate).enqueue(new Callback<List<BillDto>>() {
            @Override
            public void onResponse(Call<List<BillDto>> call, Response<List<BillDto>> response) {
                if(response.isSuccessful()){
                    List<BillDto> billDtoList = response.body();

                    if(billDtoList.size() > 0){
                        Integer[] money = getElectricWaterBillChartData(billDtoList);
                        Integer paid = money[0];
                        Integer unpaid = money[1];

                        view.setChartData(paid, unpaid);
                    }
                    else{
                        Toast.makeText(view, "Không có data", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(view, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BillDto>> call, Throwable t) {
                Toast.makeText(view, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public Integer[] getRoomBillChartData(List<BillDto> roomDtoList){
        Integer[] data = new Integer[2];

        Integer paidMoney = 0, unpaidMoney = 0;

        for (BillDto billDto: roomDtoList) {
            if(billDto.getStatus()){
                paidMoney += billDto.getRoomPrice();
            }
            else{
                unpaidMoney += billDto.getRoomPrice();
            }
        }

        data[0] = paidMoney;
        data[1] = unpaidMoney;

        return  data;
    }
    public Integer[] getElectricWaterBillChartData(List<BillDto> roomDtoList){
        Integer[] data = new Integer[2];

        Integer paidMoney = 0, unpaidMoney = 0;

        for (BillDto billDto: roomDtoList) {
            Integer totalMoney = billDto.getElectricPrice() * billDto.getElectricNumber() + billDto.getWaterNumber() * billDto.getWaterPrice();
            if(billDto.getStatus()){
                paidMoney += totalMoney;
            }
            else{
                unpaidMoney += totalMoney;
            }
        }

        data[0] = paidMoney;
        data[1] = unpaidMoney;

        return  data;
    }
}
