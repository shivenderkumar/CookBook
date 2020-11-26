package com.shivenderkumar.kitchenbook.ui.upload.viewmodelcamerx;

import androidx.camera.core.ImageProxy;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ImageProxyViewModel extends ViewModel {
    private final MutableLiveData<ImageProxy> mutableLiveDataIP = new MutableLiveData<>();

    public MutableLiveData<ImageProxy> getMutableLiveDataIP() {
        return mutableLiveDataIP;
    }

    public void setMutableLiveDataIP(ImageProxy imageProxy){
        mutableLiveDataIP.setValue(imageProxy);
    }

}
