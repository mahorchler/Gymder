package com.group5.gymder;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatFragment = new ChatFragment();
        searchFragment = new SearchFragment();
        profileFragment = new ProfileFragment();
        userList = new ArrayList<>();
        lastMessages = new ArrayList<>();
        setUserInfo();
        chatFragment.setUserList(userList);
        chatFragment.setLastMessages(lastMessages);
        searchFragment.setUserList(userList);
        User current = new User("cs431", "12345");
        current.setName("Group 5");
        current.setAge(22);
        profileFragment.setUser(current);
        replaceFragment(chatFragment);
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

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void setUserInfo(){
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
}
