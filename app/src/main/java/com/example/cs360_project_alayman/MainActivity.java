package com.example.cs360_project_alayman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity implements CallbackFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setReorderingAllowed(true)
                    .add(R.id.nav_host_fragment_container, LoginFragment.class, null)
                    .commit();
        }

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_container);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
        }
    }

    @Override
    public void addFragment () {
        /*
        * Currently just getting fragment callbacks to work, but this will determine the fragment to add in the future
        *
        *
        Fragment fragment = null;
        switch(view.getId()) {
            case (R.id.fragment_login):
                fragment = new LoginFragment();
                break;
            case (R.id.fragment_home):
                fragment = new HomeFragment();
                break;
        }
        */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setReorderingAllowed(true)
                .add(R.id.nav_host_fragment_container, HomeFragment.class, null)
                .commit();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

}