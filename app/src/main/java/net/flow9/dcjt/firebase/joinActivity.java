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
        private EditText E_Email, E_Pass, E_PassCheck;      // 로그인 입력필드

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_join);

            mFirebaseAuth = FirebaseAuth.getInstance();
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("firebase");

            E_Email = findViewById(R.id.e_email);
            E_Pass = findViewById(R.id.e_password);
            E_PassCheck = findViewById(R.id.e_passcheck);

            Button b_confirm = findViewById(R.id.b_confirm);
            b_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 회원가입 처리 시작
                    String strEmail = E_Email.getText().toString();
                    String strPwd = E_Pass.getText().toString();

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

                                // setValue : database에 insert 하는 행위1
                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                Toast.makeText(joinActivity.this, "회원 가입 완료", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(joinActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(joinActivity.this, "회원 가입 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }


    }