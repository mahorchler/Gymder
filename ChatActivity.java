package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group213.gymder.R;

import java.util.ArrayList;

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
        }
        setMessageList();
        setAdapter();
    }

    public void sendMessage(View view){
        String message = sendText.getText().toString();
        if (!message.isEmpty()) {
            messageList.add(new Message(message, true));
            messageAdapter.notifyItemInserted(messageList.size()-1);
            sendText.setText("");
        }
    }

    private void setAdapter(){
        messageAdapter = new MessageAdapter(getApplicationContext(), messageList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        messageRecycler.setLayoutManager(layoutManager);
        messageRecycler.setItemAnimator(new DefaultItemAnimator());
        messageRecycler.setAdapter(messageAdapter);
    }

    private void setMessageList(){
        messageList = new ArrayList<>();
        String message = intent.getStringExtra("message");
        messageList.add(new Message(message, false));
    }
}
