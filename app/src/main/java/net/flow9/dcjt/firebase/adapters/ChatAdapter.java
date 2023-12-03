package net.flow9.dcjt.firebase.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.flow9.dcjt.firebase.Find_Object_detail;
import net.flow9.dcjt.firebase.R;
import net.flow9.dcjt.firebase.User;
import net.flow9.dcjt.firebase.chatRoomActivity;
import net.flow9.dcjt.firebase.model.ChatModel;
import net.flow9.dcjt.firebase.model.RoomModel;
import net.flow9.dcjt.firebase.model.UserAccount;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private static final String TAG = "ChatAdapter";
    private ArrayList<RoomModel> mDataset;
    private Context context;
    private String userID = "";


    public ChatAdapter(ArrayList<RoomModel> mDataset,  String userID, Context context) {
        this.mDataset = mDataset;
        this.userID = userID;
        this.context = context;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView Chat_list;
        public String Room_name;

        public MyViewHolder(View v){
            super(v);
            Chat_list = v.findViewById(R.id.chat_list_userID);
        }
    }


    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_list, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        RoomModel room = mDataset.get(position);
        holder.Chat_list.setText(room.getRoom_name());
        holder.Room_name = room.getRoom_name();


        holder.Chat_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, chatRoomActivity.class);
                intent.putExtra("Room_name", holder.Room_name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }
}
