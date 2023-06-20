package net.flow9.dcjt.firebase;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import net.flow9.dcjt.firebase.adapters.find_PostAdapter;
import net.flow9.dcjt.firebase.adapters.lost_PostAdapter;
import net.flow9.dcjt.firebase.model.Post;
import java.util.ArrayList;
import java.util.List;
public class mainActivity extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseStorage mStore = FirebaseStorage.getInstance();
    private ChildEventListener mChild;
    private View view;
    private RecyclerView fPostRecyclerView, lPostRecyclerView;
    private find_PostAdapter fAdapter;
    private lost_PostAdapter lAdapter;
    private List<Post> mDatas1, mDatas2;
    private ExtendedFloatingActionButton fab_main, fab_sub1, fab_sub2;
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;
    private Context mContext;
    private FirebaseUser user = mAuth.getCurrentUser();

    private GridLayoutManager layoutmaneger1, layoutmaneger2;

    private TextView tv;
    private MyApplication myApplication;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);

        tv = view.findViewById(R.id.tv_address);
        myApplication = (MyApplication) getActivity().getApplication();
        String address = myApplication.getSelectedAddress();
        if (address != null) {
            tv.setText(address);
        }


        mContext = view.getContext();
        layoutmaneger1 = new GridLayoutManager(getActivity(), 2);
        layoutmaneger1.setReverseLayout(true);
        layoutmaneger2 = new GridLayoutManager(getActivity(), 2);
        layoutmaneger2.setReverseLayout(true);


        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
        fab_main =  view.findViewById(R.id.fab_main);
        fab_sub1 =  view.findViewById(R.id.fab_sub1);
        fab_sub2 =  view.findViewById(R.id.fab_sub2);
        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);
        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);
        find_object_list();
        lost_object_list();
        return view;
    }


    public void find_object_list(){
        Query myTopPostsQuery = mDatabase.child("firebase").child("Find_object").limitToLast(4);
        fPostRecyclerView = view.findViewById(R.id.main_recycleview);
        fPostRecyclerView.setLayoutManager(layoutmaneger1);
        mDatas1 = new ArrayList<>();
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDatas1.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post postdata = dataSnapshot.getValue(Post.class);
                    mDatas1.add(postdata);
                }
                fAdapter = new find_PostAdapter(mDatas1);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                fPostRecyclerView.setLayoutManager(gridLayoutManager);
                fPostRecyclerView.setAdapter(fAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void lost_object_list(){
        Query myTopPostsQuery = mDatabase.child("firebase").child("Lost_object").limitToLast(4);
        lPostRecyclerView = view.findViewById(R.id.main_recycleview2);
        lPostRecyclerView.setLayoutManager(layoutmaneger2);
        mDatas2 = new ArrayList<>();
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDatas2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post postdata = dataSnapshot.getValue(Post.class);
                    mDatas2.add(postdata);
                }
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                fPostRecyclerView.setLayoutManager(gridLayoutManager);
                lAdapter = new lost_PostAdapter(mDatas2);
                lPostRecyclerView.setAdapter(lAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_main:
                toggleFab();
                break;
            case R.id.fab_sub1:
                toggleFab();
                if(user != null) {
                    Intent intent = new Intent(getActivity(), Lost_Post_Activity.class);
                    startActivity(intent);
                } else {
                    Intent intent_start = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent_start);
                }
                break;
            case R.id.fab_sub2:
                toggleFab();
                if(user != null) {

                    Intent intent2 = new Intent(getActivity(), Find_Post_Activity.class);
                    startActivity(intent2);
                }else {
                    Intent intent_start2 = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent_start2);
                }
                break;
        }
    }
    private void toggleFab() {
        if (isFabOpen) {
            fab_sub1.startAnimation(fab_close);
            fab_sub2.startAnimation(fab_close);
            fab_sub1.setClickable(false);
            fab_sub2.setClickable(false);
            isFabOpen = false;
        } else {
            fab_sub1.startAnimation(fab_open);
            fab_sub2.startAnimation(fab_open);
            fab_sub1.setClickable(true);
            fab_sub2.setClickable(true);
            isFabOpen = true;
        }
    }
}