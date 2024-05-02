package com.example.appktx2.test;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.databinding.TestInputActivityBinding;
import com.example.appktx2.databinding.TestSelectorActivityBinding;

import java.util.ArrayList;
import java.util.List;

public class TestSelectorActivity extends AppCompatActivity {

    TestSelectorActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = TestSelectorActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        List<RoomDto> roomDtoList = new ArrayList<>();

        roomDtoList.add(RoomDto.builder().roomName("A001").build());
        roomDtoList.add(RoomDto.builder().roomName("A002").build());
        roomDtoList.add(RoomDto.builder().roomName("A003").build());
        roomDtoList.add(RoomDto.builder().roomName("A004").build());

        binding.selector.setData(roomDtoList, (obj) -> {
            RoomDto r = (RoomDto) obj;
            return r.getRoomName();
        });
        binding.selector.setOnSelected(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TestSelectorActivity.this, Integer.toString(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}