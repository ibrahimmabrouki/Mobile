package com.example.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ResetPass_Activity extends AppCompatActivity {

    //activity reset password layout
    Button acc_rec_submit_code, acc_rec_proceed;
    EditText acc_rec_ed_new_pass;
    EditText acc_rec_ed_confirm;
    TextView acc_rec_tv_pass;
    TextView acc_rec_tv_confirm;
    String contact_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        acc_rec_ed_new_pass = findViewById(R.id.acc_rec_ed_new_pass2);
        acc_rec_ed_confirm = findViewById(R.id.acc_rec_ed_confirm2);
        acc_rec_tv_pass = findViewById(R.id.acc_rec_tv_pass2);
        acc_rec_tv_confirm = findViewById(R.id.acc_rec_tv_confirm2);
        acc_rec_submit_code = findViewById(R.id.acc_rec_submit_code);

        Intent resetPass = getIntent();
        contact_value = resetPass.getStringExtra(contact_value).trim();

        // Add TextWatcher for password fields
        acc_rec_ed_new_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Leave empty
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String newPassword = acc_rec_ed_new_pass.getText().toString().trim();

                if (!isValidPassword(newPassword)) {
                    acc_rec_ed_new_pass.setError("Password must be at least 8 characters long");
                } else {
                    acc_rec_ed_new_pass.setError(null); // No error
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Leave empty
            }
        });

        acc_rec_ed_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Leave empty
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String confirmPassword = acc_rec_ed_confirm.getText().toString().trim();
                String newPassword = acc_rec_ed_new_pass.getText().toString().trim();

                if (!confirmPassword.equals(newPassword)) {
                    acc_rec_ed_confirm.setError("Passwords do not match");
                } else {
                    acc_rec_ed_confirm.setError(null); // No error
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Leave empty
            }
        });



        // Submit button logic
        acc_rec_submit_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = acc_rec_ed_new_pass.getText().toString().trim();
                String confirmPassword = acc_rec_ed_confirm.getText().toString().trim();

                if (!isValidPassword(newPassword)) {
                    acc_rec_ed_new_pass.setError("Password must be at least 8 characters long");
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    acc_rec_ed_confirm.setError("Passwords do not match");
                    return;
                }

                // Proceed with reset logic (e.g., send to server)
                resetPassword(contact_value, newPassword);
            }
        });
    }

    // Helper method to validate password
    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    public void resetPassword(String contact_value, String password) {
        // Define the URL for your backend endpoint
        String url = "http://10.0.2.2/detrackerphp/reset_password.php?contact_value=" + contact_value + "&password=" + password;

        // Make a GET request using Volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response
                        if (response.equals("success")) {
                            Toast.makeText(getApplicationContext(), "Password reset successful", Toast.LENGTH_SHORT).show();
                            // Navigate to the login screen or another activity
                            Intent intent = new Intent(ResetPass_Activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (response.equals("error")) {
                            Toast.makeText(getApplicationContext(), "Failed to reset password. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Unexpected response: " + response, Toast.LENGTH_SHORT).show();
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

}

