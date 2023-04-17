package com.cs360_project_alayman.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs360_project_alayman.WeightAdapter;
import com.cs360_project_alayman.data.entities.Weight;
import com.cs360_project_alayman.repository.UserWeightRepository;
import com.cs360_project_alayman.ui.activities.MainActivity;
import com.cs360_project_alayman.R;
import com.cs360_project_alayman.utils.auth.AuthenticatedUserManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private AuthenticatedUserManager authenticatedUserManager;
    private UserWeightRepository userWeightRepository;
    private FloatingActionButton addFab;
    private ArrayList<Weight> weightArrayList;
    private RecyclerView weightRecyclerView;
    private WeightAdapter weightAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String title = getString(R.string.title_home);
        ((MainActivity) requireActivity()).setUpToolbar(title, false);
        authenticatedUserManager = AuthenticatedUserManager.getInstance();
        userWeightRepository = UserWeightRepository.getInstance(getContext());
        addFab = view.findViewById(R.id.fab_add);
        weightRecyclerView = view.findViewById(R.id.weight_list);
        weightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Set up the toolbar menu for this fragment only
        MenuHost menuHost = getActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.appbar_menu, menu);
            }
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Toolbar menu selection handling
                switch (menuItem.getItemId()) {
                    case R.id.action_settings:
                        fragmentTransaction.replace(R.id.nav_host_fragment_container, SettingsFragment.class, null)
                                .addToBackStack(null)
                                .commit();
                        return true;
                    case R.id.action_logout:
                        authenticatedUserManager.setUser(null);
                        fragmentTransaction.replace(R.id.nav_host_fragment_container, LoginFragment.class, null)
                                .commit();
                        return true;
                    default:
                        return false;
                }
            }
        }, getViewLifecycleOwner());

        // Initialize the floating action button
        addFab.setOnClickListener((v) -> {
            showDialog();
        });

        // Initialize RecyclerView for the weight list
        initData();

    }

    /**
     * Queries the weight table for a list of weights matching the current user,
     * then sets up the RecyclerView to display the returned data
     */
    public void initData() {
        // Get weight list for current logged in user
        weightArrayList = userWeightRepository.getWeightList(authenticatedUserManager.getUser().getId());
        weightAdapter = new WeightAdapter(getContext(), weightArrayList);
        weightRecyclerView.setAdapter(weightAdapter);
        weightAdapter.notifyDataSetChanged();
    }
    // FIXME: Add logic to check for duplicate date entry
    // FIXME: Add logic to edit weight after long click on weight in recycler view
    /**
     * Creates dialog to add a new weight entry if called by the FAB, or to edit a weight entry if
     * called by a long click on a weight
     */
    public void showDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.edit_entry_dialog);
        dialog.show();
        Button btnSave = dialog.findViewById(R.id.edit_button_positive);
        Button btnCancel = dialog.findViewById(R.id.edit_button_negative);
        EditText etWeight = dialog.findViewById(R.id.weight_input);

        btnSave.setOnClickListener((v) -> {
            Weight weight = new Weight();
            // FIXME: Add function here to check for errors
            Double weightValue = Double.parseDouble(etWeight.getText().toString());
            weight.setWeight(weightValue);
            weight.setUserId(authenticatedUserManager.getUser().getId());
            userWeightRepository.addWeight(weight);
            weightAdapter.notifyDataSetChanged();
            Toast t = Toast.makeText(getActivity(), "Added daily weight!", Toast.LENGTH_LONG);
            t.show();
            dialog.dismiss();
        });
        btnCancel.setOnClickListener((v) -> {
            dialog.dismiss();
        });
    }
}