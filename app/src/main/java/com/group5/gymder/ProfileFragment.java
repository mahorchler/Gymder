package com.group5.gymder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group213.gymder.R;

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
    private TextView interests;
    private Button editProfile;
    private Button delete;

    private Button logout;

    private FirebaseAuth mAuth;

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
        name = view.findViewById(R.id.profileName);
        toolbar = view.findViewById(R.id.profileToolbar);
        pfp = view.findViewById(R.id.profilePFP);
        email = view.findViewById(R.id.profileEmail);
        age = view.findViewById(R.id.profileAge);
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
                    String ageString = user.getAge() + " years old";
                    age.setText(ageString);
                    String interestsString = "Interest(s): " + user.getInterests();
                    interests.setText(interestsString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        if (pfp == null) {
            this.pfp.setImageResource(R.mipmap.defaultpfp);
        }
        else this.pfp.setImageDrawable(pfp);

        this.view = view;
        editProfile = view.findViewById(R.id.profileEdit);
        editProfile.setOnClickListener(view1 -> editProfile());
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

    private void editProfile() {
        Context context = view.getContext();
        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("name", name.getText().toString());
        //intent.putExtra("age", user.getAge()+"");
        intent.putExtra("Interests", interests.getText());
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