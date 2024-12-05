package com.example.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //for main activity
    Button btlogin;
    TextView tv1;

    //for login
    Button loginbt;
    TextView logintv3, logintv5, logintv6, logintv7, logintv8;
    EditText login_ed_username, login_ed_input, login_ed_password;


    //for create account
    Button btsignup;
    TextView createtv3, createtv4, createtv8, createtv10, createtv11;
    EditText create_ed_username, create_ed_email_address, create_ed_password, create_ed_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle system insets for immersive UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views from the first layout
        btlogin = findViewById(R.id.btlogin);
        tv1 = findViewById(R.id.tv1);



        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch to the login layout

                Intent i_MainActivity1 = new Intent(getApplicationContext(), Add_Meals_Activity.class);
                startActivity(i_MainActivity1);
                //setContentView(R.layout.activity_main_login);
                //login();
            }
        });



        //Changing to the Create account layout
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_main_create_account);
                CreateAccount();
            }
        });
    }



    public void login(){
        // Initialize views from the second layout
        logintv3 = findViewById(R.id.logintv3);
        logintv5 = findViewById(R.id.logintv5);
        logintv6 = findViewById(R.id.logintv6);
        logintv7 = findViewById(R.id.logintv7);
        logintv8 = findViewById(R.id.logintv8);
        loginbt = findViewById(R.id.loginbt);
        login_ed_username = findViewById(R.id.login_ed_username);
        login_ed_input = findViewById(R.id.login_ed_input);
        login_ed_password = findViewById(R.id.login_ed_password);

        // Tag to track whether the user is entering email or phone
        final String[] tag = {"Email"}; // Default is "Email"


        // Text watcher tracking the email/phone input
        login_ed_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Leave empty for now
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Only validate if the tag is "Email"
                if (tag[0].equals("Email")) {
                    // Check if the email is valid
                    if (isValidEmail(login_ed_input.getText().toString().trim())) {
                        login_ed_input.setError(null);  // No error
                    }

                    else {
                        login_ed_input.setError("Invalid email address");  // Show error
                    }
                }

                else if (tag[0].equals("Phone")) {
                    // Only validate for phone number if the tag is "Phone"
                    if (isValidPhone(login_ed_input.getText().toString().trim())) {
                        login_ed_input.setError(null);  // No error
                    }

                    else {
                        login_ed_input.setError("Invalid phone number");  // Show error
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Leave empty for now
            }
        });
        // Set click listener for logintv7
        logintv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logintv3.setText("Phone Number");
                login_ed_input.setHint("Enter your phone number");
                login_ed_input.setInputType(InputType.TYPE_CLASS_NUMBER);
                tag[0] = "Phone"; // Update tag to "Phone"
            }
        });

        logintv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logintv3.setText("Email Address");
                login_ed_input.setHint("Enter your Email Address");
                login_ed_input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                tag[0] = "Email"; // Update tag to "Email"
            }
        });

        logintv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resetPass = new Intent(getApplicationContext(), Acount_Rec_Activity.class);
                startActivity(resetPass);
            }
        });

        //need database
        // Set click listener for login button
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = login_ed_username.getText().toString().trim();
                String input = login_ed_input.getText().toString().trim();
                String password = login_ed_password.getText().toString().trim();

                /*if (tag[0].equals("Email")) {
                    // Validate email
                    if (!isValidEmail(input)) {
                        login_ed_input.setError("Invalid email address");
                        return;
                    }
                }

                else if (tag[0].equals("Phone")) {
                    // Validate phone number
                    if (!isValidPhone(input)) {
                        login_ed_input.setError("Invalid phone number");
                        return;
                    }
                }

                // Validate password (ensure it's not empty)
                if (!isValidPassword(password)) {
                    login_ed_password.setError("Password must be at least 8 characters long");
                    return;
                }*/

                // Proceed with login

                loginUser(tag[0],username, password);
            }
        });


        //create an account
        logintv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_main_create_account);
                CreateAccount();
            }
        });

    }




    public void CreateAccount(){
        createtv3 = findViewById(R.id.createtv3);
        createtv4 = findViewById(R.id.createtv4);
        createtv8 = findViewById(R.id.createtv8);
        createtv10 = findViewById(R.id.createtv10);
        createtv11 = findViewById(R.id.createtv11);

        create_ed_email_address = findViewById(R.id.create_ed_email_address);
        create_ed_password = findViewById(R.id.create_ed_password);
        create_ed_confirm = findViewById(R.id.create_ed_confirm);
        create_ed_username = findViewById(R.id.create_ed_username);

        //tag indicating whether the user is entering email or phone number
        final String[] tag = {"Email"}; // Default is "Email"


        btsignup = findViewById(R.id.btsignunp);

        // Text watcher tracking the email/phone input
        create_ed_email_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Leave empty for now
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Only validate if the tag is "Email"
                if (tag[0].equals("Email")) {
                    // Check if the email is valid
                    if (isValidEmail(create_ed_email_address.getText().toString().trim())) {
                        create_ed_email_address.setError(null);  // No error
                    }

                    else {
                        create_ed_email_address.setError("Invalid email address");  // Show error
                    }
                }

                else if (tag[0].equals("Phone")) {
                    // Only validate for phone number if the tag is "Phone"
                    if (isValidPhone(create_ed_email_address.getText().toString().trim())) {
                        create_ed_email_address.setError(null);  // No error
                    }

                    else {
                        create_ed_email_address.setError("Invalid phone number");  // Show error
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Leave empty for now
            }
        });


        //text watcher tracking the password
        create_ed_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // You can leave this method empty or add logic for handling before text change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Check if the password is valid
                String password = create_ed_password.getText().toString().trim();
                if (isValidPassword(password)) {
                    // Clear the error when password is valid
                    create_ed_password.setError(null);
                } else {
                    // Optionally show an error message when the password is invalid
                    create_ed_password.setError("Password must be at least 8 characters long");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // You can add logic for after text change if needed
            }
        });


        //Email Address
        createtv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_ed_email_address.setHint("Enter your Email address");
                create_ed_email_address.setInputType(InputType.TYPE_CLASS_TEXT);
                tag[0] = "Email";
            }
        });

        //Phone Number
        createtv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_ed_email_address.setHint("Enter your phone number");
                create_ed_email_address.setInputType(InputType.TYPE_CLASS_NUMBER);
                tag[0] = "Phone"; // Update tag to "Phone"
            }
        });

        //Terms of service
        createtv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show terms of service dialog or navigate to another page
            }
        });

        //Already have account? login here
        createtv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_main_login);
                login();
            }
        });

        btsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = create_ed_username.getText().toString().trim();
                String input = create_ed_email_address.getText().toString().trim();
                String password = create_ed_password.getText().toString().trim();
                String confirmPassword = create_ed_confirm.getText().toString().trim();


                //validating the input based on email
                if(tag[0].equals("Email")){

                    //checking for valid email
                    if (!isValidEmail(input)) {
                        create_ed_email_address.setError("Invalid email address");
                    }

                    //checking for valid password where length > 8
                    else if (!isValidPassword(password)) {
                        create_ed_password.setError("Password must be at least 8 characters long");
                    }

                    //Confirming pass
                    else if (!password.equals(confirmPassword)) {
                        create_ed_confirm.setError("Passwords do not match");
                    }

                    else {
                        // Proceed with sign-up using email
                        signUpUser("Email", input, password, username);
                    }
                }

                //validating the input based on phonenumber
                else if (tag[0].equals("Phone")) {

                    //checking for valid phone number
                    //example:\\d matches any digit (0–9). and {10} specifies that exactly 10 digits are required.
                    if (!isValidPhone(input)) {
                        create_ed_email_address.setError("Invalid phone number");
                    }


                    else if (!isValidPassword(password)) {
                        create_ed_password.setError("Password must be at least 8 characters long");
                    }

                    else if (!password.equals(confirmPassword)) {
                        create_ed_confirm.setError("Passwords do not match");
                    }

                    else {
                        // Proceed with sign-up using phone
                        signUpUser("Phone", input, password, username);
                    }
                }
            }
        });

        //upon clicking it will take the user to ProfileActivity
        createtv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fill_profile = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(fill_profile);
            }
        });

    }

    //here below are the helper methods used in the Create Account Funciton
    // Helper method to validate email
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Helper method to validate phone number
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}"); // Example: 10-digit number validation
    }

    // Helper method to validate password
    private boolean isValidPassword(String password) {
        return password.length() >= 8; // Example: Minimum length requirement
    }

    private void signUpUser(String type, String input, String password, String username) {

        String url = "http://10.0.2.2/detrackerphp/signup.php?username="+username+"&password="+password+"&contact_type="+type+"&contact_value="+input;

        // Create the StringRequest for GET request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        if (response.contains("Success")) {
                            Toast.makeText(MainActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            setContentView(R.layout.activity_main_login);
                            login();
                        } else {
                            Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        // Handle error response
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(stringRequest);

    }


    // Function to handle the login action
    private void loginUser(String type, String username, String password) {
        String username_login = login_ed_username.getText().toString().trim();
        String contactalue_login = login_ed_input.getText().toString().trim();  // This will be the email or phone number
        String password_lobin = login_ed_password.getText().toString().trim();

        String url_login = "http://10.0.2.2/detrackerphp/login.php?username="+username_login+"&password="+password_lobin+"&contact_value="+contactalue_login;
        // Create a StringRequest for GET method
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        if (response.contains("Success")) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent i_MainActivity1 = new Intent(getApplicationContext(), MainActivity1.class);
                            startActivity(i_MainActivity1);
                        }
                        else if (response.contains("Wrong Email")) {
                            Toast.makeText(MainActivity.this, "Incorrect Email Address", Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("Wrong Phone Number")) {
                            Toast.makeText(MainActivity.this, "Incorrect Phone Number", Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("Failed to login")) {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
