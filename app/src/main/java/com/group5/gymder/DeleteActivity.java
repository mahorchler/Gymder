package com.group5.gymder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    public void delete(View view) {
        if (password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty())
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password.getText().toString());

            if (user != null) {
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    //delete database info first
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference reference = database.getReference("users").child(user.getUid());
                                    reference.removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        user.delete()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            Toast.makeText(DeleteActivity.this, "Account Deleted", Toast.LENGTH_LONG).show();
                                                                            Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
                                                                            startActivity(intent);
                                                                        } else {
                                                                            Toast.makeText(DeleteActivity.this, "Error: Account Not Deleted", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                    else{
                                                        Toast.makeText(DeleteActivity.this, "Error: Account Not Deleted", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                }
                                else{
                                    Toast.makeText(DeleteActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        }
    }
}
