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
    private TextView btn_find_lost_Object_list, btn_qna, btn_member_update, btn_logout, btn_login, nickname;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_mypage, container, false);

        btn_find_lost_Object_list = view.findViewById(R.id.btn_find_lost_Object_list);
        btn_qna = view.findViewById(R.id.btn_qna);
        btn_member_update = view.findViewById(R.id.btn_member_update);
        btn_login = view.findViewById(R.id.btn_login);
        nickname = view.findViewById(R.id.nickname);

        btn_find_lost_Object_list.setOnClickListener(this);
        btn_qna.setOnClickListener(this);
        btn_member_update.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_find_lost_Object_list:
                Intent intent = new Intent(getActivity(), find_lost_Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_qna:
                Intent intent1 = new Intent(getActivity(), qnaActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_member_update:
                Intent intent2 = new Intent(getActivity(), member_update_Activity.class);
                startActivity(intent2);
                break;
            case R.id.btn_login:
                Intent intent4 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent4);
                break;
        }
    }
}