package com.codepath.parseinstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.parseinstagram.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    private TextView etDescription1;
    private ImageView imageView;
    private TextView user;

    Post postdetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

        etDescription1 = findViewById( R.id.tvDescriptionDetail );
        user = findViewById( R.id.tvHandleDetail );
        imageView = findViewById( R.id.ivImagePostDetail );

        postdetail = (Post) Parcels.unwrap( getIntent().getParcelableExtra( "Post" ) );

        etDescription1.setText( postdetail.getDescription() );
        user.setText( postdetail.getUser().getUsername() );

        ParseFile image = postdetail.getImage();
        if (image != null) {
            try {
                Glide.with( this ).load( image.getFile() ).into( imageView );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }

}

