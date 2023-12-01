package net.flow9.dcjt.firebase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.flow9.dcjt.firebase.R;
import net.flow9.dcjt.firebase.model.ChatModel;

import java.util.ArrayList;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.MyViewHolder>  {
    private ArrayList<ChatModel> mDataset;
    private Context context;
    private String userID = "";
    public int xxx;

    public ChatRoomAdapter(ArrayList<ChatModel> mDataset, String userID, Context context) {
        this.mDataset = mDataset;
        this.userID = userID;
        this.context = context;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView youChat;
        public TextView tvId;
        public TextView tvChat;
        public MyViewHolder(View v){
            super(v);
            tvChat = v.findViewById(R.id.tvChat);
            youChat = v.findViewById(R.id.youChat);
            tvId = v.findViewById(R.id.tvId);
        }
    }

    public ChatRoomAdapter(ArrayList<ChatModel> myDataset){
        mDataset = myDataset;
    }

    @Override
    public int getItemViewType(int position) {
        // return super.getItemViewType(position);
        if (mDataset.get(position).getUserID().equals(userID)){
            return 1;
        } else {
            return 2;
        }
    }

    public ChatRoomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.you_text_view, parent, false);
        if(viewType == 1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        }


        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        if(mDataset.get(position).getUserID().equals(userID)){
            holder.tvChat.setText(mDataset.get(position).getText());
        }else {
            holder.youChat.setText(mDataset.get(position).getText());
            holder.tvId.setText(mDataset.get(position).getUserID());
        }


    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }
}
