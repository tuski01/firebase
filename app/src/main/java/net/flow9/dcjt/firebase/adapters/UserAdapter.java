package net.flow9.dcjt.firebase.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.flow9.dcjt.firebase.R;
import net.flow9.dcjt.firebase.User;
import net.flow9.dcjt.firebase.chatRoomActivity;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{
    private ArrayList<User> mDataset;
    private Context context;
    private String userID = "";


    public UserAdapter(ArrayList<User> mDataset,  String userID, Context context) {
        this.mDataset = mDataset;
        this.userID = userID;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView Chat_list;

        public MyViewHolder(View v){
            super(v);
            Chat_list = v.findViewById(R.id.chat_list_userID);
        }
    }


    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_list, parent, false);

        ChatAdapter.MyViewHolder vh = new ChatAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        holder.Chat_list.setText(mDataset.get(position).getEmailId());

        holder.Chat_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, chatRoomActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
