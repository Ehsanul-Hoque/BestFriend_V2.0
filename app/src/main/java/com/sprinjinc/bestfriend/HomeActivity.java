package com.sprinjinc.bestfriend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HomeActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    //SignInButton btnSignIn;
    ImageButton btnSignIn;

    private static final String TAG = "SignInActivityLogin";
    //private static final String TAG_2 = "EmailPassword";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    // [START declare_auth]
    static FirebaseAuth mFirebaseAuth;
    // [END declare_auth]

    static FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // go fullscreen
        setContentView(R.layout.activity_home);

        findViewById(R.id.imageButton_mailLogIn).setOnClickListener(this);
        findViewById(R.id.imageButton_signUp).setOnClickListener(this);

        SpannableString ss = new SpannableString("By signing up, I agree to bestfriend's Terms of Services," +
                " Privacy Policy and Conditions");

        ss.setSpan(new myClickableSpan(1), 39, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new myClickableSpan(2), 58, 72, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new myClickableSpan(3), 77, 87, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView textView_agreement = (TextView) findViewById(R.id.textView_agreement);
        textView_agreement.setText(ss);
        textView_agreement.setMovementMethod(LinkMovementMethod.getInstance());
        textView_agreement.setHighlightColor(Color.TRANSPARENT);

        /*
            Gmail login process -------------------------------------------------------------------
         */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        /*
            Firebase setup ------------------------------------------------------------------------
         */
        // [START initialize_auth]
        mFirebaseAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        database = FirebaseDatabase.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    }

    private class myClickableSpan extends ClickableSpan{

        int pos;

        myClickableSpan(int position){
            this.pos = position;
        }

        @Override
        public void onClick(View widget) {
            //Toast.makeText(getApplicationContext(), "Position "  + pos + " clicked!", Toast.LENGTH_LONG).show();

            switch (pos) {
                case 1:
                    startActivity(new Intent(HomeActivity.this, TermsOfServicesActivity.class));
                    break;

                case 2:
                    startActivity(new Intent(HomeActivity.this, PrivacyPolicyActivity.class));
                    break;

                case 3:
                    startActivity(new Intent(HomeActivity.this, ConditionsActivity.class));
                    break;
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(getResources().getColor(R.color.linkColor));
        }

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        final  Toast toast = Toast.makeText(this, "Tap again to quit", Toast.LENGTH_SHORT);

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            toast.cancel();
        }
        if(!doubleBackToExitPressedOnce) {
            toast.show();

        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 790);
        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 2000);

        //super.onBackPressed();
        //Toast.makeText(PersonalAccountActivity.this, getFragmentManager().getBackStackEntryAt(0).toString(), Toast.LENGTH_LONG).show();

    }

    /*
        Show sign in to google screen -------------------------------------------------------------
     */
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /*
        When user returns from an activity (here onActivityResult()
        is called whenever user returns from Google Login UI) -------------------------------------
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    /*
        Handle sign in result ---------------------------------------------------------------------
     */
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            firebaseAuthWithGoogle(acct);
            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
            Toast.makeText(HomeActivity.this, "Sign in attempt failed. Check your internet connection.", Toast.LENGTH_LONG).show();

            findViewById(R.id.imageButton_mailLogIn).setEnabled(true);
            findViewById(R.id.imageButton_signUp).setEnabled(true);
        }
    }

    /*
        Firebase authentication with Google -------------------------------------------------------
     */
    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(HomeActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                            //FirebaseUser user = mFirebaseAuth.getCurrentUser();

                            /*String personName = user.getDisplayName();
                            if (personName == null || personName.equals("")) personName = "Human_" + (int)(Math.random() * 10000);
                            //HomeActivity.Profile_Picture = acct.getPhotoUrl();
                            String email = user.getEmail();

                            Toast.makeText(HomeActivity.this, "Name : " + personName, Toast.LENGTH_SHORT).show();
                            Toast.makeText(HomeActivity.this, "Email : " + email, Toast.LENGTH_SHORT).show();*/

                            writeNewUser("Human " + Integer.toString((int)(Math.random() * 100000)), HomeActivity.mFirebaseAuth.getCurrentUser().getEmail());

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(HomeActivity.this, "Authentication failed. Check your internet connection.", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    private void writeNewUser(final String name, final String email) {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mDatabaseRef.child("users").child(HomeActivity.mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    // use "username" already exists
                    FirebaseUser mFirebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();
                    updateUI(mFirebaseUser);
                } else {
                    // "username" does not exist yet.

                    // Creating new user node, which returns the unique key value
                    // new user node would be /users/$userid/
                    //String userId = mDatabaseRef.push().getKey();

                    // creating user object
                    User user = new User(name, email);

                    // pushing user to 'users' node using the userId
                    FirebaseUser firebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();
                    mDatabaseRef.child("users").child(firebaseUser.getUid()).setValue(user);

                    FirebaseUser mFirebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();
                    updateUI(mFirebaseUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Writing new user cancelled.", Toast.LENGTH_SHORT).show();

                FirebaseUser mFirebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();
                updateUI(mFirebaseUser);
            }
        });
    }

    /*
        When app is started -----------------------------------------------------------------------
     */
    @Override
    public void onStart() {
        super.onStart();

        /*

        ***** Check if logged in by google when not using firebase. As this app is using firebase, this block of code is not needed (I think -_-) *****

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }*/

        findViewById(R.id.imageButton_mailLogIn).setEnabled(false);
        findViewById(R.id.imageButton_signUp).setEnabled(false);

        showProgressDialog();

        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        //Toast.makeText(HomeActivity.this, "This is epic!", Toast.LENGTH_LONG).show();
        updateUI(currentUser);
    }

    /*
        When app is resumed -----------------------------------------------------------------------
     */
    @Override
    protected void onResume() {
        super.onResume();
        hideProgressDialog();
    }

    /*
        When app is paused ------------------------------------------------------------------------
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /*
        When app is stopped -----------------------------------------------------------------------
     */
    @Override
    protected void onStop() {
        super.onStop();

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /*
        If the connection has failed... -----------------------------------------------------------
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        //Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(HomeActivity.this, "Connection failed." + connectionResult, Toast.LENGTH_LONG).show();
    }

    /*
        Show progress dialog with a message -------------------------------------------------------
     */
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        mProgressDialog.show();
    }

    /*
        Hide progress dialog ----------------------------------------------------------------------
     */
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    /*
        Update UI if/when the user is logged in or not --------------------------------------------
     */
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        //Toast.makeText(HomeActivity.this, "you are a disgrace", Toast.LENGTH_LONG).show();
        if (user != null) {
            //Toast.makeText(HomeActivity.this, "This is HomeActivity. A user is logged in. Name : " + user.getDisplayName() + ", Email : " + user.getEmail(), Toast.LENGTH_LONG).show();

            Intent NewsfeedIntent = new Intent(this, QuestionStreamActivity.class);
            NewsfeedIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(NewsfeedIntent);

            /*Intent QuestionStreamIntent = new Intent(this, QuestionStreamActivity.class);
            startActivity(QuestionStreamIntent);*/
        } else {
            //Toast.makeText(HomeActivity.this, "This is HomeActivity. No user is logged in", Toast.LENGTH_LONG).show();

            findViewById(R.id.imageButton_mailLogIn).setEnabled(true);
            findViewById(R.id.imageButton_signUp).setEnabled(true);
        }
    }

    /*
        If anything is clicked on current activity ------------------------------------------------
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imageButton_mailLogIn:
                findViewById(R.id.imageButton_mailLogIn).setEnabled(false);
                findViewById(R.id.imageButton_signUp).setEnabled(false);

                //Toast.makeText(HomeActivity.this, "touched! 1st button.", Toast.LENGTH_SHORT).show();
                signIn();

                break;
            case R.id.imageButton_signUp:
                //Toast.makeText(HomeActivity.this, "touched! 2nd button.", Toast.LENGTH_SHORT).show();

                findViewById(R.id.imageButton_mailLogIn).setEnabled(false);
                findViewById(R.id.imageButton_signUp).setEnabled(false);

                Intent signUpIntent = new Intent(this, SignUpActivity.class);
                startActivity(signUpIntent);

                break;
        }
    }

    /*
        When 'Log In With Email' button clicked... ------------------------------------------------
     */
    /*public void logInGoogleClicked(View view) {
        Toast.makeText(HomeActivity.this, "touched!", Toast.LENGTH_SHORT).show();
        //signIn();
    }*/

    /*
        When 'Sign Up' button clicked... ----------------------------------------------------------
     */
    /*public void SignUpClicked(View view) {
        Toast.makeText(HomeActivity.this, "touched! 2nd button.", Toast.LENGTH_SHORT).show();

        findViewById(R.id.imageButton_mailLogIn).setEnabled(false);
        findViewById(R.id.imageButton_signUp).setEnabled(false);

        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);
    }*/
}
