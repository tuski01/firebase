package net.flow9.dcjt.firebase;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;



public class Find_Object_detail extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView find_object_img;
    private TextView find_object_title;
    private TextView find_object_Lcategory;
    private TextView find_object_Mcategory;
    private TextView find_object_date;
    private TextView find_object_content;
    private TextView find_object_writer;
    String ObjNum;
    private Button chat_btn;
    private NaverMap mNaverMap;
    private MapView mapView;
    private String mapAddress;
    private String Objwriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_object_detail);
        NaverMapSdk.getInstance(getApplicationContext()).setClient(new NaverMapSdk.NaverCloudPlatformClient("0sjriog3ai"));

        ObjNum = getIntent().getStringExtra("ObjNum");
        Log.d("Find_Object_detail", "ObjNum: " + ObjNum);

        find_object_img = findViewById(R.id.find_object_img);
        find_object_title = findViewById(R.id.find_object_title);
        find_object_Lcategory = findViewById(R.id.find_object_Lcategory);
        find_object_Mcategory = findViewById(R.id.find_object_Mcategory);
        find_object_date = findViewById(R.id.find_object_date);
        find_object_content = findViewById(R.id.find_object_content);
        find_object_writer = findViewById(R.id.find_object_writer);
        chat_btn = findViewById(R.id.chat_btn);

        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(indexActivity.userID == null){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), chatRoomActivity.class);
                    intent.putExtra("find", "find");
                    intent.putExtra("ObjNum", ObjNum);
                    intent.putExtra("writer", find_object_writer.getText().toString());
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
                        final String img = "http://49.50.175.166/images/findpost/" + jsonObject.getString("img");
                        final String Lcategory = jsonObject.getString("Lcategory");
                        final String Mcategory = jsonObject.getString("Mcategory");
                        final String title = jsonObject.getString("title");
                        final String finddate = jsonObject.getString("finddate");
                        final String content = jsonObject.getString("content");
                        final String writer = jsonObject.getString("userId");
                        final String address = jsonObject.getString("address");

                        Toast.makeText(getApplicationContext(), "로드 성공", Toast.LENGTH_SHORT).show();
                        Glide.with(find_object_img).load(img).into(find_object_img);
                        find_object_title.setText(title);
                        find_object_Lcategory.setText(Lcategory);
                        find_object_Mcategory.setText(Mcategory);
                        find_object_date.setText(finddate);
                        find_object_content.setText(content);
                        find_object_writer.setText(writer);

                        mapAddress=(address);

                        Log.d("Find_Object_detail", "address: " + mapAddress);
                        System.out.println(writer);

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
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mNaverMap = naverMap;

        Log.d("Find_Object_detail", "onMapReady - mapAddress: " + mapAddress);
            LatLng location = convertAddressToLatLng(mapAddress);

            updateMapLocation(location);

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



//    private final ActivityResultLauncher<Intent> getSearchResult = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode() == RESULT_OK) {
//                    if (result.getData() != null) {
//                        double latitude = result.getData().getDoubleExtra("latitude2", 0.0);
//                        double longitude = result.getData().getDoubleExtra("longitude2", 0.0);
//                        String data = result.getData().getStringExtra("data2");
//
//
//                        mapAddress = data; // 검색한 주소를 변수에 저장
//
//
//                        updateMapLocation(new LatLng(latitude, longitude));
//                    }
//                }
//            }
//    );


    private void updateMapLocation(LatLng location) {
        if (mNaverMap != null) {
            mNaverMap.moveCamera(CameraUpdate.scrollTo(location));
            Marker marker = new Marker();
            marker.setPosition(location);
            marker.setMap(mNaverMap);
        }
    }
}