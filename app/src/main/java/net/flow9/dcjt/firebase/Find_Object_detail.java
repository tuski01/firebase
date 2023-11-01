package net.flow9.dcjt.firebase;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class Find_Object_detail extends AppCompatActivity {
    private ImageView find_object_img;
    private TextView find_object_title;
    private TextView find_object_Lcategory;
    private TextView find_object_Mcategory;
    private TextView find_object_date;
    private TextView find_object_content;
    String ObjNum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_object_detail);

        ObjNum = getIntent().getStringExtra("ObjNum");

        find_object_img = findViewById(R.id.find_object_img);
        find_object_title = findViewById(R.id.find_object_title);
        find_object_Lcategory = findViewById(R.id.find_object_Lcategory);
        find_object_Mcategory = findViewById(R.id.find_object_Mcategory);
        find_object_date = findViewById(R.id.find_object_date);
        find_object_content = findViewById(R.id.find_object_content);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        final String img = "http://49.50.175.166/images/findpost/" + jsonObject.getString("img");
                        final String Lcategory = jsonObject.getString("Lcategory");
                        final String Mcategory = jsonObject.getString("Mcategory");
                        final String title = jsonObject.getString("title");
                        final String finddate = jsonObject.getString("finddate");
                        final String content = jsonObject.getString("content");

                        Toast.makeText(getApplicationContext(), "로드 성공", Toast.LENGTH_SHORT).show();
                        Glide.with(find_object_img).load(img).into(find_object_img);
                        find_object_title.setText(title);
                        find_object_Lcategory.setText(Lcategory);
                        find_object_Mcategory.setText(Mcategory);
                        find_object_date.setText(finddate);
                        find_object_content.setText(content);

                        System.out.println(ObjNum);
                        System.out.println(img);
                        System.out.println(Lcategory);

                        } else {
                        Toast.makeText(getApplicationContext(), "로드 실패", Toast.LENGTH_SHORT).show();
                        return ;
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Find_Object_detail_Request detail_request = new Find_Object_detail_Request(ObjNum, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Find_Object_detail.this);
        queue.add(detail_request);
    }
}