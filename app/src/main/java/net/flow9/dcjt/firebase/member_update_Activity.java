package net.flow9.dcjt.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class member_update_Activity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_update);

        findViewById(R.id.check_button).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = (v) -> {
        switch(v.getId()){
            case R.id.check_button :
                member_update();
                break;
        }
    };
    private void member_update(){
        String ePhone = ((EditText)findViewById(R.id.e_phone)).getText().toString();
        String eDate = ((EditText)findViewById(R.id.e_date)).getText().toString();
        String eAddress = ((EditText)findViewById(R.id.e_address)).getText().toString();


        if(ePhone.length() > 0 && eDate.length() > 0 && eAddress.length() > 0) {

            } else {
                startToast("회원정보를 입력해주세요");
            }
        }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
