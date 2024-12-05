package com.example.loginactivity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Add_Meals_Activity extends AppCompatActivity {

    ListView add_meals_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_meals);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        add_meals_listview = findViewById(R.id.add_meals_listview);

        // Arrays for meal names and calories
        String[] mealNames = {
                "Grilled Chicken Salad",
                "Spaghetti Carbonara",
                "Vegetable Stir Fry",
                "Beef Burger",
                "Chicken Alfredo Pasta",
                "Caesar Salad",
                "BBQ Ribs",
                "Margherita Pizza",
                "Tuna Sandwich",
                "Vegan Buddha Bowl"
        };

        int[] mealCalories = {
                350, // Grilled Chicken Salad
                700, // Spaghetti Carbonara
                400, // Vegetable Stir Fry
                800, // Beef Burger
                650, // Chicken Alfredo Pasta
                300, // Caesar Salad
                900, // BBQ Ribs
                750, // Margherita Pizza
                450, // Tuna Sandwich
                500  // Vegan Buddha Bowl
        };

        int[] mealImages = {
                R.drawable.grilled_chicken_salad, // Replace with actual drawable resource IDs
                R.drawable.spaghetti_carbonara,
                R.drawable.vegetable_stir_fry,
                R.drawable.beef_burger,
                R.drawable.chicken_alfredo_pasta,
                R.drawable.caesar_salad,
                R.drawable.bbq_ribs,
                R.drawable.margherita_pizza,
                R.drawable.tuna_sandwich,
                R.drawable.vegan_buddha_bowl
        };

        // Set the custom adapter
        CustomAdapterMeals adapter = new CustomAdapterMeals(this, mealNames, mealImages, mealCalories);
        add_meals_listview.setAdapter(adapter);
    }
}
