package com.example.countingdate;

import android.annotation.SuppressLint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countingdate.Model.ImageMemory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemViewPageMemoryAdapter extends RecyclerView.Adapter<ItemViewPageMemoryAdapter.ViewHolder>{

    List<ImageMemory> imageMemoryList;

    public List<ImageMemory> getImageMemoryList() {
        return imageMemoryList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setImageMemoryList(List<ImageMemory> imageMemoryList) {
        this.imageMemoryList = imageMemoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_memory, parent, false);
        CardView cardView = (CardView) view.findViewById(R.id.visibleViewGroup);
        cardView.setRadius(0);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageButton.setVisibility(View.INVISIBLE);
        Picasso.get()
                .load(imageMemoryList.get(position).getUriImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return imageMemoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageButton imageButton;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageButton = itemView.findViewById(R.id.delete);
            image = itemView.findViewById(R.id.image_memory);
        }
    }
}
