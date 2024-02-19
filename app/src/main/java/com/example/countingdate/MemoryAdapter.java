package com.example.countingdate;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.countingdate.FragmentBottom.MaskRecycler;
import com.example.countingdate.Model.MemoryWithImage;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.ViewHolder>  {

    private List<MemoryWithImage> memoryWithImageList;
    private int[] icon;

    private Random random;

    private SimpleDateFormat simpleDateFormat ;

    private ItemViewPageMemoryAdapter itemViewPageMemoryAdapter ;

    private MainActivity mainActivity;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setItemViewPageMemoryAdapter(ItemViewPageMemoryAdapter itemViewPageMemoryAdapter) {
        this.itemViewPageMemoryAdapter = itemViewPageMemoryAdapter;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMemoryWithImageList(List<MemoryWithImage> memoryWithImageList) {
        this.memoryWithImageList = memoryWithImageList;
        notifyDataSetChanged();
    }

    public void setIcon(int[] icon) {
        this.icon = icon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_memory, parent, false);
        random = new Random();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int size = memoryWithImageList.get(position).getImageMemories().size()  - 1;
        Picasso.get()
                .load(icon[random.nextInt(4)])
                .into(holder.iconMemory);
        String date = simpleDateFormat.format(memoryWithImageList.get(position)
                .getMemory().getDate());
        holder.dateMemory.setText(date);
        holder.content.setText(memoryWithImageList.get(position)
                .getMemory()
                .getTitle());
        itemViewPageMemoryAdapter.setImageMemoryList(memoryWithImageList.get(position).getImageMemories());

        holder.viewPager2Memory.setAdapter(itemViewPageMemoryAdapter);

        side(holder, size);
        holder.springDotsIndicator.attachTo(holder.viewPager2Memory);


    }

    private void side(ViewHolder holder, int size){
        holder.viewPager2Memory.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            float downX = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (event.getX() != 0.0) {
                            downX = event.getX();
                        }
                        mainActivity.setEnableViewPager2(false);

                        break;
                    case MotionEvent.ACTION_UP:
                        mainActivity.setEnableViewPager2(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (holder.viewPager2Memory.getCurrentItem() == size && downX - event.getX() > 0) {
                            mainActivity.setEnableViewPager2(true);
                        }
                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return memoryWithImageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconMemory;
        public TextView dateMemory;
        public TextView content;
        public ViewPager2 viewPager2Memory;
        public SpringDotsIndicator springDotsIndicator;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.content_memory);
            iconMemory = itemView.findViewById(R.id.iconMemory);
            dateMemory = itemView.findViewById(R.id.dateMemory);
            viewPager2Memory = itemView.findViewById(R.id.view_page_memory);
            springDotsIndicator = itemView.findViewById(R.id.inLine_memory);

        }
    }
}
