package com.codepath.parseinstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFName;
    private EditText etLName;
    private EditText etUser;
    private EditText etEmail;
    private EditText etPwd;
    private Button btnSignUP;
//    private Button btnAddProfilePic;
//    private ImageView ivProfile;


    File photoFile;
    static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1234;
    static final String photoFileName = "profile.jpg";


    private static final String TAG = "SignUpActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );

        etFName = findViewById( R.id.etFName );
        etLName = findViewById( R.id.etLName );
        etUser = findViewById( R.id.etUser );
        etEmail = findViewById( R.id.etEmail );
        etPwd = findViewById( R.id.etPwd);
        btnSignUP = findViewById( R.id.btnSignUP);
//        btnAddProfilePic = findViewById( R.id.btnAddProfilePic );
//       ivProfile = findViewById( R.id.ivProfile );


//        btnAddProfilePic.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                launchCamera();
//            }
//        } );


        btnSignUP.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String firstName = etFName.getText().toString();
            String lastName = etLName.getText().toString();
            String username = etUser.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPwd.getText().toString();
           // ParseFile file = new ParseFile( photoFile );


                ParseUser user = new ParseUser();

                user.setUsername( username );
                user.setEmail( email );
                user.setPassword( password );
                user.put("first_name",firstName);
                user.put("last_name",lastName);
               // user.put( "image_profile", file);


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
                        etFName.setText( "" );
                        etLName.setText( "" );
//                        ivProfile.setImageResource(0);

                        Intent intent = new Intent( SignUpActivity.this, LoginActivity.class );
                        startActivity( intent );

                    }
                } );

           // saveImage(photoFile);

            }
        } );


    }

    private void saveImage(File photoFile){
        final ParseFile file = new ParseFile(photoFile);
        file.saveInBackground( new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Log.e(TAG, "Error on saving");
                    e.printStackTrace();
                    return;
                }
            }
        } );
    }

    private void launchCamera() {
        Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile);
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (i.resolveActivity(this.getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
              // ivProfile.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(this.getExternalFilesDir( Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }


}
