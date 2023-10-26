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


import org.w3c.dom.Text;


public class myPageActivity2 extends Fragment implements View.OnClickListener {
    private View view;
    private TextView btn_find_lost_Object_list, btn_qna, btn_member_update, btn_logout, nickname;
    public String user_uid, img_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_mypage2, container, false);


//        btn_find_lost_Object_list = view.findViewById(R.id.btn_find_lost_Object_list);
//        btn_qna = view.findViewById(R.id.btn_qna);
//        btn_member_update = view.findViewById(R.id.btn_member_update);
        btn_logout = view.findViewById(R.id.btn_logout);

        nickname = (TextView) view.findViewById(R.id.nickname);
        String userNickname = indexActivity.userNickname;
        nickname.setText(userNickname);


//        btn_find_lost_Object_list.setOnClickListener(this);
//        btn_qna.setOnClickListener(this);
//        btn_member_update.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        return view;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btn_find_lost_Object_list:
//                Intent intent = new Intent(getActivity(), find_lost_Activity.class);
//                startActivity(intent);
//                break;
//            case R.id.btn_qna:
//                Intent intent1 = new Intent(getActivity(), qnaActivity.class);
//                startActivity(intent1);
//                break;
//            case R.id.btn_member_update:
//                Intent intent2 = new Intent(getActivity(), member_update_Activity.class);
//                startActivity(intent2);
//                break;
            case R.id.btn_logout:
                indexActivity.userNickname = null;
                Intent intent3 = new Intent(getActivity(), indexActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
