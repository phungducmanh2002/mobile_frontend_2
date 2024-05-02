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
import com.example.appktx2.databinding.TestMyImageListViewActivityBinding;
import com.example.appktx2.databinding.TestMyListViewActivityBinding;
import com.example.appktx2.ui.item.ItemImage;

import java.util.ArrayList;
import java.util.List;

public class TestMyImageListViewActivity extends AppCompatActivity {

    TestMyImageListViewActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = TestMyImageListViewActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ItemImage itemImage = new ItemImage(this);
        itemImage.setItemResource(ItemResource.GetItemResourceResServer(9));

        ItemImage itemImage1 = new ItemImage(this);
        itemImage1.setItemResource(ItemResource.GetItemResourceResServer(10));

        ItemImage itemImage2 = new ItemImage(this);
        itemImage2.setItemResource(ItemResource.GetItemResourceResServer(11));

        ItemImage itemImage3 = new ItemImage(this);
        itemImage3.setItemResource(ItemResource.GetItemResourceResServer(12));

        binding.myImageListView.addChildren(itemImage1, itemImage2, itemImage3);

    }
}