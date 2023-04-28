package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.group213.gymder.R;

public class EditProfileActivity extends AppCompatActivity {

    private TextView email;
    private TextView username;
    private TextView password;
    private TextView confirmPassword;
    private TextView name;
    private TextView age;
    private TextView address;
    private TextView interests;
    private TextView[] textViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);
        Intent intent = getIntent();
        username = findViewById(R.id.editUsername);
        username.setText(intent.getStringExtra("username"));
        name = findViewById(R.id.editName);
        name.setText(intent.getStringExtra("name"));
        password = findViewById(R.id.editPassword);
        confirmPassword = findViewById(R.id.editConfirmPassword);
        age = findViewById(R.id.editAge);
        age.setText(intent.getStringExtra("age"));
        interests = findViewById(R.id.editInterests);
        interests.setText(intent.getStringExtra("interests"));
        email = findViewById(R.id.editEmail);
        address = findViewById(R.id.editAddress);
        textViews = new TextView[]{email, username, name, password, confirmPassword, age, address, interests};
    }

    public void confirm(View view) {
        for (TextView textView: textViews) {
            if (textView.getText().toString().isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(this, "Information edited successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
