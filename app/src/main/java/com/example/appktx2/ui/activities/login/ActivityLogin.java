package com.example.appktx2.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appktx2.databinding.ActivityLoginBinding;
import com.example.appktx2.interfaces.IPDM;

public class ActivityLogin extends AppCompatActivity implements IPDM.View {
    ActivityLoginBinding binding;
    LoginHandler handler  = new LoginHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        handler.setView(this);
        setEvent();
    }

    private void setEvent() {
        binding.login.setOnClickListener(v -> {
                if(checkInput()){
                    String email = binding.email.getText();
                    String password = binding.password.getText();
                    handler.login(email, password);
                }
        });
    }
    boolean checkInput(){
        if(binding.email.getText().equals("")){
            binding.email.setWarning("Invalid email");
            binding.email.requestFocus();
            return false;
        }
        else{
            binding.email.setShowWarning(false);
        }
        if(binding.password.getText().equals("")){
            binding.password.setWarning("Invalid password");
            binding.password.requestFocus();
            return false;
        }
        else{
            binding.password.setShowWarning(false);
        }

        return true;
    }
}
