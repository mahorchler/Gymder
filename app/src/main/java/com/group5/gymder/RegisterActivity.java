package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {
    private TextView email;
    private TextView password;
    private TextView town;
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
        town = findViewById(R.id.town_register);
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
        textViews = new TextView[]{email, password, confirmPassword, town, age, name, gender, interests};
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(View v){
        for (TextView textView : textViews) {
            if (textView.getText().toString().isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //save user data - in progress
                        /*
                        //User user = new User(username.getText().toString(), password.getText().toString(), email.getText().toString());
                        FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    showAppActivity();
                                }
                            });

                         */
                        showAppActivity();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_LONG).show();
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
