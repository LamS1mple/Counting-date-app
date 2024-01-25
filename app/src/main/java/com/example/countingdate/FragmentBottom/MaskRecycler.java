package com.example.countingdate.FragmentBottom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countingdate.R;

import java.util.List;

public class MaskRecycler extends RecyclerView.Adapter<MaskRecycler.ViewHolder> {

    private List<Integer> listMask;

    SendToFragment sendToFragment;

    public void setSendToFragment(SendToFragment sendToFragment) {
        this.sendToFragment = sendToFragment;
    }

    public interface SendToFragment{
        void sendImg (int imgInt);
    }

    public void setListMask(List<Integer> listMask) {
        this.listMask = listMask;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_mask,parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(listMask.get(position));
        final int selected = position;
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToFragment.sendImg(listMask.get(selected));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMask.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.mask);

        }
    }
}
