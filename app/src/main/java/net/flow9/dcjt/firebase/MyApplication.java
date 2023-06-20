package net.flow9.dcjt.firebase;

import android.app.Application;

public class MyApplication extends Application {
    private String selectedAddress; // 선택한 주소 저장 변수

    public String getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(String address) {
        selectedAddress = address;
    }
}
