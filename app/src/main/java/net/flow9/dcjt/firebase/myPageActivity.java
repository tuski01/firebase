package net.flow9.dcjt.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class myPageActivity extends Fragment implements View.OnClickListener{
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_mypage, container, false);

        Button btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);



        return view;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_login:
            {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                // 현재 액티비티 종료
            }
        }
    }
}