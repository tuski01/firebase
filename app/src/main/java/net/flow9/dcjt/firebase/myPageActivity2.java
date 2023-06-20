package net.flow9.dcjt.firebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;


public class myPageActivity2 extends Fragment implements View.OnClickListener {
    private View view;
    private TextView btn_find_lost_Object_list, btn_qna, btn_member_update, btn_logout, nickname;
    private FirebaseAuth mFirebaseAuth; // 파이어 베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private StorageReference mStorageRef;  // 파이어베이스 스토리지
    private FirebaseUser user;
    public String user_uid, img_name;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_mypage2, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("firebase");
        mStorageRef = FirebaseStorage.getInstance().getReference("firebase");

        btn_find_lost_Object_list = view.findViewById(R.id.btn_find_lost_Object_list);
        btn_qna = view.findViewById(R.id.btn_qna);
        btn_member_update = view.findViewById(R.id.btn_member_update);
        btn_logout = view.findViewById(R.id.btn_logout);
        nickname = (TextView) view.findViewById(R.id.nickname);


        btn_find_lost_Object_list.setOnClickListener(this);
        btn_qna.setOnClickListener(this);
        btn_member_update.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        user = mFirebaseAuth.getCurrentUser();
        user_uid = user.getUid();

        mDatabaseRef.child("UserAccount").child(user_uid).addListenerForSingleValueEvent(new ValueEventListener() {   //1회에 한해 데이터를 읽어옴
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount userAccount = snapshot.getValue(UserAccount.class);                   //UserInfo Class 에 User정보 저장
                if (userAccount == null) {
                    //데이터없음
                } else {
                    String temp = userAccount.getNickname();
                    nickname.setText(temp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.btn_logout:
                FirebaseAuth.getInstance().signOut();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.main_frame, new myPageActivity());
                ft.commit();
                break;
        }
    }
}