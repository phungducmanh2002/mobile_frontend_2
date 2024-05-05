package com.example.appktx2.ui.activities.bill.manager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.data.dto.BillDto;
import com.example.appktx2.data.dto.ElectricWaterDto;
import com.example.appktx2.data.dto.SemesterDto;
import com.example.appktx2.data.dto.SemesterRoomNameDto;
import com.example.appktx2.data.model.BillStatusModel;
import com.example.appktx2.data.model.BillTypeModel;
import com.example.appktx2.databinding.ActivityBillManagerBinding;
import com.example.appktx2.ui.activities.bill.BillStatus;
import com.example.appktx2.ui.activities.bill.BillType;
import com.example.appktx2.ui.dialog.MyDialog;
import com.example.appktx2.utils.MapperUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivityBillManager extends AppCompatActivity {

    BillManagerHandler handler;
    ActivityBillManagerBinding binding;
    BillStatus billStatus;
    BillType billType;
    public Integer idSemester = null;
    List<SemesterDto> semesterDtoList;
    List<BillDto> allBills = new ArrayList<>(), displayBills = new ArrayList<>();
    String searchText = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBillManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.handler =  new BillManagerHandler(this);
        setEvent();

    }
    @Override
    protected void onResume() {
        super.onResume();
        this.handler.getAllSemester();
    }
    private void setEvent() {

        binding.semesterSelector.setOnSelected(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(ActivityBillManager.this.semesterDtoList != null){
                    SemesterDto semesterDto = ActivityBillManager.this.semesterDtoList.get(position);
                    ActivityBillManager.this.onSelectSemester(semesterDto.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<BillTypeModel> billTypeModels = new ArrayList<>();
        billTypeModels.add(new BillTypeModel(0, "All"));
        billTypeModels.add(new BillTypeModel(1, "Room bill"));
        billTypeModels.add(new BillTypeModel(2, "Electric water bill"));

        List<BillStatusModel> billStatusModels = new ArrayList<>();
        billStatusModels.add(new BillStatusModel(0, null));
        billStatusModels.add(new BillStatusModel(1, true));
        billStatusModels.add(new BillStatusModel(2, false));

        binding.billType.setData(billTypeModels, (obj) -> {
            BillTypeModel t = (BillTypeModel)obj;
            return t.getType();
        });
        binding.billStatus.setData(billStatusModels, (obj) -> {
            BillStatusModel b = (BillStatusModel)obj;
            if(((BillStatusModel) obj).getIsPay() == null){
                return "All";
            }
            return  b.getIsPay() ? "Paid" : "Unpaid";
        });

        binding.billType.setOnSelected(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    setBillType(BillType.ALL);
                }
                else if(position == 1){
                    setBillType(BillType.ROOM_BILL);
                }
                else if(position == 2){
                    setBillType(BillType.ELECTRIC_WATER_BILL);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.billStatus.setOnSelected(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    setBillStatus(BillStatus.ALL);
                }
                else if(position == 1){
                    setBillStatus(BillStatus.PAID);
                }
                else if(position == 2){
                    setBillStatus(BillStatus.UN_PAID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.viewManager.setOnSearch(object -> {
            String newText = (String) object[0];
            setSearchText(newText);
        });

        /*
        * Sự kiện khi nhấn vào button thêm
        */
        binding.viewManager.setOnAction((objs -> {
            /*
            *   Lấy danh sách room name trong semester
            */
            this.handler.getAllSemesterRoomNames(this.idSemester, objs1 -> {
                List<SemesterRoomNameDto> tmpList = (List<SemesterRoomNameDto>)objs1[0];
                /*
                * Tạo dia log tạo hóa đơn điện nước với danh sách room name đã lấy được
                */
                MyDialog dialog = MyDialog.CreateCreateBillDialog(this, objs2 -> {
                    ElectricWaterDto electricWaterDto = (ElectricWaterDto) objs2[0];
                    /*
                    * Gọi api t
                    * */
                    handler.createElectricWaterBill(electricWaterDto);
                }, tmpList);
                dialog.show();
            });
        }));

    }
    /*
    * Gọi khi thay đổi bill type [room bill / electric water bill]
    * */
    public void setBillType(BillType billType){
        this.billType = billType;
        onSelectSemester(idSemester);
    }
    /*
     * Gọi khi thay đổi bill status [paid / un paid]
     * */
    public void setBillStatus(BillStatus billStatus){
        this.billStatus = billStatus;
        this.displayBills = filterBillByStatus(this.allBills, billStatus);
        this.handler.setDisplayBills(this.displayBills);
    }
    /*
    * Gọi khi selected semester
    * Khi thay đổi semester sẽ gọi api lấy các bill tương ứng với semester đó
    * */
    public void onSelectSemester(Integer idSemester){
        this.idSemester = idSemester;
        if(billType.equals(BillType.ALL)){
            this.handler.getAllBill(idSemester);
        }
        else if(billType.equals(BillType.ROOM_BILL)){
            this.handler.getAllRoomBill(idSemester);
        }
        else if(billType.equals(BillType.ELECTRIC_WATER_BILL)){
            this.handler.getAllElectricWaterBill(idSemester);
        }
    }
    /*
    * Lọc dữ liệu với bill status [paid / un paid]
    * */
    public List<BillDto> filterBillByStatus(List<BillDto> billDtoList, BillStatus billStatus){
        List<BillDto> rsl = new ArrayList<>();
        for (BillDto billDto: billDtoList) {
            if(billStatus.equals(BillStatus.PAID)){
                if(billDto.getStatus()){
                    rsl.add(billDto);
                }
            }
            else if (billStatus.equals(BillStatus.UN_PAID)){
                if(!billDto.getStatus()){
                    rsl.add(billDto);
                }
            }
            else{
                rsl.add(billDto);
            }
        }
        return  rsl;
    }
    /*
     * Lọc dữ liệu với bill status [paid / un paid]
     * */
    public List<BillDto> filterBillByStatus(List<BillDto> billDtoList){
        List<BillDto> rsl = new ArrayList<>();
        for (BillDto billDto: billDtoList) {
            if(billStatus.equals(BillStatus.PAID)){
                if(billDto.getStatus()){
                    rsl.add(billDto);
                }
            }
            else if (billStatus.equals(BillStatus.UN_PAID)){
                if(!billDto.getStatus()){
                    rsl.add(billDto);
                }
            }
            else{
                rsl.add(billDto);
            }
        }
        return  rsl;
    }
    /*
     * Lọc dữ liệu với search text
     * */
    public List<BillDto> filterBillBySearchText(List<BillDto> billDtoList){
        List<BillDto> rsl = new ArrayList<>();
        for (BillDto billDto: billDtoList) {
            if(
                    (billDto.getRoomName()!= null && billDto.getRoomName().contains(this.searchText)) ||
                    (billDto.getEmail()!= null && billDto.getEmail().contains(this.searchText)) ||
                            (this.searchText.equals(""))
            ){
                rsl.add(billDto);
            }
        }
        return  rsl;
    }
    /*
    * Gọi khi search text
    * */
    public void setSearchText(String newText){
        this.searchText = newText;
        this.handler.setDisplayBills(this.allBills);
    }
}
