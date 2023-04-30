package com.group5.gymder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group213.gymder.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private ArrayList<User> userList;
    private RecyclerView searchRecycler;
    private SearchView searchView;
    private SearchAdapter searchAdapter;
    private View view;

    private FirebaseAuth mAuth;
    
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2)
    {

        SearchFragment fragment = new SearchFragment();
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


        view = inflater.inflate(R.layout.fragment_search, container, false);
        searchRecycler = view.findViewById(R.id.searchUsersRecycler);
        searchView = view.findViewById(R.id.filterUsers);

        setAdapter();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterUsers(s);
                return true;
            }
        });
        return view;
    }

    private void filterUsers(String s) {

        ArrayList<User> filteredList = new ArrayList<>();

        for (User user : userList)
            if(user.getEmail() != null) {
                if (user.getEmail().toLowerCase(Locale.ROOT).startsWith(s))
                    filteredList.add(user);
            }
        searchAdapter.setList(filteredList);
    }

    private void setAdapter(){

        searchAdapter = new SearchAdapter(view.getContext(), userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        searchRecycler.setLayoutManager(layoutManager);
        searchRecycler.setItemAnimator(new DefaultItemAnimator());
        searchRecycler.setAdapter(searchAdapter);

    }
    public void refresh()
    {
        searchAdapter.notifyDataSetChanged();
    }
    public void setUserList(ArrayList<User> userList)
    {

        this.userList = userList;

    }
}