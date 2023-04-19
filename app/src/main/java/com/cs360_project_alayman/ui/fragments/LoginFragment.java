package com.cs360_project_alayman.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs360_project_alayman.ui.activities.MainActivity;
//import com.cs360_project_alayman.data.DatabaseManager;
import com.cs360_project_alayman.data.entities.User;
import com.cs360_project_alayman.R;
import com.cs360_project_alayman.repository.UserWeightRepository;
import com.cs360_project_alayman.utils.auth.AuthenticatedUser;
import com.cs360_project_alayman.utils.auth.AuthenticatedUserManager;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;
    private TextView txtErrorMsg;
    private UserWeightRepository weightRepo;
    private AuthenticatedUserManager authenticatedUserManager;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        etUsername = view.findViewById(R.id.username);
        etPassword = view.findViewById(R.id.password);
        Button btnLogin = view.findViewById(R.id.button_login);
        Button btnRegister = view.findViewById(R.id.button_register);
        txtErrorMsg = view.findViewById(R.id.txt_login_error);
        user = new User();

        weightRepo = UserWeightRepository.getInstance(requireActivity().getApplicationContext());

        authenticatedUserManager = AuthenticatedUserManager.getInstance();

        String title = getString(R.string.title_login);
        ((MainActivity) getActivity()).setUpToolbar(title, false);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String textInputUsername = etUsername.getText().toString().trim();
        String textInputPassword = etPassword.getText().toString().trim();

        // Handle click event for login and user registration
        switch(view.getId()) {
            case (R.id.button_login):
                loginUser(textInputUsername, textInputPassword);
                break;
            case (R.id.button_register):
                addUser(textInputUsername, textInputPassword);
                break;
            default:
                break;
        }
    }

    /**
     * Checks if the username and password fields are not empty, then compares the entered username
     * and password to the user table for a match. If a username exists and the password matches the
     * database, the current authenticated user is set and logged in to the home screen.
     * @param username - the username entered in the username field
     * @param password - the password entered in the password field
     */
    public void loginUser(String username, String password) {

        if (username.isEmpty() || password.isEmpty()) {
            txtErrorMsg.setText("Username and password required.");
            return;
        }

        if (!checkUserExists(username) || !user.getPassword().equals(password)) {
            txtErrorMsg.setText("Incorrect username or password.");
            return;
        }

        AuthenticatedUser user = new AuthenticatedUser(this.user.getId());
        authenticatedUserManager.setUser(user);

        Toast t = Toast.makeText(getActivity(), "Welcome, " + username, Toast.LENGTH_LONG);
        t.show();

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_container, HomeFragment.class, null)
                .commit();
    }


    /**
     * Adds a user to the user table if the username is not already in use
     * and the username and password fields are not empty.
     * @param username - the username entered in the username field
     * @param password - the password entered in the password field
     */
    public void addUser(String username, String password) {
        User newUser = new User();
        String message = "An account with that username already exists.";

        if (!checkUserExists(username)) {
            newUser.setUsername(username);
            newUser.setPassword(password);
            weightRepo.addUser(newUser);
            message = "Account created, please log in.";
        }

        if (username.isEmpty() || password.isEmpty()) {
            message = "Username and password required.";
        }

        txtErrorMsg.setText(message);
    }

    /**
     * Queries the user table to check if the username entered exists in the database. Also sets
     * the current user object to the user returned from the query, or null if no match is found.
     * @param username - the username entered in the username field
     * @return - true if the username matches an existing user in the user table
     *           false if no match is found
     */
    public Boolean checkUserExists(String username) {

        // Set the class user object to the user returned from the query
        user = weightRepo.getUser(username);

        // Check if the username exists in the "User" table
        return user != null;
    }
}