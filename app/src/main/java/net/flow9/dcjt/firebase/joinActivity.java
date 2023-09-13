package net.flow9.dcjt.firebase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import net.flow9.dcjt.firebase.model.Post;

import java.io.IOException;

public class joinActivity extends AppCompatActivity {


    private EditText E_Email, E_Pass, E_Nickname, E_Password_check, E_Phone, E_Name, E_Address;      // 로그인 입력필드


    private int REQUEST_IMAGE_CODE = 1001;
    private int REQUEST_EXTERNAL_STORAGE_PERMISSIONS = 1002;
    private ImageView profile_image_button;
    private Uri image;
    private UserAccount userAccount = new UserAccount();


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
        profile_image_button = findViewById(R.id.profile_img_button);



        Button b_confirm = findViewById(R.id.b_confirm);
        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 시작
                try {
                    Toast.makeText(joinActivity.this, "버튼눌림", Toast.LENGTH_SHORT).show();
                    String result;
                    String strEmail = E_Email.getText().toString();
                    String strPwd = E_Pass.getText().toString();
                    String strPwdCheck = E_Password_check.getText().toString();
                    String strName = E_Name.getText().toString();
                    String strPhone = E_Phone.getText().toString();
                    String strAddress = E_Address.getText().toString();
                    String strNick = E_Nickname.getText().toString();
                    RegisterActivity task = new RegisterActivity();

                    result = task.execute(strEmail, strPwd, strName, strPhone, strAddress, strNick).get();
                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }

                Intent intent = new Intent(joinActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
