package net.flow9.dcjt.firebase;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class indexActivity extends AppCompatActivity {
    private BottomNavigationView bottom_Navigatrion_View; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private mainActivity ma;
    private chatActivity ca;
    private mapActivity  mapa;
    private myPageActivity mypa;
    private myPageActivity2 mypa2;
    private FirebaseAuth mFirebaseAuth; // 파이어 베이스 인증


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if(user != null){
            startToast("자동 로그인 : " + user.getUid());
        }
    }

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mFirebaseAuth = FirebaseAuth.getInstance();

        bottom_Navigatrion_View = findViewById(R.id.bottomNavi);
        bottom_Navigatrion_View.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_home :
                        setFrag(0);
                        break;
                    case R.id.action_chatlist :
                        setFrag(1);
                        break;
                    case R.id.action_map :
                        setFrag(2);
                        break;
                    case R.id.action_MyPage:
                        if(user == null){
                            setFrag(3);
                        } else {
                            setFrag(4);
                        }
                        break;
                }
                return true;
            }
        });
        ma = new mainActivity();
        ca = new chatActivity();
        mapa = new mapActivity();
        mypa = new myPageActivity();
        mypa2 = new myPageActivity2();
        setFrag(0);
    }

    // 프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n){
            case 0:
                ft.replace(R.id.main_frame, ma);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, ca);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, mapa);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, mypa);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.main_frame, mypa2);
                ft.commit();
                break;
        }
    }
    @Override public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
