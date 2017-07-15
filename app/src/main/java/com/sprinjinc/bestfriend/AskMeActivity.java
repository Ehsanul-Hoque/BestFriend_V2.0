package com.sprinjinc.bestfriend;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
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
import com.sprinjinc.bestfriend.data.FeedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AskMeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = AskMeActivity.class.getSimpleName();
    private final int GALLARY_REQUEST_CODE = 564;
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;

    TextView textView_username_navHeader;
    TextView textView_email_navHeader;
    ImageView imageView_newImg;
    View navigationHeader;
    private ProgressDialog mProgressDialog = null;

    String _username;
    String _emailAddress;
    int total_post;
    Uri status_image = null;
    final String storage_image_folder = "status_images";

    private DatabaseReference mDatabaseRef;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ask_me);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("");
        navigationView.setCheckedItem(R.id.nav_ask_me);

        imageView_newImg = (ImageView) findViewById(R.id.imageView_newImage);

        findViewById(R.id.imageButton_photoUpload).setOnClickListener(this);
        findViewById(R.id.imageButton_postDone).setOnClickListener(this);


        FirebaseUser currentFirebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();

        HomeActivity.database = FirebaseDatabase.getInstance();
        mDatabaseRef = HomeActivity.database.getReference();

        storage = FirebaseStorage.getInstance();

        navigationHeader = navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        textView_username_navHeader = (TextView) navigationHeader.findViewById(R.id.textView_username_navHeader);
        textView_email_navHeader = (TextView) navigationHeader.findViewById(R.id.textView_email_navHeader);

        navigationHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personalAccountActivity = new Intent(AskMeActivity.this, PersonalAccountActivity.class);
                startActivity(personalAccountActivity);
            }
        });

        showProgressDialog();

        mDatabaseRef.child("users").child(currentFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                _username = user.getUsername();
                _emailAddress = user.getEmail();

                textView_username_navHeader.setText(_username);
                textView_email_navHeader.setText(_emailAddress);

                //Log.d(TAG, "User name: " + user.getName() + ", email " + user.getEmail());

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(AskMeActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });


        mDatabaseRef.child("post_count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                total_post = Integer.parseInt(dataSnapshot.getValue().toString());

                //Toast.makeText(AskMeActivity.this, "Total post : " + total_post, Toast.LENGTH_SHORT).show();

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(AskMeActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent newsfeedIntent = new Intent(AskMeActivity.this, QuestionStreamActivity.class);
            newsfeedIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(newsfeedIntent);
            //Toast.makeText(AskMeActivity.this, getFragmentManager().getBackStackEntryAt(0).toString(), Toast.LENGTH_LONG).show();
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
            fm.popBackStack("AskMeActivity", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            super.onBackPressed();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ask_me) {
            // Do nothing

        } else if (id == R.id.nav_question_stream) {
            Intent newsfeedIntent = new Intent(this, QuestionStreamActivity.class);
            startActivity(newsfeedIntent);

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
                    Toast.makeText(AskMeActivity.this, "Updating post...", Toast.LENGTH_LONG).show();
                    UploadTask uploadTask = profileImageRef.putFile(status_image);

                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(AskMeActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            hideProgressDialog();
                            Toast.makeText(AskMeActivity.this, "Updating failed.", Toast.LENGTH_LONG).show();

                            /*FirebaseUser user = HomeActivity.mFirebaseAuth.getCurrentUser();
                            updateUI(user);*/
                        }
                    }).addOnSuccessListener(AskMeActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
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

                            Toast.makeText(AskMeActivity.this, "Status updated.", Toast.LENGTH_LONG).show();

                            status_image = null;
                            imageView_newImg.setVisibility(View.INVISIBLE);
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

                    Toast.makeText(AskMeActivity.this, "Status updated.", Toast.LENGTH_LONG).show();

                    status_image = null;
                    imageView_newImg.setVisibility(View.INVISIBLE);
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
                imageView_newImg.setVisibility(View.INVISIBLE);
            }
    }

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
}
