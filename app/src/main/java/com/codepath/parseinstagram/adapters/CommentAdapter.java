package com.codepath.parseinstagram.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codepath.parseinstagram.R;
import com.codepath.parseinstagram.TimeFormatter;
import com.codepath.parseinstagram.models.Comment;
import com.parse.ParseUser;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {


    private Context context;
    private List<Comment> lcomment;




    public CommentAdapter(Context context, List<Comment> lcomment) {
        this.context = context;
        this.lcomment = lcomment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_comment, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Comment comment = lcomment.get(position);
        ParseUser user = comment.getUser();

        StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(user.getUsername());
        stringBuilder.setSpan(bold,0,stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(" ");
        stringBuilder.append(comment.getComment());

        viewHolder.tvUserComment.setText(user.getUsername() );
        viewHolder.tvComment.setText(stringBuilder);
        viewHolder.tvTime.setText( TimeFormatter.getTimeDifference(comment.getCreatedAt().toString()));

//        viewHolder.tvCommenter.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent( context, CommentActivity.class );
//                context.startActivity( i );
//            }
//        } );


    }

    @Override
    public int getItemCount() {
        return lcomment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView tvComment;
        private TextView tvTime;
        private TextView tvUserComment;
        private Button tvCommenter;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            tvComment = itemView.findViewById( R.id.tvComment );
            tvTime = itemView.findViewById( R.id.tvTime );
            tvUserComment = itemView.findViewById( R.id.tvUserComment );
            tvCommenter = itemView.findViewById( R.id.comment_edit );

        }
    }
}
