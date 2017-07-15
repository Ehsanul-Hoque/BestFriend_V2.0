package com.sprinjinc.bestfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class TermsOfServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_services);

        TextView textView_termsOfServices = (TextView) findViewById(R.id.textView_termsOfServices);
        textView_termsOfServices.setText(Html.fromHtml("<h1>Terms of Services</h1>" +
                "<p>Welcome to Best Friend App. By accessing Best Friend mobile app and its related properties (e.g. Best Friend App app, Social Media properties) you are agreeing to the following terms, which are designed to make sure that Best Friend App works for everyone.</p>" +
                "<p> </p>" +
                "<p><strong><span style=\"color: #1f4e79;\">Basic Terms</span></strong></p>" +
                "<p>By law you will be responsible for any or all activity that take place and content that is submitted in Best Friend App (or other owned and operated properties) under your registered user name or display name with the app.</p>" +
                "<ol>" +
                "<li style=\"margin-left: 1.5in; text-indent: -.25in;\">It's your responsibility to keep your password safe and sound.</li>" +
                "<li style=\"margin-left: 1.5in; text-indent: -.25in;\">In no condition or situation you can discriminate, abuse, harass, threaten, impersonate or intimidate others in the user community, public figures or companies.</li>" +
                "<li style=\"margin-left: 1.5in; text-indent: -.25in;\">No services of Best Friend is to use for any unlawful or unconstitutional purpose &amp; should guarantee that the service does not go against national security and/or national significance of Bangladesh and if you do then you shall solely accept the charges arising out of this end if any.</li>" +
                "<li style=\"margin-left: 1.5in; text-indent: -.25in;\">You are the only one to be held responsible for your behavior and any data, text, information, screen names, graphics, photos, profiles, audio and video clips, copyright of the content, links, that you present, post, and display on these services.</li>" +
                "<li style=\"margin-left: 1.5in; text-indent: -.25in;\">You will not give out or include spam, chain letters, or pyramid schemes, or any code of a destructive nature like viruses or any other technologies that may harm Best Friend or the interests or property of Best Friend users.</li>" +
                "<li style=\"margin-left: 1.5in; text-indent: -.25in;\">You must not compel an irrational load on our infrastructure or server with the proper working of Best Friend.</li>" +
                "<li style=\"margin-left: 1.5in; text-indent: -.25in;\">You must not copy, modify, or distribute information gathered through Best Friend services to generate or submit redundant email to anyone.</li>" +
                "<li style=\"margin-left: 1.5in; text-indent: -.25in;\">In using of Best Friend services you must not violate any laws in your authority (including but not limited to copyright laws).Violating of any of the above agreements may cause for subject to termination of Best Friend user account or legal action accordance on the sternness of violation. while Best Friend forbids intolerable demeanor and content on the site, you recognize and agree that Best Friend cannot be and will not be responsible or liable for the user generated content posted on its app and you nevertheless may be bare to such materials and that you use the these services at your own risk and with full consent.</li>" +
                "</ol>" +
                "<p> </p>" +
                "<p><strong><span style=\"color: #1f4e79;\">General Conditions</span></strong></p>" +
                "<ol>" +
                "<li>Best Friend holds the right to alter or terminate any of its service at any time for any reason without any prior notice.</li>" +
                "<li>All Best Friend services are generally free, but we may charge a fee for certain services. If any service you use is charged with fee, you'll be able to review and accept terms that will be clearly disclosed at that time.</li>" +
                "<li>If Best Friend alter these terms of service and if the modifications comprise a material change to the terms of use, Best Friend will inform you via e-mail which is given preference expressed on your account at the site. What constitutes a \"material change\" will be resolute at Best Friend's sole discretion, with good faith and using common sense and reasonable findings.</li>" +
                "<li>Best Friend holds the right to decline service to anyone for any reason at any time.</li>" +
                "<li>Best Friend may but have no obligation to, remove content and accounts containing content that we decide in our sole judgment are illegal, odious, intimidating, defamatory, obscene or otherwise objectionable or violates any other user's or any third party's intellectual property or these terms of service.</li>" +
                "<li>Best Friend admits and supports posting of content from Best Friend services to be displayed on external websites. However, pages on other websites which display content hosted on Best Friend server must quote appropriate source and provide a link to it.</li>" +
                "</ol>" +
                "<p> </p>" +
                "<p><strong><span style=\"color: #1f4e79;\">Copyright</span></strong></p>" +
                "<p>Best Friend holds or claims no intellectual property rights or copyrights over the material you provide to any of Best Friend's service. Your registered profile and materials uploaded in your profile remain yours. You have the right to remove your profile at any time by deleting or disabling your account in the site. This will also eliminate any text and images you have stored in the system of Best Friend. Best Friend heartens users to contribute and share their creations, news, views, and opinions with the Best Friend user community through its services. Best Friend embark on to obey all appropriate copyright laws. Best Friend will review all claims of copyright breach received and remove the content deemed to have been posted or distributed in violation of any such laws if it finds the objection valid. In order to make a claim, please provide us with the following:</p>" +
                "<ol>" +
                "<li>a physical or electronic signature of the copyright owner or the person authorized to act on the owner's behalf with the proper consent of the owner;</li>" +
                "<li>a narrative of the copyrighted work claimed to have been infringed;</li>" +
                "<li>a description of the infringing material and information logically sufficient to permit Best Friend to trace the material;</li>" +
                "<li>your full contact information, including your present address, telephone number, and a valid email address;</li>" +
                "<li>a testimonial by you that you, to the best of your awareness, believe that use of the material in the manner complained of is not certified by the copyright owner, its agent, or the law; and</li>" +
                "<li>a statement that the information in the notice is accurate, and, under the pains and penalties of false swearing, that you are sanctioned to act on behalf of the copyright owner.</li>" +
                "</ol>" +
                "<p> </p>" +
                "<p><strong><span style=\"color: #1f4e79;\">Resolution of Disputes</span></strong></p>" +
                "<p>If a disagreement occurs between you and Best Friend, we strongly recommend you to first contact us directly to seek a resolution by going to the Best Friend contact page. We will consider rational requests to resolve the dispute through alternative dispute resolution procedures, such as mediation or arbitration, as alternatives to litigation</p>"));
    }
}
