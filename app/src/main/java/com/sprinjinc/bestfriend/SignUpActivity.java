package com.sprinjinc.bestfriend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final String TAG = "EmailPassword";

    EditText emailText;
    EditText passwordText;

    private ProgressDialog mProgressDialog;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // go fullscreen
        setContentView(R.layout.activity_sign_up);

        emailText = (EditText) findViewById(R.id.editText_email);
        passwordText = (EditText) findViewById(R.id.editText_password);

        findViewById(R.id.imageButton_signUp).setOnClickListener(this);
        findViewById(R.id.imageButton_logIn).setOnClickListener(this);

        /*
            Firebase setup ------------------------------------------------------------------------
         */
        HomeActivity.mFirebaseAuth = FirebaseAuth.getInstance();

        HomeActivity.database = FirebaseDatabase.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onStart() {
        super.onStart();

        findViewById(R.id.imageButton_signUp).setEnabled(true);
        findViewById(R.id.imageButton_logIn).setEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void createAccount(String email, String password) {
        //Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            findViewById(R.id.imageButton_signUp).setEnabled(true);
            findViewById(R.id.imageButton_logIn).setEnabled(true);

            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        HomeActivity.mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(SignUpActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                            //FirebaseUser user = HomeActivity.mFirebaseAuth.getCurrentUser();

                            updateAccount();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed. Check your internet connection " +
                                    "and make sure that this email address has not registered before.", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailText.setError("Required.");
            valid = false;
        } else {
            emailText.setError(null);
        }

        String password = passwordText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordText.setError("Required.");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    public void updateAccount() {

        /*
        ************* To upload image to firebase cloud storage, uncomment the code below ********************

        StorageReference profileImageRef;

        // Create a storage reference from our app
        profileImageRef = PersonalAccountActivity.storage.getReference(PersonalAccountActivity.proPicFolderPath + "/" + HomeActivity.mFirebaseAuth.getCurrentUser().getUid() + ".png");

        showProgressDialog();
        UploadTask uploadTask = profileImageRef.putFile(HomeActivity.Profile_Picture);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(SignUpActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(SignUpActivity.this, "Sorry, uploading image failed.", Toast.LENGTH_SHORT).show();

                FirebaseUser user = HomeActivity.mFirebaseAuth.getCurrentUser();
                updateUI(user);
            }
        }).addOnSuccessListener(SignUpActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                hideProgressDialog();

                /*Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(SignUpActivity.this, downloadUrl.getPath(), Toast.LENGTH_LONG).show();*----------------------------------------/

                //PersonalAccountActivity.Profile_Picture = Uri.parse("android.resource://com.sprinjinc.bestfriend/" + R.drawable.profile_icon);

                FirebaseUser user = HomeActivity.mFirebaseAuth.getCurrentUser();
                updateUI(user);
            }
        });*/



        /*UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Human")
                .build();

        HomeActivity.mFirebaseAuth.getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "User profile updated.", Toast.LENGTH_SHORT).show();
                            hideProgressDialog();

                            FirebaseUser user = HomeActivity.mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                        }
                    }
                });*/


        /*int selectedId=radioSexGroup.getCheckedRadioButtonId();
        radioSexButton=(RadioButton)findViewById(selectedId);
        Toast.makeText(MainActivity.this,radioSexButton.getText(),Toast.LENGTH_SHORT).show();*/

        writeNewUser("Human " + Integer.toString((int)(Math.random() * 100000)), HomeActivity.mFirebaseAuth.getCurrentUser().getEmail());

        /*FirebaseUser mFirebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();
        updateUI(mFirebaseUser);*/
    }

    private void writeNewUser(final String name, final String email) {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mDatabaseRef.child("users").child(HomeActivity.mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    // use "username" already exists
                    Toast.makeText(SignUpActivity.this, "User already exists.", Toast.LENGTH_SHORT).show();
                } else {
                    // "username" does not exist yet.

                    // Creating new user node, which returns the unique key value
                    // new user node would be /users/$userid/
                    //String userId = mDatabaseRef.push().getKey();

                    // creating user object
                    //User user = new User(name, email);

                    // pushing user to 'users' node using the userId
                    FirebaseUser firebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();
                    mDatabaseRef.child("users").child(firebaseUser.getUid()).child("username").setValue(name);
                    mDatabaseRef.child("users").child(firebaseUser.getUid()).child("email").setValue(email);
                    mDatabaseRef.child("users").child(firebaseUser.getUid()).child("contactNo").setValue("+xxxxxxxxxxxxx");
                    mDatabaseRef.child("users").child(firebaseUser.getUid()).child("gender").setValue(Gender.MALE);
                    mDatabaseRef.child("users").child(firebaseUser.getUid()).child("maritalStatus").setValue(MaritalStatus.UNMARRIED);
                    mDatabaseRef.child("users").child(firebaseUser.getUid()).child("birth_year").setValue(1990);
                    mDatabaseRef.child("users").child(firebaseUser.getUid()).child("birth_month").setValue(1);
                    mDatabaseRef.child("users").child(firebaseUser.getUid()).child("birth_date").setValue(1);

                    FirebaseUser mFirebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();
                    updateUI(mFirebaseUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SignUpActivity.this, "Writing new user cancelled.", Toast.LENGTH_SHORT).show();

                FirebaseUser mFirebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();
                updateUI(mFirebaseUser);
            }
        });
    }

    /*private void writeNewUser(String name, String email, String contactNo, Gender gender, MaritalStatus maritalStatus, int birth_date, int birth_month, int birth_year) {
        // Creating new user node, which returns the unique key value
        // new user node would be /users/$userid/
        //String userId = mDatabaseRef.push().getKey();

        // creating user object
        User user = new User(name, email, contactNo, gender, maritalStatus, birth_date, birth_month, birth_year);

        // pushing user to 'users' node using the userId
        FirebaseUser firebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();
        mDatabaseRef.child("users").child(firebaseUser.getUid()).setValue(user);
    }*/

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            //Toast.makeText(SignUpActivity.this, "This is SignUpActivity. A user is logged in. Name : " + user.getDisplayName() + ", Email : " + user.getEmail(), Toast.LENGTH_LONG).show();

            Intent newsfeedIntent = new Intent(this, QuestionStreamActivity.class);
            newsfeedIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(newsfeedIntent);
        } else {
            //Toast.makeText(SignUpActivity.this, "This is SignUpActivity. No user is logged in", Toast.LENGTH_LONG).show();

            findViewById(R.id.imageButton_logIn).setEnabled(true);
            findViewById(R.id.imageButton_signUp).setEnabled(true);
        }
    }

    /*private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mFirebaseAuth.signOut();
        updateUI(null);
    }*/

    /*
    private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.verify_email_button).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.verify_email_button).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(EmailPasswordActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(EmailPasswordActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }*/

    /*
        If anything is clicked on current activity ------------------------------------------------
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imageButton_signUp:
                //Toast.makeText(SignUpActivity.this, "touched! 1st button.", Toast.LENGTH_SHORT).show();

                findViewById(R.id.imageButton_logIn).setEnabled(false);
                findViewById(R.id.imageButton_signUp).setEnabled(false);

                createAccount(emailText.getText().toString(), passwordText.getText().toString());

                break;
            case R.id.imageButton_logIn:
                //Toast.makeText(SignUpActivity.this, "touched! 2nd button.", Toast.LENGTH_SHORT).show();

                Intent LogInActivityIntent = new Intent(this, LogInActivity.class);
                startActivity(LogInActivityIntent);

                break;
        }
    }

    /*public void SignUpButtonClicked(View view) {
        /*Bitmap bmp = BitmapFactory.decodeResource(getResources(), profilePic.getId());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteArray = stream.toByteArray();*------------------------/

        /*Intent personalAccountIntent = new Intent(this, PersonalAccountActivity.class);
        Bundle extras = new Bundle();
        extras.putString("profile_image", profilePicturePath);
        extras.putString("username", "");
        extras.putString("email_address", emailText.getText().toString());
        extras.putString("password", passwordText.getText().toString());
        personalAccountIntent.putExtras(extras);
        startActivity(personalAccountIntent);*-----------------------------------------/


        Toast.makeText(SignUpActivity.this, "touched! 1st button.", Toast.LENGTH_SHORT).show();

        findViewById(R.id.imageButton_logIn).setEnabled(false);
        findViewById(R.id.imageButton_signUp).setEnabled(false);

        createAccount(emailText.getText().toString(), passwordText.getText().toString());
    }*/

    /*public void LogInHollowButtonClicked(View view) {
        Toast.makeText(SignUpActivity.this, "touched! 2nd button.", Toast.LENGTH_SHORT).show();

        Intent LogInActivityIntent = new Intent(this, LogInActivity.class);
        startActivity(LogInActivityIntent);
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}
