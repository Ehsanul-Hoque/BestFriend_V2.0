package com.sprinjinc.bestfriend;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Calendar;

public class PersonalAccountActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private int mYear;
    private int mMonth;
    private int mDay;


    private TextView mDateDisplay;
    //CircleImageView profileImage;
    TextView editText_username;
    TextView textView_username_navHeader;
    TextView textView_email_navHeader;
    EditText editText_email;
    EditText editText_contactNo;
    RadioGroup radioGroup_gender;
    RadioGroup radioGroup_maritalStatus;
    RadioButton radioButton_selectedGender;
    RadioButton radioButton_selectedMaritalStatus;
    //LinearLayout navigationHeader;
    View navigationHeader;

    String _username;
    String _emailAddress;
    String _contactNo;
    int _birth_date;
    int _birth_month;
    int _birth_year;
    Gender _gender;
    MaritalStatus _marital_status;

    private ProgressDialog mProgressDialog;

    static final int DATE_DIALOG_ID = 149;

    //static FirebaseStorage storage = FirebaseStorage.getInstance();
    //static String proPicFolderPath = "users_profile_images";
    //static String userUID = HomeActivity.mFirebaseAuth.getCurrentUser().getUid();
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_personal_account);
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

        // Get data from firebase

        FirebaseUser currentFirebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();

        HomeActivity.database = FirebaseDatabase.getInstance();
        mDatabaseRef = HomeActivity.database.getReference("users");

        // Set username, email and password

        editText_username = (EditText) findViewById(R.id.editText_accountUsername);
        //textView_username_navHeader = (TextView) findViewById(R.id.textView_username_navHeader);
        //textView_email_navHeader = (TextView) findViewById(R.id.textView_email_navHeader);
        editText_email = (EditText) findViewById(R.id.editText_accountEmail);
        editText_contactNo = (EditText) findViewById(R.id.editText_accountContactNo);
        radioGroup_gender = (RadioGroup) findViewById(R.id.radioGroup_gender);
        radioGroup_maritalStatus = (RadioGroup) findViewById(R.id.radioGroup_maritalStatus);
        //navigationHeader = (LinearLayout) findViewById(R.id.linearLayout_navHeader);
        mDateDisplay = (EditText) findViewById(R.id.editText_accountBirthday);

        navigationHeader = navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        textView_username_navHeader = (TextView) navigationHeader.findViewById(R.id.textView_username_navHeader);
        textView_email_navHeader = (TextView) navigationHeader.findViewById(R.id.textView_email_navHeader);

        /*int selectedId = radioGroup_gender.getCheckedRadioButtonId();
        radioButton_selectedGender = (RadioButton)findViewById(selectedId);
        Toast.makeText(PersonalAccountActivity.this, radioButton_selectedGender.getText(), Toast.LENGTH_SHORT).show();*/

        editText_username.setFocusable(false);
        editText_username.setClickable(false);
        editText_email.setFocusable(false);
        editText_email.setClickable(false);
        mDateDisplay.setFocusable(false);
        mDateDisplay.setClickable(true);
        //editText_email.setKeyListener(null);

        showProgressDialog();

        mDatabaseRef.child(currentFirebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    _username = dataSnapshot.child("username").getValue().toString();
                    _emailAddress = dataSnapshot.child("email").getValue().toString();
                    _contactNo = dataSnapshot.child("contactNo").getValue().toString();
                    _gender = Gender.valueOf(dataSnapshot.child("gender").getValue().toString());
                    _marital_status = MaritalStatus.valueOf(dataSnapshot.child("maritalStatus").getValue().toString());
                    _birth_date = Integer.parseInt(dataSnapshot.child("birth_date").getValue().toString());
                    _birth_month = Integer.parseInt(dataSnapshot.child("birth_month").getValue().toString());
                    _birth_year = Integer.parseInt(dataSnapshot.child("birth_year").getValue().toString());
                } else {
                    _username = "Anonymous";
                    _emailAddress = "anonymous@android.com";
                    _contactNo = "xxxxxxxxxxxxx";
                    _gender = Gender.MALE;
                    _marital_status = MaritalStatus.UNMARRIED;
                    _birth_date = 1;
                    _birth_month = 1;
                    _birth_year = 1990;
                }

                /*User user = dataSnapshot.getValue(User.class);

                _username = user.getUsername();
                _emailAddress = user.getEmail();
                _contactNo = user.getContactNo();
                _gender = user.getGender();
                _marital_status = user.getMaritalStatus();
                _birth_date = user.getBirth_date();
                _birth_month = user.getBirth_month();
                _birth_year = user.getBirth_year();*/

                editText_username.setText(_username);
                textView_username_navHeader.setText(_username);
                textView_email_navHeader.setText(_emailAddress);
                editText_email.setText(_emailAddress);
                editText_contactNo.setText(_contactNo);

                mYear = _birth_year;
                mMonth = _birth_month;
                mDay = _birth_date;

                // Display the date

                updateDisplay();

                if (_gender == Gender.MALE)
                    ((RadioButton) findViewById(R.id.radioButton_male)).setChecked(true);
                else if (_gender == Gender.FEMALE)
                    ((RadioButton) findViewById(R.id.radioButton_female)).setChecked(true);
                else if (_gender == Gender.OTHER)
                    ((RadioButton) findViewById(R.id.radioButton_other)).setChecked(true);

                if (_marital_status == MaritalStatus.MARRIED)
                    ((RadioButton) findViewById(R.id.radioButton_married)).setChecked(true);
                else if (_marital_status == MaritalStatus.UNMARRIED)
                    ((RadioButton) findViewById(R.id.radioButton_unmarried)).setChecked(true);
                else if (_marital_status == MaritalStatus.DIVORCED)
                    ((RadioButton) findViewById(R.id.radioButton_divorced)).setChecked(true);


                //Log.d(TAG, "User name: " + user.getName() + ", email " + user.getEmail());

                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(PersonalAccountActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });





        findViewById(R.id.button_cancel).setOnClickListener(this);
        findViewById(R.id.button_save).setOnClickListener(this);
        findViewById(R.id.editText_accountBirthday).setOnClickListener(this);
        /*findViewById(R.id.textView_username_navHeader).setOnClickListener(this);
        findViewById(R.id.textView_email_navHeader).setOnClickListener(this);*/
        //navigationHeader.setOnClickListener(this);
        navigationHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
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
            //Toast.makeText(PersonalAccountActivity.this, getFragmentManager().getBackStackEntryAt(0).toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.personal_account, menu);
        return true;
    }

    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
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
            /*FragmentManager fm = getSupportFragmentManager();

            int totalBackStack = fm.getBackStackEntryCount();

            if (fm.getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry first = fm.getBackStackEntryAt(0);
                fm.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }*/
            //fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            /*finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            super.onDestroy();*/

            minimizeApp();

            //super.onBackPressed();

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
            Intent askMeIntent = new Intent(this, AskMeActivity.class);
            startActivity(askMeIntent);

        } else if (id == R.id.nav_question_stream) {
            Intent newsfeedIntent = new Intent(this, QuestionStreamActivity.class);
            newsfeedIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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





    private void updateUser(String name, String email, String contactNo, Gender gender, MaritalStatus maritalStatus, int birth_date, int birth_month, int birth_year) {
        // creating user object
        User user = new User(name, email, contactNo, gender, maritalStatus, birth_date, birth_month, birth_year);

        // pushing user to 'users' node using the userId
        FirebaseUser firebaseUser = HomeActivity.mFirebaseAuth.getCurrentUser();
        mDatabaseRef = HomeActivity.database.getReference("users");
        mDatabaseRef.child(firebaseUser.getUid()).setValue(user);
    }

    /*@SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(DATE_DIALOG_ID);
    }*/

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == DATE_DIALOG_ID) {
            return new DatePickerDialog(this,
                    mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    private void updateDisplay() {
        this.mDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
    }

    /*
        If anything is clicked on current activity ------------------------------------------------
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.editText_accountBirthday:
                showDialog(DATE_DIALOG_ID);

                break;

            case R.id.button_cancel:
                Intent newsfeedIntent = new Intent(this, QuestionStreamActivity.class);
                newsfeedIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newsfeedIntent);

                break;

            case R.id.button_save:
                if (!validateInfo()) {
                    return;
                }

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(editText_username.getText().toString())
                        .build();

                HomeActivity.mFirebaseAuth.getCurrentUser().updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    hideProgressDialog();
                                    Toast.makeText(PersonalAccountActivity.this, "User profile updated.", Toast.LENGTH_SHORT).show();

                                    Intent newsfeedIntent = new Intent(PersonalAccountActivity.this, QuestionStreamActivity.class);
                                    newsfeedIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(newsfeedIntent);
                                } else {
                                    Toast.makeText(PersonalAccountActivity.this, "User profile update failed.", Toast.LENGTH_SHORT).show();
                                    hideProgressDialog();
                                }
                            }
                        });

                Gender selectedGender = Gender.MALE;
                MaritalStatus selectedMaritalStatus = MaritalStatus.UNMARRIED;

                if (((RadioButton) findViewById(R.id.radioButton_male)).isChecked())
                    selectedGender = Gender.MALE;
                else if (((RadioButton) findViewById(R.id.radioButton_female)).isChecked())
                    selectedGender = Gender.FEMALE;
                else if (((RadioButton) findViewById(R.id.radioButton_other)).isChecked())
                    selectedGender = Gender.OTHER;

                if (((RadioButton) findViewById(R.id.radioButton_married)).isChecked())
                    selectedMaritalStatus = MaritalStatus.MARRIED;
                else if (((RadioButton) findViewById(R.id.radioButton_unmarried)).isChecked())
                    selectedMaritalStatus = MaritalStatus.UNMARRIED;
                else if (((RadioButton) findViewById(R.id.radioButton_divorced)).isChecked())
                    selectedMaritalStatus = MaritalStatus.DIVORCED;

                updateUser(editText_username.getText().toString(),
                        editText_email.getText().toString(),
                        editText_contactNo.getText().toString(),
                        selectedGender,
                        selectedMaritalStatus,
                        mDay,
                        mMonth,
                        mYear);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean validateInfo() {
        boolean valid = true;

        String username = editText_username.getText().toString();
        if (TextUtils.isEmpty(username)) {
            editText_username.setError("Required.");
            valid = false;
        } else {
            editText_username.setError(null);
        }

        String email = editText_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editText_email.setError("Required.");
            valid = false;
        } else {
            editText_email.setError(null);
        }

        String password = editText_contactNo.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editText_contactNo.setError("Required.");
            valid = false;
        } else {
            editText_contactNo.setError(null);
        }

        return valid;
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading_2));
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
