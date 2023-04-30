package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.group213.gymder.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private TextView email;
    private TextView password;
    private Button signIn;
    private Button register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.sign);
        register = findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            showAppActivity();
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser(view);
            }
        });
    }

    private void authenticateUser(View v) {
        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            email.setText("");
                            password.setText("");
                            showAppActivity();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            String errorM = task.getException().getLocalizedMessage();
                            Log.d("login",errorM);
                        }
                    }
                });
    }

    private void showAppActivity() {
        Intent intent = new Intent(this, AppActivity.class);

        startActivity(intent);
    }

    public void register(View v){
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        this.startActivity(registerIntent);
    }
}