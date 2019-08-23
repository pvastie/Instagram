package com.codepath.parseinstagram.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.parseinstagram.CommentActivity;
import com.codepath.parseinstagram.DetailActivity;
import com.codepath.parseinstagram.R;
import com.codepath.parseinstagram.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;

import org.parceler.Parcels;

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {


        final Post post = lPosts.get(i);
        try {
            viewHolder.bind(post);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.postContainer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, DetailActivity.class);

                intent.putExtra("Post", Parcels.wrap( post ) );

//                intent.putExtra( Post.KEY_USER, post.getUser().getUsername() );
//
//                try {
//                    intent.putExtra( Post.KEY_IMAGE, post.getImage().getFile() );
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

                context.startActivity( intent );
            }
        } );

        viewHolder.bnComment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, CommentActivity.class );
                context.startActivity( intent );
            }
        } );




    }

    @Override
    public int getItemCount() {
        return lPosts.size();
    }

    public void clear(){
        lPosts.clear();
        notifyDataSetChanged();
    }

    public void addPosts(List<Post> listPost){
        lPosts.addAll( listPost );
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvHandle;
        private ImageView ivImagePost;
        private TextView tvDescription;
        LinearLayout postContainer;
        private Button bnComment;



        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            tvHandle = itemView.findViewById( R.id.tvHandle );
            ivImagePost = itemView.findViewById( R.id.ivImagePost );
            tvDescription = itemView.findViewById( R.id.tvDescription );
            postContainer = itemView.findViewById( R.id.viewContainer );
            bnComment = itemView.findViewById( R.id.comment_edit );


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
