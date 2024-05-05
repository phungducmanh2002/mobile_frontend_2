package com.example.appktx2.ui.activities.roomCollection.manager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.data.dto.RoomCollectionDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.databinding.ActivityRoomCollectionManagerBinding;
import com.example.appktx2.databinding.ActivityRoomManagerBinding;
import com.example.appktx2.ui.dialog.MyDialog;

import java.util.List;

public class ActivityRoomCollectionManager extends AppCompatActivity {
    RoomCollectionManagerHandler handler;
    ActivityRoomCollectionManagerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRoomCollectionManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.handler =  new RoomCollectionManagerHandler(this);
        setEvent();

    }
    @Override
    protected void onResume() {
        super.onResume();
        this.handler.getAllRoomCollection();
    }
    private void setEvent() {
        binding.viewManager.setOnAction(objects -> {
            MyDialog myDialog = MyDialog.CreateRoomCollectonDialog(this, objects2 -> {
                RoomCollectionDto roomCollectionDto = (RoomCollectionDto) objects2[0];
                handler.createRoomCollection(roomCollectionDto);
            });
            myDialog.show();
        });
    }

}
