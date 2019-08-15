package com.codepath.parseinstagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.parseinstagram.Post;
import com.codepath.parseinstagram.PostAdapter;
import com.codepath.parseinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private RecyclerView rvPosts;
    private PostAdapter adapter;
    private List<Post> lPosts;

    // onCreateView


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_home, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts = view.findViewById( R.id.rvPosts );


        // create data source
        lPosts = new ArrayList<>( );
        // create adapter
        adapter = new PostAdapter( getContext(), lPosts );

        //set adpter on recycler view
        rvPosts.setAdapter( adapter );

        //set layout manager in the recycler view
        rvPosts.setLayoutManager( new LinearLayoutManager( getContext() ) );

        queryPost();

    }

        private void queryPost() {
            ParseQuery<Post> postQuery = new ParseQuery<Post>( Post.class );
            postQuery.include( Post.KEY_USER );
            postQuery.findInBackground( new FindCallback<Post>() {
                @Override
                public void done(List<Post> posts, ParseException e) {
                    if(e != null){
                        Log.e(TAG, "Error with query");
                        e.printStackTrace();
                        return;
                    }
                    lPosts.addAll( posts );
                    adapter.notifyDataSetChanged();

                    for (int i = 0; i < posts.size(); i++){
                        Log.d(TAG, "Post: " + posts.get(i).getDescription() + "Username" + posts.get( i )
                                .getUser().getUsername());
                    }

                }
            } );
        }
}
