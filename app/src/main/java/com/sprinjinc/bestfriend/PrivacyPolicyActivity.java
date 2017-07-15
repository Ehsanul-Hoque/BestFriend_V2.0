package com.sprinjinc.bestfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        TextView textView_privacyPolicy = (TextView) findViewById(R.id.textView_privacyPolicy);
        textView_privacyPolicy.setText(Html.fromHtml("<p style=\"line-height: normal;\"><strong><span style=\"font-size: 24.0pt; font-family: 'Times New Roman',serif;\">Privacy Policy</span></strong></p>" +
                "<p style=\"line-height: normal;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend has produced this privacy policy in order to express our vow to treat personal information with respect and caution. The following discloses our information gathering and distribution practices for the mobile app.</span></p>" +
                "<ul>" +
                "<li style=\"color: #1f4e79; line-height: normal; tab-stops: list .5in;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Maintaining Users' Anonymity</span></strong></li>" +
                "</ul>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">For all posting and commenting on Best Friend, the  option will always be to keep the user ID as anonymous. </span></li>" +
                "<li style=\"color: #1f4e79; line-height: normal; tab-stops: list .5in;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Information of Visitor's</span></strong></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">You can visit our app Best Friend at any time without providing any information of your identity or providing information about yourself.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend servers only register or gather the domain name (IP address), browser and session information and not in any circumstances gather email address or personal name of visitors.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend exercises this information to analyze the use of our app to continuously improve its quality, monitor and prevent fraud and abuse, help shaping privileges and provide more location and topic relevance in content, persons and advertisements.</span></li>" +
                "</ul>" +
                "</ul>" +
                "<ul>" +
                "<li style=\"color: #1f4e79; line-height: normal; tab-stops: list .5in;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Gathering of personal information</span></strong></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend gathers or accumulates personal information only when you register with us or actively involve yourself in our services.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend may accumulate information about your dealings with us or with our business partners if and when you use any of our web services.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">For situations where Best Friend needs to communicate with you, Best Friend may contact you to request personal information or feedback from you.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">The Best Friend app's products and services use personal information for the following purposes: products and services provision, identification and authentication, services improvement, contact, research, recommendations, billing and anonymous reporting.</span></li>" +
                "</ul>" +
                "</ul>" +
                "<ul>" +
                "<li style=\"color: #1f4e79; line-height: normal; tab-stops: list .5in;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Security</span></strong></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend is very careful and well aware of the importance of the security of the user's information. That's why all personal information and all user information dwell behind a firewall, with access controlled to, authorized Best Friend personnel only.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend doesn't sell or share this information with our advertisers or our advertising companies. However, Best Friend privacy policy does not cover how internet service providers directing traffic to us deal with information before or after using our services.</span></li>" +
                "</ul>" +
                "</ul>" +
                "<ul>" +
                "<li style=\"color: #1f4e79; line-height: normal; tab-stops: list .5in;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Use of cookies</span></strong></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend tries to give registered customers information as they customized to their personal information. For this Best Friend may use cookies. Cookies are stored on your hard drive with the purpose of letting you be recognized next time you visit our page to make your browsing easier and comfortable.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend uses cookies for a number of purposes, including to: </span></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Asking you to re-enter your password after a session time is out for security reasons.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Keeping track of your settings.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Analyses the size and traffic of all usage.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Improve our products and services.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Ensure that visitors are not shown the same advertisement and to customize advertising based on browser type and user habit information.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Display user profile and related service feeds through profile widget. Best Friend's cookies are not shared with our business partners in no condition or situation.</span></li>" +
                "</ul>" +
                "</ul>" +
                "</ul>" +
                "<ul>" +
                "<li style=\"color: #1f4e79; line-height: normal; tab-stops: list .5in;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Links to other sites</span></strong></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend app may contain links to other websites and is therefore not responsible for the privacy practices or content of those websites.</span></li>" +
                "</ul>" +
                "</ul>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif; color: #1f4e79;\">Disclosure of information:</span></strong></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">When you submit personal information through Best Friend you are sharing your personal information with Best Friend and its affiliated entities, which use is covered by this policy. Though information is not shared with unrelated third parties, Best Friend may display for you targeted advertisements based on personal information and usage habits. Best Friend may use or share your personal information where it is necessary to complete a transaction, to operate or improve the Best Friend products and services, to do something that you have asked us to do, or tell you of products and services that Best Friend thinks may be of your interest.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">If you contribute willingly in any of our sponsor's activities, clearly stating the name of the third party, the information you provide may be shared with them. Best Friend preserve the right to contact you with certain communication related to our products and services, such as service announcements, administrative messages, newsletters and emails without offering you the opportunity to opt-out of receiving them.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend accumulate user-submitted account information such as name, email address, mobile number and present address to identify users and send notifications related to the use of the service.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend also collects user-submitted profile information, including additional contact information, location, biographical information. This additional profile information will assist other users in finding and communicating with you.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend will store your user data in a central profile to be used by Best Friend app and will display your name, if any, along with your submitted content and on your personal pages.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend will display your personal information on your personal pages according to the preferences you set in your account.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend will also allow users to search for you and products produced by you, according to the preferences you set in your account.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">If you post personal information online that is accessible to the public, you may receive unwanted messages from other parties in return.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">If you post personal information of others on Best Friend.com, this content may be deleted and your account terminated.</span></li>" +
                "</ul>" +
                "</ul>" +
                "<ul>" +
                "<li style=\"color: #1f4e79; line-height: normal; tab-stops: list .5in;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend may share personal information about you under the following circumstances:</span></strong></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend may share your personal information to react to written police requests, court orders, or legal process, to establish or exercise our legal rights or, defend against legal claims and if deemed necessary by us to comply with emergency powers or in such circumstances disclosure is appropriate.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend may share your personal information if Best Friend believes it is necessary in order to investigate, prevent, or take action regarding illegal activities, suspected fraud, situations involving potential threats to the physical safety of any person, violations of our various terms of use, or as otherwise required by law.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Should a situation occur where Best Friend will sell all or parts of our business, such transactions, personally identifiable information you have shared with us is generally one of the business assets that will be transferred. In such a situation, the personally identifiable information transferred will remain subject to the promises made in this privacy policy or subsequent policies to which you have consented.</span></li>" +
                "</ul>" +
                "</ul>" +
                "<ul>" +
                "<li style=\"color: #1f4e79; line-height: normal; tab-stops: list .5in;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Internet security</span></strong></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend is not in a position to guarantee a 100% secure data transmission over the internet.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend can't ensure or warrant the security of any information you transmit to us and you do so at your own risk. When Best Friend receives your transmission, Best Friend makes best effort to ensure security on our systems.</span></li>" +
                "</ul>" +
                "</ul>" +
                "<ul>" +
                "<li style=\"color: #1f4e79; line-height: normal; tab-stops: list .5in;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Choice to opt-out of Communication</span></strong></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend provides its visitors with the ability to opt-out of receiving communication from Best Friend at the point where information about the visitor is requested. Best Friend asserts you the option to get personal information changed or deleted from our database.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">This can be done from your profile page in any time. Any issue regarding your privacy concern regarding the use of Best Friend's services will be taken very seriously and you will be contacted upon us receiving your concern.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">In a few cases, honoring such requests may make it difficult for us to provide you a good service and may in worst case require us to terminate our relationship with you.</span></li>" +
                "</ul>" +
                "</ul>" +
                "<ul>" +
                "<li style=\"color: #1f4e79; line-height: normal; tab-stops: list .5in;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Revision and updates</span></strong></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend will regularly revise and update its privacy policy to enhance the quality of the services.</span></li>" +
                "</ul>" +
                "</ul>" +
                "<p style=\"line-height: normal;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif; color: #1f4e79;\">Collecting User Information:</span></strong></p>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">When you download Best Friend App app you will be asked to provide us with your name, email or phone number and allow us access to your mobile device's address book</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">A copy of the phone numbers and names in your address book (but not emails, notes or any other Personal Information in your address book) will only be used to: Request your contacts on your address book to download Best Friend App Android App if they wish to.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">The copy of your address book (names and phones) is stored on a live database. This database does not have a \"historical backup\". If you delete the address book from our servers (more about this below), it will be deleted instantly and permanently.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend App may interact with your phone's SMS inbox history for the sole purpose of sending invites to download the app, reading the contents of the activation SMS sent to you by Best Friend App. WE WILL NOT ACCESS THE CONTENTS OF ANY OTHER SMS.</span></li>" +
                "</ul>" +
                "<p style=\"line-height: normal;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif; color: #1f4e79;\">Analytics Collection</span></strong></p>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend App uses Google Analytics to help us anonymously track and report user/visitor behavior information and users' standard log information to the Site and the App. This information (including but not limited to: IP address, data storage, maintenance services, database management, web analytics and information processing) helps us analyze and evaluate how the App and Site are used as part of our ongoing efforts to improve the Site and App's features and services.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">No personally identifying data is included in this type of reporting. Google Analytics may have access to your information only for the purposes of performing these tasks and on behalf of Best Friend App and under obligation similar in those in this Privacy Policy.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">For more information about the terms that govern Google Analytics, please visit the Google Analytics terms of service.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">We will never (and will not allow any third party to) use the statistical analytics tool to track or to collect any Personal Information of visitors or users. Google will not associate your IP address with any other data held by Google.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Neither we nor Google will link, or seek to link, an IP address with the identity of a device user. We will not associate any data gathered from this site with any Personally Identifiable Information from any source, unless you explicitly submit that information via a fill-in form on our app.</span></li>" +
                "</ul>" +
                "<p style=\"line-height: normal;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif; color: #1f4e79;\">Sharing and Disclosure of Information</span></strong></p>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">We do not rent, sell, or share any information about our users with any third-parties, except as specifically described herein. We may disclose your Personal Information if we believe such action is necessary to: </span></li>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">comply with the law, or legal process served on us;</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">protect and defend our rights or property (including the enforcement of our agreements); or</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list 1.0in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">act in urgent circumstances to protect the personal safety of users of our Service or members of the public.</span></li>" +
                "</ul>" +
                "</ul>" +
                "<p style=\"line-height: normal;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">The following is a description of how we may share your Personal Information with trusted third party partners. Should we add additional partners, we will immediately update this description:</span></p>" +
                "<ul>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">We use third party companies in order to send you an authentication SMS when you register with Best Friend App. In order to send you the SMS, we send one of these third party companies your phone number and the authentication message. These third parties are contractually obligated to only use your phone number for the transmission of the authentication SMS.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">If we fail to send you an SMS (this happens in certain cases) you may ask us resend the sms code on the phone.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">We may use Push Notification Services to notify you about various events related to the app. If we do, the phone number or the name of the person calling you or sending you a message may be part of the notification. This message is sent to the push notification service provider, for delivery to your device.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">We may disclose information about you if we determine that for national security, law enforcement, or other issues of public importance that disclosure of information is necessary.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">We may from time to time ask you to provide information about your experiences, which will be used to measure and improve quality. You are at no time under any obligation to provide any of such data. Any and all information which is voluntarily submitted in feedback forms on the Site or any survey that you accept to take part in is used for the purposes of reviewing this feedback and improving the Best Friend App software &amp; products.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">We may contact you to send you messages about faults and service issues. Furthermore, we reserve the right to use email, the Best Friend App software or SMS to notify you of any eventual claims related to your use of our software, and/or products, including without limitation claims of violation of third party rights.</span></li>" +
                "<li style=\"line-height: normal; tab-stops: list .5in;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">We may send you alerts via the Best Friend App software to notify you when someone has tried to contact you. We may also use the Best Friend App software to keep you up to date with news about our software and products that you have purchased and/or to inform you about other Best Friend App products and related information.</span></li>" +
                "</ul>" +
                "<p style=\"line-height: normal;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif; color: #1f4e79;\">Children's Privacy</span></strong></p>" +
                "<p style=\"line-height: normal;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">Best Friend App  clients are not intended for or designed to attract anyone under the age of 13 and we do not intentionally or knowingly collect Personal Information on our sites from anyone under the age of 13 (or older in some jurisdictions). We encourage parents to be involved in the online activities of their children to ensure that no information is collected from a child without parental permission.</span></p>" +
                "<p style=\"line-height: normal;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif; color: #1f4e79;\">Security</span></strong></p>" +
                "<p style=\"line-height: normal;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">We take reasonable precaution to protect Personal Information from misuse, loss and unauthorized access. Although we cannot guarantee that Personal Information will not be subject to unauthorized access, we have physical, electronic, and procedural safeguards in place to protect Personal Information. Personal Information is stored on our servers and protected by secured networks to which access is limited to a few authorized employees and personnel. However, no method of transmission over the Internet, or method of electronic storage, is 100% secure.</span></p>" +
                "<p style=\"line-height: normal;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif; color: #1f4e79;\">Changes to Policy</span></strong></p>" +
                "<p style=\"line-height: normal;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">From time to time, we may revise this Policy. We reserve the right to update or modify this Policy, or any other of our policies or practices, at any time with or without notice. However, we will not use your Personal Information in a way that is materially different than the uses described in this Policy without giving you an opportunity to opt-out of such differing uses. We will post the revised Policy on the Site, so that users can always be aware of what information we collect, how the information is used and under what circumstances such information may be disclosed. You agree to review the Policy periodically so that you are aware of any modifications. Your continued use of the Site indicates your assent to any changes and signifies your agreement to the terms of our Policy. If you do not agree with these terms, you should not use the Site, the Best Friend App App, or any other Service.</span></p>" +
                "<p style=\"line-height: normal;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif; color: #1f4e79;\">Change or Removal of Information</span></strong></p>" +
                "<p style=\"line-height: normal;\"><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif;\">If you provided your e-mail address to us and wish to update it, you may do so by sending an e-mail to ehsanfahad01@gmail.com .If you have additional concerns and/or requests, you can contact Best Friend App team at ehsanfahad01@gmail.com</span></p>" +
                "<p style=\"line-height: normal;\"><strong><span style=\"font-size: 12.0pt; font-family: 'Times New Roman',serif; color: #1f4e79;\">Contact Information</span></strong></p>" +
                "<p><span style=\"font-size: 12.0pt; line-height: 107%; font-family: 'Times New Roman',serif;\">Please direct all questions in connection with this Policy via e-mail to ehsanfahad01@gmail.com</span></p>" +
                "    "));
    }
}
