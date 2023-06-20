package net.flow9.dcjt.firebase;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.naver.maps.geometry.Coord;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.util.List;

public class mapActivity extends Fragment implements OnMapReadyCallback, View.OnClickListener{
    private View view;
    private MapView mapView;
    private ExtendedFloatingActionButton fab_main, fab_sub1, fab_sub2;
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;
    private Context mContext;

    private TextView mtvAddress;
    private NaverMap mNaverMap;
    private static final int SEARCH_REQUEST_CODE = 1;
    private ImageView search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_map, container, false);

        NaverMapSdk.getInstance(requireContext()).setClient(new NaverMapSdk.NaverCloudPlatformClient("0sjriog3ai"));

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        mContext = view.getContext();

        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);

        fab_main =  view.findViewById(R.id.fab_main);
        fab_sub1 =  view.findViewById(R.id.fab_sub1);
        fab_sub2 =  view.findViewById(R.id.fab_sub2);

        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);

        mtvAddress = view.findViewById(R.id.tv_address);
        mtvAddress.setFocusable(false);

        search = view.findViewById(R.id.search);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);

        search.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getSearchResult.launch(intent);
        });
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        return view;
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
        String address = mtvAddress.getText().toString();
        LatLng latLng = convertAddressToLatLng(address);
        if (latLng != null) {
            Marker marker = new Marker();
            marker.setPosition(latLng);
            marker.setMap(mNaverMap);
            naverMap.moveCamera(CameraUpdate.scrollTo(latLng));
        }
    }

    private LatLng convertAddressToLatLng(String address) {
        try{
            Geocoder geocoder = new Geocoder(requireContext());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_main:
                toggleFab();
                break;
            case R.id.fab_sub1:
                toggleFab();
                Intent intent = new Intent(getActivity(), Lost_Post_Activity.class);
                startActivity(intent);
                break;
            case R.id.fab_sub2:
                toggleFab();
                Intent intent2 = new Intent(getActivity(), Find_Post_Activity.class);
                startActivity(intent2);
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

    private final ActivityResultLauncher<Intent> getSearchResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        double latitude = result.getData().getDoubleExtra("latitude", 0.0);
                        double longitude = result.getData().getDoubleExtra("longitude", 0.0);
                        String data = result.getData().getStringExtra("data");

                        mtvAddress.setText(data);


                        updateMapLocation(new LatLng(latitude, longitude));
                    }
                }
            }
    );
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SEARCH_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
//            double latitude = data.getDoubleExtra("latitude", 0.0);
//            double longitude = data.getDoubleExtra("longitude", 0.0);
//            String address = data.getStringExtra("selectedAddress");
//
//            mEtAddress.setText(address);
//            LatLng latLng = new LatLng(latitude, longitude);
//            updateMapLocation(latLng);
//        }
//    }



    private void updateMapLocation(LatLng location) {
        if (mNaverMap != null) {
            mNaverMap.moveCamera(CameraUpdate.scrollTo(location));
            Marker marker = new Marker();
            marker.setPosition(location);
            marker.setMap(mNaverMap);
        }
    }



}
