package com.example.appktx2.ui.activities.bill.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.data.dto.BillDto;
import com.example.appktx2.databinding.ActivityBillDetailBinding;
import com.example.appktx2.ui.dialog.MyDialog;
import com.example.appktx2.utils.DateUtils;
import com.example.appktx2.utils.NumberUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class ActivityBillDetail extends AppCompatActivity {
    private static final int REQUEST_CODE = 42;
    BillDetailHandler handler;
    ActivityBillDetailBinding binding;
    Integer idBill;
    public BillDto currentBillDto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBillDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getIdBillExtra();
        this.handler =  new BillDetailHandler(this);
        setEvent();

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(idBill != -1){
            this.handler.getBillDetail(idBill);
        }
    }
    private void getIdBillExtra(){
        this.idBill = getIntent().getIntExtra("idBill", -1);
    }
    private void setEvent() {

        binding.exportPdf.setOnClickListener(v -> {
            openFilePicker();
        });

        binding.comfirmPaid.setOnClickListener(v -> {
            MyDialog dialog = MyDialog.CreateComfirmDialog(this, object -> {
                this.handler.payBill(idBill);
            }, "Xác nhận thanh toán?");

            dialog.show();
        });

    }
    /*
    * Mở dialog chọn nơi lưu tệp
    * */
    private void openFilePicker() {
        String billName = "KTX_SV_BILL_" +currentBillDto.getId().toString() + ".pdf";
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
                View view = binding.bill;
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
    public void setBillUI(BillDto billDto){
        binding.billId.setText(billDto.getId().toString());
        // set ngày in
        binding.dateNow.setText(DateUtils.Date2String(new Date()));
        // set trạng thái
        binding.status.setText(billDto.getStatus() ? "Đã thanh toán" : "Chưa thanh toán");

        // Nếu bill đã thanh toán
        if(billDto.getStatus()){
            binding.comfirmPaid.setVisibility(View.GONE);
            binding.payTimeWrapper.setVisibility(View.VISIBLE);
            binding.payTime.setText(DateUtils.Date2String(billDto.getUpdatedAt()));
        }
        else{
            binding.comfirmPaid.setVisibility(View.VISIBLE);
            binding.payTimeWrapper.setVisibility(View.GONE);
        }

        // set title
        binding.title.setText(billDto.getTitle());

        // bill tiền phòng
        if(billDto.getIdRegis() != null){
            binding.electricRow.setVisibility(View.GONE);
            binding.waterRow.setVisibility(View.GONE);
            binding.roomRow.setVisibility(View.VISIBLE);

            binding.roomUnitPrice.setText(NumberUtils.GetMoney(billDto.getRoomPrice()));
            binding.roomTotalPrice.setText(NumberUtils.GetMoney(billDto.getRoomPrice()));
            binding.totalPrice.setText(NumberUtils.GetMoney(billDto.getRoomPrice()));
        }
        // bill tiền điện nước
        else if(billDto.getIdElectricWater() != null){
            binding.roomRow.setVisibility(View.GONE);

            binding.electricRow.setVisibility(View.VISIBLE);
            binding.waterRow.setVisibility(View.VISIBLE);

            Integer electricTotalPrice = billDto.getElectricPrice() * billDto.getElectricNumber();
            Integer waterTotalPrice = billDto.getWaterPrice() * billDto.getWaterNumber();

            binding.electricPriceUnit.setText(NumberUtils.GetMoney(billDto.getElectricPrice()));
            binding.electricNumber.setText(NumberUtils.FormatNumber(billDto.getElectricNumber()) + " Kwh");
            binding.electricTotalPrice.setText(NumberUtils.GetMoney(electricTotalPrice));

            binding.waterPriceUnit.setText(NumberUtils.GetMoney(billDto.getWaterPrice()));
            binding.waterNumber.setText(NumberUtils.FormatNumber(billDto.getWaterNumber()) + " m3");
            binding.waterTotalPrice.setText(NumberUtils.GetMoney(waterTotalPrice));

            binding.totalPrice.setText(NumberUtils.GetMoney(electricTotalPrice + waterTotalPrice));
        }
    }
}
