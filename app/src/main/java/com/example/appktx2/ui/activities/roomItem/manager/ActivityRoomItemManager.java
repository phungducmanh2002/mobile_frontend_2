package com.example.appktx2.ui.activities.roomItem.manager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.data.dto.ItemDto;
import com.example.appktx2.databinding.ActivityRoomItemManagerBinding;
import com.example.appktx2.ui.dialog.MyDialog;

public class ActivityRoomItemManager  extends AppCompatActivity {

    RoomItemManagerHandler handler;
    ActivityRoomItemManagerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRoomItemManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.handler =  new RoomItemManagerHandler(this);
        setEvent();
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.handler.getAllRoomItem();
    }
    private void setEvent() {
        binding.viewManager.setOnAction(object -> {
            MyDialog dialog = MyDialog.CreateItemDialog(this, (Object... objs) -> {
                String itemName = (String) objs[0];
                Integer quantity = (Integer) objs[1];

                ItemDto itemDto = new ItemDto();
                itemDto.setItemName(itemName);
                itemDto.setQuantity(quantity);

                handler.createItem(itemDto);

            });
            dialog.show();
        });
    }
}
