package com.example.appktx2.ui.activities.room.manager;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.data.dto.RoomCollectionDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.databinding.ActivityRoomManagerBinding;
import com.example.appktx2.ui.dialog.MyDialog;

import java.util.List;

public class ActivityRoomManager extends AppCompatActivity {

    RoomManagerHandler handler;
    ActivityRoomManagerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRoomManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.handler =  new RoomManagerHandler(this);
        setEvent();

    }
    @Override
    protected void onResume() {
        super.onResume();
        this.handler.getAllRoom();
    }
    private void setEvent() {
        binding.viewManager.setOnAction(objects -> {
            handler.getAllRoomCollection((Object...objects1) -> {
                List<RoomCollectionDto> dataList = (List<RoomCollectionDto>) objects1[0];
                MyDialog myDialog = MyDialog.CreateRoomDialog(this, objects2 -> {
                    RoomDto roomDto = (RoomDto) objects2[0];
                    handler.createRoom(roomDto);
                }, dataList);
                myDialog.show();

            });
        });
    }

}
