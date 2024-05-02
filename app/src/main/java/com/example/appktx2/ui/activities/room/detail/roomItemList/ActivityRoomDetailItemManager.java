package com.example.appktx2.ui.activities.room.detail.roomItemList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.R;
import com.example.appktx2.databinding.ActivityRoomDetailBinding;
import com.example.appktx2.databinding.ActivityRoomDetailItemManagerBinding;
import com.example.appktx2.utils.FileUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.IOException;
import java.io.InputStream;

public class ActivityRoomDetailItemManager extends AppCompatActivity {

    RoomDetailItemManagerHandler handler;
    ActivityRoomDetailItemManagerBinding binding;
    Integer idRoom = 1;
    boolean isShowItemAdded = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRoomDetailItemManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getExtraIdRoom();
        this.handler =  new RoomDetailItemManagerHandler(this);
        setEvent();

    }
    private void getExtraIdRoom() {
        idRoom = getIntent().getIntExtra("idRoom", -1);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isShowItemAdded){
            binding.itemAdded.callOnClick();
        }
        else{
            binding.itemNotAdded.callOnClick();
        }
    }
    private void setEvent() {
        binding.itemAdded.setOnClickListener(v -> {
            isShowItemAdded=true;
            binding.itemAdded.setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_blue));
            binding.itemNotAdded.setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_gray));
            handler.getAllItemAdded(idRoom);
        });

        binding.itemNotAdded.setOnClickListener(v -> {
            isShowItemAdded=false;
            binding.itemNotAdded.setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_blue));
            binding.itemAdded.setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_gray));
            handler.getAllItemNoAdded(idRoom);
        });
    }
}
