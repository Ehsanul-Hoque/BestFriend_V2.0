package com.sprinjinc.bestfriend;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sprinjinc.bestfriend.adapter.FeedListAdapter;
import com.sprinjinc.bestfriend.app.AppController;
import com.sprinjinc.bestfriend.data.FeedItem;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

public class QuestionStreamActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = QuestionStreamActivity.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    FeedItem item;

    TextView textView_username_navHeader;
    TextView textView_email_navHeader;
    ImageView imageView_newImg;
    View navigationHeader;
    private ProgressDialog mProgressDialog;
    Uri status_image = null;

    public static String _username;
    String _emailAddress;
    int total_post;
    private final int GALLARY_REQUEST_CODE = 8725;
    final String storage_image_folder = "status_images";
    //boolean[] isLovedList = new boolean[total_post];

    private DatabaseReference mDatabaseRef;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_question_stream);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent askMeIntent = new Intent(this, AskMeActivity.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(askMeIntent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("");
        navigationView.setCheckedItem(R.id.nav_question_stream);




        imageView_newImg = (ImageView) findViewById(R.id.imageView_newImage);
        imageView_newImg.setVisibility(View.GONE);

        findViewById(R.id.imageButton_photoUpload).setOnClickListener(this);
        findViewById(R.id.imageButton_postDone).setOnClickListener(this);


        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);

        // We first check for cached request
        /*Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }*/




        FirebaseUser currentFirebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();

        HomeActivity.database = FirebaseDatabase.getInstance();
        mDatabaseRef = HomeActivity.database.getReference();

        storage = FirebaseStorage.getInstance();

        navigationHeader = navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        textView_username_navHeader = (TextView) navigationHeader.findViewById(R.id.textView_username_navHeader);
        textView_email_navHeader = (TextView) navigationHeader.findViewById(R.id.textView_email_navHeader);

        /*navigationHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personalAccountActivity = new Intent(QuestionStreamActivity.this, PersonalAccountActivity.class);
                startActivity(personalAccountActivity);
            }
        });*/

        showProgressDialog();

        final String _uid = currentFirebaseUser.getUid();

        /*mDatabaseRef.child("users").child(currentFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);

                    /*_username = user.getUsername();
                    _emailAddress = user.getEmail();

                    textView_username_navHeader.setText(_username);
                    textView_email_navHeader.setText(_emailAddress);*---/


                    //Log.d(TAG, "User name: " + user.getName() + ", email " + user.getEmail());
                } else {
                   /*Toast.makeText(QuestionStreamActivity.this, "Failed to get user information.", Toast.LENGTH_SHORT).show();

                    _username = "Android Studio";
                    _emailAddress = "android.studio@android.com";*---/
                }

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(QuestionStreamActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });*/

        /*mDatabaseRef.child("all_posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    getAllPosts((Map<String,Object>) dataSnapshot.getValue());
                else
                    Toast.makeText(QuestionStreamActivity.this, "Failed to get posts.", Toast.LENGTH_SHORT).show();

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuestionStreamActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();

                hideProgressDialog();
            }
        });*/

        mDatabaseRef.child("users").child(currentFirebaseUser.getUid()).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    _username = dataSnapshot.getValue().toString();
                } else {
                   Toast.makeText(QuestionStreamActivity.this, "Failed to get username.", Toast.LENGTH_SHORT).show();

                    _username = "Android Studio";
                }

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(QuestionStreamActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

        mDatabaseRef.child("users").child(currentFirebaseUser.getUid()).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    _emailAddress = dataSnapshot.getValue().toString();
                } else {
                    Toast.makeText(QuestionStreamActivity.this, "Failed to get email.", Toast.LENGTH_SHORT).show();

                    _emailAddress = "android.studio@android.com";
                }

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(QuestionStreamActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

        mDatabaseRef.child("post_count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    total_post = Integer.parseInt(dataSnapshot.getValue().toString());

                    /*int i;

                    for (i = 1; i <= total_post; i++) {
                        mDatabaseRef.child("all_posts").child("post_" + String.valueOf(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    //getAllPosts((Map<String,Object>) dataSnapshot.getValue());
                                } else
                                    Toast.makeText(QuestionStreamActivity.this, "Failed to get posts.", Toast.LENGTH_SHORT).show();

                                hideProgressDialog();
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Toast.makeText(QuestionStreamActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();

                                hideProgressDialog();
                            }
                        });
                    }*/
                } else
                    Toast.makeText(QuestionStreamActivity.this, "Failed to get total post number.", Toast.LENGTH_SHORT).show();

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuestionStreamActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

        mDatabaseRef.child("all_posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Map <String, Map <String, Object> > posts = new HashMap <>();

                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        int id = Integer.parseInt(postSnapshot.child("id").getValue().toString());
                        //Toast.makeText(QuestionStreamActivity.this, "ID :: " + String.valueOf(id), Toast.LENGTH_SHORT).show();
                        int totalComments = Integer.parseInt(postSnapshot.child("totalComments").getValue().toString());
                        boolean lovedByCurrentUser = Boolean.valueOf(postSnapshot.child("lovedByCurrentUser").getValue().toString());
                        String name = postSnapshot.child("name").getValue().toString();
                        String status = postSnapshot.child("status").getValue().toString();
                        String timeStamp = postSnapshot.child("timeStamp").getValue().toString();


                        Map<String, Object> single_post = new HashMap<String, Object>();

                        single_post.put("id", id);
                        single_post.put("totalComments", totalComments);
                        single_post.put("lovedByCurrentUser", lovedByCurrentUser);
                        single_post.put("name", name);
                        single_post.put("status", status);
                        single_post.put("timeStamp", timeStamp);

                        getSinglePost(single_post);

                        //posts.put("post_" + String.valueOf(id), single_post);
                    }

                    listAdapter.notifyDataSetChanged();

                    //Toast.makeText(QuestionStreamActivity.this, "status of 2nd post :: " + posts.get("post_2").get("status").toString(), Toast.LENGTH_LONG).show();

                    //getAllPosts(posts);
                } else
                    Toast.makeText(QuestionStreamActivity.this, "Failed to get posts.", Toast.LENGTH_SHORT).show();

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuestionStreamActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();

                hideProgressDialog();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int lastFirstVisibleItem;
            int lastLastVisibleItem;

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int i;
                lastFirstVisibleItem = listView.getFirstVisiblePosition();
                lastLastVisibleItem = listView.getLastVisiblePosition();

                lastLastVisibleItem = ((lastLastVisibleItem == -1) ? 0 : lastLastVisibleItem);
                lastFirstVisibleItem = ((lastFirstVisibleItem == -1) ? 0 : lastFirstVisibleItem);

                lastFirstVisibleItem = firstVisibleItem;
                lastLastVisibleItem = listView.getLastVisiblePosition();
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int i;

                for (i = 0; i < total_post; i++) {
                    final int position = i;

                    ImageButton love_btn = (ImageButton) (getViewByPosition(i, listView).findViewById(R.id.imageButton_love));
                    love_btn.setImageResource(R.drawable.love_button_not_clicked);

                    int postID = (int) love_btn.getTag(R.id.loveButtonCurrentItemID);

                    mDatabaseRef.child("loves").child("post_" + String.valueOf(postID)).child(_username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // If child exists, it means the user loved the status.
                            if (dataSnapshot.exists()) {
                                ImageButton love_btn = (ImageButton) (getViewByPosition(position, listView).findViewById(R.id.imageButton_love));
                                love_btn.setImageResource(R.drawable.love_button_clicked);

                            } else {
                                ImageButton love_btn = (ImageButton) (getViewByPosition(position, listView).findViewById(R.id.imageButton_love));
                                love_btn.setImageResource(R.drawable.love_button_not_clicked);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            // Toast.makeText(activity.getApplicationContext(), "Failed to read value.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                /*if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

                }*/
            }
        });


    }

    private void showLoved(final int position) {
        ImageView love_btn = (ImageView) (getViewByPosition(position, listView).findViewById(R.id.imageButton_love));
        love_btn.setImageResource(R.drawable.love_button_not_clicked);

        //Toast.makeText(QuestionStreamActivity.this, "Came here at least!", Toast.LENGTH_SHORT).show();

        mDatabaseRef.child("loves").child("post_" + String.valueOf(position + 1)).child(_username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If child exists, it means the user loved the status.
                if (dataSnapshot.exists()) {
                    //FeedItem item = (FeedItem) getViewByPosition(position - 1, listView);
                    FeedItem item = (FeedItem) listView.getItemAtPosition(position);
                    item.setLovedByCurrentUser(true);

                    //Toast.makeText(QuestionStreamActivity.this, item.getName() + " :: " + "Loved item position : " + String.valueOf(position - 1), Toast.LENGTH_SHORT).show();

                    ImageView love_btn = (ImageView) (getViewByPosition(position, listView).findViewById(R.id.imageButton_love));
                    love_btn.setImageResource(R.drawable.love_button_clicked);
                } else {
                    FeedItem item = (FeedItem) listView.getItemAtPosition(position);
                    item.setLovedByCurrentUser(false);

                    ImageView love_btn = (ImageView) (getViewByPosition(position, listView).findViewById(R.id.imageButton_love));
                    love_btn.setImageResource(R.drawable.love_button_not_clicked);

                    //Toast.makeText(QuestionStreamActivity.this, item.getName() + " :: " + String.valueOf(position - 1) + ".", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(QuestionStreamActivity.this, "It does not exist for position " + String.valueOf(position - 1) + ".", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Toast.makeText(activity.getApplicationContext(), "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //Toast.makeText(QuestionStreamActivity.this, getFragmentManager().getBackStackEntryAt(0).toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.personal_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Bestfriend is a great app! :D";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Bestfriend App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));

            return true;
        } else if (id == R.id.action_exit) {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack("QuestionStreamActivity", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            super.onBackPressed();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    --- This method comes with the code of "if (id == R.id.action_share)" in the upper method.
    --- For details, http://www.viralandroid.com/2016/05/adding-share-action-to-android-app.html

    public void shareText(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Your shearing message goes here";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ask_me) {
            Intent askMeIntent = new Intent(this, AskMeActivity.class);
            startActivity(askMeIntent);

        } else if (id == R.id.nav_question_stream) {
            // Do nothing

        } else if (id == R.id.nav_about) {
            Intent aboutUsIntent = new Intent(this, AboutUsActivity.class);
            startActivity(aboutUsIntent);

        } else if (id == R.id.nav_support) {
            Intent supportIntent = new Intent(this, SupportActivity.class);
            startActivity(supportIntent);

        } else if (id == R.id.nav_sign_out) {
            signOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        HomeActivity.mFirebaseAuth.signOut();
        //updateUI(null);
        Intent signOutIntent = new Intent(this, HomeActivity.class);
        signOutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signOutIntent);
    }

    /*
        If anything is clicked on current activity ------------------------------------------------
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imageButton_postDone:
                // First upload the image if there's any
                if (status_image != null) {
                    StorageReference profileImageRef;

                    // Create a storage reference from our app
                    profileImageRef = storage.getReference(storage_image_folder + "/" + "image_for_post_" + String.valueOf(total_post + 1) + ".png");

                    showProgressDialog();
                    Toast.makeText(QuestionStreamActivity.this, "Updating post...", Toast.LENGTH_LONG).show();
                    UploadTask uploadTask = profileImageRef.putFile(status_image);

                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(QuestionStreamActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            hideProgressDialog();
                            Toast.makeText(QuestionStreamActivity.this, "Updating failed.", Toast.LENGTH_LONG).show();

                            /*FirebaseUser user = HomeActivity.mFirebaseAuth.getCurrentUser();
                            updateUI(user);*/
                        }
                    }).addOnSuccessListener(QuestionStreamActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            hideProgressDialog();

                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            // Now update database
                            long unixTimeInMilis = System.currentTimeMillis();

                            uploadStatus(total_post + 1, _username, downloadUrl.toString(),
                                    ((EditText) findViewById(R.id.editText_newStatus)).getText().toString(), String.valueOf(unixTimeInMilis), null);

                            total_post++;

                            DatabaseReference postCountRef = HomeActivity.database.getReference();;
                            postCountRef.child("post_count").setValue(total_post);

                            Toast.makeText(QuestionStreamActivity.this, "Status updated.", Toast.LENGTH_LONG).show();

                            status_image = null;
                            imageView_newImg.setVisibility(View.GONE);
                            ((EditText) findViewById(R.id.editText_newStatus)).setText("");

                            /*FirebaseUser user = HomeActivity.mFirebaseAuth.getCurrentUser();
                            updateUI(user);*/
                        }
                    });
                } else {
                    long unixTimeInMilis = System.currentTimeMillis();

                    uploadStatus(total_post + 1, _username, null,
                            ((EditText) findViewById(R.id.editText_newStatus)).getText().toString(), String.valueOf(unixTimeInMilis), null);

                    total_post++;

                    DatabaseReference postCountRef = HomeActivity.database.getReference();
                    postCountRef.child("post_count").setValue(total_post);

                    Toast.makeText(QuestionStreamActivity.this, "Status updated.", Toast.LENGTH_LONG).show();

                    status_image = null;
                    imageView_newImg.setVisibility(View.GONE);
                    ((EditText) findViewById(R.id.editText_newStatus)).setText("");
                }

                break;

            case R.id.imageButton_photoUpload:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLARY_REQUEST_CODE);

                break;
        }
    }

    private void uploadStatus(int id, String name, String image, String status, String timeStamp, String url) {
        // creating feeditem object
        FeedItem newStatus = new FeedItem(id, name, image, status, timeStamp, url);

        // pushing feeditem to 'posts' node
        mDatabaseRef = HomeActivity.database.getReference("all_posts");
        mDatabaseRef.child("post_" + String.valueOf(total_post + 1)).setValue(newStatus);

        feedItems.add(0, newStatus);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

        smoothScrollToPositionFromTop(listView, 0);
    }

    public static void smoothScrollToPositionFromTop(final AbsListView view, final int position) {
        View child = getChildAtPosition(view, position);
        // There's no need to scroll if child is already at top or view is already scrolled to its end
        if ((child != null) && ((child.getTop() == 0) || ((child.getTop() > 0) && !view.canScrollVertically(1)))) {
            return;
        }

        view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    view.setOnScrollListener(null);

                    // Fix for scrolling bug
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            view.setSelection(position);
                        }
                    });
                }
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
                                 final int totalItemCount) { }
        });

        // Perform scrolling to position
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                view.smoothScrollToPositionFromTop(position, 0);
            }
        });
    }

    public static View getChildAtPosition(final AdapterView view, final int position) {
        final int index = position - view.getFirstVisiblePosition();
        if ((index >= 0) && (index < view.getChildCount())) {
            return view.getChildAt(index);
        } else {
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLARY_REQUEST_CODE)
            if (resultCode == Activity.RESULT_OK) {
                status_image = data.getData();
                imageView_newImg.setVisibility(View.VISIBLE);
                imageView_newImg.setImageURI(status_image);
                /*imageView_newImg
                        .setResponseObserver(new FeedImageView.ResponseObserver() {
                            @Override
                            public void onError() {
                            }

                            @Override
                            public void onSuccess() {
                            }
                        });*/
            } else if (resultCode == Activity.RESULT_CANCELED) {
                status_image = null;
                imageView_newImg.setVisibility(View.GONE);
            }
    }

    /*private void getAllPosts(Map<String, Map<String, Object> > users) {

        //iterate through each user, ignoring their UID
        //for (Map.Entry<String, Map > entry : users.entrySet()){

        int i;

        for (i = 1; i <= users.size(); i++) {

            //Get user map
            Map singleUser = (Map) users.get("post_" + i);

            item = new FeedItem();

            item.setId( Integer.parseInt(singleUser.get("id").toString()) );
            item.setName(singleUser.get("name").toString());

            // Image might be null sometimes
            String image = singleUser.containsKey("image") ? singleUser.get("image").toString() : null;
            item.setImage(image);

            item.setStatus(singleUser.get("status").toString());
            item.setTimeStamp(singleUser.get("timeStamp").toString());

            // url might be null sometimes
            String feedUrl = singleUser.containsKey("url") ? singleUser.get("url").toString() : null;
            item.setUrl(feedUrl);

            feedItems.add(0, item);
        }

        listAdapter.notifyDataSetChanged();
    }*/

    private void getSinglePost(Map<String, Object> single_post) {

        //iterate through each user, ignoring their UID
        //for (Map.Entry<String, Map > entry : users.entrySet()){

        //Get user map

        item = new FeedItem();

        item.setId( Integer.parseInt(single_post.get("id").toString()) );
        item.setName(single_post.get("name").toString());

        // Image might be null sometimes
        String image = single_post.containsKey("image") ? single_post.get("image").toString() : null;
        item.setImage(image);

        item.setStatus(single_post.get("status").toString());
        item.setTimeStamp(single_post.get("timeStamp").toString());

        // url might be null sometimes
        String feedUrl = single_post.containsKey("url") ? single_post.get("url").toString() : null;
        item.setUrl(feedUrl);

        feedItems.add(0, item);
    }

    /*private static Map<String, Object> sortByValue(Map<String, Object> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Object>> list =
                new LinkedList<Map.Entry<String, Object>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> o1,
                               Map.Entry<String, Object> o2) {
                return (o1.getValue().toString()).compareTo(o2.getValue().toString());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Object> sortedMap = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, Object> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*-------------------------------/


        return sortedMap;
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

    public void love_clicked(final View view) {
        /*ImageButton imageButton_love = (ImageButton) view;

        if (imageButton_love.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.love_button_not_clicked).getConstantState()) {
            imageButton_love.setImageResource(R.drawable.love_button_clicked);
        } else {
            imageButton_love.setImageResource(R.drawable.love_button_not_clicked);
        }*/

        // Get the comments
        /*mDatabaseRef.child("comments").child("comments_for_post_" + post_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int id = Integer.parseInt(view.getTag(R.id.loveButtonCurrentItemID).toString());

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
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
            //return null;
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public void comment_clicked(View view) {
        int id = (int) view.getTag(R.id.commentButtonCurrentItemID);
        // We have to state that are intention is to open another Activity. We do so
        // by passing a Context and the Activity that we want to open
        Intent commentActivityIntent = new Intent(this, CommentActivity.class);

        // To send data use putExtra with a String name followed by its value
        commentActivityIntent.putExtra("post_id", id);
        startActivity(commentActivityIntent);
    }

    public void share_clicked(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = (String) view.getTag(R.id.commentButtonCurrentItemStatus);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Bestfriend status");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
    }
}
