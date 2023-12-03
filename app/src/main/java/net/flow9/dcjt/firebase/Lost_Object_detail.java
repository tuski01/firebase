package net.flow9.dcjt.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import net.flow9.dcjt.firebase.adapters.find_PostAdapter;
import net.flow9.dcjt.firebase.adapters.lost_PostAdapter;
import net.flow9.dcjt.firebase.model.Post;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Lost_Object_detail extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView lost_object_img;
    private TextView lost_object_title;
    private TextView lost_object_Lcategory;
    private TextView lost_object_Mcategory;
    private TextView lost_object_date;
    private TextView lost_object_content;
    private TextView lost_object_writer;
    String ObjNum;

    private NaverMap mNaverMap;
    private MapView mapView;
    private String mapAddress;
    private Button Chat_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_object_detail);
        NaverMapSdk.getInstance(getApplicationContext()).setClient(new NaverMapSdk.NaverCloudPlatformClient("0sjriog3ai"));


        ObjNum = getIntent().getStringExtra("ObjNum");

        lost_object_img = findViewById(R.id.lost_object_img);
        lost_object_title = findViewById(R.id.lost_object_title);
        lost_object_Lcategory = findViewById(R.id.lost_object_Lcategory);
        lost_object_Mcategory = findViewById(R.id.lost_object_Mcategory);
        lost_object_date = findViewById(R.id.lost_object_date);
        lost_object_content = findViewById(R.id.lost_object_content);
        lost_object_writer = findViewById(R.id.lost_object_writer);
        Chat_Btn = findViewById(R.id.chat_btn);


        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        Chat_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(indexActivity.userID == null){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), chatRoomActivity.class);
                    intent.putExtra("writer", lost_object_writer.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
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
                        final String writer = jsonObject.getString("userId");
                        final String address = jsonObject.getString("address");

                        Toast.makeText(getApplicationContext(), "로드 성공", Toast.LENGTH_SHORT).show();
                        Glide.with(lost_object_img).load(img).into(lost_object_img);
                        lost_object_title.setText(title);
                        lost_object_Lcategory.setText(Lcategory);
                        lost_object_Mcategory.setText(Mcategory);
                        lost_object_date.setText(lostdate);
                        lost_object_content.setText(content);
                        lost_object_writer.setText(writer);
                        mapAddress=(address);

                        Log.d("Find_Object_detail", "address: " + mapAddress);
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

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    private LatLng convertAddressToLatLng(String address) {
        try{
            Geocoder geocoder = new Geocoder(getApplicationContext());
            // 주소를 좌표로 변환
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (!addresses.isEmpty()) {
                Address firstAddress = addresses.get(0);
                double latitude = firstAddress.getLatitude();
                double longitude = firstAddress.getLongitude();
                return new LatLng(latitude, longitude);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateMapLocation(LatLng location) {
        if (mNaverMap != null) {
            mNaverMap.moveCamera(CameraUpdate.scrollTo(location));
            Marker marker = new Marker();
            marker.setPosition(location);
            marker.setMap(mNaverMap);
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mNaverMap = naverMap;
        String address = mapAddress;
        LatLng latLng = convertAddressToLatLng(address);
        if (latLng != null) {
            Marker marker = new Marker();
            marker.setPosition(latLng);
            marker.setMap(mNaverMap);
            naverMap.moveCamera(CameraUpdate.scrollTo(latLng));
        }
    }
}