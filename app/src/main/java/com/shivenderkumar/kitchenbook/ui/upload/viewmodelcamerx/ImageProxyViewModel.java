package com.shivenderkumar.kitchenbook.ui.upload.viewmodelcamerx;

import android.graphics.Bitmap;

import androidx.camera.core.ImageProxy;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ImageProxyViewModel extends ViewModel {
    private final MutableLiveData<ImageProxy> mutableLiveDataIP = new MutableLiveData<>();

    private Bitmap image_bitmap;

    public MutableLiveData<ImageProxy> getMutableLiveDataIP() {
        return mutableLiveDataIP;
    }

    public void setMutableLiveDataIP(ImageProxy imageProxy){
        mutableLiveDataIP.setValue(imageProxy);
    }

    public Bitmap getImage_bitmap() {
        return image_bitmap;
    }

    public void setImage_bitmap(Bitmap image_bitmap) {
        this.image_bitmap = image_bitmap;
    }

}
