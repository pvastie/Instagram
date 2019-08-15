package com.codepath.parseinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

   // private EditText etFName;
   // private EditText etLName;
    private EditText etUser;
    private EditText etEmail;
    private EditText etPwd;
    private Button btnSignUP;


    private static final String TAG = "SignUpActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );

//        etFName = findViewById( R.id.etFName );
//        etLName = findViewById( R.id.etLName );
        etUser = findViewById( R.id.etUser );
        etEmail = findViewById( R.id.etEmail );
        etPwd = findViewById( R.id.etPwd);
        btnSignUP = findViewById( R.id.btnSignUP);


        btnSignUP.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            String firstName = etFName.getText().toString();
//            String lastName = etLName.getText().toString();
            String username = etUser.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPwd.getText().toString();


                ParseUser user = new ParseUser();
                //User user1 = new User();

                //user1.setFName( firstName );
                //user1.setLName( lastName );
                user.setUsername( username );
                user.setEmail( email );
                user.setPassword( password );

//                user1.signUpInBackground( new SignUpCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        if (e != null) {
//                            Log.e( TAG, "Error while saving" );
//                            e.printStackTrace();
//                            return;
//                        }
//
//                        Log.d( TAG, "Success!" );
//
//                        etFName.setText( "" );
//                        etLName.setText( "" );
//
//                    }
//                } );

                user.signUpInBackground( new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e( TAG, "Error while saving" );
                            e.printStackTrace();
                            return;
                        }

                        Log.d( TAG, "Success!" );

                        etEmail.setText( "" );
                        etPwd.setText( "" );
                        etUser.setText( "" );

                        Intent intent = new Intent( SignUpActivity.this, LoginActivity.class );
                        startActivity( intent );

                    }
                } );

            }
        } );


    }
}
