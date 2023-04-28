// Generated by view binder compiler. Do not edit!
package com.group213.gymder.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.group213.gymder.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ChatBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton btnSend;

  @NonNull
  public final CircleImageView chatProfilePicture;

  @NonNull
  public final TextView chatUserName;

  @NonNull
  public final ConstraintLayout constraintLayout;

  @NonNull
  public final RecyclerView messageRecycler;

  @NonNull
  public final EditText sendText;

  @NonNull
  public final Toolbar toolbar;

  private ChatBinding(@NonNull ConstraintLayout rootView, @NonNull ImageButton btnSend,
      @NonNull CircleImageView chatProfilePicture, @NonNull TextView chatUserName,
      @NonNull ConstraintLayout constraintLayout, @NonNull RecyclerView messageRecycler,
      @NonNull EditText sendText, @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.btnSend = btnSend;
    this.chatProfilePicture = chatProfilePicture;
    this.chatUserName = chatUserName;
    this.constraintLayout = constraintLayout;
    this.messageRecycler = messageRecycler;
    this.sendText = sendText;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ChatBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ChatBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.chat, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ChatBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnSend;
      ImageButton btnSend = ViewBindings.findChildViewById(rootView, id);
      if (btnSend == null) {
        break missingId;
      }

      id = R.id.chatProfilePicture;
      CircleImageView chatProfilePicture = ViewBindings.findChildViewById(rootView, id);
      if (chatProfilePicture == null) {
        break missingId;
      }

      id = R.id.chatUserName;
      TextView chatUserName = ViewBindings.findChildViewById(rootView, id);
      if (chatUserName == null) {
        break missingId;
      }

      id = R.id.constraintLayout;
      ConstraintLayout constraintLayout = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout == null) {
        break missingId;
      }

      id = R.id.messageRecycler;
      RecyclerView messageRecycler = ViewBindings.findChildViewById(rootView, id);
      if (messageRecycler == null) {
        break missingId;
      }

      id = R.id.sendText;
      EditText sendText = ViewBindings.findChildViewById(rootView, id);
      if (sendText == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      return new ChatBinding((ConstraintLayout) rootView, btnSend, chatProfilePicture, chatUserName,
          constraintLayout, messageRecycler, sendText, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
