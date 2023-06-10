package net.flow9.dcjt.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class joinActivity extends AppCompatActivity {

        private FirebaseAuth mFirebaseAuth; // 파이어 베이스 인증
        private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
        private EditText E_Email, E_Pass, E_Nickname, E_Password_check, E_Phone, E_Name, E_Address;      // 로그인 입력필드

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

                                        UserAccount account = new UserAccount();
                                        account.setIdToken(firebaseUser.getUid());
                                        account.setEmailId(firebaseUser.getEmail());
                                        account.setPassword(strPwd);
                                        account.setNickname(strNick);
                                        account.setName(strName);
                                        account.setAddress(strAddress);
                                        account.setPhone(strPhone);


                                        // setValue : database에 insert 하는 행위1
                                        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                                        Intent intent = new Intent(joinActivity.this, uploadActivity.class);
                                        startActivity(intent);
                                    } else {
                                        if (task.getException() != null) {
                                            startToast(task.getException().toString());
                                        }
                                    }
                                }
                            });
                        } else {
                            startToast("비밀번호가 일치 하지 않습니다.");
                        }
                    } else {
                        startToast("빈칸을 모두 채워주세요");
                    }
                }
            });
        }
        private void startToast(String msg){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

    }
