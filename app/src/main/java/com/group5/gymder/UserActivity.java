package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class UserActivity extends AppCompatActivity {
    private TextView name;
    private FirebaseAuth mAuth;
    private TextView username;
    private TextView age;
    private ImageButton like;
    private ImageButton dislike;
    private CircleImageView profilePicture;
    private TextView interests;
    private Toolbar toolbar;
    private Intent intent;

    public boolean check=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searched_user);
        name = findViewById(R.id.searchedName);
        username = findViewById(R.id.searchedUsername);
        age = findViewById(R.id.searchedAge);
        profilePicture = findViewById(R.id.searchedUserPFP);
        interests = findViewById(R.id.interests);
        toolbar = findViewById(R.id.userToolbar);
        like = findViewById(R.id.like);
        dislike = findViewById(R.id.dislike);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
        intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        username.setText(intent.getStringExtra("username"));
        String ageString = intent.getStringExtra("age") + " years old";
        age.setText(ageString);
        profilePicture.setImageResource(R.drawable.omar);
    }

    public void like(View view){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser wow=mAuth.getCurrentUser();
        DatabaseReference ref = database.getReference().child("users").child(wow.getUid());
        ref.child("likes").child(intent.getStringExtra("uid")).setValue(true);
        DatabaseReference other = database.getReference().child("users").child(intent.getStringExtra("uid"));
        isMatch("");
        like.setForeground(AppCompatResources.getDrawable(this, R.drawable.ic_like_clicked));
        dislike.setForeground(AppCompatResources.getDrawable(this, R.drawable.ic_dislike_name));

    }
    public void isMatch(String uid)
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser cur=mAuth.getCurrentUser();//current user
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("users").child(intent.getStringExtra("uid")).child("likes");// loction of the other user to check the other user to see if they like current user or not

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChild(cur.getUid())) {
                    // run some code

                    DatabaseReference other = FirebaseDatabase.getInstance().getReference().child("users").child(intent.getStringExtra("uid"));
                    DatabaseReference currentuserloc = FirebaseDatabase.getInstance().getReference().child("users").child(cur.getUid());
                    String key= FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
                    FirebaseDatabase.getInstance().getReference().child("chat").child(key).push().setValue("We're matched " +cur.getEmail());
                    currentuserloc.child("matches").child(intent.getStringExtra("uid")).child("chatid").setValue(key);
                    other.child("matches").child(cur.getUid()).child("chatid").setValue(key);


                }
                else
                    Log.d("no body loves me ","doesnt exist");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
            public void dislike(View view){
        like.setForeground(AppCompatResources.getDrawable(this, R.drawable.ic_like_name));
        dislike.setForeground(AppCompatResources.getDrawable(this, R.drawable.ic_dislike_clicked));
    }
}
