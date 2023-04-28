package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.group213.gymder.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private TextView username;
    private TextView password;
    private Button signIn;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.sign);
        register = findViewById(R.id.register);
    }

    public void signIn(View v){
        String user = username.getText().toString();
        String pass = password.getText().toString();
        Toast status = Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT);
        if (user.isEmpty() || pass.isEmpty()) {
            status.setText("Invalid credentials!");
            status.show();
            return;
        }
        if (user.equalsIgnoreCase("cs431") && pass.equals("12345")){
            status.setText("Welcome " + user.toLowerCase(Locale.ROOT));
            status.show();
            Intent chatIntent = new Intent(this, AppActivity.class);
            this.startActivity(chatIntent);
        } else {
            status.setText("Invalid credentials!");
            status.show();
        }
    }
    private void print(String x){
        System.out.println(x);
    }

    public void register(View v){
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        this.startActivity(registerIntent);
    }
}