package net.flow9.dcjt.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText mEtEmail, mEtPwd;      // 로그인 입력필드



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEtEmail = findViewById(R.id.e_email);
        mEtPwd = findViewById(R.id.e_password);




        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 요청
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                try {
                    Toast.makeText(LoginActivity.this, "버튼눌림", Toast.LENGTH_SHORT).show();
                    String result;
                    Login_Request task = new Login_Request();

                    result = task.execute(strEmail, strPwd).get();
                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }

                Intent intent = new Intent(LoginActivity.this, mainActivity.class);
                startActivity(intent);


            }
        });


        Button btn_register = findViewById(R.id.btn_join);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, joinActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.pw_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PasswordResetActivity.class);
                startActivity(intent);
            }
        });
    }


    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}