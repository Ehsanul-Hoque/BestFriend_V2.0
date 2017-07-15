package com.sprinjinc.bestfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView textView_aboutUs = (TextView) findViewById(R.id.textView_aboutUs);
        textView_aboutUs.setText(Html.fromHtml("<p style=\"line-height: normal;\"><strong><span style=\"font-size: 24.0pt; font-family: 'Times New Roman',serif;\">About Us</span></strong></p>" +
                "<p style=\"margin-bottom: 12.0pt; line-height: normal;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\"> </span></p>" +
                "<p style=\"line-height: normal;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">&ldquo;Best Friend App&rdquo; is a Bangladesh University of Engineering and Technology- BUET based Android app project developed for competing in </span><strong>IEEEmadC Mobile Applications Development </strong><strong>Contest</strong><strong><span style=\"font-size: 12.0pt;\"> 2017</span></strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\"> ( </span><a href=\"http://ieeemadc.org\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">http://ieeemadc.org</span></a><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\"> ).Our developing team went to world renowned NGO, BRAC, through its Gender Justice and Diversity department, to further develop the platform and reach more users in future. Since the competition has started, some user queries and submitted the Android app to <strong>I</strong></span><strong>EEEmadC Mobile Applications Development Contest</strong><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\"> 2017</span></strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\"> Authority (</span><a href=\"http://ieeemadc.org\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">http://ieeemadc.org</span></a><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\"> ) <br /> <br /> Our Android App has been committed to reinventing the way people in developing countries access expert advice and share knowledge. By eliminating the social, financial and geographic barriers to accessing expert advice on health, psychosocial and legal issues; Best Friend app developing team aims to be everyone's personal digital wellbeing assistant. <br /> <br /> Best Friend app is re-inventing the way people in developing countries access vetted expert advice. </span></p>" +
                "<p style=\"line-height: normal;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend is a knowledge sharing/messaging platform available on Android. <br /> <br /> Our team currently encompassing a wide range of topics, primarily; Public Health, Psycho-social and Legal. <br /> In countries like Bangladesh, where there are many barriers to accessing reliable advice (particularly for women and teenagers), our service is easy-to-use and very user friendly. Both men and women can ask questions in English, Bangla,Hindi,Arabic,Spanish,French, Banglish and or Voice. Questions are then routed to a vetted network of experts (doctors, therapists etc.) through a real-time, uber-like backend. </span></p>"));
    }
}
