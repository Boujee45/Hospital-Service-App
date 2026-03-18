package com.example.hospitalservicerequest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    EditText userName;
    EditText Password;
    TextView forgotPassword;
    Button Login;
    TextView signUp;
    TextView Output;
    databaseHelper dbHelper;


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

        userName = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.login);
        signUp = findViewById(R.id.signup);
        Output = findViewById(R.id.output);
        forgotPassword = findViewById(R.id.forgot);
        dbHelper = new databaseHelper(this);

        Login.setOnClickListener(v ->
        {
            String userNameText = userName.getText().toString().trim();
            String passwordText = Password.getText().toString().trim();

            if(userNameText.isEmpty() || passwordText.isEmpty())
            {
                Output.setText(getString(R.string.empty_fields));
                Output.setVisibility(View.VISIBLE);
                return;
            }

            if(userNameText.equals("Admin") && passwordText.equals("admin"))
            {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }

            if(dbHelper.checkUser(userNameText,passwordText))
            {
                Toast.makeText(this,getString(R.string.login_success),Toast.LENGTH_LONG)
                        .show();

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("username", userNameText);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(this,getString(R.string.invalid_credentials),Toast.LENGTH_LONG)
                        .show();
            }

        });

        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, fPassword.class);
            startActivity(intent);
            finish();
        });

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });


    }


}