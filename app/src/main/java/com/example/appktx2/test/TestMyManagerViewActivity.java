package com.example.appktx2.test;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.R;
import com.example.appktx2.data.model.itemCommon.ItemAction;
import com.example.appktx2.data.model.itemCommon.ItemData;
import com.example.appktx2.data.model.itemCommon.ItemResource;
import com.example.appktx2.databinding.TestMyListViewActivityBinding;
import com.example.appktx2.databinding.TestMyManagerViewActivityBinding;

import java.util.ArrayList;
import java.util.List;

public class TestMyManagerViewActivity extends AppCompatActivity {

    TestMyManagerViewActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = TestMyManagerViewActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<ItemData> itemDataList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            try {
                ItemData itemData = ItemData.builder()
                        .itemResource(
                                ItemResource.builder()
                                        .idResource(9)
                                        .isLocal(false)
                                        .build()
                        )
                        .title("Test my list view")
                        .itemAction(
                                ItemAction.builder()
                                        .text("REMOVE")
                                        .textColor(getColor(R.color.white))
                                        .background(getDrawable(R.drawable.bg_rounded_red))
                                        .callBack((Object... objects) -> {
                                            try {
                                                ItemAction itemAction = (ItemAction) objects[1];
                                                Integer roomId = (Integer)itemAction.getStoredData();
                                                Toast.makeText(this, "ID ROOM: " + roomId, Toast.LENGTH_SHORT).show();
                                            }
                                            catch (Exception ex){
                                                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .storedData(i)
                                        .build()
                        )
                        .textColor(getColor(R.color.white))
                        .backgroundDrawable(getDrawable(R.drawable.bg_rounded_blue))
                        .build();

                itemDataList.add(itemData);
            }
            catch (Exception ex){
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        try {
            binding.myManagerView.setItemDataList(itemDataList);
        }
        catch (Exception exception){
            Log.d("YGUHBKJNMLdfas", exception.getMessage());
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}