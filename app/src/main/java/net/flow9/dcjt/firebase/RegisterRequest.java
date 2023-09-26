package net.flow9.dcjt.firebase;


import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    // 서버 URL 설정
    final static private String URL = "http://144.24.94.84/test/Register.php";
    private Map<String, String> map;

    public RegisterRequest(String userID, String userPassword, String userName,  String userPhone, String userAddress,String userNickname,  Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("userPhone", userPhone);
        map.put("userAddress", userAddress);
        map.put("userNickname", userNickname);

    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
