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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class LogInActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final String TAG = "EmailPassword";

    EditText emailText;
    EditText passwordText;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // go fullscreen
        setContentView(R.layout.activity_log_in);

        emailText = (EditText) findViewById(R.id.editText_email);
        passwordText = (EditText) findViewById(R.id.editText_password);

        findViewById(R.id.imageButton_logIn).setOnClickListener(this);
        findViewById(R.id.imageButton_signUp).setOnClickListener(this);

        /*
            Firebase setup ------------------------------------------------------------------------
         */
        HomeActivity.mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        findViewById(R.id.imageButton_logIn).setEnabled(true);
        findViewById(R.id.imageButton_signUp).setEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void signIn(String email, String password) {
        //Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            findViewById(R.id.imageButton_logIn).setEnabled(true);
            findViewById(R.id.imageButton_signUp).setEnabled(true);

            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        HomeActivity.mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LogInActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = HomeActivity.mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed. Check your internet connection " +
                                    "and make sure that this email address has registered before.", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
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

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            //Toast.makeText(LogInActivity.this, "This is LogInActivity. A user is logged in. Name : " + user.getDisplayName() + ", Email : " + user.getEmail(), Toast.LENGTH_LONG).show();

            Intent newsfeedIntent = new Intent(this, QuestionStreamActivity.class);
            //newsfeedIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(newsfeedIntent);
        } else {
            //Toast.makeText(LogInActivity.this, "This is LogInActivity. No user is logged in", Toast.LENGTH_LONG).show();

            findViewById(R.id.imageButton_logIn).setEnabled(true);
            findViewById(R.id.imageButton_signUp).setEnabled(true);
        }
    }

    /*private void signOut() {
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
            case R.id.imageButton_logIn:
                //Toast.makeText(LogInActivity.this, "touched! 1st button.", Toast.LENGTH_SHORT).show();

                findViewById(R.id.imageButton_logIn).setEnabled(false);
                findViewById(R.id.imageButton_signUp).setEnabled(false);

                signIn(emailText.getText().toString(), passwordText.getText().toString());

                break;
            case R.id.imageButton_signUp:
                //Toast.makeText(LogInActivity.this, "touched! 2nd button.", Toast.LENGTH_SHORT).show();

                Intent SignUpActivityIntent = new Intent(this, SignUpActivity.class);
                startActivity(SignUpActivityIntent);

                break;
        }
    }

    /*public void LogInButtonClicked(View view) {
        Toast.makeText(LogInActivity.this, "touched! 1st button.", Toast.LENGTH_SHORT).show();

        findViewById(R.id.imageButton_logIn).setEnabled(false);
        findViewById(R.id.imageButton_signUp).setEnabled(false);

        signIn(emailText.getText().toString(), passwordText.getText().toString());
    }

    public void SignUpHollowButtonClicked(View view) {
        Toast.makeText(LogInActivity.this, "touched! 2nd button.", Toast.LENGTH_SHORT).show();

        Intent SignUpActivityIntent = new Intent(this, SignUpActivity.class);
        startActivity(SignUpActivityIntent);
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
