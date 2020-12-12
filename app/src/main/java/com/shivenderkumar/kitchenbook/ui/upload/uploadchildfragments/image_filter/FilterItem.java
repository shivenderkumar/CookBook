package com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments.image_filter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

public class FilterItem {
    private Drawable drawable;
    private String string;

    public FilterItem(Drawable drawable, String string) {
        this.drawable = drawable;
        this.string = string;
    }

    public Drawable getImageview_item_imageeffect() {
        return drawable;
    }

    public void setImageview_item_imageeffect(Drawable drawable) {
        this.drawable =drawable;
    }

    public String getTextview_item_imageeffect() {
        return string;
    }

    public void setTextview_item_imageeffect(String string) {
        this.string = string;
    }
}
