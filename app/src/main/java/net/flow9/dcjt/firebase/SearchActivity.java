package net.flow9.dcjt.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.naver.maps.geometry.LatLng;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new BridgeInterface(), "Android");
        myApplication = (MyApplication) getApplicationContext();

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                // Android -> javascript 함수 호출
                webView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        // 최초 웹뷰 로드
        webView.loadUrl("https://fir-d8d40.web.app");
    }

    public class BridgeInterface {

        public String selectedAddress; // 선택한 주소 저장 변수

        @JavascriptInterface
        public void processDATA(String data){
            // 다음 주소 검색 API의 결과 값이 브릿지 통로를 통해 전달 받는다. (from Javascript)
            String processedData = extractDongFromAddress(data);
            myApplication.setSelectedAddress(data);
            // 주소를 실제 위도와 경도로 변환
            LatLng latLng = convertAddressToLatLng(processedData);
            if (latLng != null) {
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;

                // 위도와 경도 값을 Intent에 추가하여 mapActivity로 전달
                Intent intent = new Intent();
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);

                // 주소 값을 설정하여 전달
                intent.putExtra("data", data);
                setResult(RESULT_OK, intent);

                finish();
            }
        }

        public void setSelectedAddress(String address) {
            selectedAddress = address;
        }
        public String getSelectedAddress() {
            return selectedAddress;
        }

    }

    private LatLng convertAddressToLatLng(String address) {
        try {
            Geocoder geocoder = new Geocoder(SearchActivity.this);
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (!addresses.isEmpty()) {
                Address firstAddress = addresses.get(0);
                double latitude = firstAddress.getLatitude();
                double longitude = firstAddress.getLongitude();
                return new LatLng(latitude, longitude);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String extractDongFromAddress(String address) {
        // 주소 문자열에서 "동" 저보 추출
        int startIndex = address.indexOf("구") + 1;
        int endIndex = address.indexOf("동") + 1;
        if (startIndex > 0 && endIndex > 0 && endIndex > startIndex) {
            return address.substring(startIndex, endIndex);
        }
        return address;
    }

}