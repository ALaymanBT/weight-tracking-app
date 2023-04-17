package com.cs360_project_alayman.ui.fragments;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cs360_project_alayman.WeightAdapter;
import com.cs360_project_alayman.data.entities.Weight;
import com.cs360_project_alayman.data.entities.WeightGoal;
import com.cs360_project_alayman.repository.UserWeightRepository;
import com.cs360_project_alayman.ui.activities.MainActivity;
import com.cs360_project_alayman.R;
import com.cs360_project_alayman.utils.auth.AuthenticatedUserManager;
import com.cs360_project_alayman.viewmodel.WeightGoalViewModel;
import com.cs360_project_alayman.viewmodel.WeightViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeFragment extends Fragment {

    private AuthenticatedUserManager authenticatedUserManager;
    private UserWeightRepository userWeightRepository;
    private FloatingActionButton addFab;
    private RecyclerView weightRecyclerView;
    private WeightAdapter weightAdapter;
    private WeightViewModel weightViewModel;
    private WeightGoalViewModel weightGoalViewModel;
    private TextView txtGoalWeight;
    private long currentUser;
    private Dialog dialog;

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
        currentUser = authenticatedUserManager.getUser().getId();

        // FIXME: Might not need this if using viewmodel, don't forget the add button and variable declaration
        userWeightRepository = UserWeightRepository.getInstance(getContext());

        addFab = view.findViewById(R.id.fab_add);
        txtGoalWeight = view.findViewById(R.id.goal_weight);
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.edit_entry_dialog);

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
            showDialog(null);
        });

        weightGoalViewModel = new ViewModelProvider(this).get(WeightGoalViewModel.class);
        WeightGoal userGoalWeight = weightGoalViewModel.getWeightGoal(currentUser);
        if (userGoalWeight == null) {

            //FIXME: This all needs to go under add/edit dialog. For new goal weight we need radio buttons
            LinearLayout linearLayout = dialog.findViewById(R.id.entry_secondary_container);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            DatePicker datePicker = new DatePicker(getContext());
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.show();
            datePicker.setLayoutParams(layoutParams);
            linearLayout.addView(datePicker);
            //showDialog(null);
            Toast t = Toast.makeText(getActivity(), "null goal weight", Toast.LENGTH_LONG);
            t.show();

            userGoalWeight = new WeightGoal();
            //weightGoal.setGoalWeight(150.0);
            //weightGoal.setGoalType(0);
            //weightGoal.setUserId(currentUser);
            //
            //weightGoalViewModel.addWeightGoal(weightGoal);
        }
        else {
            txtGoalWeight.setText(Double.toString(userGoalWeight.getGoalWeight()));
        }


        // Initialize RecyclerView for the weight list
        initData();

    }

    /**
     * Queries the weight table for a list of weights matching the current user,
     * then gets LiveData from the ViewModel and sets up the RecyclerView
     * to display the returned data
     */
    public void initData() {
        weightViewModel = new ViewModelProvider(this).get(WeightViewModel.class);
        weightAdapter = new WeightAdapter(this, weightViewModel);

        // Get weight list live data for current logged in user
        weightViewModel.getWeightList(authenticatedUserManager.getUser().getId())
                .observe(getViewLifecycleOwner(), (weights) -> {
                    weightAdapter.setWeightList(weights);
                });

        weightRecyclerView.setAdapter(weightAdapter);
    }
    // FIXME: Add logic to check for duplicate date entry
    // FIXME: Add logic to edit weight after long click on weight in recycler view
    /**
     * Creates dialog to add a new weight entry if called by the FAB, or to edit a weight entry if
     * called by a long click on a weight. Pass null for a new entry or a weight object to edit the selected entry
     */
    public void showDialog(Weight weight) {


        Button btnCancel = dialog.findViewById(R.id.edit_button_negative);
        Button btnSave = dialog.findViewById(R.id.edit_button_positive);
        EditText etWeight = dialog.findViewById(R.id.weight_input);
        TextView txtTitle = dialog.findViewById(R.id.edit_entry_title);
        Weight newWeight;

        // Add a new weight to the database if null
        if (weight == null) {

            txtTitle.setText(R.string.add_weight_entry);
            newWeight = new Weight();

            btnSave.setOnClickListener((v) -> {
                // FIXME: Add try/catch here to check for errors?
                Double weightValue = Double.parseDouble(etWeight.getText().toString());
                newWeight.setWeight(weightValue);
                newWeight.setUserId(authenticatedUserManager.getUser().getId());
                weightViewModel.addWeight(newWeight);
                Toast t = Toast.makeText(getActivity(), "Added daily weight!", Toast.LENGTH_LONG);
                t.show();
                dialog.dismiss();
            });
        }
        // Populate fields with current weight data and set save button to update otherwise
        else {
            txtTitle.setText(R.string.edit_weight_entry);
            etWeight.setText(Double.toString(weight.getWeight()));
            newWeight = weight;

            btnSave.setOnClickListener((v) -> {
                Double weightValue = Double.parseDouble(etWeight.getText().toString());
                newWeight.setWeight(weightValue);
                weightViewModel.updateWeight(newWeight);
                Toast t = Toast.makeText(getActivity(), "Entry updated", Toast.LENGTH_LONG);
                t.show();
                dialog.dismiss();
            });
        }

        btnCancel.setOnClickListener((v) -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}