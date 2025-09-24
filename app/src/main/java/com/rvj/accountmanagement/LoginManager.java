package com.rvj.accountmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginManager extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signUpButton;

    private static final String PREFS_NAME = "account_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        signUpButton = findViewById(R.id.btnSignUp);

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginManager.this, SignupActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String usernameOrEmail = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(usernameOrEmail) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.msg_login_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedEmail = prefs.getString(KEY_EMAIL, null);
        String savedPassword = prefs.getString(KEY_PASSWORD, null);

        boolean matches = savedEmail != null
                && savedPassword != null
                && password.equals(savedPassword)
                && (usernameOrEmail.equalsIgnoreCase(savedEmail));

        if (matches) {
            Toast.makeText(this, R.string.msg_login_success, Toast.LENGTH_SHORT).show();
            Intent dashboardIntent = new Intent(LoginManager.this, DashboardActivity.class);
            startActivity(dashboardIntent);
        } else {
            Toast.makeText(this, R.string.msg_login_failed, Toast.LENGTH_SHORT).show();
        }
    }
}
