package com.sprinjinc.bestfriend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView_comment;
    private List<String> commentsList;
    private ListAdapter listView_Adapter;
    private EditText editText_newComment;

    int post_id;
    int total_comments;
    String _username;
    String _emailAddress;

    private ProgressDialog mProgressDialog;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comment);

        listView_comment = (ListView) findViewById(R.id.listView_comment);
        commentsList = new ArrayList<String>();
        listView_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commentsList);
        listView_comment.setAdapter(listView_Adapter);

        editText_newComment = (EditText) findViewById(R.id.editText_newComment);

        // Get the Intent that called for this Activity to open
        Intent previousActivity = getIntent();


        // Get the data that was sent
        post_id = previousActivity.getExtras().getInt("post_id");

        findViewById(R.id.imageButton_commentDone).setOnClickListener(this);

        FirebaseUser currentFirebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();

        HomeActivity.database = FirebaseDatabase.getInstance();
        mDatabaseRef = HomeActivity.database.getReference();

        showProgressDialog();

        // Get current user info
        mDatabaseRef.child("users").child(currentFirebaseUser.getUid()).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    _username = dataSnapshot.getValue().toString();
                } else {
                    Toast.makeText(CommentActivity.this, "Failed to get username.", Toast.LENGTH_SHORT).show();

                    _username = "Android Studio";
                }

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CommentActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

        mDatabaseRef.child("users").child(currentFirebaseUser.getUid()).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    _emailAddress = dataSnapshot.getValue().toString();
                } else {
                    Toast.makeText(CommentActivity.this, "Failed to get email.", Toast.LENGTH_SHORT).show();

                    _emailAddress = "android.studio@android.com";
                }

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CommentActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

        // Get the total number of comments
        mDatabaseRef.child("all_posts").child("post_" + post_id).child("totalComments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                total_comments = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(CommentActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the comments
        /*mDatabaseRef.child("comments").child("comments_for_post_" + post_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    listView_comment.setVisibility(View.VISIBLE);
                    getAllComments((Map<String, String>) dataSnapshot.getValue());
                } else {
                    listView_comment.setVisibility(View.GONE);
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(CommentActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });*/

        mDatabaseRef.child("comments").child("comments_for_post_" + post_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    listView_comment.setVisibility(View.VISIBLE);

                    for (DataSnapshot commentSnapshot: dataSnapshot.getChildren()) {
                        String singleComment = commentSnapshot.getValue().toString();
                        commentsList.add(0, singleComment);
                    }

                    ((BaseAdapter) listView_comment.getAdapter()).notifyDataSetChanged();
                } else {
                    listView_comment.setVisibility(View.GONE);
                    //Toast.makeText(CommentActivity.this, "Failed to get comments or no comments.", Toast.LENGTH_SHORT).show();
                }

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(CommentActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();

                hideProgressDialog();
            }
        });
    }

    /*private void getAllComments(Map<String, String> comments) {
        //commentsList.clear();

        for (Map.Entry<String, String> entry : comments.entrySet()){
            String singleComment = entry.getValue();
            commentsList.add(0, singleComment);
        }

        //listView_Adapter.notify();
        ((BaseAdapter) listView_comment.getAdapter()).notifyDataSetChanged();
    }*/

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
            //mProgressDialog.setCanceledOnTouchOutside(false);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imageButton_commentDone:
                if (!editText_newComment.getText().toString().isEmpty()) {
                    //Toast.makeText(CommentActivity.this, "not empty", Toast.LENGTH_SHORT).show();
                    DatabaseReference postCountRef = HomeActivity.database.getReference();

                    postCountRef
                            .child("comments")
                            .child("comments_for_post_" + post_id)
                            .child("comment_" + String.valueOf(total_comments + 1))
                            .setValue(_username + " : " + editText_newComment.getText().toString());

                    total_comments++;

                    postCountRef
                            .child("all_posts")
                            .child("post_" + post_id)
                            .child("totalComments")
                            .setValue(total_comments);

                    listView_comment.setVisibility(View.VISIBLE);
                    commentsList.add(0, _username + " : " + editText_newComment.getText().toString());
                    ((BaseAdapter) listView_comment.getAdapter()).notifyDataSetChanged();

                    editText_newComment.setText("");
                } else {
                    // Do not post empty comment
                    //Toast.makeText(CommentActivity.this, "empty", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
