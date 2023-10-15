package net.flow9.dcjt.firebase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.flow9.dcjt.firebase.R;
import net.flow9.dcjt.firebase.model.Post;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class lost_PostAdapter extends RecyclerView.Adapter<lost_PostAdapter.CustomViewHolder> {

    private ArrayList<Post> arrayList;

    public lost_PostAdapter(ArrayList<Post> arrayList){
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public lost_PostAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull lost_PostAdapter.CustomViewHolder holder, int position) {
        Glide.with(holder.item_post_image).load(arrayList.get(position).getItem_post_image()).into(holder.item_post_image);
        holder.L_category.setText(arrayList.get(position).getL_category());
        holder.M_category.setText(arrayList.get(position).getM_category());
        holder.item_post_title.setText(arrayList.get(position).getItem_post_title());
        holder.item_post_date.setText(arrayList.get(position).getItem_post_date());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.item_post_title.getText().toString();
                Toast.makeText(view.getContext(), curName, Toast.LENGTH_SHORT).show();
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

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.item_post_image = (ImageView) itemView.findViewById(R.id.item_post_image);
            this.L_category = (TextView) itemView.findViewById(R.id.L_category);
            this.M_category = (TextView) itemView.findViewById(R.id.M_category);
            this.item_post_title = (TextView) itemView.findViewById(R.id.item_post_title);
            this.item_post_date = (TextView) itemView.findViewById(R.id.item_post_date);
        }
    }
}

