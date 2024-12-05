package com.example.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Acount_Rec_Activity extends AppCompatActivity {


    EditText acc_rec_input;
    EditText acc_rec_ed_verification_code;
    TextView acc_rec_tv_email;
    TextView acc_rec_tv_phone;
    TextView acc_rec_tv1;
    Button acc_rec_proceed;
    int verificationtag = 0;

    final static String code = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_rec);

        // Initialize views
        acc_rec_input = findViewById(R.id.acc_rec_input);

        acc_rec_ed_verification_code = findViewById(R.id.acc_rec_ed_verification_code);
        acc_rec_tv_email = findViewById(R.id.acc_rec_tv_email);
        acc_rec_tv_phone = findViewById(R.id.acc_rec_tv_phone);
        acc_rec_tv1 = findViewById(R.id.acc_rec_tv1);
        acc_rec_proceed = findViewById(R.id.acc_rec_proceed);

        // Default tag to track input type
        final String[] tag = {"Email"}; // Default to Email

        // Switch to phone input
        acc_rec_tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acc_rec_tv1.setText("Phone Number");
                acc_rec_input.setHint("Enter your phone number");
                acc_rec_input.setInputType(InputType.TYPE_CLASS_NUMBER);
                tag[0] = "Phone"; // Update tag
            }
        });

        // Switch to email input
        acc_rec_tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acc_rec_tv1.setText("Email Address");
                acc_rec_input.setHint("Enter your email address");
                acc_rec_input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                tag[0] = "Email"; // Update tag
            }
        });

        // Add TextWatcher for acc_rec_input
        acc_rec_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Leave empty
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String input = acc_rec_input.getText().toString().trim();

                if (tag[0].equals("Email")) {
                    // Validate email
                    if (isValidEmail(input)) {
                        acc_rec_input.setError(null); // No error
                    } else {
                        acc_rec_input.setError("Invalid email address");
                    }
                }

                else if (tag[0].equals("Phone")) {
                    // Validate phone number
                    if (isValidPhone(input)) {
                        acc_rec_input.setError(null); // No error
                    } else {
                        acc_rec_input.setError("Invalid phone number");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Leave empty
            }
        });

        // Add TextWatcher to validate the verification code
        acc_rec_ed_verification_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Leave empty
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String verificationCode = acc_rec_ed_verification_code.getText().toString().trim();

                if (isValidVerificationCode(verificationCode)) {
                    acc_rec_ed_verification_code.setError(null); // No error
                } else {
                    acc_rec_ed_verification_code.setError("Invalid verification code");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Leave empty
            }
        });

        /*acc_rec_ed_verification_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Leave empty
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String verificationCode = acc_rec_ed_verification_code.getText().toString().trim();

                // Check if the code is valid
                if (isValidVerificationCode(verificationCode) && verificationCode.equals(code)) {
                    acc_rec_ed_verification_code.setError(null); // No error

                    Intent resetPass = new Intent(getApplicationContext(), ResetPass_Activity.class);
                    if(verificationtag == 1){
                        String contact_value_send = acc_rec_input.getText().toString().trim();
                        resetPass.putExtra("contact_value", contact_value_send);
                        startActivity(resetPass);
                    }

                }

                else {
                    acc_rec_ed_verification_code.setError("Invalid verification code");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Leave empty
            }
        });*/

        acc_rec_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact_type = tag[0]; // Email or Phone
                String contact_value = acc_rec_input.getText().toString().trim();
                String verificationCode = acc_rec_ed_verification_code.getText().toString().trim();

                // Validate inputs
                if (TextUtils.isEmpty(contact_value)) {
                    acc_rec_input.setError("Field is empty!");
                    return;
                }

                if (TextUtils.isEmpty(verificationCode)) {
                    acc_rec_ed_verification_code.setError("Verification code is empty!");
                    return;
                }

                // Validate the contact_value with the server
                String url = "http://10.0.2.2/detrackerphp/verify_contact_value.php?contact_type="
                        + contact_type + "&contact_value=" + contact_value;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Handle the response
                                if (response.equals("success")) {
                                    verificationtag = 1; // Mark verification as successful

                                    // Now check the verification code
                                    if (verificationCode.equals(code)) {
                                        // Redirect to ResetPass_Activity
                                        Intent resetPass = new Intent(Acount_Rec_Activity.this, ResetActivity.class);
                                        resetPass.putExtra("contact_value", contact_value);
                                        startActivity(resetPass);
                                    }
                                    else {
                                        acc_rec_ed_verification_code.setError("Invalid verification code");
                                    }
                                } else if (response.equals("error")) {
                                    acc_rec_input.setError("Contact not found");
                                } else {
                                    Toast.makeText(getApplicationContext(), "Unexpected response", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                // Add the request to the RequestQueue
                Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
            }
        });




        /*acc_rec_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact_type = tag[0];
                String contact_value = acc_rec_input.getText().toString().trim();

                if (TextUtils.isEmpty(contact_value)) {
                    acc_rec_input.setError("Field is empty!");
                    return;
                }

                // Define the URL for your backend endpoint
                String url = "http://10.0.2.2/detrackerphp/verify_contact_value.php?contact_type="+contact_type+"&contact_value="+contact_value;

                // Make a GET request using Volley
                //RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Handle the response
                                if (response.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Valid " + contact_value, Toast.LENGTH_SHORT).show();
                                    verificationtag = 1;
                                    //send the message using message but first take permission
                                }
                                else if (response.equals("error")) {
                                    acc_rec_input.setError("Contact not found");
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Unexpected response", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                // Add the request to the RequestQueue
                Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
            }
        });*/




    }



    // Helper method to validate email
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Helper method to validate phone number
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}"); // Example: 10-digit number validation
    }


    private boolean isValidVerificationCode(String code) {
        return code.matches("\\d{6}"); // Validate 6-digit code
    }

}




