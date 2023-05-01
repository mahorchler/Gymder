package com.group5.gymder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group213.gymder.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    private RecyclerView chatRecyclerView;
    private ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<String> lastMessages;
    private DatabaseReference mDataBaseUser;
    public ChatAdapter chatAdapter;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String currentUserID;
    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatRecyclerView = view.findViewById(R.id.chatView);
        getusers();
        setAdapter();
        return view;
    }
    private void setAdapter(){
         chatAdapter = new ChatAdapter(view.getContext(), userList, lastMessages);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setItemAnimator(new DefaultItemAnimator());
        chatRecyclerView.setAdapter(chatAdapter);
    }
    public void refresh()
    {
        if(chatAdapter!=null)
        chatAdapter.notifyDataSetChanged();
    }
    public void getusers()
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser cur=mAuth.getCurrentUser();
        currentUserID=cur.getUid();
        mDataBaseUser= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID).child("matches");
        mDataBaseUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName)
            {
                Log.d("added","yea");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot)
            {
                for(int i=0;i<userList.size();i++)
                {
                    if(!snapshot.hasChild(userList.get(i).getUid()))
                    {
                        userList.remove(i);
                        refresh();
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public ArrayList<String> getLastMessages() {
        return lastMessages;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setLastMessages(ArrayList<String> lastMessages) {
        this.lastMessages = lastMessages;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}