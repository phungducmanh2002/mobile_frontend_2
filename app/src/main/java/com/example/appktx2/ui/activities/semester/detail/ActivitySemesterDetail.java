package com.example.appktx2.ui.activities.semester.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.databinding.ActivitySemesterDetailBinding;
import com.example.appktx2.databinding.ActivitySemesterManagerBinding;
import com.example.appktx2.ui.activities.room.detail.roomItemList.ActivityRoomDetailItemManager;
import com.example.appktx2.ui.activities.semester.detail.semesterRoomList.ActivitySemesterDetailRoomManager;
import com.example.appktx2.utils.FileUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.IOException;
import java.io.InputStream;

public class ActivitySemesterDetail extends AppCompatActivity {

    SemesterDetailHandler handler;
    ActivitySemesterDetailBinding binding;
    Integer idSemester = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySemesterDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getExtraIdRoom();
        this.handler =  new SemesterDetailHandler(this);
        setEvent();

    }
    private void getExtraIdRoom() {
        idSemester = getIntent().getIntExtra("idSemester", -1);
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.handler.getSemesterDetail(idSemester);
    }
    private void setEvent() {
        binding.roomManager.setOnClickListener(v -> {
            Intent intentSemeterDetailRoomManager = new Intent(this, ActivitySemesterDetailRoomManager.class);
            intentSemeterDetailRoomManager.putExtra("idSemester", idSemester);
            startActivity(intentSemeterDetailRoomManager);
        });
    }
}
