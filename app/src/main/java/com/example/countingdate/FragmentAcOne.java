package com.example.countingdate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countingdate.Model.ImageMemory;
import com.example.countingdate.Model.MemoryWithImage;

import java.util.List;

public class FragmentAcOne extends Fragment {


    RecyclerView recyclerViewMemory;
    ImageButton addMemory;

    List<MemoryWithImage> memoryWithImageList;
    MemoryAdapter memoryAdapter;

    int[] icon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ac_one, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        icon = new int[]{R.drawable.flower,R.drawable.flower1,R.drawable.flower2,R.drawable.flower3};
        recyclerViewMemory = view.findViewById(R.id.listMemory);
        addMemory = view.findViewById(R.id.add_memory);

        memoryWithImageList = AppDatabase.getInstance(getContext()).memoryDAO().getAllMemory();
//
        memoryAdapter = new MemoryAdapter();
        memoryAdapter.setIcon(icon);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMemory.setLayoutManager(linearLayoutManager);

        memoryAdapter.setMemoryWithImageList(memoryWithImageList);
        memoryAdapter.setItemViewPageMemoryAdapter(new ItemViewPageMemoryAdapter());
        memoryAdapter.setMainActivity((MainActivity) getActivity());
        recyclerViewMemory.setAdapter(memoryAdapter);

        addMemory = view.findViewById(R.id.add_memory);

        addMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}
