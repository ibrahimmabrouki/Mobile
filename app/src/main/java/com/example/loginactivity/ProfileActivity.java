package com.example.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    SeekBar profile_height_seekbar, profile_weight_seekbar;
    int minage = 10; int maxage = 80;
    int minheight = 50; int maxheight = 200;//in cm
    int minweight = 30; int maxweight = 200;//in kg


    TextView profiletvheight, profiletvweight;

    ImageButton profile_next_bt, profile2_next_bt;

    //Views of the profile
    ImageView profile_image;
    RadioButton profile_male_rb, profile_female_rb;
    Spinner profile_age_spinner;


    //Views of the profile2
    SeekBar profile2_weight_seekbar;
    TextView profile2tvweight;
    Spinner profile2_goals_spinner;


    //Views of the profile3
    ListView profile3_listView;
    DatePicker profile3_datePicker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ResumeCompletingProfile();
    }

    // Method to fill an array with numbers between min and max (inclusive)
    private int[] fillArray(int minage, int maxage) {
        int size = maxage - minage + 1; // Calculate the size of the array
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = minage + i;
        }
        return numbers;
    }

    // Method to set up the Spinner adapter
    private void setSpinnerAdapter(int[] numbers) {
        // Convert int[] to String[] because ArrayAdapter works with objects
        String[] stringNumbers = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            stringNumbers[i] = String.valueOf(numbers[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, stringNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_age_spinner.setAdapter(adapter);
    }

    public void ResumeCompletingProfile(){
        profile_image = findViewById(R.id.profile_image);

        profile_male_rb = findViewById(R.id.profile_male_rb);
        profile_female_rb = findViewById(R.id.profile_female_rb);

        profile_age_spinner = findViewById(R.id.profile_age_spinner);


        profile_height_seekbar = findViewById(R.id.profile_height_seekbar);
        profiletvheight = findViewById(R.id.profiletvheight);

        profile_weight_seekbar = findViewById(R.id.profile_weight_seekbar);
        profiletvweight = findViewById(R.id.profiletvweight);


        profile_next_bt = findViewById(R.id.profile_next_bt);

        profile_next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_profile2);
                //inside the resume completing profile we can controle activity_profile2
                ResumeCompletingProfile2();
            }
        });



        //recieve intent
        Intent fill_profile = getIntent();
        // Fill array and set adapter to Spinner
        int[] ageArray = fillArray(minage, maxage); // Method to fill array
        setSpinnerAdapter(ageArray);



        profile_height_seekbar.setMax(maxheight - minheight); // Set range size (e.g., 40)
        // Adjust the progress to fit the range
        profile_height_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = minheight + progress; // Calculate the actual value
                // Use 'value' as needed
                profiletvheight.setText(String.valueOf(value));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        profile_weight_seekbar.setMax(maxweight - minweight); // Set range size (e.g., 40)
        // Adjust the progress to fit the range
        profile_weight_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = minweight + progress; // Calculate the actual value
                // Use 'value' as needed
                profiletvweight.setText(String.valueOf(value));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


    }

    public void ResumeCompletingProfile2(){
        profile2_next_bt = findViewById(R.id.profile2_next_bt);
        profile2_weight_seekbar = findViewById(R.id.profile2_weight_seekbar);
        profile2tvweight = findViewById(R.id.profile2tvweight);
        profile2_goals_spinner = findViewById(R.id.profile2_goals_spinner);


        //to be programmed
        profile2_goals_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        profile2_weight_seekbar.setMax(maxweight - minweight); // Set range size (e.g., 40)
        // Adjust the progress to fit the range
        profile2_weight_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = minweight + progress; // Calculate the actual value
                // Use 'value' as needed
                profile2tvweight.setText(String.valueOf(value));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        profile2_next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_profile3);
                //inside the ResumeCompletingProfile3 we controle the actions in profile 3
                ResumeCompletingProfile3();
            }
        });
    }

    public void ResumeCompletingProfile3() {
        profile3_datePicker = findViewById(R.id.profile3_datePicker);
        profile3_listView = findViewById(R.id.profile3_listView);

        int[] imageids = {R.drawable.vacation, R.drawable.wedding, R.drawable.sport, R.drawable.summer, R.drawable.reunion, R.drawable.birthday, R.drawable.otherevent};
        String[] events = {"Vacation", "Wedding", "Sports competition", "Summer", "Reunion", "Birthday", "Other event"};
        profile3_listView.setAdapter(new CustomAdapterEvents(getApplicationContext(), events, imageids));
    }



    }