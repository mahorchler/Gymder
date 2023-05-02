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
    private ArrayList<Message> messageList=  new ArrayList<Message>();
    private MessageAdapter messageAdapter;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private String rchatid;

    private String matchId;

    DatabaseReference mDataBaseUser, mDataBaseChat;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        matchId=getIntent().getExtras().getString("uid");
        mDataBaseChat=FirebaseDatabase.getInstance().getReference().child("chat");
        currentUserID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDataBaseUser=FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID).child("matches").child(matchId);
        getchatid();
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
        profilePicture.setImageResource(R.mipmap.defaultpfp);
        /*
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
         */

        setAdapter();
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


        mDataBaseChat.addChildEventListener(new ChildEventListener() {
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
                        messageList.add(new Message(message, currentUserBoolean));
                        messageAdapter.notifyDataSetChanged();
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
        mDataBaseUser.child("chatid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                    rchatid = snapshot.getValue().toString();
                    Log.d("really bro",rchatid);
                    mDataBaseChat= mDataBaseChat.child(rchatid);
                    getmessages();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
