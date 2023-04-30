package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.group213.gymder.R;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RegisterActivity extends AppCompatActivity {
    private TextView email;
    private TextView password;
    private TextView gym;
    private TextView confirmPassword;
    private TextView age;
    private TextView name;
    private TextView gender;
    private TextView interests;
    private Button register;
    private TextView[] textViews;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        confirmPassword = findViewById(R.id.confirm_register);
        gym = findViewById(R.id.gym_register);
        age = findViewById(R.id.age_register);
        name = findViewById(R.id.name_register);
        gender = findViewById(R.id.gender_register);
        interests = findViewById(R.id.interests_register);
        register = findViewById(R.id.register2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(view);
            }
        });
        textViews = new TextView[]{email, password, confirmPassword, gym, age, name, gender, interests};
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(View v){
        for (TextView textView : textViews) {
            if (textView.getText().toString().isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User newUser = new User(mAuth.getCurrentUser().getUid(),email.getText().toString(), password.getText().toString());
                        newUser.setName(name.getText().toString());
                        newUser.setGym(gym.getText().toString());
                        newUser.setAge(age.getText().toString());
                        newUser.setGender(gender.getText().toString());
                        newUser.setInterests(interests.getText().toString());
                        newUser.setProfilePicture(null);
                        Map<String,Object> m= new HashMap<>();
                        FirebaseDatabase.getInstance().getReference("users")
                            .child(mAuth.getCurrentUser().getUid())
                            .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    showAppActivity();
                                }
                            });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_LONG).show();
                        String errorM = task.getException().getLocalizedMessage();
                        Log.d("login",errorM);
                    }
                }
            });
    }

    private void showAppActivity(){
        Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
        finish();
    }
}
