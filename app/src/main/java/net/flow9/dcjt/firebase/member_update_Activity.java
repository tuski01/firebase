package net.flow9.dcjt.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class member_update_Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_update);

        findViewById(R.id.password_reset).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = (v) -> {
        switch(v.getId()){
            case R.id.password_reset:
                password_reset();
                break;
        }
    };

    public void password_reset(){
        Intent intent = new Intent(member_update_Activity.this, password_reset_Activity.class);
        startActivity(intent);
        finish();
    }
}
