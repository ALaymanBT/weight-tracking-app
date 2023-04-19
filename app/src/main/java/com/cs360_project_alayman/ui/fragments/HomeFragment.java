package com.cs360_project_alayman.ui.fragments;

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
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cs360_project_alayman.ui.adapters.WeightAdapter;
import com.cs360_project_alayman.data.entities.Weight;
import com.cs360_project_alayman.data.entities.WeightGoal;
import com.cs360_project_alayman.ui.activities.MainActivity;
import com.cs360_project_alayman.R;
import com.cs360_project_alayman.utils.auth.AuthenticatedUserManager;
import com.cs360_project_alayman.utils.notification.NotificationHelper;
import com.cs360_project_alayman.viewmodel.WeightGoalViewModel;
import com.cs360_project_alayman.viewmodel.WeightViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private AuthenticatedUserManager authenticatedUserManager;
    private RecyclerView weightRecyclerView;
    private WeightAdapter weightAdapter;
    private WeightViewModel weightViewModel;
    private WeightGoalViewModel weightGoalViewModel;
    private NotificationHelper notificationHelper;
    private TextView txtGoalWeight;
    private TextView txtProgress;
    private TextView txtCurrentWeight;
    private long currentUser;
    private WeightGoal userGoalWeight;

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
        notificationHelper = NotificationHelper.getInstance();
        notificationHelper.setUserId(currentUser);

        weightGoalViewModel = new ViewModelProvider(this).get(WeightGoalViewModel.class);
        weightViewModel = new ViewModelProvider(this).get(WeightViewModel.class);
        weightAdapter = new WeightAdapter(this, weightViewModel);

        FloatingActionButton addFab = view.findViewById(R.id.fab_add);
        txtGoalWeight = view.findViewById(R.id.goal_weight);
        txtCurrentWeight = view.findViewById(R.id.current_weight);
        txtProgress = view.findViewById(R.id.weight_progress);

        weightRecyclerView = view.findViewById(R.id.weight_list);
        weightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Set up the toolbar menu for this fragment only
        MenuHost menuHost = getActivity();
        Objects.requireNonNull(menuHost).addMenuProvider(new MenuProvider() {
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
                        notificationHelper.userLoggedOut();
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
            createDialog(null, 0);
        });

        initData();
    }

    /**
     * Queries the weight table for a list of weights matching the current user,
     * then gets LiveData from the ViewModel and sets up the RecyclerView
     * to display the returned data
     */
    public void initData() {
        userGoalWeight = weightGoalViewModel.getWeightGoal(currentUser);

        // Prompt user to add a goal weight if no goal weight exists in the database
        if (userGoalWeight == null) {
            createDialog(null, 1);
        }
        else {
            txtGoalWeight.setText(Double.toString(userGoalWeight.getGoalWeight()));
        }

        // Get weight list live data for current logged in user
        weightViewModel.getWeightList(currentUser)
                .observe(getViewLifecycleOwner(), (weights) -> {

                    if(weights.size() > 0) {
                        double currentWeight = weights.get(0).getWeight();
                        Double startingWeight = weights.get(weights.size() - 1).getWeight();

                        if(notificationHelper.getNotificationPreference()) {
                            switch (userGoalWeight.getGoalType()) {
                                case 0:
                                    if (userGoalWeight.getGoalWeight() >= currentWeight) {
                                        notificationHelper.createNotification();
                                    }
                                    break;
                                case 1:
                                    if (userGoalWeight.getGoalWeight() <= currentWeight) {
                                        notificationHelper.createNotification();
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        txtCurrentWeight.setText(Double.toString(currentWeight));

                        // Set progress card to the newest minus the oldest weight
                        txtProgress.setText(Double.toString(currentWeight - startingWeight));
                        weightAdapter.setWeightList(weights);
                        weightRecyclerView.setAdapter(weightAdapter);
                    }

                });




    }

    /**
     * @param weight - the weight to update, or null if adding a new weight
     * @param dialogType - the type of dialog to create. 0 for add/edit weight entry,
     *                   or 1 for add weight goal
     * Creates dialog to add a new weight entry if called by the FAB, or to edit a weight entry if
     * called by a long click on a weight.
     */
    public void createDialog(Weight weight, int dialogType) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.edit_entry_dialog);

        Button btnCancel = dialog.findViewById(R.id.edit_button_negative);
        Button btnSave = dialog.findViewById(R.id.edit_button_positive);
        EditText etWeight = dialog.findViewById(R.id.weight_input);
        TextView txtTitle = dialog.findViewById(R.id.edit_entry_title);
        TextView txtLabel = dialog.findViewById(R.id.entry_secondary_label);
        TextView txtError = dialog.findViewById(R.id.text_dialog_error);


        // Create add/edit dialog
        if (dialogType == 0) {
            TextView txtDate = dialog.findViewById(R.id.date_input);
            Weight newWeight;

            // Set up the date label and date picker
            txtLabel.setText(R.string.date);
            txtDate.setVisibility(View.VISIBLE);

            // Set title and selected weight values for edit dialog, add title otherwise
            if (weight != null) {
                newWeight = weight;
                txtTitle.setText(R.string.edit_weight_entry);
                etWeight.setText(Double.toString(weight.getWeight()));
                txtDate.setText(weight.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
            }
            else {
                newWeight = new Weight();
                txtTitle.setText(R.string.add_weight_entry);
                txtDate.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
            }

            btnSave.setOnClickListener((view) -> {
                String message;
                String errorMessage = null;
                double weightValue;

                DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
                newWeight.setDate(LocalDate.parse(txtDate.getText(), formatter));

                if (false) {
                    errorMessage = "A daily weight entry was already entered on this date. Please enter a different date.";
                }
                else {
                    try {
                        weightValue = Double.parseDouble(etWeight.getText().toString());
                        newWeight.setWeight(weightValue);

                        if (weight == null) {
                            if (!checkDuplicateDate(newWeight)) {
                                newWeight.setUserId(currentUser);
                                weightViewModel.addWeight(newWeight);
                                message = "Added daily weight!";
                                dialog.dismiss();
                                txtDate.setVisibility(View.GONE);
                                Toast t = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
                                t.show();
                            }
                            else {
                                errorMessage = "A daily weight entry was already entered on this date. Please enter a different date.";
                            }
                        }

                        else{
                            if (!checkDuplicateDate(newWeight) || (checkDuplicateDate(newWeight) && newWeight.getDate() == weight.getDate())) {
                                weightViewModel.updateWeight(newWeight);
                                message = "Entry updated";
                                dialog.dismiss();
                                txtDate.setVisibility(View.GONE);
                                Toast t = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
                                t.show();
                            }
                            else {
                                errorMessage = "A daily weight entry was already entered on this date. Please enter a different date.";
                            }
                        }

                    }

                    catch (NumberFormatException e) {
                        errorMessage = "Please enter a valid weight";
                    }
                }
                if (errorMessage != null) {
                    txtError.setText(errorMessage);
                }
            });

            // Display date picker dialog
            txtDate.setOnClickListener((v) -> {
                LocalDate dateToShow;
                if (weight == null) {
                    dateToShow = LocalDate.now();
                }
                else {
                    dateToShow = newWeight.getDate();
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        (datePicker, year, month, day) -> {
                            LocalDate date = LocalDate.of(year, month + 1, day);
                            String formattedDate = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
                            txtDate.setText(formattedDate);
                        },
                        dateToShow.getYear(), dateToShow.getMonthValue() - 1, dateToShow.getDayOfMonth());
                datePickerDialog.show();
            });

        }

        // Create a set goal type dialog
        if (dialogType == 1) {
            txtLabel.setText(R.string.set_goal_weight);
            RadioGroup goalRadioGroup = dialog.findViewById(R.id.goal_radio_group);
            goalRadioGroup.setVisibility(View.VISIBLE);

            btnSave.setOnClickListener((view -> {
                String message = null;
                int goalType = -1;

                // Goal type is stored in the database as an int
                switch(goalRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.radio_goal_lose:
                        goalType = 0;
                        break;
                    case R.id.radio_goal_gain:
                        goalType = 1;
                        break;
                    default:
                        message = "Select a goal type";
                        break;
                }
                try {
                    if (goalType != -1) {
                        WeightGoal weightGoal = new WeightGoal();
                        weightGoal.setGoalWeight(Double.parseDouble(etWeight.getText().toString()));
                        weightGoal.setGoalType(goalType);
                        weightGoal.setUserId(currentUser);
                        userGoalWeight = weightGoal;
                        weightGoalViewModel.addWeightGoal(weightGoal);
                        txtGoalWeight.setText(Double.toString(weightGoal.getGoalWeight()));
                        goalRadioGroup.setVisibility(View.GONE);
                        dialog.cancel();
                    }
                }
                catch (NumberFormatException e) {
                    message = "Please enter a weight";
                }
                if (message != null) {
                    txtError.setText(message);
                }
            }));
        }

        btnCancel.setOnClickListener((v) -> dialog.cancel());

        dialog.show();
        dialog.setOnCancelListener((v) -> {
            if (weightGoalViewModel.getWeightGoal(currentUser) == null) {
                dialog.show();
                txtError.setText("Goal weight and type are required");
            }
        });
    }

    /**
     *
     * @param weight - the weight to be added to the database
     * @return - false if a weight with a duplicate date exists for the current user in the database,
     *           true otherwise
     */
    public Boolean checkDuplicateDate(Weight weight) {
        if (weightViewModel.getDate(currentUser, weight.getDate()) != null) {
            return true;
        }

        return false;
    }
}