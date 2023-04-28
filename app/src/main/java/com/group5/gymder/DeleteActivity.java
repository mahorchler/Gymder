package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.group213.gymder.R;

public class DeleteActivity extends AppCompatActivity {
    private TextView password;
    private TextView confirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_delete);
        password = findViewById(R.id.deletePassword);
        confirmPassword = findViewById(R.id.deleteConfirm);
    }

    public void delete(View view){
        if (password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()) Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Profile deleted successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
