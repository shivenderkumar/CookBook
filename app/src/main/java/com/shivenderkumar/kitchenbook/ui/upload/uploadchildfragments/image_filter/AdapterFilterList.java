package com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments.image_filter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shivenderkumar.kitchenbook.R;

import java.util.ArrayList;

public class AdapterFilterList extends RecyclerView.Adapter<AdapterFilterList.FilterItemViewHolder> {
    private ArrayList<FilterItem> arrayListfilterItem;
    private Context context;

    public AdapterFilterList(Context context, ArrayList<FilterItem> arrayListfilterItem) {
        DataSource dataSource = new DataSource(context);
        this.arrayListfilterItem = dataSource.getArrayListfilterItem();
        this.arrayListfilterItem = arrayListfilterItem;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return arrayListfilterItem.size();
    }

    @Override
    public FilterItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_imageeffect, parent, false);

        return new FilterItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterItemViewHolder holder, int position) {
        FilterItem filterItem = arrayListfilterItem.get(position);
        holder.imageview_item_imageeffect.setImageDrawable(filterItem.getImageview_item_imageeffect());
        holder.textview_item_imageeffect.setText(filterItem.getTextview_item_imageeffect());

    }

    public class FilterItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageview_item_imageeffect;
        private TextView textview_item_imageeffect;

        public FilterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageview_item_imageeffect = (ImageView) itemView.findViewById(R.id.imageview_item_imageeffect);
            this.textview_item_imageeffect = (TextView) itemView.findViewById(R.id.textview_item_imageeffect);
        }
    }

}
