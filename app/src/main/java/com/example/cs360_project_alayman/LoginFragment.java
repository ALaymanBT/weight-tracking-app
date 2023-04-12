package com.example.cs360_project_alayman;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    Button loginButton;
    CallbackFragment callbackFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        loginButton = (Button) rootView.findViewById(R.id.button_login);
        MainActivity main = (MainActivity) getActivity();

        loginButton.setOnClickListener((v) -> {
            callbackFragment.addFragment();
            Toast.makeText(v.getContext(), "Login Clicked", Toast.LENGTH_LONG).show();
        });
        return rootView;
    }

    public void setCallbackFragment(CallbackFragment callbackFragment) {
        this.callbackFragment = callbackFragment;
    }


}