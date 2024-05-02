package com.example.appktx2.ui.activities.roomItem.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.data.dto.ItemDto;
import com.example.appktx2.databinding.ActivityRoomItemDetailBinding;
import com.example.appktx2.databinding.ActivityRoomItemManagerBinding;
import com.example.appktx2.ui.dialog.MyDialog;
import com.example.appktx2.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;

public class ActivityRoomItemDetail extends AppCompatActivity {

    RoomItemDetailHandler handler;
    ActivityRoomItemDetailBinding binding;
    Integer idItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRoomItemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getExtraId();
        this.handler =  new RoomItemDetailHandler(this);
        setEvent();
    }
    @Override
    protected void onResume() {
        super.onResume();
        handler.getItem(idItem);
    }
    private void setEvent() {
        binding.addQuantity.setOnClickListener(v -> {
            MyDialog dialog = MyDialog.CreateUpdateItemQuantityDialog(this, object -> {
                Integer quantity = (Integer) object[0];
                handler.addQuantity(idItem, quantity);
            });

            dialog.show();
        });

        binding.subQuantity.setOnClickListener(v -> {
            MyDialog dialog = MyDialog.CreateUpdateItemQuantityDialog(this, object -> {
                Integer quantity = (Integer) object[0];
                handler.addQuantity(idItem, -quantity);
            });

            dialog.show();
        });
    }
    private void getExtraId(){
        idItem = getIntent().getIntExtra("idItem", -1);
        Toast.makeText(this, "ID: "  + Integer.toString(idItem), Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            return;
        }
        Uri uri = data.getData();
        if(uri == null){
            return;
        }
        try {
            InputStream iStream =   getContentResolver().openInputStream(uri);
            byte[] avatarData = FileUtils.GetBytes(iStream);
            handler.onSelectAvatarResponse(avatarData, idItem);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
