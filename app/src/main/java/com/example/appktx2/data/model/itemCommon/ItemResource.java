package com.example.appktx2.data.model.itemCommon;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appktx2.interfaces.ICallBack;
import com.example.appktx2.net.RetrofitClient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResource {
    public static ItemResource GetItemResourceResServer(@NonNull Integer idResource){
        return ItemResource.builder()
                .idResource(idResource)
                .isLocal(false)
                .callBack(object -> {})
                .build();
    }
    public static ItemResource GetItemResourceResServer(@NonNull Integer idResource, @NonNull ICallBack callBack){
        return ItemResource.builder()
                .idResource(idResource)
                .isLocal(false)
                .callBack(callBack)
                .build();
    }
    public static void AssignResource(@NonNull Context context, @NonNull ItemResource itemResource, @NonNull ImageView imageView){
        if(itemResource.isLocal){
            imageView.setImageResource(itemResource.idResource);
        }
        else{
            String url = RetrofitClient.GI().baseUrl + "/api/v1/res/" + itemResource.getIdResource().toString();
            Glide.with(context)
                    .load(url)
                    .into(imageView);
        }
    }
    Integer id;
    ICallBack callBack;
    boolean isLocal;
    Integer idResource;
}
