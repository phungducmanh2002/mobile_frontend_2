package com.example.appktx2.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.appktx2.databinding.ComponentInputBinding;
import com.example.appktx2.R;

public class Input extends LinearLayout {
    public ComponentInputBinding binding;
    boolean isShowWarning = false;
    public Input(Context context) {
        super(context);
        init(context, null);
    }

    public Input(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Input(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public Input(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        binding = ComponentInputBinding.inflate(LayoutInflater.from(context), this, true);

        if(attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.input);
            String text = a.getString(R.styleable.input_inputText);
            String hint = a.getString(R.styleable.input_inputHint);
            String warning = a.getString(R.styleable.input_inputWarning);
            String length = a.getString(R.styleable.input_inputLength);
            String textAlign = a.getString(R.styleable.input_inputTextAlign);
            String type = a.getString(R.styleable.input_inputType);

            if(text != null){
                setText(text);
            }
            if(hint != null){
                setHint(hint);
            }
            if(warning != null){
                setWarning(warning);
            }
            if(length != null){
                Integer lengthNum = Integer.parseInt(length);
                setLength(lengthNum);
            }
            if(textAlign != null){
                setTextAlign(textAlign);
            }
            if(type != null){
                setInputType(type);
            }
            a.recycle();
        }

        setEvent();
    }
    private void setEvent() {
        binding.passwordActionIcon.setOnClickListener(v -> {
            if(binding.input.getInputType() != 129){
                binding.input.setInputType(129);
                binding.passwordActionIcon.setImageResource(R.drawable.img_show);
            }
            else{
                binding.passwordActionIcon.setImageResource(R.drawable.img_hide);
                binding.input.setInputType(140);
            }
            binding.input.setSelection(binding.input.getText().toString().length());
        });
    }
    public void setShowWarning(boolean isShow){
        this.isShowWarning = isShow;
        if(this.isShowWarning){
            binding.warning.setVisibility(VISIBLE);
        }
        else{
            binding.warning.setVisibility(GONE);
        }
    }
    public boolean getShowWarning(){
        return  this.isShowWarning;
    }
    public void setText(String text){
        this.binding.input.setText(text);
    }
    public void setHint(String hint){
        binding.input.setHint(hint);
    }
    public void setWarning(String warning){
        binding.warning.setText(warning);
        setShowWarning(true);
    }
    public void setLength(Integer length){
        InputFilter[] inputFilter = new InputFilter[1];
        inputFilter[0] = new InputFilter.LengthFilter(length);
        binding.input.setFilters(inputFilter);
    }
    public void setTextAlign(String textAlign){
        if(textAlign.equals("center")){
            binding.input.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        }
        else if(textAlign.equals("left")){
            binding.input.setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        }
        else if(textAlign.equals("right")){
            binding.input.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
        }
    }
    public void setInputType(String inputType){
        if(inputType.equals("number")){
            binding.passwordActionIcon.setVisibility(GONE);
            binding.input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        else if(inputType.equals("password")){
            binding.passwordActionIcon.setVisibility(VISIBLE);
            binding.input.setInputType(129);
        }
        else{
            binding.passwordActionIcon.setVisibility(GONE);
            binding.input.setInputType(140);
        }
    }
    public void setInputType(Integer inputType){
        binding.input.setInputType(inputType);
    }
    public String getText(){
        return this.binding.input.getText().toString();
    }
}
