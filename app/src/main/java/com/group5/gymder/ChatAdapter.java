package com.group5.gymder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group213.gymder.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private ArrayList<User> usersList;
    private ArrayList<String> lastMessages;
    private Context context;

    private FirebaseAuth mAuth;
    public ChatAdapter(Context context, ArrayList<User> usersList, ArrayList<String> lastMessages){
        this.usersList = usersList;
        this.lastMessages = lastMessages;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView profilePicture;
        private TextView profileName;
        //private TextView lastMessage;

        public MyViewHolder(final View view){
            super(view);
            profilePicture = view.findViewById(R.id.profilePicture);
            profileName = view.findViewById(R.id.tvName);
            //lastMessage = view.findViewById(R.id.tvRecentMessage);
        }
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        Drawable profilePicture = usersList.get(position).getProfilePicture();
        if(profilePicture==null){
            holder.profilePicture.setImageResource(R.mipmap.defaultpfp);
        }
        else{
            holder.profilePicture.setImageDrawable(profilePicture);
        }
        String profileName = usersList.get(position).getName();
        String uid= usersList.get(position).getUid();
        holder.profileName.setText(profileName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatIntent = new Intent(context, ChatActivity.class);
                chatIntent.putExtra("name", profileName);
                chatIntent.putExtra("uid",uid);
                Log.d("happened","yea");
                context.startActivity(chatIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
