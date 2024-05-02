package com.example.appktx2.test;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.R;
import com.example.appktx2.data.model.itemCommon.ItemAction;
import com.example.appktx2.data.model.itemCommon.ItemDataManyAction;
import com.example.appktx2.data.model.itemCommon.ItemResource;
import com.example.appktx2.databinding.TestInputActivityBinding;
import com.example.appktx2.databinding.TestManyActionActivityBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestItemManyActionActivity extends AppCompatActivity {

    TestManyActionActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = TestManyActionActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<ItemAction> actionList = new ArrayList<>();
        actionList.add(ItemAction.builder()
                        .text("ADD")
                        .callBack(object -> {
                            Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show();

                        })
                        .textColor(getColor(R.color.white))
                        .background(getDrawable(R.drawable.bg_rounded_blue))
                        .storedData(1)
                .build());

        actionList.add(ItemAction.builder()
                .text("SUB")
                .callBack(object -> {
                    Toast.makeText(this, "SUB", Toast.LENGTH_SHORT).show();
                })
                .textColor(getColor(R.color.white))
                .background(getDrawable(R.drawable.bg_rounded_orange))
                .storedData(1)
                .build());

        actionList.add(ItemAction.builder()
                .text("REMOVE")
                .callBack(object -> {
                    Toast.makeText(this, "REMOVE", Toast.LENGTH_SHORT).show();

                })
                .textColor(getColor(R.color.white))
                .background(getDrawable(R.drawable.bg_rounded_red))
                .storedData(1)
                .build());


        ItemDataManyAction itemDataManyAction = new ItemDataManyAction();
        itemDataManyAction.setItemResource(ItemResource.GetItemResourceResServer(10));
        itemDataManyAction.setItemActionList(actionList);
        itemDataManyAction.setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_smoke));

        Map<String,String> attrs = new HashMap<>();
        attrs.put("Quantity: ", "10");
        attrs.put("Total: ", "100");

        itemDataManyAction.setFields(attrs);

        binding.manyAction.setItemDataManyAction(itemDataManyAction);

    }
}