package com.example.appktx2.net.services;

import com.example.appktx2.data.dto.BillDto;
import com.example.appktx2.data.dto.ElectricWaterDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IBillService {
    @POST("/api/v1/bill/create-electric-water")
    Call<BillDto> createElectricWaterBill(@Body ElectricWaterDto electricWaterDto);

    @GET("/api/v1/bill/get-all-by-semester/{idSemester}")
    Call<List<BillDto>> getAllBillBySemesterId(@Path("idSemester") Integer idSemester);

    @GET("/api/v1/bill/room-bill-by-semester/{idSemester}")
    Call<List<BillDto>> getAllRoomBillBySemesterId(@Path("idSemester") Integer idSemester);

    @GET("/api/v1/bill/electric-water-bill-by-semester/{idSemester}")
    Call<List<BillDto>> getAllElectricWaterBillBySemesterId(@Path("idSemester") Integer idSemester);

    @GET("/api/v1/bill/{idBill}")
    Call<BillDto> getBillDetail(@Path("idBill") Integer idBill);

    @PUT("/api/v1/bill/pay/{idBill}")
    Call<BillDto> payBill(@Path("idBill") Integer idBill);
}
