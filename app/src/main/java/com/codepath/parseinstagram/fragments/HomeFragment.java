package com.codepath.parseinstagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.parseinstagram.R;
import com.codepath.parseinstagram.adapters.PostAdapter;
import com.codepath.parseinstagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private RecyclerView rvPosts;
    protected PostAdapter adapter;
    protected List<Post> lPosts;
    private SwipeRefreshLayout swipeContainer;



    // onCreateView


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_home, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts = view.findViewById( R.id.rvPosts );

        swipeContainer = view.findViewById( R.id.swipeContainer );

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        // create data source
        lPosts = new ArrayList<>( );
        // create adapter
        adapter = new PostAdapter( getContext(), lPosts );

        //set adapter on recycler view
        rvPosts.setAdapter( adapter );

        //set layout manager in the recycler view
        rvPosts.setLayoutManager( new LinearLayoutManager( getContext() ) );

        queryPost();

        swipeContainer.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "Content is being refresh");
                queryPost();
            }
        } );

    }

        protected void queryPost() {
            ParseQuery<Post> postQuery = new ParseQuery<Post>( Post.class );
            postQuery.include( Post.KEY_USER );
            postQuery.setLimit( 20 );
            postQuery.addDescendingOrder( Post.KEY_CREATED_AT );
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
