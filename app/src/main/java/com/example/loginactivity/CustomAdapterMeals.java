package com.example.loginactivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapterMeals extends CustomAdapterEvents {
    int[] calories;

    // Constructor
    public CustomAdapterMeals(Context context, String[] text, int[] imageids, int[] calories) {
        super(context, text, imageids); // Call the superclass constructor
        this.calories = calories; // Assign the additional field
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Inflate the row layout
        View rowview = inflater.inflate(R.layout.row_meals, null);

        // Set text and image for the meal
        ((TextView) rowview.findViewById(R.id.row_meal_tv)).setText(text[i] + " " + calories[i]);
        ((ImageView) rowview.findViewById(R.id.row_meal_image)).setImageResource(imageids[i]);

        // Configure the remove button
       /* ImageView removeButton = rowview.findViewById(R.id.row_meal_remove);
        if (removeButton != null) {
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Remove clicked at position " + i, Toast.LENGTH_SHORT).show();
                }
            });
        }*/

        // Optional: Handle row clicks
        rowview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You clicked on position " + i, Toast.LENGTH_SHORT).show();
            }
        });

        return rowview;
    }

}
