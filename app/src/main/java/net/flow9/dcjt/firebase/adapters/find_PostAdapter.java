package net.flow9.dcjt.firebase.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.flow9.dcjt.firebase.Find_Object_detail;
import net.flow9.dcjt.firebase.R;
import net.flow9.dcjt.firebase.model.Post;

import java.util.ArrayList;
public class find_PostAdapter extends RecyclerView.Adapter<find_PostAdapter.CustomViewHolder> {

        private ArrayList<Post> arrayList;
        private Context context;

        public find_PostAdapter(ArrayList<Post> arrayList, Context context){
            this.arrayList = arrayList;
            this.context = context;
        }
    @NonNull
    @Override
    public find_PostAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        find_PostAdapter.CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Post post = arrayList.get(position);

        holder.item_post_ObjNum.setText(arrayList.get(position).getItem_post_ObjNum());
        Glide.with(holder.item_post_image).load(arrayList.get(position).getItem_post_image()).into(holder.item_post_image);
        holder.L_category.setText(arrayList.get(position).getL_category());
        holder.M_category.setText(arrayList.get(position).getM_category());
        holder.item_post_title.setText(arrayList.get(position).getItem_post_title());
        holder.item_post_date.setText(arrayList.get(position).getItem_post_date());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getAdapterPosition();
                Intent intent = new Intent(context, Find_Object_detail.class);
                intent.putExtra("ObjNum", arrayList.get(mPosition).getItem_post_ObjNum());
                context.startActivity(intent);
            }
        });
    }


        @Override
        public int getItemCount() {
            return (null != arrayList ? arrayList.size() : 0);
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            protected ImageView item_post_image;
            protected TextView L_category;
            protected TextView M_category;
            protected TextView item_post_title;
            protected TextView item_post_date;
            protected TextView item_post_ObjNum;

            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);

                this.item_post_ObjNum = itemView.findViewById(R.id.item_post_ObjNum);
                this.item_post_image = (ImageView) itemView.findViewById(R.id.item_post_image);
                this.L_category = (TextView) itemView.findViewById(R.id.L_category);
                this.M_category = (TextView) itemView.findViewById(R.id.M_category);
                this.item_post_title = (TextView) itemView.findViewById(R.id.item_post_title);
                this.item_post_date = (TextView) itemView.findViewById(R.id.item_post_date);
            }
        }
    }



