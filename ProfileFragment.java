package com.group5.gymder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.group213.gymder.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment{
    private Toolbar toolbar;
    private TextView username;
    private TextView name;
    private CircleImageView pfp;
    private TextView age;
    private TextView interests;
    private User user;
    private Button editProfile;
    private Button delete;

    private Button logout;

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
        username = view.findViewById(R.id.profileUsername);
        toolbar = view.findViewById(R.id.profileToolbar);
        pfp = view.findViewById(R.id.profilePFP);
        name = view.findViewById(R.id.profileName);
        age = view.findViewById(R.id.profileAge);
        interests = view.findViewById(R.id.profileInterests);
        username.setText(user.getUsername());
        name.setText(user.getName());
        String age = user.getAge() + " years old";
        this.age.setText(age);
        Drawable pfp = user.getProfilePicture();
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
        logout.setOnClickListener(view1 -> logout());
        return view;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void editProfile() {
        Context context = view.getContext();
        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.putExtra("username", username.getText().toString());
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("age", user.getAge()+"");
        intent.putExtra("Interests", interests.getText());
        context.startActivity(intent);
    }

    private void deleteProfile(){
        Context context = view.getContext();
        Intent intent = new Intent(context, DeleteActivity.class);
        context.startActivity(intent);
    }

    private void logout(){
        getActivity().finish();
    }
}