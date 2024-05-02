package com.example.appktx2.ui.fragments.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.appktx2.AppKtx;
import com.example.appktx2.data.apiResponse.UpdateAvatarRes;
import com.example.appktx2.data.dto.RoleDto;
import com.example.appktx2.databinding.FragmentProfileBinding;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.I_ProfileService;
import com.example.appktx2.ui.activities.login.ActivityLogin;
import com.example.appktx2.ui.activities.profile.editUser.ActivityEditUser;
import com.github.dhaval2404.imagepicker.ImagePicker;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile  extends Fragment {

    FragmentProfileBinding binding;
    I_ProfileService service = RetrofitClient.GI().getRetrofit().create(I_ProfileService.class);

    public FragmentProfile() {
    }
    public static FragmentProfile newInstance() {
        FragmentProfile fragment = new FragmentProfile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setEvent();
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    private void setEvent() {
        binding.firstName.setText(AppKtx.userDto.getFirstName());
        binding.lastName.setText(AppKtx.userDto.getLastName());
        binding.gender.setText(AppKtx.userDto.getGender() == 0 ? "Nam" : "Nữ");
        binding.email.setText(AppKtx.userDto.getEmail());
        binding.role.setText(RoleDto.GetRoleName(AppKtx.userDto.getIdRole()));

        binding.editAvatar.setOnClickListener(v -> {
            ImagePicker.with(getActivity()).crop().compress(1024).maxResultSize(1080,1080).start();
        });
        binding.editInfo.setOnClickListener(v -> {
            Intent editUserActivity = new Intent(getContext(), ActivityEditUser.class);
            getActivity().startActivity(editUserActivity);
        });
        binding.logout.setOnClickListener(v ->{
            AppKtx.Reset();
            Intent loginIntent = new Intent(getActivity(), ActivityLogin.class);
            getActivity().startActivity(loginIntent);
            getActivity().finish();
        });
        getAvatar();
    }
    public void onSelectAvatarResponse(byte[] imgData){

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgData);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "newavatar.image", requestBody);

        try {
            service.updateAvatar(body).enqueue(new Callback<UpdateAvatarRes>() {
                @Override
                public void onResponse(Call<UpdateAvatarRes> call, Response<UpdateAvatarRes> response) {
                    if(response.isSuccessful()){
//                        Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_LONG).show();
                        Integer idAvatarNew = response.body().getIdResource();
                        AppKtx.userDto.setIdResource(idAvatarNew);
                        setAvatarData(imgData);
                    }
                    else{
                        Toast.makeText(getActivity(), "Goi api that bai", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateAvatarRes> call, Throwable t) {
//                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception ex){
            Log.d("SDFHOJNM", ex.getMessage());
        }
    }
    private void setAvatarData(byte[] data){
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        binding.avatar.setImageBitmap(bitmap);
    }
    public void getAvatar(){
        if(AppKtx.userDto.getIdResource() == null){
            return;
        }
//        service.getAvatar(AppKtx.userDto.getIdResource()).enqueue(new Callback<ResourceDto>() {
//            @Override
//            public void onResponse(Call<ResourceDto> call, Response<ResourceDto> response) {
//                if(response.isSuccessful()){
////                    Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();
//                    ResourceDto resourceDto = response.body();
//                    Log.d("IOHJLKNOIHJ", resourceDto.toString());
//                    setAvatarData(resourceDto.getData().getData());
//                }
//                else{
//                    Toast.makeText(getActivity(), "LOI GET AVATAR", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResourceDto> call, Throwable t) {
//
//            }
//        });
        String url = RetrofitClient.GI().baseUrl + "/api/v1/res/" + AppKtx.userDto.getIdResource().toString();
        Glide.with(getContext())
                .load(url)
                .into(binding.avatar);
    }
}