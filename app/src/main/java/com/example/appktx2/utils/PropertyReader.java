package com.example.appktx2.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static PropertyReader instance;
    public static PropertyReader getInstance(Context context){
        if(instance == null){
            instance = new PropertyReader();
        }
        instance.context = context;
        return  instance;
    }
    private PropertyReader(){
        properties = new Properties();
    }
    private Properties properties;
    private Context context;
    private Properties getMyProperties() throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("Application.properties");
        properties.load(inputStream);
        return properties;
    }
    public String getProperty(String key)throws IOException{
        Properties properties = getMyProperties();
        return properties.getProperty(key);
    }
}
