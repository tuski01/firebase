package net.flow9.dcjt.firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.flow9.dcjt.firebase.adapters.ChatAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

public class chatRoomActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton msg_send_btn;
    private EditText msg_send_input;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mfirebaseAuth;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        msg_send_input = findViewById(R.id.msg_send_input);
        msg_send_btn = findViewById(R.id.msg_send_btn);
        recyclerView = findViewById(R.id.recycler_chatroom_msg_list);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = {"test1", "test2", "test3", "test4"};
        mAdapter = new ChatAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
        mfirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Chat");
        userID = indexActivity.userID;

        msg_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stText = msg_send_input.getText().toString();
                FirebaseUser firebaseUser = mfirebaseAuth.getCurrentUser();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
                String datetime = dateFormat.format(c.getTime());

                Hashtable<String, String> numbers = new Hashtable<String, String>();
                numbers.put("userID", userID);
                numbers.put("text", stText);

                mDatabaseRef.child(datetime).setValue(numbers);

                Toast.makeText(getApplicationContext(), "MSG:" + stText, Toast.LENGTH_SHORT).show();
            }
        });


    }
}