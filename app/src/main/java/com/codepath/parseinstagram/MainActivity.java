package com.codepath.parseinstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.parseinstagram.fragments.ComposeFragment;
import com.codepath.parseinstagram.fragments.HomeFragment;
import com.codepath.parseinstagram.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";



    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        final FragmentManager fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById( R.id.bottom_navigation );




        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        // TODO: swap fragment
                        fragment = new HomeFragment();
                        Toast.makeText( MainActivity.this, "Home", Toast.LENGTH_SHORT ).show();
                        break;
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        Toast.makeText( MainActivity.this, "Compose", Toast.LENGTH_SHORT ).show();
                        break;
                    case R.id.action_profile:
                    default:
                        // TODO: SWAP FRAGMENT
                        fragment = new ProfileFragment();
                        Toast.makeText( MainActivity.this, "Profile", Toast.LENGTH_SHORT ).show();
                        break;
                }
                fragmentManager.beginTransaction().replace( R.id.flContainer, fragment ).commit();
                return true;
            }
        } );

        bottomNavigationView.setSelectedItemId( R.id.action_home );
    }

}
