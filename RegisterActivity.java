package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.group213.gymder.R;

public class RegisterActivity extends AppCompatActivity {
    private TextView email;
    private TextView username;
    private TextView password;
    private TextView address;
    private TextView confirmPassword;
    private TextView age;
    private TextView name;
    private TextView gender;
    private TextView interests;
    private Button register;
    private TextView[] textViews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        email = findViewById(R.id.email_register);
        username = findViewById(R.id.username_register);
        password = findViewById(R.id.password_register);
        confirmPassword = findViewById(R.id.confirm_register);
        address = findViewById(R.id.address_register);
        age = findViewById(R.id.age_register);
        name = findViewById(R.id.name_register);
        gender = findViewById(R.id.gender_register);
        interests = findViewById(R.id.interests_register);
        register = findViewById(R.id.register2);
        textViews = new TextView[]{email, username, password, confirmPassword, address, age, name, gender, interests};
    }

    public void registerUser(View v){
        for (TextView textView : textViews) {
            if (textView.getText().toString().isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
