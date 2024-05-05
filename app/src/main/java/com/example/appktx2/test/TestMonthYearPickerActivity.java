package com.example.appktx2.test;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.databinding.TestInputActivityBinding;
import com.example.appktx2.databinding.TestMonthYearPickerActivityBinding;
import com.example.appktx2.ui.dialog.MonthPickerDialog;

import java.util.Calendar;

public class TestMonthYearPickerActivity extends AppCompatActivity {

    TestMonthYearPickerActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = TestMonthYearPickerActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Lấy tháng và năm hiện tại
        binding.pick.setOnClickListener(v -> {
            MonthPickerDialog dialog = new MonthPickerDialog(this, 11, 2002);
            dialog.onSelect((Object... objs) -> {
                Integer month = (Integer) objs[0];
                Integer year = (Integer) objs[1];

                String selectedText = "Month: " + month.toString() + "\nYear: " + year.toString();

                Toast.makeText(this, selectedText, Toast.LENGTH_SHORT).show();

            });
            dialog.show();
        });

    }
}