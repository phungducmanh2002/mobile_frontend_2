package com.example.appktx2.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.appktx2.interfaces.ICallBack;
import com.example.appktx2.ui.activities.room.detail.ActivityRoomDetail;
import com.example.appktx2.ui.activities.roomItem.detail.ActivityRoomItemDetail;
import com.example.appktx2.ui.activities.semester.detail.ActivitySemesterDetail;
import com.example.appktx2.ui.item.ItemCommon;

public class CallBackManager {
    public static ICallBack GetCallBackItemActionRoomManager(Context context){
        return new ICallBack() {
            @Override
            public void action(Object... object) {
                ItemCommon itemCommon = (ItemCommon) object[0];
                Integer idRoom = (Integer)itemCommon.getItemData().getItemAction().getStoredData();
//                Toast.makeText(itemCommon.getContext(), "ID ROOM: " + idRoom.toString(), Toast.LENGTH_SHORT).show();
                Intent roomDetailIntent = new Intent(context, ActivityRoomDetail.class);
                roomDetailIntent.putExtra("idRoom", idRoom);
                context.startActivity(roomDetailIntent);
            }
        };
    }

    public static ICallBack GetCallBackItemActionItemManager(Context context){
        return new ICallBack() {
            @Override
            public void action(Object... object) {
                ItemCommon itemCommon = (ItemCommon) object[0];
                Integer idItem = (Integer)itemCommon.getItemData().getItemAction().getStoredData();
                Intent intentDetail = new Intent(context, ActivityRoomItemDetail.class);
                intentDetail.putExtra("idItem", idItem);
                context.startActivity(intentDetail);
//                Toast.makeText(itemCommon.getContext(), "ID ITEM: " + idRoom.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }
    public static ICallBack GetCallBackItemActionSemesterManager(Context context){
        return new ICallBack() {
            @Override
            public void action(Object... object) {
                ItemCommon itemCommon = (ItemCommon) object[0];
                Integer idSemester = (Integer)itemCommon.getItemData().getItemAction().getStoredData();
                Intent intentDetail = new Intent(context, ActivitySemesterDetail.class);
                intentDetail.putExtra("idSemester", idSemester);
                context.startActivity(intentDetail);
//                Toast.makeText(itemCommon.getContext(), "ID SEMESTER: " + idSemester.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }

}
