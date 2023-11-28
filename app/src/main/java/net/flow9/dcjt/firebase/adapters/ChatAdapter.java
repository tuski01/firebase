package net.flow9.dcjt.firebase.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.flow9.dcjt.firebase.Find_Object_detail;
import net.flow9.dcjt.firebase.R;
import net.flow9.dcjt.firebase.chatRoomActivity;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private String[] mDataset;
    private Context context;

    public ChatAdapter(String[] mDataset, Context context) {
        this.mDataset = mDataset;
        this.context = context;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textview;
        public MyViewHolder(View v){
            super(v);
            textview = v.findViewById(R.id.tvChat);
        }
    }

    public ChatAdapter(String[] myDataset){
        mDataset = myDataset;
    }

    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.textview.setText(mDataset[position]);

        holder.textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, chatRoomActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataset.length;
    }
}
