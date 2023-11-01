package net.flow9.dcjt.firebase;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import net.flow9.dcjt.firebase.adapters.find_PostAdapter;
import net.flow9.dcjt.firebase.adapters.lost_PostAdapter;
import net.flow9.dcjt.firebase.model.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class mainActivity extends Fragment implements View.OnClickListener {
    private View view;

    private ExtendedFloatingActionButton fab_main, fab_sub1, fab_sub2;
    private Animation fab_open, fab_close;

    private String userId = indexActivity.userID;

    private boolean isFabOpen = false;
    private Context mContext;

    // 리사이클러뷰 관련
    private ArrayList<Post> postlist;
    private ArrayList<Post> postlist2;
    private lost_PostAdapter lost_adapter;
    private find_PostAdapter find_adapter;
    private RecyclerView lostpost_recyclerview;
    private RecyclerView findpost_recyclerview;
    private RecyclerView.LayoutManager layoutManager1, layoutManager2;
    private RecyclerView.Adapter adapter1, adapter2;


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

        findpost_recyclerview = (RecyclerView) view.findViewById(R.id.findpost_list);
        layoutManager1 = new LinearLayoutManager(getActivity());
        findpost_recyclerview.setLayoutManager(layoutManager1);


        lostpost_recyclerview = (RecyclerView) view.findViewById(R.id.lostpost_list);
        layoutManager2 = new LinearLayoutManager(getActivity());
        lostpost_recyclerview.setLayoutManager(layoutManager2);



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

        return view;
    }


    public void find_object_list(){
        postlist = new ArrayList<>();
        find_adapter = new find_PostAdapter(postlist, getContext());
        findpost_recyclerview.setAdapter(find_adapter);
        new find_BackgroundTask().execute();
        find_adapter.notifyDataSetChanged();

    }
    public void lost_object_list(){
        postlist2 = new ArrayList<>();
        lost_adapter = new lost_PostAdapter(postlist2, getContext());
        lostpost_recyclerview.setAdapter(lost_adapter);
        new lost_BackgroundTask().execute();
        lost_adapter.notifyDataSetChanged();
    }


    @Override
    public void onStart() {
        super.onStart();
        find_object_list();
        lost_object_list();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    class lost_BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        protected void onPreExecute(){
            target = "http://49.50.175.166/lostpost_load.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String ObjNum, img, L_category, M_category, title, date;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    ObjNum = object.getString("ObjNum");
                    img = "http://49.50.175.166/images/lostpost/" + object.getString("img");
                    L_category = object.getString("Lcategory");
                    M_category = object.getString("Mcategory");
                    title = object.getString("title");
                    date =  object.getString("uploadDate");
                    Post post = new Post(ObjNum, img, L_category, M_category, title, date);
                    postlist2.add(post);
                    count++;
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            lost_adapter.notifyDataSetChanged();
        }
    }

    class find_BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        protected void onPreExecute(){
            target = "http://49.50.175.166/findpost_load.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String ObjNum, img, L_category, M_category, title, date;
                while(count < jsonArray.length()){
                    JSONObject object2 = jsonArray.getJSONObject(count);
                    ObjNum = object2.getString("ObjNum");
                    img = "http://49.50.175.166/images/findpost/" + object2.getString("img");
                    L_category = object2.getString("Lcategory");
                    M_category = object2.getString("Mcategory");
                    title = object2.getString("title");
                    date =  object2.getString("uploadDate");
                    Post post = new Post(ObjNum, img, L_category, M_category, title, date);
                    postlist.add(post);
;
                    count++;
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            find_adapter.notifyDataSetChanged();
        }
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