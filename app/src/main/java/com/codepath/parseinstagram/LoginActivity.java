package com.codepath.parseinstagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignUp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        // Add Toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        etUsername = findViewById( R.id.etUsername );
        etPassword = findViewById( R.id.etPassword );
        btnLogin = findViewById( R.id.btnLogin );
        btnSignUp = findViewById( R.id.btnSignUp );




        btnSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( LoginActivity.this, SignUpActivity.class );
                startActivity(i);
            }
        } );

        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                login(username, password);
            }
        } );
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground( username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    // TODO: Better error handling
                    Log.e(TAG, "Issues with login");
                    e.printStackTrace();
                    return;
                }
                // TODO: navigate to new activity if user login property

              goMainActivity();

            }
        } );
    }

    private void goMainActivity() {
        Intent i = new Intent( this, MainActivity.class );
        startActivity(i);
        finish();
    }
}
