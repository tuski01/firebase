package net.flow9.dcjt.firebase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.flow9.dcjt.firebase.model.Post;

import java.io.IOException;

public class joinActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어 베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText E_Email, E_Pass, E_Nickname, E_Password_check, E_Phone, E_Name, E_Address;      // 로그인 입력필드


    private int REQUEST_IMAGE_CODE = 1001;
    private int REQUEST_EXTERNAL_STORAGE_PERMISSIONS = 1002;
    private ImageView profile_image_button;
    private Uri image;
    private UserAccount userAccount = new UserAccount();


    private Button confirm_btn;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorageRef;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("firebase");

        E_Email = findViewById(R.id.e_email);
        E_Pass = findViewById(R.id.e_password);
        E_Nickname = findViewById(R.id.e_nickname);
        E_Password_check = findViewById(R.id.e_password_check);
        E_Phone = findViewById(R.id.e_phone);
        E_Address = findViewById(R.id.e_address);
        E_Name = findViewById(R.id.e_name);
        profile_image_button = findViewById(R.id.profile_img_button);

        mStorageRef = FirebaseStorage.getInstance().getReference("firebase");



        if(ContextCompat.checkSelfPermission(joinActivity.this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_PERMISSIONS);
            }
        } else {

        }

        // 이미지 추가를 위해 ImageView 선택시 일어나는 Event
        profile_image_button.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_CODE);
            }
        });


        Button b_confirm = findViewById(R.id.b_confirm);
        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 시작
                String strEmail = E_Email.getText().toString();
                String strPwd = E_Pass.getText().toString();
                String strPwdCheck = E_Password_check.getText().toString();
                String strName = E_Name.getText().toString();
                String strPhone = E_Phone.getText().toString();
                String strAddress = E_Address.getText().toString();
                String strNick = E_Nickname.getText().toString();


                if (strEmail.length() > 0 && strPwd.length() > 0 && strPwdCheck.length() > 0 && strNick.length() > 0 && strName.length() > 0 && strPhone.length() > 9 && strAddress.length() > 0) {
                    if (strPwd.equals(strPwdCheck)) {
                        // Firebase Auth 진행
                        mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(joinActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                    userAccount.setIdToken(firebaseUser.getUid());
                                    userAccount.setEmailId(firebaseUser.getEmail());
                                    userAccount.setPassword(strPwd);
                                    userAccount.setNickname(strNick);
                                    userAccount.setName(strName);
                                    userAccount.setAddress(strAddress);
                                    userAccount.setPhone(strPhone);


                                    // setValue : database에 insert 하는 행위1
                                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(userAccount);
                                    Intent intent = new Intent(joinActivity.this, uploadActivity.class);
                                    startActivity(intent);
                                } else {
                                    if (task.getException() != null) {

                                    }
                                }
                            }
                        });
                    } else {
                    }
                } else {

                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CODE) {
            image = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
                profile_image_button.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            StorageReference riversRef = mStorageRef.child("Find_Post").child("Find_images" + image.getLastPathSegment());
            riversRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String upload = taskSnapshot.getUploadSessionUri().toString();
                    String uploadId = mDatabaseRef.push().getKey();
                    userAccount.setImage(uploadId);
                    userAccount.setImgUrl(upload);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        } else {
        }
    }




}
