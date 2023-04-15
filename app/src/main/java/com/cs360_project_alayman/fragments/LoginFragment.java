package com.cs360_project_alayman.fragments;

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

import com.cs360_project_alayman.controller.MainActivity;
//import com.cs360_project_alayman.data.DatabaseManager;
import com.cs360_project_alayman.model.User;
import com.cs360_project_alayman.R;
import com.cs360_project_alayman.repository.UserWeightRepository;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private TextView txtErrorMsg;
    private UserWeightRepository weightRepo;
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
        btnLogin = view.findViewById(R.id.button_login);
        btnRegister = view.findViewById(R.id.button_register);
        txtErrorMsg = view.findViewById(R.id.txt_login_error);
        user = new User();

        weightRepo = UserWeightRepository.getInstance(getActivity().getApplicationContext());

        String title = getString(R.string.title_login);
        ((MainActivity) getActivity()).setUpToolbar(title, false);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    public void loginUser(String username, String password) {

        if (username.isEmpty() || password.isEmpty()) {
            txtErrorMsg.setText("Username and password required.");
            return;
        }

        if (!authenticateUser(username, password)) {
            txtErrorMsg.setText("Incorrect username or password.");
            return;
        }
        Toast t = Toast.makeText(getActivity(), "Welcome, " + username, Toast.LENGTH_LONG);
        t.show();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_container, HomeFragment.class, null)
                .commit();
    }

    public Boolean authenticateUser(String username, String password) {
        if (!checkUserExists(username)) {
            return false;
        }
        if (user.getPassword().compareTo(password) == 0) {
            return true;
        }
        return false;
    }

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

    // FIXME: Move this to an authentication class
    public Boolean checkUserExists(String username) {

        // Set the class user object to the user returned from the query
        user = weightRepo.getUser(username);

        // Check if the username exists in the "User" table
        if (user != null) {
            return true;
        }
        return false;
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
}