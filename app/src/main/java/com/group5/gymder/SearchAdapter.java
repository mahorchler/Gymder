package com.group5.gymder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group213.gymder.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private ArrayList<User> usersList;
    private Context context;

    public SearchAdapter(Context context, ArrayList<User> usersList) {
        this.usersList = usersList;
        this.context = context;
    }

    public void setList(ArrayList<User> filteredList) {

        usersList = filteredList;

        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView searchProfilePicture;
        private TextView searchEmail;
        private TextView searchName;

        public MyViewHolder(final View view) {
            super(view);
            searchProfilePicture = view.findViewById(R.id.searchPFP);
            searchEmail = view.findViewById(R.id.searchEmail);
            searchName = view.findViewById(R.id.searchName);
        }
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        User user = usersList.get(position);
        Drawable profilePicture = user.getProfilePicture();
        String profileName = user.getName();
        String email = user.getEmail();
        holder.searchProfilePicture.setImageDrawable(profilePicture);
        holder.searchName.setText(profileName);
        holder.searchEmail.setText(email);
        holder.itemView.setOnClickListener(view -> {
            Intent searchIntent = new Intent(context, UserActivity.class);
            searchIntent.putExtra("name", profileName);
            searchIntent.putExtra("email", email);
            searchIntent.putExtra("age", user.getAge());
            searchIntent.putExtra("uid", user.getUid());
            context.startActivity(searchIntent);
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}