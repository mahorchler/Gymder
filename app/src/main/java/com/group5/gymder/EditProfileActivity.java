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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.group213.gymder.R;

public class EditProfileActivity extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private TextView name;
    private TextView age;
    private TextView gender;
    private TextView gym;
    private TextView interests;
    private TextView[] textViews;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);
        Intent intent = getIntent();
        gender = findViewById(R.id.editGender);
        gender.setText(intent.getStringExtra("gender"));
        name = findViewById(R.id.editName);
        name.setText(intent.getStringExtra("name"));
        password = findViewById(R.id.editPassword);
        password.setText(intent.getStringExtra("password"));
        confirmPassword = findViewById(R.id.editConfirmPassword);
        age = findViewById(R.id.editAge);
        age.setText(intent.getStringExtra("age"));
        interests = findViewById(R.id.editInterests);
        interests.setText(intent.getStringExtra("interests"));
        email = findViewById(R.id.editEmail);
        email.setText(intent.getStringExtra("email"));
        gym = findViewById(R.id.editGym);
        gym.setText(intent.getStringExtra("gym"));
        textViews = new TextView[]{email, name, password, confirmPassword, age, gender, gym, interests};
        mAuth = FirebaseAuth.getInstance();
    }

    public void confirm(View view) {
        for (TextView textView: textViews) {
            if (textView.getText().toString().isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

        User newUser = new User(mAuth.getCurrentUser().getUid(),email.getText().toString(), password.getText().toString());
        newUser.setName(name.getText().toString());
        newUser.setGym(gym.getText().toString());
        newUser.setAge(age.getText().toString());
        newUser.setGender(gender.getText().toString());
        newUser.setInterests(interests.getText().toString());
        newUser.setProfilePicture(null);
        FirebaseDatabase.getInstance().getReference("users")
                .child(mAuth.getCurrentUser().getUid())
                .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditProfileActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(EditProfileActivity.this, "Update failed", Toast.LENGTH_LONG).show();
                            String errorM = task.getException().getLocalizedMessage();
                            Log.d("editInfo",errorM);
                        }
                    }
                });
    }
}
