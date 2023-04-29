package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group213.gymder.R;
import com.group213.gymder.databinding.MainBinding;

import java.util.ArrayList;

public class AppActivity extends AppCompatActivity {
    private MainBinding binding;
    private ChatFragment chatFragment;
    private SearchFragment searchFragment;
    private ProfileFragment profileFragment;
    private ArrayList<User> userList;
    private ArrayList<String> lastMessages;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatFragment = new ChatFragment();
        searchFragment = new SearchFragment();
        profileFragment = new ProfileFragment();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            finish();
        }

        userList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s: snapshot.getChildren()){
                    userList.add((User) s.getValue(User.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        lastMessages = new ArrayList<>();
        chatFragment.setUserList(userList);
        chatFragment.setLastMessages(lastMessages);
        searchFragment.setUserList(userList);

        replaceFragment(searchFragment);
        binding.bottomNavigationView.setSelectedItemId(R.id.search);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.chat:
                    replaceFragment(chatFragment);
                    break;
                case R.id.search:
                    replaceFragment(searchFragment);
                    break;
                case R.id.profile:
                    replaceFragment(profileFragment);
                    break;
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    /*
    public void setUserInfo(){
        User user = new User("Omar", "12345");
        user.setName("Omar");
        user.setProfilePicture(getDrawable(R.drawable.omar));
        user.setGym("College Avenue");
        user.setAge(22);
        user.setEmail("omar@gmail.com");
        userList.add(user);
        lastMessages.add("My name is Omar.");
        user = new User("Maylin", "12345");
        user.setName("Maylin");
        user.setProfilePicture(getDrawable(R.drawable.maylin));
        user.setGym("College Avenue");
        user.setAge(21);
        user.setEmail("maylin@gmail.com");
        userList.add(user);
        lastMessages.add("My name is Maylin.");
        user = new User("Ricky", "12345");
        user.setName("Ricky");
        user.setProfilePicture(getDrawable(R.drawable.ricky));
        user.setGym("Easton Avenue");
        user.setAge(23);
        user.setEmail("ricky@gmail.com");
        userList.add(user);
        lastMessages.add("My name is Ricky.");
        user = new User("Arnav", "12345");
        user.setName("Arnav");
        user.setProfilePicture(getDrawable(R.drawable.arnav));
        user.setGym("Easton Avenue");
        user.setAge(24);
        user.setEmail("arnav@gmail.com");
        userList.add(user);
        lastMessages.add("My name is Arnav.");
    }
     */
}
