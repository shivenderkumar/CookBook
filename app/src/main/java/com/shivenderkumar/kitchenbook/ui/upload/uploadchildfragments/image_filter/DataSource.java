package com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments.image_filter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.load.engine.Resource;
import com.shivenderkumar.kitchenbook.R;

import java.util.ArrayList;

public class DataSource {
    private ArrayList<FilterItem> arrayListfilterItem;
    private Context context;

    public DataSource(Context context) {
        this.arrayListfilterItem = new ArrayList<>();
        this.context = context;
        addFiltersToArrayList();
    }

    private void addFiltersToArrayList() {
        //add filters to arraylist
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_1" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_2" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_3" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_4" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_5" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_6" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_7" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_8" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_9" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_10" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_11" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_12" ));
        arrayListfilterItem.add(new FilterItem(ContextCompat.getDrawable(context, R.drawable.no_image_available), "Effect_13" ));

    }

    public ArrayList<FilterItem> getArrayListfilterItem() {
        return arrayListfilterItem;
    }
}
