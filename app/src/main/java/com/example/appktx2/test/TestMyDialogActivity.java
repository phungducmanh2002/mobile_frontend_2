package com.example.appktx2.test;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.databinding.TestInputActivityBinding;
import com.example.appktx2.databinding.TestMyDialogActivityBinding;
import com.example.appktx2.databinding.TestMyListViewActivityBinding;
import com.example.appktx2.ui.components.Input;
import com.example.appktx2.ui.dialog.MyDialog;

import java.util.List;

public class TestMyDialogActivity extends AppCompatActivity {

    TestMyDialogActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = TestMyDialogActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.openDialog.setOnClickListener(v -> {
            MyDialog myDialog = new MyDialog(this   );
            myDialog.addChildren(new Input(this));
            myDialog.addChildren(new Input(this));
            myDialog.onCancle((Object... objs) -> {
                MyDialog dialog = (MyDialog)objs[0];
                dialog.cancel();
            });
            myDialog.onSave((Object... objs) -> {
                MyDialog dialog = (MyDialog)objs[0];
                List<LinearLayout> childs = dialog.getAllChilds();
                Input input = (Input)childs.get(0);
                input.setInputType("password");
            });
            myDialog.show();
        });


    }
}