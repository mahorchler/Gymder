package com.group5.gymder;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.group213.gymder.R;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment{
    private Toolbar toolbar;
    private TextView name;
    private TextView email;
    private CircleImageView pfp;
    private TextView age;
    private TextView gender;
    private TextView gym;
    private TextView interests;
    private Button editProfile;
    //private Button uploadPicture;
    private Button delete;
    private Button logout;
    private FirebaseAuth mAuth;
    private String pfpString;
    private String password;
    private String ageString;
    private String genderString;
    private String gymString;
    private String interestsString;
    StorageReference sr;

    private View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        sr = FirebaseStorage.getInstance().getReference();
        name = view.findViewById(R.id.profileName);
        toolbar = view.findViewById(R.id.profileToolbar);
        pfp = view.findViewById(R.id.profilePFP);
        email = view.findViewById(R.id.profileEmail);
        age = view.findViewById(R.id.profileAge);
        gender = view.findViewById(R.id.profileGender);
        gym = view.findViewById(R.id.profileGym);
        interests = view.findViewById(R.id.profileInterests);

        Drawable pfp = null;
        email.setText(currentUser.getEmail());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    name.setText(user.getName());
                    pfpString = user.getPfp();
                    ageString = user.getAge();
                    age.setText(ageString + " years old");
                    genderString = user.getGender();
                    gender.setText("Gender: " + genderString);
                    gymString = user.getGym();
                    gym.setText("Gym: " + gymString);
                    interestsString = user.getInterests();
                    interests.setText("Interest(s): " + interestsString);
                    password = user.getPassword();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        this.pfp.setImageResource(R.mipmap.defaultpfp);
        if (pfpString != null) {
            this.pfp.setImageResource(R.mipmap.defaultpfp);
        }

        this.view = view;
        editProfile = view.findViewById(R.id.profileEdit);
        editProfile.setOnClickListener(view1 -> editProfile());
        /*
        uploadPicture = view.findViewById(R.id.profileUploadPicture);
        uploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });
         */
        delete = view.findViewById(R.id.profileDelete);
        delete.setOnClickListener(view1 -> deleteProfile());
        logout = view.findViewById(R.id.profileLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                uploadImage(imageUri);
            }
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mtm = MimeTypeMap.getSingleton();
        return mtm.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadImage(Uri imageUri) {
        String imageFileName = mAuth.getCurrentUser().getUid() + "." + getFileExtension(imageUri);
        StorageReference fileRef = sr.child(imageFileName);
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());
                ref.child("pfp").setValue(imageFileName);
                Toast.makeText(getContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Image Upload Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void editProfile() {
        Context context = view.getContext();
        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("password", password);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("age", ageString);
        intent.putExtra("gender", genderString);
        intent.putExtra("gym", gymString);
        intent.putExtra("interests", interestsString);
        context.startActivity(intent);
    }

    private void deleteProfile(){
        Context context = view.getContext();
        Intent intent = new Intent(context, DeleteActivity.class);
        context.startActivity(intent);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        getActivity().finish();
    }
}