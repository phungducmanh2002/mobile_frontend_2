package com.example.appktx2.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.example.appktx2.data.dto.RoomCollectionDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.data.dto.SemesterDto;
import com.example.appktx2.databinding.DialogMyDialogBinding;
import com.example.appktx2.interfaces.ICallBack;
import com.example.appktx2.ui.components.InputDialog;
import com.example.appktx2.ui.components.PickDate;
import com.example.appktx2.ui.components.Selector;
import com.example.appktx2.utils.DateUtils;
import com.example.appktx2.utils.LayoutUtils;

import java.util.Date;
import java.util.List;

public class MyDialog extends Dialog {
    public static MyDialog CreateSemesterDialog(Context context, ICallBack onDataValid){
        MyDialog myDialog = new MyDialog(context);
        InputDialog inputSemesterName = new InputDialog(context);
        InputDialog inputRoomPrice = new InputDialog(context);
        InputDialog inputElectricPrice = new InputDialog(context);
        InputDialog inputWaterPrice = new InputDialog(context);
        PickDate pickDateStartAt = new PickDate(context);
        PickDate pickDateEndAt = new PickDate(context);

        myDialog.onSave((Object... objs) -> {
            if(inputSemesterName.isValidate() && inputRoomPrice.isValidate()  && inputElectricPrice.isValidate()  && inputWaterPrice.isValidate()){
                if(pickDateStartAt.getDate() == null){
                    pickDateStartAt.pickDate();
                    return;
                }
                if(pickDateEndAt.getDate() == null){
                    pickDateEndAt.pickDate();
                    return;
                }

                String semesterName = inputSemesterName.getText();
                Integer roomPrice = Integer.parseInt(inputRoomPrice.getText());
                Integer electricPrice = Integer.parseInt(inputElectricPrice.getText());
                Integer waterPrice = Integer.parseInt(inputWaterPrice.getText());
                Date startAt = pickDateStartAt.getDate();
                Date endAt = pickDateEndAt.getDate();

                SemesterDto semesterDto = new SemesterDto();
                semesterDto.setSemesterName(semesterName);
                semesterDto.setRoomPrice(roomPrice);
                semesterDto.setElectricPrice(electricPrice);
                semesterDto.setWaterPrice(waterPrice);
                semesterDto.setStartAt(startAt);
                semesterDto.setEndAt(endAt);

                onDataValid.action(semesterDto);

                myDialog.cancel();
            }
        });

        pickDateStartAt.setText("Start at");
        pickDateStartAt.setCallBack((objects -> {
            if(pickDateEndAt.getDate() == null)
                return;
            Date newDate = (Date) objects[0];
            Date endDate = pickDateEndAt.getDate();

            if(newDate.after(endDate)){
                pickDateStartAt.setDate(DateUtils.MinusDay(endDate, 1));
            }
        }));
        pickDateEndAt.setText("End at");
        pickDateEndAt.setCallBack((objects -> {
            if(pickDateStartAt.getDate() == null)
                return;
            Date newDate = (Date) objects[0];
            Date startDate = pickDateStartAt.getDate();

            if(newDate.before(startDate)){
                pickDateEndAt.setDate(DateUtils.PlusDay(startDate, 1));
            }
        }));

        myDialog.onCancle(object -> {
            myDialog.cancel();
        });

        inputSemesterName.setHint("Semester name");
        inputSemesterName.setInputType("text");
        inputSemesterName.setLength(50);

        inputRoomPrice.setHint("Room price");
        inputRoomPrice.setInputType("number");
        inputRoomPrice.setLength(10);

        inputElectricPrice.setHint("Electric price");
        inputElectricPrice.setInputType("number");
        inputElectricPrice.setLength(10);

        inputWaterPrice.setHint("Water price");
        inputWaterPrice.setInputType("number");
        inputWaterPrice.setLength(10);

        myDialog.addChildren(inputSemesterName, inputRoomPrice, inputElectricPrice, inputWaterPrice, pickDateStartAt, pickDateEndAt);

        return  myDialog;
    }
    public static MyDialog CreateItemDialog(Context context, ICallBack onDataValid){
        MyDialog myDialog = new MyDialog(context);
        InputDialog itemNameInput = new InputDialog(context);
        InputDialog itemQuantityInput = new InputDialog(context);

        myDialog.onSave((Object... objs) -> {
            if(itemNameInput.isValidate() && itemQuantityInput.isValidate()){

                String itemName = itemNameInput.getText();
                Integer quantity = Integer.parseInt(itemQuantityInput.getText());
                onDataValid.action(itemName, quantity);
                myDialog.cancel();
            }
        });

        myDialog.onCancle(object -> {
            myDialog.cancel();
        });

        itemNameInput.setHint("Item name");
        itemNameInput.setInputType("text");
        itemNameInput.setLength(50);

        itemQuantityInput.setHint("Quatity");
        itemQuantityInput.setInputType("number");
        itemQuantityInput.setLength(5);

        myDialog.addChildren(itemNameInput, itemQuantityInput);

        return  myDialog;
    }
    public static MyDialog CreateRoomDialog(Context context, ICallBack onDataValid, List<RoomCollectionDto> dataList){
        MyDialog myDialog = new MyDialog(context);

        myDialog.setTitle("CREATE ROOM");

        Selector<RoomCollectionDto> roomCollectionSelector = new Selector<>(context);
        InputDialog roomNameInput = new InputDialog(context);
        InputDialog roomAcreage = new InputDialog(context);
        RadioGroup radioGroup = new RadioGroup(context);
        RadioButton radioButtonMale = new RadioButton(context);
        RadioButton radioButtonFemale = new RadioButton(context);
        CheckBox checkBoxActive = new CheckBox(context);

        roomCollectionSelector.setData(dataList, (item -> {RoomCollectionDto r = (RoomCollectionDto) item;return r.getRoomCollectionName();}));

        checkBoxActive.setText("Active");
        checkBoxActive.setChecked(true);

        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.addView(radioButtonMale);
        radioGroup.addView(radioButtonFemale);

        radioButtonMale.setText("Male");
        radioButtonMale.setChecked(true);
        radioButtonFemale.setText("Female");

        myDialog.onSave((Object... objs) -> {
            if(roomNameInput.isValidate() && roomAcreage.isValidate()){
                String roomName = roomNameInput.getText();
                Integer acreage = Integer.parseInt(roomAcreage.getText());
                Boolean isMale = radioButtonMale.isChecked();
                Boolean isActive = checkBoxActive.isChecked();
                RoomCollectionDto roomCollectionDto = roomCollectionSelector.getSelectedItem();

                RoomDto roomDto = RoomDto.builder()
                        .roomName(roomName)
                        .roomGender(isMale ? 0 : 1)
                        .roomStatus(isActive ? 0 : 1)
                        .roomAcreage(acreage.floatValue())
                        .idRoomCollection(roomCollectionDto.getId())
                        .build();

                onDataValid.action(roomDto);
                myDialog.cancel();
            }
        });

        myDialog.onCancle(object -> {
            myDialog.cancel();
        });

        roomNameInput.setHint("Room name");
        roomNameInput.setInputType("text");
        roomNameInput.setLength(50);

        roomAcreage.setHint("Room acreage");
        roomAcreage.setInputType("number");
        roomAcreage.setLength(5);

        myDialog.addChildren(roomCollectionSelector, roomNameInput, roomAcreage, radioGroup, checkBoxActive);

        return  myDialog;
    }
    public static MyDialog CreateUpdateItemQuantityDialog(Context context, ICallBack onDataValid){
        MyDialog myDialog = new MyDialog(context);
        myDialog.setTitle("Quantity");

        InputDialog itemQuantityInput = new InputDialog(context);

        myDialog.onSave((Object... objs) -> {
            if(itemQuantityInput.isValidate()){
                Integer quantity = Integer.parseInt(itemQuantityInput.getText());
                onDataValid.action(quantity);
                myDialog.cancel();
            }
        });

        myDialog.onCancle(object -> {
            myDialog.cancel();
        });

        myDialog.binding.save.setText("Save");

        itemQuantityInput.setHint("Quatity");
        itemQuantityInput.setInputType("number");
        itemQuantityInput.setLength(5);

        myDialog.addChildren(itemQuantityInput);

        return  myDialog;
    }
    DialogMyDialogBinding binding;
    LinearLayout.LayoutParams layoutParams;
    public MyDialog(@NonNull Context context) {
        super(context);
        binding = DialogMyDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();
        if(window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttrs = window.getAttributes();
        windowAttrs.gravity = Gravity.CENTER;
        setCancelable(true);

        this.layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(30,30,30,30);

        setEvent();
    }
    private void setEvent() {
    }
    public void onSave(ICallBack callBack){
        this.binding.save.setOnClickListener(v -> {
            callBack.action(this);
        });
    }
    public void onCancle(ICallBack callBack){
        this.binding.cancle.setOnClickListener(v -> {
            callBack.action(this);
        });
    }
    public void setTitle(String title){
        this.binding.dialogTitle.setText(title);
    }
    public List<LinearLayout> getAllChilds(){
        List<LinearLayout> childs = LayoutUtils.GetAllChilds(binding.dialogContainer);
        return childs;
    }
    public void addChildren(View component){
        if(component.getParent() != null){
            ((ViewGroup)component.getParent()).removeView(component);
        }
        this.binding.dialogContainer.addView(component, this.layoutParams);
    }
    public void addChildren(View... components){
        this.binding.dialogContainer.removeAllViews();
        for (View view:components) {
            this.addChildren(view);
        }
    }
}
