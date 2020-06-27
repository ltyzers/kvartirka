package com.example.kvartirka;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private List<Model> mModel;
    Adapter(Context context, List<Model> modelList) {

        mContext = context;
        mModel = modelList;
    }

    private static final String EXTRA_URL = "imageUrl";
    static final String EXTRA_URLS = "stringArray";
    static final String EXTRA_ADDRESS = "address";
    static final String EXTRA_PRICE = "dayPrice";
    static final String EXTRA_TITLE = "title";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.model_layout, viewGroup,
                false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent showFlatIntent = new Intent(mContext, ShowFlatActivity.class);
                Model clickedItem = mModel.get(position);

                showFlatIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
                showFlatIntent.putExtra(EXTRA_ADDRESS, clickedItem.getFlatAddress());
                showFlatIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
                showFlatIntent.putExtra(EXTRA_URLS, clickedItem.getStringArray());
                showFlatIntent.putExtra(EXTRA_PRICE,clickedItem.getDayPrice());

                mContext.startActivity(showFlatIntent);
            }

            @Override
            public void onItemLongClick(View view, int position){}


    });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Model currentItem = mModel.get(position);

        String imageUrl = currentItem.getImageUrl();
        String flatAddress = currentItem.getFlatAddress();
        int dayPrice = currentItem.getDayPrice();

        viewHolder.mTextViewAddress.setText("Адрес: " + flatAddress);
        viewHolder.mTextViewPrice.setText("Цена: " + dayPrice);

        Picasso.get().load(imageUrl).fit().centerInside().into(viewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }
}