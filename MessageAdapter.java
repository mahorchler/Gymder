package com.group5.gymder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group213.gymder.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private ArrayList<Message> messageList;
    private Context context;

    public MessageAdapter(Context context, ArrayList<Message> messageList){
        this.messageList = messageList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView message;

        public MyViewHolder(final View view){
            super(view);
            message = view.findViewById(R.id.message);
        }
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 0) itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_right, parent, false);
        else itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_left, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        String message = messageList.get(position).getMessage();
        holder.message.setText(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).senderIsHost()) return 0;
        else return 1;
    }
}

