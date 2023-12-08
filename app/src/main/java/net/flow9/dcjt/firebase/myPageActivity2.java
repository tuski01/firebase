package net.flow9.dcjt.firebase;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;


public class myPageActivity2 extends Fragment implements View.OnClickListener {
    private View view;
    private TextView btn_logout, nickname, find_Object_load, lost_Object_load, QnA, CS, Inquiry, User_information_update, app_setting;
    public String user_uid, img_name;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseUser firebaseUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_mypage2, container, false);

        mfirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mfirebaseAuth.getCurrentUser();


        find_Object_load = view.findViewById(R.id.find_Object_load);
        lost_Object_load = view.findViewById(R.id.lost_Object_load);
        QnA = view.findViewById(R.id.QnA);
        CS = view.findViewById(R.id.CS);
        Inquiry = view.findViewById(R.id.Inquiry);
        User_information_update = view.findViewById(R.id.User_information_update);
        app_setting = view.findViewById(R.id.app_setting);
        btn_logout = view.findViewById(R.id.btn_logout);
        nickname = view.findViewById(R.id.nickname);

        nickname = (TextView) view.findViewById(R.id.nickname);
        String userNickname = firebaseUser.getUid();
        nickname.setText(userNickname);


        find_Object_load.setOnClickListener(this);
        lost_Object_load.setOnClickListener(this);
        QnA.setOnClickListener(this);
        QnA.setOnClickListener(this);
        CS.setOnClickListener(this);
        Inquiry.setOnClickListener(this);
        User_information_update.setOnClickListener(this);
        app_setting.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        return view;


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.find_Object_load:
                Intent intent = new Intent(getActivity(), find_object_load.class);
                startActivity(intent);
                break;
            case R.id.lost_Object_load:
                Intent intent1 = new Intent(getActivity(), lost_object_load.class);
                startActivity(intent1);
                break;
            case R.id.btn_logout:
                indexActivity.userNickname = null;
                Intent intent3 = new Intent(getActivity(), indexActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
