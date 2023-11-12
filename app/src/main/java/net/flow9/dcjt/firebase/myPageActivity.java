package net.flow9.dcjt.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class myPageActivity extends Fragment implements View.OnClickListener{
    private View view;
    private TextView btn_login, nickname, find_Object_load, lost_Object_load, QnA, CS, Inquiry, User_information_update, app_setting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_mypage, container, false);


        find_Object_load = view.findViewById(R.id.find_Object_load);
        lost_Object_load = view.findViewById(R.id.lost_Object_load);
        QnA = view.findViewById(R.id.QnA);
        CS = view.findViewById(R.id.CS);
        Inquiry = view.findViewById(R.id.Inquiry);
        User_information_update = view.findViewById(R.id.User_information_update);
        app_setting = view.findViewById(R.id.app_setting);
        btn_login = view.findViewById(R.id.btn_login);
        nickname = view.findViewById(R.id.nickname);


        find_Object_load.setOnClickListener(this);
        lost_Object_load.setOnClickListener(this);
        QnA.setOnClickListener(this);
        QnA.setOnClickListener(this);
        CS.setOnClickListener(this);
        Inquiry.setOnClickListener(this);
        User_information_update.setOnClickListener(this);
        app_setting.setOnClickListener(this);
        btn_login.setOnClickListener(this);



        return view;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.find_Object_load:
                Intent intent = new Intent(getActivity(), find_object_load.class);
                startActivity(intent);
                break;
            case R.id.lost_Object_load:
                Intent intent1 = new Intent(getActivity(), lost_object_load.class);
                startActivity(intent1);
                break;
            case R.id.btn_login:
                Intent intent4 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent4);
                break;
        }
    }
}