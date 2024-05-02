package com.example.appktx2.ui.activities.semester.detail.semesterRoomList;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.R;
import com.example.appktx2.databinding.ActivitySemesterDetailRoomManagerBinding;

public class ActivitySemesterDetailRoomManager extends AppCompatActivity {

    SemesterDetailRoomManagerHandler handler;
    ActivitySemesterDetailRoomManagerBinding binding;
    Integer idSemester = 1;
    boolean isShowItemAdded = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySemesterDetailRoomManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getExtraIdRoom();
        this.handler =  new SemesterDetailRoomManagerHandler(this);
        setEvent();
    }
    private void getExtraIdRoom() {
        idSemester = getIntent().getIntExtra("idSemester", -1);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isShowItemAdded){
            binding.roomAdded.callOnClick();
        }
        else{
            binding.roomNotAdded.callOnClick();
        }
    }
    private void setEvent() {
        binding.roomAdded.setOnClickListener(v -> {
            isShowItemAdded=true;
            binding.roomAdded.setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_blue));
            binding.roomNotAdded.setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_gray));
            handler.getAllRoomAdded(idSemester);
        });

        binding.roomNotAdded.setOnClickListener(v -> {
            isShowItemAdded=false;
            binding.roomNotAdded.setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_blue));
            binding.roomAdded.setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_gray));
            handler.getAllRoomNoAdded(idSemester);
        });
    }
}
