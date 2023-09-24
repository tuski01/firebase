package net.flow9.dcjt.firebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {


    private EditText E_Email, E_Pass, E_Nickname, E_Password_check, E_Phone, E_Name, E_Address;      // 로그인 입력필드



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        E_Email = findViewById(R.id.e_email);
        E_Pass = findViewById(R.id.e_password);
        E_Nickname = findViewById(R.id.e_nickname);
        E_Password_check = findViewById(R.id.e_password_check);
        E_Phone = findViewById(R.id.e_phone);
        E_Address = findViewById(R.id.e_address);
        E_Name = findViewById(R.id.e_name);



        Button b_confirm = findViewById(R.id.b_confirm);
        b_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                // 회원가입 처리 시작
                String userID = E_Email.getText().toString();
                String userPw =  E_Pass.getText().toString();
                String userNickname = E_Nickname.getText().toString();
                String userPhone = E_Phone.getText().toString();
                String userAddress = E_Address.getText().toString();
                String userName = E_Name.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "회원 등록에 실패", Toast.LENGTH_SHORT).show();
                                return ;
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userID, userPw, userNickname,userPhone, userAddress,userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }

        });
    }

}
