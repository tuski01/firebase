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
import net.flow9.dcjt.firebase.adapters.find_PostAdapter;
import net.flow9.dcjt.firebase.adapters.lost_PostAdapter;
import net.flow9.dcjt.firebase.model.Post;
import java.util.ArrayList;
import java.util.List;
public class mainActivity extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView fPostRecyclerView, lPostRecyclerView;
    private find_PostAdapter fAdapter;
    private lost_PostAdapter lAdapter;
    private List<Post> mDatas1, mDatas2;


    private ExtendedFloatingActionButton fab_main, fab_sub1, fab_sub2;
    private Animation fab_open, fab_close;

    private String userId = indexActivity.userID;

    private boolean isFabOpen = false;
    private Context mContext;


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
    }
    public void lost_object_list(){

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
                if(userId != null) {
                    Intent intent = new Intent(getActivity(), Lost_Post_Activity.class);
                    startActivity(intent);
                } else {
                    Intent intent_start = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent_start);
                }
                break;
            case R.id.fab_sub2:
                toggleFab();
                if(userId != null) {

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