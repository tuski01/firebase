package net.flow9.dcjt.firebase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.flow9.dcjt.firebase.R;
import net.flow9.dcjt.firebase.model.Post;

import org.w3c.dom.Text;

import java.util.List;

public class lost_PostAdapter extends RecyclerView.Adapter<lost_PostAdapter.PostViewHolder> {

    private List<Post> datas;
    private FirebaseStorage storage= FirebaseStorage.getInstance();
    private StorageReference stoRef = storage.getReference();



    public lost_PostAdapter(List<Post> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post data = datas.get(position);
        String sdate = String.valueOf(data.getDate());
        holder.title.setText(data.getTitle());
        holder.date.setText(sdate);
        holder.L_category.setText(data.getL_category());
        holder.M_category.setText(data.getM_category());
        Glide.with(holder.itemView).load(data.getmImageUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private TextView L_category;
        private TextView M_category;
        private ImageView image;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_post_image);
            L_category = itemView.findViewById(R.id.L_category);
            M_category = itemView.findViewById(R.id.M_category);
            title = itemView.findViewById(R.id.item_post_title);
            date = itemView.findViewById(R.id.item_post_date);
        }
    }


}
