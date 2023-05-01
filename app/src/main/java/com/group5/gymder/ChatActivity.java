package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group213.gymder.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private CircleImageView profilePicture;
    private TextView name;
    private Intent intent;
    private TextView sendText;
    private ImageButton send;
    private RecyclerView messageRecycler;
    private ArrayList<Message> messageList;
    private MessageAdapter messageAdapter;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private String rchatid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
        profilePicture = findViewById(R.id.chatProfilePicture);
        name = findViewById(R.id.chatUserName);
        sendText = findViewById(R.id.sendText);
        send = findViewById(R.id.btnSend);
        messageRecycler = findViewById(R.id.messageRecycler);
        intent = getIntent();
        String userName = intent.getStringExtra("name");
        name.setText(userName);
        switch (userName){
            case "Omar":
                profilePicture.setImageDrawable(getDrawable(R.drawable.omar));
                break;
            case "Maylin":
                profilePicture.setImageDrawable(getDrawable(R.drawable.maylin));
                break;
            case "Arnav":
                profilePicture.setImageDrawable(getDrawable(R.drawable.arnav));
                break;
            case "Ricky":
                profilePicture.setImageDrawable(getDrawable(R.drawable.ricky));
                break;
            default:
                profilePicture.setImageDrawable(getDrawable(R.drawable.ricky));
                break;
        }
        setMessageList();

    }

    public void sendMessage(View view){

        String other=intent.getStringExtra("uid");
        Map mes= new HashMap<String,String>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser cur=mAuth.getCurrentUser();
        currentUserID=cur.getUid();
        DatabaseReference ref = database.getReference().child("chat");
        Log.d("chat","|"+other+"|"+ cur.getUid());
        DatabaseReference chat= database.getReference().child("users").child(cur.getUid()).child("matches").child(other).child("chatid");
        chat.addListenerForSingleValueEvent(new ValueEventListener() {//getting chatid
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                String chatid=snapshot.getValue().toString();
                rchatid=chatid;
                String message = sendText.getText().toString();
                mes.put("sender",cur.getUid());
                mes.put("text",message);
                if (!message.isEmpty()) {
                    //TODO: REPLCE WITH ACTUAL CURRENWT USER
                    messageList.add(new Message(message, true));
                    messageAdapter.notifyItemInserted(messageList.size()-1);
                    sendText.setText("");
                }
                ref.child(chatid).push().setValue(mes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    public void getmessages()
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref= database.getReference().child("chat").child(rchatid);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    String message=null;
                    String sender=null;
                    if(snapshot.child("text").getValue()!=null)
                    {
                        message=snapshot.child("text").getValue().toString();
                    }
                    if(snapshot.child("sender").getValue()!=null)
                    {
                        sender=snapshot.child("sender").getValue().toString();
                    }
                    if(message!=null&&sender!=null)
                    {
                        boolean currentUserBoolean=false;
                        if(sender.equals(currentUserID))
                        {
                            currentUserBoolean=true;
                        }

                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mAuth = FirebaseAuth.getInstance();
    }
    private void setAdapter(){
        messageAdapter = new MessageAdapter(getApplicationContext(), messageList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        messageRecycler.setLayoutManager(layoutManager);
        messageRecycler.setItemAnimator(new DefaultItemAnimator());
        messageRecycler.setAdapter(messageAdapter);
    }
    public void getchatid()
    {


    }
    private void setMessageList(){

        messageList = new ArrayList<>();
        String message = intent.getStringExtra("message");
        //TODO: REPLACE WITH ACTUAL CURRENT USER
        messageList.add(new Message(message, false));
        String other=intent.getStringExtra("uid");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser cur=mAuth.getCurrentUser();
        currentUserID=cur.getUid();
        DatabaseReference ref = database.getReference().child("chat");
        Log.d("chat","|"+other+"|"+ cur.getUid());
        DatabaseReference chat= database.getReference().child("users").child(cur.getUid()).child("matches").child(other).child("chatid");
        chat.addListenerForSingleValueEvent(new ValueEventListener() {//getting chatid
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                String chatid=snapshot.getValue().toString();
                rchatid=chatid;
                ref.child(rchatid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        for (DataSnapshot s:snapshot.getChildren())
                        {

                            Log.d("jesus",s.child("text").getValue().toString());
                            String sender=s.child("sender").getValue().toString();
                            String text=s.child("text").getValue().toString();
                            Boolean host=currentUserID.equals(sender);
                            messageList.add(new Message(text, host));
                        }
                        setAdapter();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
