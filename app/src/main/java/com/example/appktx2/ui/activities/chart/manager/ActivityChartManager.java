package com.example.appktx2.ui.activities.chart.manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appktx2.R;
import com.example.appktx2.databinding.ActivityChartManagerBinding;
import com.example.appktx2.databinding.ActivityHomeBinding;
import com.example.appktx2.interfaces.IPDM;
import com.example.appktx2.ui.fragments.home.FragmentHome;
import com.example.appktx2.ui.fragments.profile.FragmentProfile;
import com.example.appktx2.utils.DateUtils;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityChartManager extends AppCompatActivity implements IPDM.View {
    private static final int REQUEST_CODE = 42;
    ChartManagerHandle handler = new ChartManagerHandle();
    ActivityChartManagerBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChartManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        handler.setView(this);

        setEvent();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void setEvent()
    {
        binding.chartMain.setVisibility(View.GONE);
        binding.exportPdf.setVisibility(View.GONE);

        binding.fromDate.setText("From date");
        binding.toDate.setText("To date");

        binding.search.setOnClickListener(v -> {

            binding.exportPdf.setVisibility(View.GONE);
            binding.chartMain.setVisibility(View.GONE);

            Date fromDate = binding.fromDate.getDate();
            if(fromDate == null){
                binding.fromDate.pickDate();
                return;
            }
            Date toDate = binding.toDate.getDate();
            if(toDate == null){
                binding.toDate.pickDate();
                return;
            }

            binding.showTimeFrom.setText(DateUtils.Date2String(fromDate));
            binding.showTimeTo.setText(DateUtils.Date2String(toDate));

            if(binding.typeRoom.isChecked()){
                handler.getRoomBillChart(fromDate, toDate);
                binding.title.setText("Thống kê tiền phòng");
            }
            else{
                handler.getElectricWaterBillChart(fromDate, toDate);
                binding.title.setText("Thống kê tiền điện nước");
            }
        });

        binding.exportPdf.setOnClickListener(v -> {
            openFilePicker();
        });
    }
    public void setChartData(Integer paid, Integer unpaid){
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(paid, "Paid"));
        entries.add(new PieEntry(unpaid, "Unpaid"));

        PieDataSet pieDataSet = new PieDataSet(entries, "bill status");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        binding.pieChart.setData(pieData);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.animateY(1000);
        binding.pieChart.invalidate();

        binding.chartMain.setVisibility(View.VISIBLE);
        binding.exportPdf.setVisibility(View.VISIBLE);
    }
    /*
     * Mở dialog chọn nơi lưu tệp
     * */
    private void openFilePicker() {
        String billName = "KTX_SV_CHART.pdf";
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, billName);
        startActivityForResult(intent, REQUEST_CODE);
    }
    /*
     * Khi chọn được nơi lưu tệp
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    savePdf(uri);
                }
            }
        }
    }
    /*
     * Lưu tệp
     * */
    private void savePdf(Uri uri) {
        try {
            OutputStream outputStream = getContentResolver().openOutputStream(uri);
            if (outputStream != null) {
                Document document = new Document();
                PdfWriter.getInstance(document, outputStream);
                document.open();

                // Chụp hình ảnh của view
                View view = binding.chartMain;
                Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);

                // Chuyển đổi bitmap thành hình ảnh PDF và thêm vào tài liệu
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());
                document.add(image);

                document.close();
                outputStream.close();
                Toast.makeText(this, "PDF saved successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
