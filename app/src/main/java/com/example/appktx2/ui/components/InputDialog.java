package com.example.appktx2.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.appktx2.R;
import com.example.appktx2.databinding.ComponentInputBinding;
import com.example.appktx2.interfaces.IDialogComponent;

public class InputDialog extends Input implements IDialogComponent {

    public InputDialog(Context context) {
        super(context);
    }

    public InputDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InputDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InputDialog(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onChange(Object... objects) {
    }

    @Override
    public void onClick(Object... objects) {

    }

    @Override
    public boolean isValidate(Object... objects) {
        if(getText().equals("")){
            binding.input.requestFocus();
            this.setWarning("Invalid");
            return false;
        }
        else{
            this.setShowWarning(false);
        }
        return true;
    }
}