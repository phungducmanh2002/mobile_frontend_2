package com.example.appktx2.ui.activities.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.appktx2.databinding.ActivityHomeBinding;
import com.example.appktx2.interfaces.IPDM;
import com.example.appktx2.ui.fragments.home.FragmentHome;
import com.example.appktx2.ui.fragments.profile.FragmentProfile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ActivityHome extends AppCompatActivity implements IPDM.View {
    HomeHandler handler = new HomeHandler();
    boolean requestExit = false;
    ActivityHomeBinding binding;
    FragmentProfile fragmentProfile;
    int fragmentNumber = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        handler.setView(this);

        setControl();
        setEvent();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(fragmentNumber == 0){
            replaceFragment(FragmentHome.newInstance());
        }
        else if(fragmentNumber == 1){
            fragmentProfile = FragmentProfile.newInstance();
            replaceFragment(fragmentProfile);
        }
        handler.getMyInfo();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return super.getOnBackInvokedDispatcher();
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
            byte[] avatarData = getBytes(iStream);
            fragmentProfile.onSelectAvatarResponse(avatarData);
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("GVBNKIUYGHBJ", e.getMessage());
//            throw new RuntimeException(e);
        }
    }
    private void setControl() {
    }
    private void setEvent() {
        navigationEvent();
        
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(!requestExit){
                    Toast.makeText(ActivityHome.this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
                    requestExit = true;
                }
                else{
                    finish();
                }
            }
        });
    }
    private void navigationEvent() {
        binding.bottomNavigationMenu.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.menu_home){
                replaceFragment(FragmentHome.newInstance());
                fragmentNumber = 0;
            }
            if(item.getItemId() == R.id.menu_profile){
                if(fragmentProfile == null){
                    fragmentProfile = FragmentProfile.newInstance();
                }
                replaceFragment(fragmentProfile);
                fragmentNumber = 1;
            }

            return  true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bottomNavigationView, fragment);
        fragmentTransaction.commit();
    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
