package com.example.a13058.weather;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 13058 on 14-02-17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterViewHolder> {
    private ArrayList<details> mData = null;
    private ItemAdapterOnClickHandler clickHandler;

    public ItemAdapter(ItemAdapterOnClickHandler clickHandler) {

        this.clickHandler = clickHandler;
    }

    public interface ItemAdapterOnClickHandler {
        void onClick(int index);
    }

    public class ItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public final TextView mTextViewDayTitle;
        public final TextView mTextViewDescription;
        public final TextView mTextViewDayTemperature;
        public ItemAdapterViewHolder(View view) {
            super(view);
            mTextViewDayTitle = (TextView) view.findViewById(R.id.day_title);
            mTextViewDescription = (TextView) view.findViewById(R.id.day_description);
            mTextViewDayTemperature = (TextView) view.findViewById(R.id.day_temperature);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            clickHandler.onClick(adapterPosition);
        }
    }

    @Override
    public ItemAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_weather;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,
                viewGroup, shouldAttachToParentImmediately);
        return new ItemAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapterViewHolder itemAdapterViewHolder, int position) {

        itemAdapterViewHolder.mTextViewDayTitle.setText("Day " + (position+1));
        itemAdapterViewHolder.mTextViewDescription.setText("Weather: "+ mData.get(position).getDay_weather_description());
        itemAdapterViewHolder.mTextViewDayTemperature.setText("General Day Temperature: "+ mData.get(position).getDegree_day().toString()+"°");
    }

    @Override
    public int getItemCount() {
        if (null == mData) return 0;
        return mData.size();
    }

    public void setData(ArrayList<details> data) {
        mData = data;
        notifyDataSetChanged();
    }


}
