package net.flow9.dcjt.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.flow9.dcjt.firebase.adapters.ChatAdapter;
import net.flow9.dcjt.firebase.adapters.find_PostAdapter;

public class chatActivity extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_chat, container, false);

        recyclerView = view.findViewById(R.id.chat_list);

        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = {"test1", "test2", "test3", "test4"};
        mAdapter = new ChatAdapter(myDataset, getContext());
        recyclerView.setAdapter(mAdapter);

        return view;
    }


}
