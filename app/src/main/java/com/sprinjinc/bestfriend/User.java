package com.sprinjinc.bestfriend;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Calendar;

/**
 * Created by Md. Ehsanul Hoque on 7/2/2017.
 */

@IgnoreExtraProperties
class User {

    private int birth_date = 1;
    private int birth_month = 1;
    private int birth_year = 1990;
    private String username = "";
    private String email = "";
    private String contactNo = "";
    private Gender gender = Gender.MALE;
    private MaritalStatus maritalStatus = MaritalStatus.UNMARRIED;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    User(String username, String email) {
        this.username = username;
        this.email = email;
        this.contactNo = "+xxxxxxxxxxxxx";
        this.gender = Gender.MALE;
        this.maritalStatus = MaritalStatus.UNMARRIED;
        /*this.birth_date = 1;
        this.birth_month = 1;
        this.birth_year = 1990;*/

        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2000);

        this.birth_year = c.get(Calendar.YEAR);
        this.birth_month = c.get(Calendar.MONTH);
        this.birth_date = c.get(Calendar.DAY_OF_MONTH);
    }

    User(String username, String email, String contactNo, Gender gender, MaritalStatus maritalStatus, int birth_date, int birth_month, int birth_year) {
        this.username = username;
        this.email = email;
        this.contactNo = contactNo;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.birth_date = birth_date;
        this.birth_month = birth_month;
        this.birth_year = birth_year;
    }

    int getBirth_date() {
        return birth_date;
    }

    int getBirth_month() {
        return birth_month;
    }

    int getBirth_year() {
        return birth_year;
    }

    String getUsername() {
        return username;
    }

    String getEmail() {
        return email;
    }

    String getContactNo() {
        return contactNo;
    }

    Gender getGender() {
        return gender;
    }

    MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }
}
