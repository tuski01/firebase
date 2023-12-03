package net.flow9.dcjt.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.flow9.dcjt.firebase.adapters.ChatAdapter;
import net.flow9.dcjt.firebase.adapters.ChatRoomAdapter;
import net.flow9.dcjt.firebase.model.ChatModel;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class chatRoomActivity extends AppCompatActivity {
    private static final String TAG = "chatRoomActivity";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton msg_send_btn;
    private EditText msg_send_input;
    private String writer;
    private String room_name;

    // firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mfirebaseAuth;
    private String userID;
    private ArrayList<ChatModel> chatArrayList;
    private String Room_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //      파이어베이스
        mfirebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Room_name = getIntent().getStringExtra("Room_name");


        FirebaseUser firebaseUser = mfirebaseAuth.getCurrentUser();

        userID = getIntent().getStringExtra("writer");
        room_name = userID + " " + firebaseUser.getUid();

        if(userID == null){
            mDatabaseRef = firebaseDatabase.getReference("Chat").child(Room_name);
        } else {
            mDatabaseRef = firebaseDatabase.getReference("Chat").child(room_name);
        }

        chatArrayList = new ArrayList<>();
        Log.d(TAG, room_name);




        msg_send_input = findViewById(R.id.msg_send_input);
        msg_send_btn = findViewById(R.id.msg_send_btn);
        recyclerView = findViewById(R.id.recycler_chatroom_msg_list);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ChatRoomAdapter(chatArrayList, firebaseUser.getUid(), getApplicationContext());
        recyclerView.setAdapter(mAdapter);

        // Firebase Realtime Database 값 읽어오기
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded: " + snapshot.getKey());

                ChatModel newChat = snapshot.getValue(ChatModel.class);
                String commentKey = snapshot.getKey();
                String stEmail = newChat.getUserID();
                String stText = newChat.getText();
                Log.d(TAG, "stEmail" + stEmail);
                Log.d(TAG, "stText" + stText);
                chatArrayList.add(newChat);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildChanged: " + snapshot.getKey());

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onChildRemoved: " + snapshot.getKey());

                String commentKey = snapshot.getKey();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildMoved: " + snapshot.getKey());

                ChatModel movedChat = snapshot.getValue(ChatModel.class);
                String commentKey = snapshot.getKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "postComments:onCancelled", error.toException());
                Toast.makeText(chatRoomActivity.this, "Failed to load comments", Toast.LENGTH_SHORT).show();
            }
        };
        mDatabaseRef.addChildEventListener(childEventListener);

        msg_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stText = msg_send_input.getText().toString();


                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
                String datetime = dateFormat.format(c.getTime());

                Hashtable<String, String> numbers = new Hashtable<String, String>();
                numbers.put("userID", firebaseUser.getUid());
                numbers.put("text", stText);


                mDatabaseRef.child(datetime).setValue(numbers);

                Toast.makeText(getApplicationContext(), "MSG:" + stText, Toast.LENGTH_SHORT).show();
            }
        });


    }
}