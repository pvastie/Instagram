package com.codepath.parseinstagram;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codepath.parseinstagram.adapters.CommentAdapter;
import com.codepath.parseinstagram.models.Comment;
import com.codepath.parseinstagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private EditText etComment;
    private ImageButton bvComment;
    RecyclerView rvComment;
    CommentAdapter commentAdapter;
    List<Comment> list;

    TextView tvComment;
    TextView tvTime;

   Post poster;


    private static final String TAG = "CommentActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_comment );

        etComment = findViewById( R.id.etComment );
        bvComment = findViewById( R.id.bvComment );


        tvComment = findViewById( R.id.tvComment );
        tvTime = findViewById( R.id.tvTime );

        rvComment = findViewById( R.id.rvComment );


        list = new ArrayList<>();
        commentAdapter = new CommentAdapter(CommentActivity.this,list);
        rvComment.setLayoutManager(new LinearLayoutManager(CommentActivity.this,RecyclerView.VERTICAL,false));
        rvComment.setAdapter(commentAdapter);

        queryComment();


        bvComment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mComment = etComment.getText().toString();
                if (mComment.isEmpty()) {
                    Snackbar.make( v, "Please add a comment.", Snackbar.LENGTH_SHORT ).show();
                    return;
                }
                Comment comment = new Comment();
                comment.setUser( ParseUser.getCurrentUser() );
                comment.setPost( poster );
                comment.setComment( mComment );


                comment.saveInBackground( new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e != null) {
                            Log.d( "save", e.getMessage() );
                            e.printStackTrace();
                            return;
                        }

                        etComment.setText( "" );

                    }
                } );

            }

        });

    }

    protected void queryComment() {
        ParseQuery<Comment> commentQuery = new ParseQuery<Comment>( Comment.class );
        commentQuery.include( Comment.KEY_USER );
       commentQuery.addDescendingOrder( Post.KEY_CREATED_AT );
       commentQuery.findInBackground( new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> listPost, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                listPost.addAll( list );
                commentAdapter.notifyDataSetChanged();

                for (int i = 0; i < listPost.size(); i++){
                    Log.d(TAG, "Post: " + listPost.get(i).getComment() + "Username" + listPost.get( i )
                            .getUser().getUsername());
                }

            }
        } );
    }

}
