package com.example.appktx2.ui.activities.semester.detail.semesterRoomList;

import android.content.Intent;
import android.widget.Toast;

import com.example.appktx2.R;
import com.example.appktx2.data.dto.ItemAddedDto;
import com.example.appktx2.data.dto.ItemDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.data.dto.SemesterRoomDto;
import com.example.appktx2.data.model.itemCommon.ItemAction;
import com.example.appktx2.data.model.itemCommon.ItemDataManyAction;
import com.example.appktx2.data.model.itemCommon.ItemResource;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.IRoomCollectionService;
import com.example.appktx2.net.services.IRoomService;
import com.example.appktx2.net.services.ISemesterService;
import com.example.appktx2.ui.activities.room.detail.ActivityRoomDetail;
import com.example.appktx2.ui.activities.roomItem.detail.ActivityRoomItemDetail;
import com.example.appktx2.ui.dialog.MyDialog;
import com.example.appktx2.ui.item.ItemManyAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SemesterDetailRoomManagerHandler {
    ActivitySemesterDetailRoomManager view;
    ISemesterService semesterService = RetrofitClient.GI().getRetrofit().create(ISemesterService.class);
    IRoomCollectionService romCollectionService = RetrofitClient.GI().getRetrofit().create(IRoomCollectionService.class);
    public SemesterDetailRoomManagerHandler(ActivitySemesterDetailRoomManager view){
        this.view = view;
    }
    public void getAllRoomAdded(Integer idSemester){
        semesterService.getAllRoomAdded(idSemester).enqueue(new Callback<List<RoomDto>>() {
            @Override
            public void onResponse(Call<List<RoomDto>> call, Response<List<RoomDto>> response) {
                if(response.isSuccessful()){
                    view.binding.myListView.resetView();
                    for (RoomDto roomDto: response.body()) {

                        ItemManyAction itemManyAction = new ItemManyAction(view);
                        itemManyAction.setItemDataManyAction(RoomAdded2ItemDataManyAction(roomDto, idSemester));

                        view.binding.myListView.addChildren(itemManyAction);
                    }
                }
                else{
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RoomDto>> call, Throwable t) {

            }
        });
    }
    public void getAllRoomNoAdded(Integer idSemester){
        semesterService.getAllRoomNotAdded(idSemester).enqueue(new Callback<List<RoomDto>>() {
            @Override
            public void onResponse(Call<List<RoomDto>> call, Response<List<RoomDto>> response) {
                if(response.isSuccessful()){
                    view.binding.myListView.resetView();
                    for (RoomDto roomDto: response.body()) {

                        ItemManyAction itemManyAction = new ItemManyAction(view);
                        itemManyAction.setItemDataManyAction(RoomNotAdded2ItemDataManyAction(roomDto, idSemester));

                        view.binding.myListView.addChildren(itemManyAction);
                    }
                }
                else{
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RoomDto>> call, Throwable t) {

            }
        });
    }
    private ItemDataManyAction RoomAdded2ItemDataManyAction(RoomDto roomDto, Integer idSemester){
        List<ItemAction> itemActionList = new ArrayList<>();
        itemActionList.add(CreateItemActionRemove(idSemester, roomDto.getId()));
        itemActionList.add(CreateItemActionDetail(idSemester, roomDto.getId()));

        Map<String, String> attrs = new HashMap<>();
        attrs.put("Gender: ", roomDto.getRoomGender() == 0 ? "Male" : "Female");
        attrs.put("Status: ", roomDto.getRoomStatus() == 0 ? "Active" : "Disable");
        attrs.put("Slot: ", roomDto.getSlot().toString());
        attrs.put("Slot use: ", roomDto.getSlotUse().toString());

        ItemDataManyAction itemDataManyAction = new ItemDataManyAction();
        itemDataManyAction.setTitle(roomDto.getRoomName());

        itemDataManyAction.setItemActionList(itemActionList);
        itemDataManyAction.setFields(attrs);

        return  itemDataManyAction;
    }
    private ItemDataManyAction RoomNotAdded2ItemDataManyAction(RoomDto roomDto, Integer idSemester){
        List<ItemAction> itemActionList = new ArrayList<>();
        itemActionList.add(CreateItemActionAdd(idSemester, roomDto.getId()));
        itemActionList.add(CreateItemActionDetail(idSemester, roomDto.getId()));

        Map<String, String> attrs = new HashMap<>();
        attrs.put("Gender: ", roomDto.getRoomGender() == 0 ? "Male" : "Female");
        attrs.put("Status: ", roomDto.getRoomStatus() == 0 ? "Active" : "Disable");

        ItemDataManyAction itemDataManyAction = new ItemDataManyAction();
        itemDataManyAction.setTitle(roomDto.getRoomName());

        itemDataManyAction.setItemActionList(itemActionList);
        itemDataManyAction.setFields(attrs);

        return  itemDataManyAction;
    }
    public ItemAction CreateItemActionAdd(Integer idSemester, Integer idRoom){
        return ItemAction.builder()
                .background(view.getDrawable(R.drawable.bg_rounded_blue))
                .text("ADD")
                .textColor(view.getColor(R.color.white))
                .storedData(idRoom)
                .callBack(objects -> {
//                    Toast.makeText(view, "ADD: " + idRoom.toString() + "______" + idItem.toString(), Toast.LENGTH_SHORT).show();
                    SemesterRoomDto semesterRoomDto = new SemesterRoomDto();
                    semesterRoomDto.setIdSemester(idSemester);
                    semesterRoomDto.setIdRoom(idRoom);
                    semesterService.semesterAddRoom(semesterRoomDto).enqueue(new Callback<SemesterRoomDto>() {
                        @Override
                        public void onResponse(Call<SemesterRoomDto> call, Response<SemesterRoomDto> response) {
                            if(response.isSuccessful()){
                                view.onResume();
                            }
                            else{
                                Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SemesterRoomDto> call, Throwable t) {

                        }
                    });
                })
                .build();
    }
    public ItemAction CreateItemActionRemove(Integer idSemester, Integer idRoom){
        return ItemAction.builder()
                .background(view.getDrawable(R.drawable.bg_rounded_red))
                .text("REMOVE")
                .textColor(view.getColor(R.color.white))
                .storedData(idRoom)
                .callBack(objects -> {
//                    Toast.makeText(view, "REMOVE: " + idRoom.toString() + "______" + idItem.toString(), Toast.LENGTH_SHORT).show();
                        semesterService.semesterRemoveRoom(idSemester, idRoom).enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                if(response.isSuccessful()){
                                    view.onResume();
                                }
                                else{
                                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {

                            }
                        });
                })
                .build();
    }
    public ItemAction CreateItemActionDetail(Integer idSemester, Integer idRoom){
        return ItemAction.builder()
                .background(view.getDrawable(R.drawable.bg_rounded_blue))
                .text("Detail")
                .textColor(view.getColor(R.color.white))
                .storedData(idRoom)
                .callBack(objects -> {
                    Intent itentRoomDetail = new Intent(view, ActivityRoomDetail.class);
                    itentRoomDetail.putExtra("idRoom", idRoom);
                    view.startActivity(itentRoomDetail);
//                    Toast.makeText(view, "REMOVE: " + idRoom.toString() + "______" + idItem.toString(), Toast.LENGTH_SHORT).show();
                })
                .build();
    }

}
