package com.codepath.parseinstagram.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName( "Comment" )
public class Comment extends ParseObject {


        public static final String KEY_USER = "user";
        public static final String KEY_COMMENT = "comment";
        public static final String KEY_POST = "post";



        public void setUser(ParseUser content){put(KEY_USER,content);}
        public ParseUser getUser(){return getParseUser(KEY_USER);}
        public void setComment(String content){put(KEY_COMMENT,content);}
        public String getComment(){return getString(KEY_COMMENT);}
        public void setPost(ParseObject content){put(KEY_POST, content);}


    }

