//package com.example.appktx2.data.model.itemImage;
//
//import android.content.Context;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.example.appktx2.interfaces.ICallBack;
//import com.example.appktx2.net.RetrofitClient;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.NonNull;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class ItemImageData {
//    public static ItemImageData GetItemImageDataLinkServer(Integer idResource, ICallBack callBack){
//        ItemImageData itemImageData = ItemImageData.builder()
//                .idResource(idResource)
//                .isLocal(false)
//                .callBack(callBack)
//                .build();
//
//        return itemImageData;
//    }
//    public static void RenderImage(@NonNull Context context, @NonNull ItemImageData itemImageData, @NonNull ImageView imageView){
//        if(itemImageData.isLocal){
//            imageView.setImageResource(itemImageData.idResource);
//        }
//        else{
//            String url = RetrofitClient.GI().baseUrl + "/api/v1/res/" + itemImageData.getIdResource().toString();
//            Glide.with(context)
//                    .load(url)
//                    .into(imageView);
//        }
//    }
//    Integer idResource;
//    boolean isLocal;
//    ICallBack callBack;
//}
