package net.flow9.dcjt.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class chatActivity extends Fragment {
    private View view;
    private ImageView img;
    private TextView tv;
    private MyApplication myApplication;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_chat, container, false);

        img = view.findViewById(R.id.img);
        tv = view.findViewById(R.id.tv_address);

        myApplication = (MyApplication) getActivity().getApplication();
        String address = myApplication.getSelectedAddress();
        if (address != null) {
            tv.setText(address);
        }


        img.setOnClickListener(v ->{
            Intent intent1 = new Intent(getActivity(), chatRoomActivity.class);
            startActivity(intent1);
        });


        return view;
    }


}
