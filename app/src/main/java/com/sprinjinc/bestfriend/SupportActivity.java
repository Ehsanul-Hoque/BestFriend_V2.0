package com.sprinjinc.bestfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        TextView textView_support = (TextView) findViewById(R.id.textView_support);
        textView_support.setText(Html.fromHtml("<h3>Support<br /> </h3>" +
                "<p><strong><span style=\"font-family: 'Calibri',sans-serif;\">If you have any query or you need any help to use this app, feel free to mail our support team 24/7<br /> Email: <br /> </span></strong><a href=\"mailto:ehsanfahad01@gmail.com\">ehsanfahad01@gmail.com</a></p>" +
                "<p><a href=\"mailto:shawon.scientist@gmail.com\">shawon.scientist@gmail.com</a></p>"));
    }
}
