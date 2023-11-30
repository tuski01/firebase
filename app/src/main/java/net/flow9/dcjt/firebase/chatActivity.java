package net.flow9.dcjt.firebase;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.flow9.dcjt.firebase.adapters.ChatAdapter;
import net.flow9.dcjt.firebase.model.UserAccount;

import java.util.ArrayList;

public class chatActivity extends Fragment implements View.OnClickListener {
    private static final String TAG = "chatActivity";
    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<User> userArrayList;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mfirebaseAuth;
    private String userID;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_chat, container, false);

        userArrayList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.chat_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        userID = indexActivity.userID;

        mAdapter = new ChatAdapter(userArrayList, userID, getContext());
        recyclerView.setAdapter(mAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();

        mDatabaseRef = firebaseDatabase.getReference("User");


        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDatachanged : " + snapshot.getValue().toString());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    User user = dataSnapshot.getValue(User.class);
                    userArrayList.add(user);
                    Log.d(TAG, "dataSnapShot : " + user.getEmailId() );
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            ;

        });

        return view;
    }


    @Override
    public void onClick(View view) {

    }

}
