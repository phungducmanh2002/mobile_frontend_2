package com.example.appktx2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.data.model.itemCommon.ItemAction;
import com.example.appktx2.data.model.itemCommon.ItemData;
import com.example.appktx2.data.model.itemCommon.ItemResource;
import com.example.appktx2.databinding.ActivityMain1Binding;
import com.example.appktx2.interfaces.ICallBack;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMain1Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        ItemResource itemResource = new ItemResource();
//        itemResource.setIdResource(9);
//        itemResource.setLocal(false);
//        ItemResource.AssignResource(this, itemResource, binding.image);

//        ItemAction itemAction = new ItemAction();
//        itemAction.setBackground(getDrawable(R.drawable.bg_rounded_blue));
//        itemAction.setText("DETAIL");
//        itemAction.setTextColor(getColor(R.color.white));
//        itemAction.setCallBack((Object... object) -> {
//            Toast.makeText(this, object.toString(), Toast.LENGTH_SHORT).show();
//        });
//        ItemAction.AssignItemAction(ItemAction.GetActionAdd(this), binding.button);

//        String text = Integer.toString(R.color.white) + "_______" + Integer.toString(Color.WHITE) + "_____________" + Color.parseColor(Integer.toString(R.color.white));
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//        Log.d("FGVBNKLJHGJB", text);

        Map<String, String> attrs = new HashMap<>();
        attrs.put("Gender: ", "Male");
        attrs.put("Status: ", "Active");

        ItemData itemData = new ItemData();
        itemData.setTitle("Phòng 2002");
        //set callback when click action
        itemData.setItemAction(
                ItemAction.builder()
                        .text("DANGER").background(getDrawable(R.drawable.bg_rounded_blue))
                        .textColor(getColor(R.color.white))
                        .callBack((Object... objects) -> {
                            Toast.makeText(this, objects[0].toString(), Toast.LENGTH_SHORT).show();
                        })
                        .build());
        itemData.setItemResource(ItemResource.builder().idResource(9).isLocal(false).build());
        itemData.setFields(attrs);
        itemData.setTextColor(getColor(R.color.white));
        itemData.setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_red));

        binding.itemCommon.setItemData(itemData);


        ItemResource itemResource = ItemResource.GetItemResourceResServer(9);
        itemResource.setCallBack((Object... objects) -> {
            Toast.makeText(this, objects[1].toString(), Toast.LENGTH_SHORT).show();
        });
        binding.itemImage.setItemResource(itemResource);
    }
}