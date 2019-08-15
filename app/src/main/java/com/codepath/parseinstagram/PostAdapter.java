package com.codepath.parseinstagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private Context context;
    private List<Post> lPosts;

    public PostAdapter(Context context, List<Post> lPosts) {
        this.context = context;
        this.lPosts = lPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( context ).inflate( R.layout.item_post, viewGroup, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Post post = lPosts.get(i);
        try {
            viewHolder.bind(post);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return lPosts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvHandle;
        private ImageView ivImagePost;
        private TextView tvDescription;


        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            tvHandle = itemView.findViewById( R.id.tvHandle );
            ivImagePost = itemView.findViewById( R.id.ivImagePost );
            tvDescription = itemView.findViewById( R.id.tvDescription );

        }

        public void bind(Post post) throws ParseException {

            tvHandle.setText( post.getUser().getUsername() );
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with( context ).load( image.getFile()).into( ivImagePost );
            }

            tvDescription.setText( post.getDescription() );

        }
    }
}
