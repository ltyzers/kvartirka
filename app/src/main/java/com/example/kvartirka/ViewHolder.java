package com.example.kvartirka;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ViewHolder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mTextViewAddress, mTextViewPrice;
    View mView;

    private ViewHolder.ClickListener mClickListener;

    ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v,getAdapterPosition());
                return true;
            }
        });

        mImageView = itemView.findViewById(R.id.image_view);
        mTextViewAddress = itemView.findViewById(R.id.tvAddress);
        mTextViewPrice = itemView.findViewById(R.id.tvPrice);
    }

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}