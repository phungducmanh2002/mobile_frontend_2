package com.example.appktx2.ui.activities.profile.editUser;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.AppKtx;
import com.example.appktx2.data.dto.UserDto;
import com.example.appktx2.databinding.ActivityEditUserBinding;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.I_EditUserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditUser extends AppCompatActivity {
    ActivityEditUserBinding binding;
    I_EditUserService service = RetrofitClient.GI().getRetrofit().create(I_EditUserService.class);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setEvent();
    }
    private void setEvent() {
        binding.ipFirstName.setText(AppKtx.userDto.getFirstName());
        binding.ipLastName.setText(AppKtx.userDto.getLastName());
        binding.rbFemale.setChecked(AppKtx.userDto.getGender() != 0);

        binding.save.setOnClickListener(v -> {
            if(checkInput() && isDataChanged()){
                editUser();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private boolean checkInput(){
        if(binding.ipFirstName.getText().equals("")){
            binding.ipFirstName.setWarning("Họ không được để trống!");
            binding.ipFirstName.requestFocus();
            return false;
        }
        else{
            binding.ipFirstName.setShowWarning(false);
        }
        if(binding.ipLastName.getText().equals("")){
            binding.ipLastName.setWarning("Tên không được để trống!");
            binding.ipLastName.requestFocus();
            return false;
        }
        else{
            binding.ipLastName.setShowWarning(false);
        }
        return true;
    }
    private boolean isDataChanged(){
        return !(binding.ipFirstName.getText().equals(AppKtx.userDto.getFirstName())
                && binding.ipLastName.getText().equals(AppKtx.userDto.getLastName())
                && (
                (binding.rbFemale.isChecked() && AppKtx.userDto.getGender() != 0)
                ||
                (binding.rbMale.isChecked() && AppKtx.userDto.getGender() == 0)
                ));
    }
    public void editUser(){
        UserDto userDto = AppKtx.userDto.clone();
        userDto.setFirstName(binding.ipFirstName.getText());
        userDto.setLastName(binding.ipLastName.getText());
        userDto.setGender(0);
        if(binding.rbFemale.isChecked()){
            userDto.setGender(1);
        }
        service.editUser(userDto).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if(response.isSuccessful()){
                    AppKtx.userDto = response.body();
                    Log.d("UGKJBUOHJ", AppKtx.userDto.toString());
                    finish();
                }
                else{
                    Toast.makeText(ActivityEditUser.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {

            }
        });
    }
}
