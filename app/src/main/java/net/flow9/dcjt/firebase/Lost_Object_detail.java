package net.flow9.dcjt.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;


public class Lost_Object_detail extends AppCompatActivity {
    private ImageView lost_object_img;
    private TextView lost_object_title;
    private TextView lost_object_Lcategory;
    private TextView lost_object_Mcategory;
    private TextView lost_object_date;
    private TextView lost_object_content;
    String ObjNum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_object_detail);

        ObjNum = getIntent().getStringExtra("ObjNum");

        lost_object_img = findViewById(R.id.lost_object_img);
        lost_object_title = findViewById(R.id.lost_object_title);
        lost_object_Lcategory = findViewById(R.id.lost_object_Lcategory);
        lost_object_Mcategory = findViewById(R.id.lost_object_Mcategory);
        lost_object_date = findViewById(R.id.lost_object_date);
        lost_object_content = findViewById(R.id.lost_object_content);

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
                        final String img = "http://49.50.175.166/images/lostpost/" + jsonObject.getString("img");
                        final String Lcategory = jsonObject.getString("Lcategory");
                        final String Mcategory = jsonObject.getString("Mcategory");
                        final String title = jsonObject.getString("title");
                        final String lostdate = jsonObject.getString("lostdate");
                        final String content = jsonObject.getString("content");

                        Toast.makeText(getApplicationContext(), "로드 성공", Toast.LENGTH_SHORT).show();
                        Glide.with(lost_object_img).load(img).into(lost_object_img);
                        lost_object_title.setText(title);
                        lost_object_Lcategory.setText(Lcategory);
                        lost_object_Mcategory.setText(Mcategory);
                        lost_object_date.setText(lostdate);
                        lost_object_content.setText(content);

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
        Lost_Object_detail_Request detail_request = new Lost_Object_detail_Request(ObjNum, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Lost_Object_detail.this);
        queue.add(detail_request);
    }
}