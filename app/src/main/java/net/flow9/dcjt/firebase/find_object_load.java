package net.flow9.dcjt.firebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import net.flow9.dcjt.firebase.adapters.find_PostAdapter;
import net.flow9.dcjt.firebase.model.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class find_object_load extends AppCompatActivity {

    private ArrayList<Post> postlist;
    private find_PostAdapter find_adapter;
    private RecyclerView find_object_list;
    private RecyclerView.LayoutManager LayoutManager;
    private String postData = indexActivity.userID;
    private TextView lostpost_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_object_load);

        lostpost_title = findViewById(R.id.lost_post_title);
        find_object_list = findViewById(R.id.find_object_list);
        LayoutManager = new LinearLayoutManager(getApplicationContext());
        find_object_list.setLayoutManager(LayoutManager);

        lostpost_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(find_object_load.this, lost_object_load.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        find_object_list();
    }

    public void find_object_list(){
        postlist = new ArrayList<>();
        find_adapter = new find_PostAdapter(postlist, getApplicationContext());
        find_object_list.setAdapter(find_adapter);
        new find_BackgroundTask().execute();
        find_adapter.notifyDataSetChanged();
    }

    class find_BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        protected void onPreExecute(){
            if(indexActivity.userID == null){
                Intent intent = new Intent(find_object_load.this, LoginActivity.class);
                startActivity(intent);
            } else if (indexActivity.userID.equals("qwer")) {
                target = "http://49.50.175.166/find_object_load/find_object_load_qwer.php";
            } else if (indexActivity.userID.equals("aaa")){
                target = "http://49.50.175.166/find_object_load/find_object_load_aaa.php";
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
                httpURLConnection.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");


                System.out.println(postData);


                byte[] data = postData.getBytes(StandardCharsets.UTF_8);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(data);
                os.close();

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

                    count++;
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            find_adapter.notifyDataSetChanged();
        }
    }
}